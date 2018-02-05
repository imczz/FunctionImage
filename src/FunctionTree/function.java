package FunctionTree;
import java.util.ArrayList;
import java.util.Scanner;

import CompilationPrinciple.expsubroutine;
import CompilationPrinciple.lexanal;
import CompilationPrinciple.token;
import CompilationPrinciple.tokenlist;
//函数，继承自语法分析，覆盖为语义分析器，生成tree_node语法树
public class function extends expsubroutine{
	private String funcname;							//函数名
	private String sourceexp;							//来自表达式
	private ArrayList<paramtablenode> paramtable;		//参数列表(自定义数据结构)
	private tree_node root;								//函数体
	ArrayList<tree_node> ss;		//stack
	ArrayList<Character> ss2;		//a+- b*/%的符号栈
	ArrayList<String> ssn;			//name栈
	tree_node tmp;						//tree_node 临时”指针“变量
	token tmptk;						//token 临时”指针“变量
	tokenlist tklist;					//tokenlist指针
	int last;							//有的时候递归子程序是空产生式
	private static int selfstart;					//内置函数的界限
	private static int fselected=-1;					//被选定的函数（控制台某些功能使用）
	private static int fselected2=-1;					//被选定的函数（控制台参数方程专用）
	private static ArrayList<function> func;		//函数表
	public ArrayList<Integer> independent;			//自变量列表（从参数列表中选取）
	private int param_num;							//当前变量个数（也就是说参数列表中不全是自变量）
	
	public function(String name, tokenlist tks) {			//构造方法
		super("fun_exp", tks);			//文法的名字是 fun_exp
		setSelfstart(25);					//全局function表25号及之后为自定义函数
		setFuncname(name);
		tklist=tks;
		ArrayList<token> tmptk10=tklist.getTk();
		if(getFunc()==null) setFunc(new ArrayList<function>());
		tmptk=tmptk10.get(tmptk10.size()-1);
		if(tmptk.getIndex1()!=5 || tmptk.getIndex2()!=35){
			tmptk10.add(new token(5,35,"#"));
		}
		setParamtable(new ArrayList<paramtablenode>());
		ss=new ArrayList<tree_node>();
		ss2 = new ArrayList<Character>();
		ssn = new ArrayList<String>();
		setParam_num(0);
		setIndependent(new ArrayList<Integer>());
	}

	public function(function afunc){		//复制构造函数（方法）,便于反复调用
		setFuncname(afunc.getName());
		tklist=afunc.tklist;
		super.setname("fun_exp");
		ArrayList<token> tmptk10=tklist.getTk();
		if(getFunc()==null) setFunc(new ArrayList<function>());
		tmptk=tmptk10.get(tmptk10.size()-1);
		if(tmptk.getIndex1()!=5 || tmptk.getIndex2()!=35){
			tmptk10.add(new token(5,35,"#"));
		}
		setParamtable(new ArrayList<paramtablenode>(afunc.getParamtable()));		//复制参数表（名称对应的引用位置被破坏）
		ss=new ArrayList<tree_node>();
		ss2 = new ArrayList<Character>();
		ssn = new ArrayList<String>();
		setParam_num(afunc.getParam_num());					//返回新增
		setRoot(new tree_node(afunc.getRoot()));				//递归复制语义树
		setIndependent(new ArrayList<Integer>(afunc.getIndependent()));				//独立的自变量编号列表
		getRoot().belong(this);
	}
	
	public void setsource(String s){					//设置来源表达式
		setSourceexp(s);
	}
	
	public String retsource(){							//返回来源表达式
		return getSourceexp();
	}
	
	public static int ifexistfunc(String name){			//全局函数表是否存在函数（static）
		for(int i=0;i<getFunc().size();i++){
			if(getFunc().get(i).getFuncname().equals(name)) return i;		//存在
		}
		return -1;		//不存在
	}
	
	public static int addfunc(String name,tokenlist tks){		//全局函数表增加函数（static）
		function tmpfunc;
		int funcindex=ifexistfunc(name);
		if(funcindex!=-1)return -funcindex;				//已经存在
		tmpfunc = new function(name,tks);
		getFunc().add(tmpfunc);
		return (getFunc().size()-1);							//返回新增
	}
	
	public static int addfunc(function afunc){		//全局函数表增加函数（static）
		int funcindex=ifexistfunc(afunc.getFuncname());
		if(funcindex!=-1)return -funcindex;				//已经存在
		getFunc().add(afunc);
		return (getFunc().size()-1);							//返回新增
	}
	
