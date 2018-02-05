package CompilationPrinciple;
import java.util.ArrayList;

public class tokenlist {
	ArrayList<token> iT;			//标识符
	ArrayList<token> cT;			//字符
	ArrayList<token> sT;			//字符串
	ArrayList<token> CT;			//常数
	static ArrayList<token> KT;			//关键字
	static ArrayList<token> PT;			//界符
	
	private ArrayList<token> tk;
	
	static ArrayList<Character> strtable;
	
	tokenlist(){
		iT=new ArrayList<token>();			//标识符		0
		cT=new ArrayList<token>();			//字符		1
		sT=new ArrayList<token>();			//字符串		2
		CT=new ArrayList<token>();			//常数		3
		KT = new ArrayList<token>();		//关键字   	4
		PT=new ArrayList<token>();			//界符		5
		
		setTk(new ArrayList<token>());			//待生成的token序列
		
		strtable=new ArrayList<Character>();			//承认的界符（用来决定是否进入PT自动机）
		
		strtable.add('<');
		strtable.add('>');
		strtable.add('=');
		strtable.add('!');
		strtable.add('|');
		strtable.add('&');
		strtable.add('+');
		strtable.add('-');
		strtable.add('*');
		strtable.add('/');
		strtable.add(':');
		strtable.add('(');
		strtable.add(')');
		strtable.add('[');
		strtable.add(']');
		strtable.add('{');
		strtable.add('}');
		strtable.add(';');
		strtable.add(',');
		strtable.add('^');
		strtable.add('%');
		strtable.add('?');
		strtable.add('.');
		strtable.add('#');
		//strtable.add('\'');
		//strtable.add('\"');
		
		KT.add(new token(4,0,"asm"));						//C++的63个标识符
		KT.add(new token(4,1,"auto"));
		KT.add(new token(4,2,"bool"));
		KT.add(new token(4,3,"break"));
		KT.add(new token(4,0,"case"));
		KT.add(new token(4,0,"catch"));
		KT.add(new token(4,0,"char"));
		KT.add(new token(4,0,"class"));
		KT.add(new token(4,0,"const"));
		KT.add(new token(4,0,"const_cast"));
		KT.add(new token(4,0,"continue"));
		KT.add(new token(4,0,"default"));
		KT.add(new token(4,0,"delete"));
		KT.add(new token(4,0,"do"));
		KT.add(new token(4,0,"double"));
		KT.add(new token(4,0,"dynamic_cast"));
		KT.add(new token(4,0,"else"));
		KT.add(new token(4,0,"enum"));
		KT.add(new token(4,0,"explicit"));
		KT.add(new token(4,0,"export"));
		KT.add(new token(4,0,"extern"));
		KT.add(new token(4,0,"false"));
		KT.add(new token(4,0,"float"));
		KT.add(new token(4,0,"for"));
		KT.add(new token(4,0,"friend"));
		KT.add(new token(4,0,"goto"));
		KT.add(new token(4,0,"if"));
		KT.add(new token(4,0,"inline"));
		KT.add(new token(4,0,"int"));
		KT.add(new token(4,0,"long"));
		KT.add(new token(4,0,"mutable"));
		KT.add(new token(4,0,"namespace"));
		KT.add(new token(4,0,"new"));
		KT.add(new token(4,0,"operator"));
		KT.add(new token(4,0,"private"));
		KT.add(new token(4,0,"protected"));
		KT.add(new token(4,0,"public"));
		KT.add(new token(4,0,"register"));
		KT.add(new token(4,0,"reinterpret_cast"));
		KT.add(new token(4,0,"return"));
		KT.add(new token(4,0,"short"));
		KT.add(new token(4,0,"signed"));
		KT.add(new token(4,0,"sizeof"));
		KT.add(new token(4,0,"static"));
		KT.add(new token(4,0,"static_cast"));
		KT.add(new token(4,0,"struct"));
		KT.add(new token(4,0,"switch"));
		KT.add(new token(4,0,"template"));
		KT.add(new token(4,0,"this"));
		KT.add(new token(4,0,"throw"));
		KT.add(new token(4,0,"true"));
		KT.add(new token(4,0,"try"));
		KT.add(new token(4,0,"typedef"));
		KT.add(new token(4,0,"typeid"));
		KT.add(new token(4,0,"typename"));
		KT.add(new token(4,0,"union"));
		KT.add(new token(4,0,"unsigned"));
		KT.add(new token(4,0,"using"));
		KT.add(new token(4,0,"virtual"));
		KT.add(new token(4,0,"void"));
		KT.add(new token(4,0,"volatile"));
		KT.add(new token(4,0,"wchar_t"));
		KT.add(new token(4,0,"while"));
		for(int i=0;i<63;i++){
			KT.get(i).setIndex2(i);
		}
		
		PT.add(new token(5,0,">"));								//c++的界符（可能不完整）
		PT.add(new token(5,1,">="));
		PT.add(new token(5,2,">>"));
		PT.add(new token(5,3,"<"));
		PT.add(new token(5,4,"<="));
		PT.add(new token(5,5,"<<"));
		PT.add(new token(5,6,"="));
		PT.add(new token(5,7,"=="));
		PT.add(new token(5,8,"!"));
		PT.add(new token(5,9,"!="));
		PT.add(new token(5,10,"|"));
		PT.add(new token(5,11,"||"));
		PT.add(new token(5,12,"&"));
		PT.add(new token(5,13,"&&"));
		PT.add(new token(5,14,"+"));
		PT.add(new token(5,15,"++"));
		PT.add(new token(5,16,"-"));
		PT.add(new token(5,17,"--"));
		PT.add(new token(5,18,"->"));
		PT.add(new token(5,19,":"));
		PT.add(new token(5,20,"::"));
		PT.add(new token(5,21,"*"));
		PT.add(new token(5,22,"/"));
		PT.add(new token(5,23,"%"));
		PT.add(new token(5,24,"^"));
		PT.add(new token(5,25,"("));
		PT.add(new token(5,26,")"));
		PT.add(new token(5,27,"["));
		PT.add(new token(5,28,"]"));
		PT.add(new token(5,29,"{"));
		PT.add(new token(5,30,"}"));
		PT.add(new token(5,31,";"));
		PT.add(new token(5,32,","));
		PT.add(new token(5,33,"."));
		PT.add(new token(5,34,"?"));
		PT.add(new token(5,35,"#"));
		for(int i=0;i<=35;i++){
			PT.get(i).setIndex2(i);
		}
	}
	
