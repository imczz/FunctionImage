package CompilationPrinciple;
import java.util.ArrayList;

public class tokenlist {
	ArrayList<token> iT;			//��ʶ��
	ArrayList<token> cT;			//�ַ�
	ArrayList<token> sT;			//�ַ���
	ArrayList<token> CT;			//����
	static ArrayList<token> KT;			//�ؼ���
	static ArrayList<token> PT;			//���
	
	private ArrayList<token> tk;
	
	static ArrayList<Character> strtable;
	
	tokenlist(){
		iT=new ArrayList<token>();			//��ʶ��		0
		cT=new ArrayList<token>();			//�ַ�		1
		sT=new ArrayList<token>();			//�ַ���		2
		CT=new ArrayList<token>();			//����		3
		KT = new ArrayList<token>();		//�ؼ���   	4
		PT=new ArrayList<token>();			//���		5
		
		setTk(new ArrayList<token>());			//�����ɵ�token����
		
		strtable=new ArrayList<Character>();			//���ϵĽ�������������Ƿ����PT�Զ�����
		
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
		
		KT.add(new token(4,0,"asm"));						//C++��63����ʶ��
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
		
		PT.add(new token(5,0,">"));								//c++�Ľ�������ܲ�������
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
	
	public int addtoken(String clss,StringBuffer buff){				//���ַ���װ����Ӧ���ֵ�token������
		token tmp=new token(0,0,"");
		String tmpstr=buff.toString();
		StringBuffer strbuff=new StringBuffer(buff);			//�����и��ַ���
		int flag1=0;
		if(clss=="KTiT"){
			flag1=0;
			for(int i=0;i<KT.size();i++){				//����KT
				tmp=KT.get(i);
				if(tmp.getStr().equals(tmpstr)){
					flag1=1;
					break;
				}
			}
			if(flag1==0){
				for(int i=0;i<iT.size();i++){			//����iT
					tmp=iT.get(i);
					if(tmp.getStr().equals(tmpstr)){
						flag1=1;
						break;
					}
				}
			}
			if(flag1==0){
				tmp=new token(0,iT.size(),tmpstr);		//�µı�ʶ��
				iT.add(tmp);
			}
		}
		else if(clss=="CT"){					//����
			flag1=0;
			for(int i=0;i<CT.size();i++){
				tmp=CT.get(i);
				if(tmp.getStr().equals(tmpstr)){
					flag1=1;
					break;
				}
			}
			if(flag1==0){						//����
				tmp=token.StrToNum(tmpstr);
				tmp.setindex2(CT.size());
				CT.add(tmp);
			}
		}
		else if(clss=="cT"){					//����
			flag1=0;
			for(int i=0;i<cT.size();i++){
				tmp=cT.get(i);
				if(tmp.getStr().equals(tmpstr)){
					flag1=1;
					break;
				}
			}
			if(flag1==0){						//����
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
			for(int i=0;i<sT.size();i++){			//����
				tmp=sT.get(i);
				if(tmp.getStr().equals(tmpstr)){
					flag1=1;
					break;
				}
			}
			if(flag1==0){							//����
				tmp=new token(2,sT.size(),tmpstr);
				sT.add(tmp);
			}
		}
		else if(clss=="PT"){				//����
			flag1=0;
			for(int i=0;i<PT.size();i++){
				tmp=PT.get(i);
				if(tmp.getStr().equals(tmpstr)){
					flag1=1;
					break;
				}
			}
			if(flag1==0) System.out.println("ERR123");		//�Ҳ����ǲ����ܵ�
		}
		else{
			return 0;						//�����ܵ����
		}
		
		getTk().add(tmp);				//����token����
		return 1;
	}
	
	public int output(int n){					//����̨һһ��Ӧ����Ѿ��ɳɵ�token����
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