	public static int editfunc(int index, String newname, ArrayList<Integer> plist){//编辑函数（未使用）（static）
		if(index<0|| index>=getFunc().size()) return -1;			//不存在的编号
		if(ifexistfunc(newname)!=-1) return -2;				//重名
		if(newname!="") getFunc().get(index).setFuncname(newname);
		if(plist==null) return 0;							//仅仅改个名
		if(plist.size()> getFunc().get(index).getParamtable().size()) return -3;		//不正常的列表
		getFunc().get(index).setIndependent(plist);
		getFunc().get(index).setParam_num(plist.size());
		return 1;
	}
	
	public static tree_node runfunc(int index,ArrayList<tree_node> plist){ 	//调用函数（static）
		tree_node tmptn=new tree_node();					//保存结果
		tmptn.setIndexoffunction(0);
		if(index<0 && index>=getFunc().size()) return null;		//不存在的函数
		function tmpfunc=new function(getFunc().get(index));		//复制一个函数
		if(tmpfunc.calculate(plist)==1){				//根据列表plist装载函数并计算
			tmptn.setIfnumbered(true);
			tmptn.setIntnum(tmpfunc.getRoot().getIntnum());
			tmptn.setFloatnum(tmpfunc.getRoot().getFloatnum());
			return tmptn;								//返回计算结果
		}
		return null;
	}
	
	public int addparam(String name, tree_node tn){			//参数列表增加内容
		int flag1=0;
		int i;
		for(i=0;i<getParamtable().size();i++){
			if(getParamtable().get(i).getName()==name){				//已经存在的参数名
				flag1=1;
				break;
			}
		}
		if(flag1==1){
			getParamtable().get(i).list.add(tn);					//记录被引用的位置
			//return i;
		}
		else{
			getParamtable().add(new paramtablenode());			//新增节点
			getParamtable().get(i).setName(name);
			getParamtable().get(i).list=new ArrayList<tree_node>();
			getParamtable().get(i).list.add(tn);					//记录被引用的位置
		}
		return i;
	}
	
	private int sub_E(){							//E->T{,T GEQ(,)}	 //GEQ(+)生成,节点 栈顶2各元素装入,的参数表中
		if (i < tklist.getTk().size()){
			if (sub_T()==0) return 0;
			tmptk=tklist.getTk().get(i);
			while (tmptk.getIndex1()==5 && tmptk.getIndex2()==32){
				i++;
				if (sub_T()==0) return 0;
				tmp=new tree_node(',');				//新增节点
				tmp.parameter.add(ss.get(ss.size()-2));			//构造节点
				tmp.parameter.add(ss.get(ss.size()-1));
				ss.remove(ss.size()-1);				//弹出栈顶两个元素
				ss.remove(ss.size()-1);
				ss.add(tmp);						//,入栈
			}
			return 1;
		}
		return 0;
	}
	
	private int sub_T(){						//T->F{aF GEQ(+|-)}
		if (i < tklist.getTk().size()){
			if (sub_F()==0) return 0;
			tmptk=tklist.getTk().get(i);
			while (tmptk.getIndex1()==5 &&(tmptk.getIndex2()==14 || tmptk.getIndex2()==16)){
				if(tmptk.getIndex2()==14) ss2.add('+');				//具体是+还是-需要入符号栈
				else ss2.add('-');
				i++;
				if (sub_F()==0) return 0;
				tmp=new tree_node(ss2.get(ss2.size()-1));
				ss2.remove(ss2.size()-1);
				tmp.parameter.add(ss.get(ss.size()-2));
				tmp.parameter.add(ss.get(ss.size()-1));
				ss.remove(ss.size()-1);
				ss.remove(ss.size()-1);
				ss.add(tmp);
			}
			return 1;
		}
		return 0;
		
	}
	
	private int sub_F(){						//F->G{bG GEQ(*|/|%)}
		if (i < tklist.getTk().size()){
			if (sub_G()==0) return 0;
			tmptk=tklist.getTk().get(i);
			while (tmptk.getIndex1()==5 &&(tmptk.getIndex2()==21 || tmptk.getIndex2()==22 || tmptk.getIndex2()==23)){
				if(tmptk.getIndex2()==21) ss2.add('*');		//具体是*/还是%需要入符号栈
				else if(tmptk.getIndex2()==22) ss2.add('/');
				else ss2.add('%');
				i++;
				if (sub_G()==0) return 0;
				tmp=new tree_node(ss2.get(ss2.size()-1));
				ss2.remove(ss2.size()-1);
				tmp.parameter.add(ss.get(ss.size()-2));
				tmp.parameter.add(ss.get(ss.size()-1));
				ss.remove(ss.size()-1);
				ss.remove(ss.size()-1);
				ss.add(tmp);
			}
			return 1;
		}
		return 0;
	}
	