	public int addtoken(String clss,StringBuffer buff){				//把字符串装进对应名字的token序列中
		token tmp=new token(0,0,"");
		String tmpstr=buff.toString();
		StringBuffer strbuff=new StringBuffer(buff);			//用来切割字符串
		int flag1=0;
		if(clss=="KTiT"){
			flag1=0;
			for(int i=0;i<KT.size();i++){				//遍历KT
				tmp=KT.get(i);
				if(tmp.getStr().equals(tmpstr)){
					flag1=1;
					break;
				}
			}
			if(flag1==0){
				for(int i=0;i<iT.size();i++){			//遍历iT
					tmp=iT.get(i);
					if(tmp.getStr().equals(tmpstr)){
						flag1=1;
						break;
					}
				}
			}
			if(flag1==0){
				tmp=new token(0,iT.size(),tmpstr);		//新的标识符
				iT.add(tmp);
			}
		}
		else if(clss=="CT"){					//遍历
			flag1=0;
			for(int i=0;i<CT.size();i++){
				tmp=CT.get(i);
				if(tmp.getStr().equals(tmpstr)){
					flag1=1;
					break;
				}
			}
			if(flag1==0){						//新增
				tmp=token.StrToNum(tmpstr);
				tmp.setindex2(CT.size());
				CT.add(tmp);
			}
		}
		else if(clss=="cT"){					//遍历
			flag1=0;
			for(int i=0;i<cT.size();i++){
				tmp=cT.get(i);
				if(tmp.getStr().equals(tmpstr)){
					flag1=1;
					break;
				}
			}
			if(flag1==0){						//新增
				tmp=new token(1,cT.size(),tmpstr);
				tmp.ch=tmpstr.charAt(1);
				cT.add(tmp);
			}
		}
		else if(clss=="sT"){
			strbuff.deleteCharAt(strbuff.length()-1);
			strbuff.deleteCharAt(0);
			tmpstr=strbuff.toString();
			flag1=0;
			for(int i=0;i<sT.size();i++){			//遍历
				tmp=sT.get(i);
				if(tmp.getStr().equals(tmpstr)){
					flag1=1;
					break;
				}
			}
			if(flag1==0){							//新增
				tmp=new token(2,sT.size(),tmpstr);
				sT.add(tmp);
			}
		}
		else if(clss=="PT"){				//遍历
			flag1=0;
			for(int i=0;i<PT.size();i++){
				tmp=PT.get(i);
				if(tmp.getStr().equals(tmpstr)){
					flag1=1;
					break;
				}
			}
			if(flag1==0) System.out.println("ERR123");		//找不到是不可能的
		}
		else{
			return 0;						//不可能的情况
		}
		
		getTk().add(tmp);				//加入token序列
		return 1;
	}
	
	public int output(int n){					//控制台一一对应输出已经成成的token序列
		int in1,in2;
		for(int i=0;i<getTk().size();i++)
		{
			System.out.print("<");
			in1=getTk().get(i).getIndex1();
			System.out.print(in1 + ",");
			in2=getTk().get(i).getIndex2();
			if(in2<10) System.out.print("0");
			System.out.print(in2 + "> ");
			if(in1==3)
			{
				if(getTk().get(i).getNum1()==0) System.out.print(getTk().get(i).getNum2());
				else System.out.print(getTk().get(i).getNum1());
			}
			else if(in1==1){
				System.out.print(getTk().get(i).ch);
			}
			else{
				System.out.print(getTk().get(i).getStr());
			}
			System.out.println();
		}
		return n;
	}

	public ArrayList<token> getTk() {
		return tk;
	}

	public void setTk(ArrayList<token> tk) {
		this.tk = tk;
	}
}
