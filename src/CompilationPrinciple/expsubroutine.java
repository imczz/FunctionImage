package CompilationPrinciple;
//表达式的递归子程序分析法,实现抽象方法start
public class expsubroutine extends synanal{
	
	protected int i;				//当前终结符
	
	public expsubroutine(){			//空构造方法
		super();
	}
	
	public expsubroutine(tokenlist tks){			//把token转换成终结符序列
		super("fun_exp",tks);
	}
	
	public expsubroutine(StringBuffer vtlist){		//已有终结符序列
		super(vtlist);
		setname("fun_exp");
	}

	public expsubroutine(String name,tokenlist tks){		//其他文法
		super(name,tks);
	}
	
	private int sub_E(){						//E->T{,T}
		if (i < buffer.length()){
			if (sub_T()==0) return 0;

			while (buffer.charAt(i)==','){
				i++;
				if (sub_T()==0) return 0;
			}
			return 1;
		}
		return 0;
	}
	
	private int sub_T(){						//T->F{aF}
		if (i < buffer.length()){
			if (sub_F()==0) return 0;

			while (buffer.charAt(i)=='a'){
				i++;
				if (sub_F()==0) return 0;
			}
			return 1;
		}
		return 0;
		
	}
	
	private int sub_F(){						//F->G{bG}
		if (i < buffer.length()){
			if (sub_G()==0) return 0;

			while (buffer.charAt(i)=='b'){
				i++;
				if (sub_G()==0) return 0;
			}
			return 1;
		}
		return 0;
	}
	
	private int sub_G(){						//G->H{^H}
		if (i < buffer.length()){
			if (sub_H()==0) return 0;

			while (buffer.charAt(i)=='^'){
				i++;
				if (sub_H()==0) return 0;
			}
			return 1;
		}
		return 0;
	}
	
	private int sub_H(){						//H->i|(E)|dK
		if (i < buffer.length()){
			if (buffer.charAt(i) == 'i'){
				i++;
			}
			else if (buffer.charAt(i) == '('){
				i++;
				if (sub_E()==0) return 0;
				if (buffer.charAt(i) != ')') return 0;
				i++;
			}
			else if(buffer.charAt(i) == 'd'){
				i++;
				if (sub_K()==0) return 0;
			}
			else{
				return 0;
			}
			return 1;
		}
		return 0;
	}
	
	private int sub_K(){						//K->(E)|空
		if (i < buffer.length()){
			if (buffer.charAt(i) == '('){
				i++;
				if (sub_E()==0) return 0;
				if (buffer.charAt(i) != ')') return 0;
				i++;
			}
			return -1;
		}
		return 0;
	}
	
	public boolean start() {					//主程序
		// TODO Auto-generated method stub
		i=0;
		sub_E();
		if(buffer.charAt(i)=='#') return true;
		else return false;
	}
}