	private int sub_G(){						//G->H{^H GEQ(^)}
		if (i < tklist.getTk().size()){
			if (sub_H()==0) return 0;
			tmptk=tklist.getTk().get(i);
			while (tmptk.getIndex1()==5 &&tmptk.getIndex2()==24){
				i++;
				if (sub_H()==0) return 0;
				tmp=new tree_node('^');
				tmp.parameter.add(ss.get(ss.size()-2));
				tmp.parameter.add(ss.get(ss.size()-1));
				ss.remove(ss.size()-1);
				ss.remove(ss.size()-1);
				ss.add(tmp);
			}
			return 1;
		}
		return 0;
	}
	
	private int sub_H(){					//H->i PUSH(i) | (E) | d PUSH(d,name) K POP(d,name)
		int funcindex;
		String tmpstr;
		int ii;
		tree_node tmp2;
		if (i < tklist.getTk().size()){
			tmptk=tklist.getTk().get(i);
			if (tmptk.getIndex1()==3){
				tmp=new tree_node(tmptk);
				ss.add(tmp);
				i++;
			}
			else if (tmptk.getIndex1()==5 &&tmptk.getIndex2()==25){
				i++;
				if (sub_E()==0) return 0;
				if (tmptk.getIndex1()!=5 || tmptk.getIndex2()!=26) return 0;
				i++;
				
			}
			else if(tmptk.getIndex1()==0 || tmptk.getIndex1()==4 ){				//dK
				tmp=new tree_node(tmptk);
				ssn.add(tmptk.getStr());					//name先存起来
				ss.add(tmp);						//入栈
				i++;
				last=sub_K();						//K
				if (last==0) return 0;
				else{
					if(last==-1){					//空产生式
						tmp = ss.get(ss.size()-1);
						tmp.setPtnum(addparam(ssn.get(ssn.size()-1), tmp));		//新增参数
						tmp.ptn=getParamtable().get(tmp.getPtnum());
						ssn.remove(ssn.size()-1);
					}
					else if(last==1){				//(E)
						tmpstr=ssn.get(ssn.size()-1);
						funcindex=ifexistfunc(tmpstr);			//查询属于哪个函数
						ssn.remove(ssn.size()-1);
						if(funcindex==-1) return -4;			//不存在的函数
						else{
							tmp=ss.get(ss.size()-2);
							tmp.setIndexoffunction(funcindex);		//装载函数
							tmp.setParam_num(getFunc().get(funcindex).getParam_num());		//参数个数
							if(tmp.getParam_num()==1){
								tmp.parameter.add(ss.get(ss.size()-1));			//只有一个参数
							}
							else if(tmp.getParam_num()>1){							//多个参数
								ii=ss.get(ss.size()-2).getParam_num();
								tmp2=ss.get(ss.size()-1);
								while(ii>0){								//展开,节点循环装入函数节点参数表
									if(tmp2.getIndexoffunction()==1){
										tmp.parameter.add(0, tmp2.parameter.get(1));
										tmp2=tmp2.parameter.get(0);
									}
									else{
										tmp.parameter.add(0, tmp2);
										ii--;
										break;
									}
									ii--;
								}
								if(ii!=0) return -5;
							}
							else{
								return -5;					//不允许的参数个数
							}
							ss.remove(ss.size()-1);							//去掉栈顶的函数的参数
						}	
					}
				}
			}
			else{					//表达式不正确
				return 0;
			}
			return 1;
		}
		return 0;
	}
	
	private int sub_K(){				//K->(E)|空
		if (i < tklist.getTk().size()){
			tmptk=tklist.getTk().get(i);
			if (tmptk.getIndex1()==5 && tmptk.getIndex2()==25){
				i++;
				if (sub_E()==0) return 0;
				if (tmptk.getIndex1()!=5 || tmptk.getIndex2()!=26) return 0;
				i++;
				return 1;
			}
			return -1;
		}
		return 0;
	}
	
	public boolean start() {				//开始语义分析，覆盖父类语法分析
		i=0;
		sub_E();
		if(buffer.charAt(i)=='#'){			//分析成功
			setRoot(ss.get(0));					//栈顶元素指针放root
			setParam_num(getParamtable().size());	//函数参数列表
			for(int ii=0;ii<getParam_num();ii++)	getIndependent().add(ii);		//自变量表
			ss.remove(0);					//清空栈
			return true;
		}
		else return false;
	}
	
