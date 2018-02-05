package CompilationPrinciple;
//单个token,2级结构
public class token {
	private int index1;				//name
	private int index2;				//index
	private String str;				//value	保留原始文本
	char ch;				//如果是字符
	private int num2;				//如果num1（double）不为零
	private double num1;			//决定这个数是否为浮点数（省却标志）
	
	token(){			//构造方法
		setIndex1(0);
		setIndex2(0);
		ch='\0';
		setNum1(0);
		setNum2(0);
	}
	
	token(int in1,int in2,String str,char c,int n1,int n2){		//复杂的构造方法
		setIndex1(in1);
		setIndex2(in2);
		this.setStr(str);
		ch=c;
		setNum1(n1);
		setNum2(n2);
	}
	
	public token(int in1,int in2,String str){				//简易的构造方法
		setIndex1(in1);
		setIndex2(in2);
		this.setStr(str);
		ch='\0';
		setNum1(0);
		setNum2(0);
	}
	
	token(token t1){					//复制构造方法
		setIndex1(t1.getIndex1());
		setIndex2(t1.getIndex2());
		setStr(t1.getStr());
		ch=t1.ch;
		setNum1(t1.getNum1());
		setNum2(t1.getNum2());
	}
	
	boolean equalto(token t1){			//完全相等
		if(getIndex1()==t1.getIndex1() && getIndex2()==t1.getIndex2() && getStr()==t1.getStr()) return true;
		else return false;
	}
	
	boolean equalstr(token t1){			//来源字符串相同
		if(getStr()==t1.getStr()) return true;
		else return false;
	}
	
	public void setindex1(int in1){		//改变大类
		setIndex1(in1);
	}
	
	public void setindex2(int in2){		//改变编号
		setIndex2(in2);
	}
	
	public static token StrToNum(String str){			//表示数字的字符转化成数字
		token tmp = new token(3,0,str,'\0',0,0);		//3代表数字；index2以后再改变
		char tmpch=0;
		int flag1=0;
		double f2=10;
		for(int i=0;i<str.length();i++){
			tmpch=str.charAt(i);
			if(tmpch>='0' && tmpch<='9'){
				if(flag1==0){
					tmp.setNum2(tmp.getNum2() * 10 + (tmpch-'0'));
				}
				else{
					tmp.setNum1(tmp.getNum1() + (tmpch-'0')/f2);
					f2 *=10;
				}
			}
			else if(tmpch=='.')							//遇到小数点
			{
				tmp.setNum1(1.0 * tmp.getNum2());
				flag1=1;
			}
		}
		return tmp;
	}

	public int getIndex1() {
		return index1;
	}

	public void setIndex1(int index1) {
		this.index1 = index1;
	}

	public double getNum1() {
		return num1;
	}

	public void setNum1(double num1) {
		this.num1 = num1;
	}

	public int getNum2() {
		return num2;
	}

	public void setNum2(int num2) {
		this.num2 = num2;
	}

	public int getIndex2() {
		return index2;
	}

	public void setIndex2(int index2) {
		this.index2 = index2;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}
}
