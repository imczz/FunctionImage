package FunctionTree;
import java.util.ArrayList;

import CompilationPrinciple.token;
//语法树的节点
public class tree_node {
	/*函数编号*/
	private int indexoffunction;//0常数 -1变量 >0函数
	
	/*是否求值完成*/
	private boolean ifnumbered;
	
	/*如果是整数*/
	private long intnum;
	
	/*如果是浮点数*/
	private double floatnum;
	
	/*根节点不存在的双亲*/
	tree_node parent;
	
	/*函数变量个数*/
	private int param_num;
	
	paramtablenode ptn;				//参数表
	private int ptnum;
	
	
	/*变量列表*/
	ArrayList<tree_node> parameter;
		
	function belongs;
	
	/*空构造方法*/
	public tree_node(){
		setIndexoffunction(-2);
		setIfnumbered(false);
		setIntnum(0);
		setFloatnum(0);
		setParam_num(0);
		setPtnum(-1);
		parameter = new ArrayList<tree_node>();
	}
	
	public tree_node(tree_node tn){				//复制构造方法
		tree_node tmptn;
		setIndexoffunction(tn.getIndexoffunction());
		setIfnumbered(tn.isIfnumbered());
		setIntnum(tn.getIntnum());
		setFloatnum(tn.getFloatnum());
		setParam_num(tn.getParam_num());
		parameter = new ArrayList<tree_node>();
		for(int i=0;i<getParam_num();i++){
			tmptn=new tree_node(tn.parameter.get(i));
			parameter.add(tmptn);
		}
		setPtnum(tn.getPtnum());
	}
	
	public tree_node(char ch){					//由字符构造
		this();
		setParam_num(2);
		if(ch==',') setIndexoffunction(1);
		else if(ch=='+')setIndexoffunction(2);
		else if(ch=='-')setIndexoffunction(3);
		else if(ch=='*')setIndexoffunction(4);
		else if(ch=='/')setIndexoffunction(5);
		else if(ch=='%')setIndexoffunction(6);
		else if(ch=='^')setIndexoffunction(7);
	}
	
	public tree_node(token tk){					//由token构造
		this();
			
		if(tk.getIndex1()==0 || tk.getIndex1()==4){
			setIndexoffunction(-1);
		}
		else if(tk.getIndex1()==3){
			setIndexoffunction(0);
			setFloatnum(tk.getNum1());
			setIntnum(tk.getNum2());
			setIfnumbered(true);
		}
		else if(tk.getIndex1()==5){
			setParam_num(2);
			if(tk.getIndex2()==32) setIndexoffunction(1);
			else if(tk.getIndex2()==14)setIndexoffunction(2);
			else if(tk.getIndex2()==16)setIndexoffunction(3);
			else if(tk.getIndex2()==21)setIndexoffunction(4);
			else if(tk.getIndex2()==22)setIndexoffunction(5);
			else if(tk.getIndex2()==23)setIndexoffunction(6);
			else if(tk.getIndex2()==24)setIndexoffunction(7);
			else{
				System.out.println("errorabc");
			}
		}
		else{
			System.out.println("errorxyz");
		}
	}
	
	public boolean ifint(){						//是否为整数
		if(getFloatnum()==0) return true;
		else return false;
	}
	
	public double retfn(){						//返回存储的浮点型的值
		if(ifint()) return (double)getIntnum();
		else return getFloatnum();
	}
	
	public void belong(function func){			//递归给每个节点都标记属于哪一个函数
		if(func==null) return;
		belongs = func;
		for(int i=0;i<parameter.size();i++){
			parameter.get(i).belong(func);
		}
	}
	
	public void unnumed(){						//递归使每个节点都标记为未求值
		if(getIndexoffunction()!=0) setIfnumbered(false);
		for(int i=0;i<parameter.size();i++){
			parameter.get(i).unnumed();
		}
	}

	public int getIndexoffunction() {
		return indexoffunction;
	}

	public void setIndexoffunction(int indexoffunction) {
		this.indexoffunction = indexoffunction;
	}

	public boolean isIfnumbered() {
		return ifnumbered;
	}

	public void setIfnumbered(boolean ifnumbered) {
		this.ifnumbered = ifnumbered;
	}

	public long getIntnum() {
		return intnum;
	}

	public void setIntnum(long intnum) {
		this.intnum = intnum;
	}

	public double getFloatnum() {
		return floatnum;
	}

	public void setFloatnum(double floatnum) {
		this.floatnum = floatnum;
	}

	public int getParam_num() {
		return param_num;
	}

	public void setParam_num(int param_num) {
		this.param_num = param_num;
	}

	public int getPtnum() {
		return ptnum;
	}

	public void setPtnum(int ptnum) {
		this.ptnum = ptnum;
	}
}