	public int calculate() {				//由根节点递归求值
		if(calculatenode(getRoot())==1){
			return 1;		//曾经求值
		}
		else{
			return 0;
		}
	}
	
	public int calculatenode(tree_node tn) {			//由当前节点递归求值
		//int i,n;
		if(tn.isIfnumbered()) return 1;				//曾经求值
		else{
			if(tn.getIndexoffunction()>0){			//函数
				if(tn.getIndexoffunction()==1) return -50;		//,错误
				else if(tn.getIndexoffunction()==2){				//+
					if(!tn.parameter.get(0).isIfnumbered()) calculatenode(tn.parameter.get(0));
					if(!tn.parameter.get(1).isIfnumbered()) calculatenode(tn.parameter.get(1));
					if(tn.parameter.get(0).getFloatnum()==0 && tn.parameter.get(1).getFloatnum()==0){
						tn.setFloatnum(0);
						tn.setIntnum(tn.parameter.get(0).getIntnum()+tn.parameter.get(1).getIntnum());
					}
					else{
						tn.setIntnum(0);
						tn.setFloatnum(tn.parameter.get(0).retfn()+tn.parameter.get(1).retfn());
					}
				}
				else if(tn.getIndexoffunction()==3){				//-
					if(!tn.parameter.get(0).isIfnumbered()) calculatenode(tn.parameter.get(0));
					if(!tn.parameter.get(1).isIfnumbered()) calculatenode(tn.parameter.get(1));
					if(tn.parameter.get(0).getFloatnum()==0 && tn.parameter.get(1).getFloatnum()==0){
						tn.setFloatnum(0);
						tn.setIntnum(tn.parameter.get(0).getIntnum()-tn.parameter.get(1).getIntnum());
					}
					else{
						tn.setIntnum(0);
						tn.setFloatnum(tn.parameter.get(0).retfn()-tn.parameter.get(1).retfn());
					}
				}
				else if(tn.getIndexoffunction()==4){				//*
					if(!tn.parameter.get(0).isIfnumbered()) calculatenode(tn.parameter.get(0));
					if(!tn.parameter.get(1).isIfnumbered()) calculatenode(tn.parameter.get(1));
					if(tn.parameter.get(0).getFloatnum()==0 && tn.parameter.get(1).getFloatnum()==0){
						tn.setFloatnum(0);
						tn.setIntnum(tn.parameter.get(0).getIntnum()*tn.parameter.get(1).getIntnum());
					}
					else{
						tn.setIntnum(0);
						tn.setFloatnum(tn.parameter.get(0).retfn()*tn.parameter.get(1).retfn());
					}
				}
				else if(tn.getIndexoffunction()==5){				///
					if(!tn.parameter.get(0).isIfnumbered()) calculatenode(tn.parameter.get(0));
					if(!tn.parameter.get(1).isIfnumbered()) calculatenode(tn.parameter.get(1));
					if(tn.parameter.get(0).getFloatnum()==0 && tn.parameter.get(1).getFloatnum()==0){		//整数相除
						if(tn.parameter.get(1).getIntnum()==0){			//除数为零
							tn.setFloatnum(tn.parameter.get(0).retfn()/0);
							tn.setIntnum(0);
						}
						else{
							tn.setFloatnum(tn.parameter.get(0).getIntnum() % tn.parameter.get(1).getIntnum());
							if(tn.getFloatnum()==0){
								tn.setIntnum(tn.parameter.get(0).getIntnum()/tn.parameter.get(1).getIntnum());
							}
							else
							{
								tn.setFloatnum(tn.parameter.get(0).retfn()/tn.parameter.get(1).retfn());
								tn.setIntnum(0);
							}
						}
					}
					else{
						tn.setIntnum(0);
						tn.setFloatnum(tn.parameter.get(0).retfn()/tn.parameter.get(1).retfn());
					}
				}
				else if(tn.getIndexoffunction()==6){			//取余
					if(!tn.parameter.get(0).isIfnumbered()) calculatenode(tn.parameter.get(0));
					if(!tn.parameter.get(1).isIfnumbered()) calculatenode(tn.parameter.get(1));
					if(tn.parameter.get(0).getFloatnum()==0 && tn.parameter.get(1).getFloatnum()==0){
						tn.setFloatnum(0);
						tn.setIntnum(tn.parameter.get(0).getIntnum() % tn.parameter.get(1).getIntnum());
					}
					else{
						return -2;					//数值应该为整数
					}
				}
				else if(tn.getIndexoffunction()==7){			//^
					if(!tn.parameter.get(0).isIfnumbered()) calculatenode(tn.parameter.get(0));
					if(!tn.parameter.get(1).isIfnumbered()) calculatenode(tn.parameter.get(1));
					if(tn.parameter.get(0).getFloatnum()==0 && tn.parameter.get(1).getFloatnum()==0){
						tn.setFloatnum(0);
						tn.setIntnum((long) Math.pow(tn.parameter.get(0).getIntnum() , tn.parameter.get(1).getIntnum()));
					}
					else{
						tn.setIntnum(0);
						tn.setFloatnum(Math.pow(tn.parameter.get(0).retfn(), tn.parameter.get(1).retfn()));
					}
				}
				else if(tn.getIndexoffunction()==8){			//sin
					if(!tn.parameter.get(0).isIfnumbered()) calculatenode(tn.parameter.get(0));
					tn.setFloatnum(Math.sin(tn.parameter.get(0).retfn()));
				}
				else if(tn.getIndexoffunction()==9){			//cos
					if(!tn.parameter.get(0).isIfnumbered()) calculatenode(tn.parameter.get(0));
					tn.setFloatnum(Math.cos(tn.parameter.get(0).retfn()));
				}
				else if(tn.getIndexoffunction()==10){		//tan
					if(!tn.parameter.get(0).isIfnumbered()) calculatenode(tn.parameter.get(0));
					tn.setFloatnum(Math.tan(tn.parameter.get(0).retfn()));
				}
				else if(tn.getIndexoffunction()==11){		//arcsin
					if(!tn.parameter.get(0).isIfnumbered()) calculatenode(tn.parameter.get(0));
					tn.setFloatnum(Math.asin(tn.parameter.get(0).retfn()));
				}
				else if(tn.getIndexoffunction()==12){		//arccos
					if(!tn.parameter.get(0).isIfnumbered()) calculatenode(tn.parameter.get(0));
					tn.setFloatnum(Math.acos(tn.parameter.get(0).retfn()));
				}
				else if(tn.getIndexoffunction()==13){		//arctan
					if(!tn.parameter.get(0).isIfnumbered()) calculatenode(tn.parameter.get(0));
					tn.setFloatnum(Math.atan(tn.parameter.get(0).retfn()));
				}
				else if(tn.getIndexoffunction()==14){		//log 10 x
					if(!tn.parameter.get(0).isIfnumbered()) calculatenode(tn.parameter.get(0));
					tn.setFloatnum(Math.log10(tn.parameter.get(0).retfn()));
				}
				else if(tn.getIndexoffunction()==15){		//log e x
					if(!tn.parameter.get(0).isIfnumbered()) calculatenode(tn.parameter.get(0));
					tn.setFloatnum(Math.log(tn.parameter.get(0).retfn()));
				}
				else if(tn.getIndexoffunction()==16){		//log x y
					if(!tn.parameter.get(0).isIfnumbered()) calculatenode(tn.parameter.get(0));
					if(!tn.parameter.get(1).isIfnumbered()) calculatenode(tn.parameter.get(1));
					tn.setFloatnum(Math.log(tn.parameter.get(1).retfn()) / Math.log(tn.parameter.get(0).retfn()));
				}
				else if(tn.getIndexoffunction()==17){		//开平方
					if(!tn.parameter.get(0).isIfnumbered()) calculatenode(tn.parameter.get(0));
					tn.setFloatnum(Math.sqrt(tn.parameter.get(0).retfn()));
				}
				else if(tn.getIndexoffunction()==18){		//乘方
					if(!tn.parameter.get(0).isIfnumbered()) calculatenode(tn.parameter.get(0));
					if(!tn.parameter.get(1).isIfnumbered()) calculatenode(tn.parameter.get(1));
					if(tn.parameter.get(0).getFloatnum()==0 && tn.parameter.get(1).getFloatnum()==0){
						tn.setFloatnum(0);
						tn.setIntnum((long) Math.pow(tn.parameter.get(0).getIntnum() , tn.parameter.get(1).getIntnum()));
					}
					else{
						tn.setIntnum(0);
						tn.setFloatnum(Math.pow(tn.parameter.get(0).retfn(), tn.parameter.get(1).retfn()));
					}
				}
				else if(tn.getIndexoffunction()==19){		//sinh双曲正弦
					if(!tn.parameter.get(0).isIfnumbered()) calculatenode(tn.parameter.get(0));
					tn.setFloatnum(Math.sinh(tn.parameter.get(0).retfn()));
				}
				else if(tn.getIndexoffunction()==20){		//cosh
					if(!tn.parameter.get(0).isIfnumbered()) calculatenode(tn.parameter.get(0));
					tn.setFloatnum(Math.cosh(tn.parameter.get(0).retfn()));
				}
				else if(tn.getIndexoffunction()==21){		//tanh
					if(!tn.parameter.get(0).isIfnumbered()) calculatenode(tn.parameter.get(0));
					tn.setFloatnum(Math.tanh(tn.parameter.get(0).retfn()));
				}
				else if(tn.getIndexoffunction()==22){		//随机数
					if(!tn.parameter.get(0).isIfnumbered()) calculatenode(tn.parameter.get(0));
					tn.setFloatnum((Math.random()*tn.parameter.get(0).retfn()));
				}
				else if(tn.getIndexoffunction()==23){		//阶乘（自然数运算）
					if(!tn.parameter.get(0).isIfnumbered()) calculatenode(tn.parameter.get(0));
					if(!tn.parameter.get(0).ifint()) return -2; //数值应该为整数
					tn.setFloatnum(0);
					tn.setIntnum(factor(tn.parameter.get(0).getIntnum()));
					if(tn.getIntnum()==-3){
						tn.setIntnum(0);
						return -3;		//数字过大
					}
				}
				else if(tn.getIndexoffunction()==24){		//斐波那契数列的项（从0开始）（自然数运算）
					if(!tn.parameter.get(0).isIfnumbered()) calculatenode(tn.parameter.get(0));
					if(!tn.parameter.get(0).ifint()) return -2; //数值应该为整数
					tn.setFloatnum(0);
					tn.setIntnum(fib(tn.parameter.get(0).getIntnum()));
					if(tn.getIntnum()==-3){
						tn.setIntnum(0);
						return -3;		//数字过大
					}
				}
				else{				//自定义的函数
					tree_node tmpnode1;
					for(int ii=0;ii<tn.getParam_num();ii++){
						if(!tn.parameter.get(ii).isIfnumbered()){			//给每个参数求值
							if(calculatenode(tn.parameter.get(ii))!=1){
								return -100;					//某个参数没有值
							}
						}
					}
					tmpnode1 = runfunc(tn.getIndexoffunction(),tn.parameter);		//通过参数调用函数表中的函数
					if(tmpnode1==null){
						System.out.println("err");
						return -100;					//调用失败
					}
					else{							//返回值
						tn.setFloatnum(tmpnode1.getFloatnum());
						tn.setIntnum(tmpnode1.getIntnum());
					}
				}
				
				tn.setIfnumbered(true);					//当前节点求值完成
				return 1;
			}
			else if(tn.getIndexoffunction()==0){		//常数
				tn.setIfnumbered(true);
				return 1;
			}
			else if(tn.getIndexoffunction()==-1){	//参数
				if(tn.ptn==null){
					if(tn.getPtnum()==-1) return -1;			//没有值
					else{
						tn.ptn=tn.belongs.getParamtable().get(tn.getPtnum());		//有标注，可以找到所属函数的参数表
					}
				}
				if(tn.ptn.getPoint()==null) return -1;		//还是没有值
				else{
					tn.setFloatnum(tn.ptn.getPoint().getFloatnum());
					tn.setIntnum(tn.ptn.getPoint().getIntnum());
					tn.setIfnumbered(true);
					return 1;							//给参数赋值
				}
			}
		}
		return 0;
	}
	
	public int calculate(ArrayList<tree_node> paramlist) {		//对应传参，计算函数
		if(getParam_num()!=paramlist.size()) return -1;
		int ia;
		for(ia=0;ia<getParam_num();ia++){
			getParamtable().get(getIndependent().get(ia)).setPoint(paramlist.get(ia));
		}
		return calculate();
	}
	
	public int setupparam1(){						//控制台设置参数
		int i, n;
		String tmpstr2;
		Scanner sc = new Scanner(System.in); 
		ArrayList<Integer> xlist = new ArrayList<Integer>();
		int x_num;
		
		System.out.println("参数列表：");
		for(i=0;i<getParamtable().size();i++){
			System.out.println(i +","+ getParamtable().get(i).getName());
		}
		
		System.out.println("请输入变量个数:");
		x_num=sc.nextInt();
		
		while(true){
			if(x_num>getParamtable().size()){
				System.out.println("请重新输入变量个数:");
				x_num=sc.nextInt();
				continue;		//设置变量数量不应该大于参数的个数
			}
			else break;
		}
		
		System.out.println("请输入作为变量的编号（们）：");
		for(i=0;i<x_num;i++){
			n=sc.nextInt();
			if(xlist.contains(n)){
				i--;
				System.out.println("重复");
				continue;
			}
			if(n>=getParamtable().size()){
				i--;
				System.out.println("不存在的编号");
				continue;
			}
			xlist.add(n);
		}
		sc.close();
		sc = new Scanner(System.in);
		for(i=0;i<getParamtable().size();i++){
			if(!xlist.contains(i)){
				System.out.println("确定当前参数的值： " + getParamtable().get(i).getName());
				tmpstr2=sc.next();
				getParamtable().get(i).setPoint(new tree_node(token.StrToNum(tmpstr2)));
			}
		}
		sc.close();
		return 0;
	}

	public int setupparam(int x_num){				//控制台设置参数 num为期望自变量数量
		if(x_num>getParamtable().size()) return -1;		//设置变量数量不应该大于参数的个数
		int i, n;
		String tmpstr2;
		Scanner sc = new Scanner(System.in); 
		ArrayList<Integer> xlist = new ArrayList<Integer>();
		this.setParam_num(x_num);
		
		System.out.println("参数列表：");
		for(i=0;i<getParamtable().size();i++){
			System.out.println(i +","+ getParamtable().get(i).getName());
		}

		System.out.println("请输入作为变量的编号（们）：");
		for(i=0;i<x_num;i++){
			n=sc.nextInt();
			if(xlist.contains(n)){
				i--;
				System.out.println("重复");
				continue;
			}
			if(n>=getParamtable().size()){
				i--;
				System.out.println("不存在的编号");
				continue;
			}
			xlist.add(n);
		}

		for(i=0;i<getParamtable().size();i++){
			if(!xlist.contains(i)){
				System.out.println("确定当前参数的值" + getParamtable().get(i).getName());
				tmpstr2=sc.next();
				getParamtable().get(i).setPoint(new tree_node(token.StrToNum(tmpstr2)));
			}
		}
		sc.close();
		return 0;
	}
	
	public static void initfunc(){							//初始化内置函数占位（static）
		lexanal lex0=new lexanal(new StringBuffer("zero(x)"));
		lex0.calculatetoken();
		addfunc("zero",lex0.getTklist());
		getFunc().get(0).setParam_num(1);
		
		lexanal lex1=new lexanal(new StringBuffer("one(x)"));
		lex1.calculatetoken();
		addfunc("one",lex1.getTklist());
		getFunc().get(1).setParam_num(1);
		
		lexanal lex2=new lexanal(new StringBuffer("a+b"));
		lex2.calculatetoken();
		addfunc("add",lex2.getTklist());
		getFunc().get(2).setParam_num(2);
		
		lexanal lex3=new lexanal(new StringBuffer("a-b"));
		lex3.calculatetoken();
		addfunc("sub",lex3.getTklist());
		getFunc().get(3).setParam_num(2);
		
		lexanal lex4=new lexanal(new StringBuffer("a*b"));
		lex4.calculatetoken();
		addfunc("mul",lex4.getTklist());
		getFunc().get(4).setParam_num(2);
		
		lexanal lex5=new lexanal(new StringBuffer("a/b"));
		lex5.calculatetoken();
		addfunc("dev",lex5.getTklist());
		getFunc().get(5).setParam_num(2);
		
		lexanal lex6=new lexanal(new StringBuffer("a%b"));
		lex6.calculatetoken();
		addfunc("remainder",lex6.getTklist());
		getFunc().get(6).setParam_num(2);
		
		lexanal lex7=new lexanal(new StringBuffer("a^b"));
		lex7.calculatetoken();
		addfunc("power",lex7.getTklist());
		getFunc().get(7).setParam_num(2);
		
		lexanal lex8=new lexanal(new StringBuffer("sin(x)"));
		lex8.calculatetoken();
		addfunc("sin",lex8.getTklist());
		getFunc().get(8).setParam_num(1);
		
		lexanal lex9=new lexanal(new StringBuffer("cos(x)"));
		lex9.calculatetoken();
		addfunc("cos",lex9.getTklist());
		getFunc().get(9).setParam_num(1);
		
		lexanal lex10=new lexanal(new StringBuffer("tan(x)"));
		lex10.calculatetoken();
		addfunc("tan",lex10.getTklist());
		getFunc().get(10).setParam_num(1);
		
		lexanal lex11=new lexanal(new StringBuffer("arcsin(x)"));
		lex11.calculatetoken();
		addfunc("arcsin",lex11.getTklist());
		getFunc().get(11).setParam_num(1);
		
		lexanal lex12=new lexanal(new StringBuffer("arccos(x)"));
		lex12.calculatetoken();
		addfunc("arccos",lex12.getTklist());
		getFunc().get(12).setParam_num(1);
		
		lexanal lex13=new lexanal(new StringBuffer("arctan(x)"));
		lex13.calculatetoken();
		addfunc("arctan",lex13.getTklist());
		getFunc().get(13).setParam_num(1);
		
		lexanal lex14=new lexanal(new StringBuffer("lg(x)"));
		lex14.calculatetoken();
		addfunc("lg",lex14.getTklist());
		getFunc().get(14).setParam_num(1);
		
		lexanal lex15=new lexanal(new StringBuffer("ln(x)"));
		lex15.calculatetoken();
		addfunc("ln",lex15.getTklist());
		getFunc().get(15).setParam_num(1);
		
		lexanal lex16=new lexanal(new StringBuffer("log(x,y)"));
		lex16.calculatetoken();
		addfunc("log",lex16.getTklist());
		getFunc().get(16).setParam_num(2);
		
		lexanal lex17=new lexanal(new StringBuffer("sqrt(x)"));
		lex17.calculatetoken();
		addfunc("sqrt",lex17.getTklist());
		getFunc().get(17).setParam_num(1);
		
		lexanal lex18=new lexanal(new StringBuffer("pow(x,y)"));
		lex18.calculatetoken();
		addfunc("pow",lex18.getTklist());
		getFunc().get(18).setParam_num(2);
		
		lexanal lex19=new lexanal(new StringBuffer("sinh(x)"));
		lex19.calculatetoken();
		addfunc("sinh",lex19.getTklist());
		getFunc().get(19).setParam_num(1);
		
		lexanal lex20=new lexanal(new StringBuffer("cosh(x)"));
		lex20.calculatetoken();
		addfunc("cosh",lex20.getTklist());
		getFunc().get(20).setParam_num(1);
		
		lexanal lex21=new lexanal(new StringBuffer("tanh(x)"));
		lex21.calculatetoken();
		addfunc("tanh",lex21.getTklist());
		getFunc().get(21).setParam_num(1);
		
		lexanal lex22=new lexanal(new StringBuffer("random(x)"));
		lex22.calculatetoken();
		addfunc("random",lex22.getTklist());
		getFunc().get(22).setParam_num(1);
		
		lexanal lex23=new lexanal(new StringBuffer("factor(x)"));
		lex23.calculatetoken();
		addfunc("factor",lex23.getTklist());
		getFunc().get(23).setParam_num(1);
		
		lexanal lex24=new lexanal(new StringBuffer("fib(x)"));
		lex24.calculatetoken();
		addfunc("fib",lex24.getTklist());
		getFunc().get(24).setParam_num(1);
	}
	
	public long factor(long i){			//阶乘
		if(i<0 || i>24) return -3;				//运算超限
		if(i==0) return 1;
		return i*factor(i-1);
	}
	
	public long fib(long i){			//fibnacci数列
		if(i<0 || i>24) return -3;				//运算超限
		if(i==0 || i==1) return 1;
		return fib(i-1)+fib(i-2);
	}

	public static int getFselected() {
		return fselected;
	}

	public static void setFselected(int fselected) {
		function.fselected = fselected;
	}

	public static int getFselected2() {
		return fselected2;
	}

	public static void setFselected2(int fselected2) {
		function.fselected2 = fselected2;
	}

	public static ArrayList<function> getFunc() {
		return func;
	}

	public static void setFunc(ArrayList<function> func) {
		function.func = func;
	}

	public tree_node getRoot() {
		return root;
	}

	public void setRoot(tree_node root) {
		this.root = root;
	}

	public String getFuncname() {
		return funcname;
	}

	public void setFuncname(String funcname) {
		this.funcname = funcname;
	}

	public String getSourceexp() {
		return sourceexp;
	}

	public void setSourceexp(String sourceexp) {
		this.sourceexp = sourceexp;
	}

	public static int getSelfstart() {
		return selfstart;
	}

	public static void setSelfstart(int selfstart) {
		function.selfstart = selfstart;
	}

	public int getParam_num() {
		return param_num;
	}

	public void setParam_num(int param_num) {
		this.param_num = param_num;
	}

	public ArrayList<paramtablenode> getParamtable() {
		return paramtable;
	}

	public void setParamtable(ArrayList<paramtablenode> paramtable) {
		this.paramtable = paramtable;
	}

	public ArrayList<Integer> getIndependent() {
		return independent;
	}

	public void setIndependent(ArrayList<Integer> independent) {
		this.independent = independent;
	}
}


