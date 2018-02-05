package CompilationPrinciple;
//Syntax analysis语法分析
public abstract class synanal {
	protected StringBuffer buffer;		//终结符序列
	private String name;				//分析法的名字
	
	
	public synanal(){			//构造方法
		buffer = new StringBuffer();
	}
	
	public synanal(StringBuffer vtlist){	//构造方法
		buffer = new StringBuffer(vtlist);
	}
	
	public synanal(String name,tokenlist tks){		//有名字的构造方法
		buffer = new StringBuffer();
		pretreatment(name,tks);						//预处理
		this.setName(name);
	}
	
	public void setname(String str){
		setName(str);
	}
	
	public void setbuffer(StringBuffer vtlist){
		buffer = new StringBuffer(vtlist);
	}
	
	public int pretreatment(String name,tokenlist tks){		//把token序列预处理成终结符序列
		token atk;
		if(name.equals("fun_exp")){						//函数表达式
			for(int i=0;i<tks.getTk().size();i++){
				atk=tks.getTk().get(i);
				if(atk.getIndex1()==0 || atk.getIndex1()==4){
					buffer.append("d");					//标识符
				}
				else if(atk.getIndex1()==3){
					buffer.append("i");					//数字
				}
				else if(atk.getIndex1()==5){
					if(atk.getIndex2()==32){
						buffer.append(",");				//,
					}
					else if(atk.getIndex2()==14 || atk.getIndex2()==16){
						buffer.append("a");				//+-
					}
					else if(atk.getIndex2()==21 || atk.getIndex2()==22 || atk.getIndex2()==23){
						buffer.append("b");				//*/%
					}
					else if(atk.getIndex2()==24){
						buffer.append("^");				//^
					}
					else if(atk.getIndex2()==25){
						buffer.append("(");				//(
					}
					else if(atk.getIndex2()==26){
						buffer.append(")");				//(
					}
					else if(atk.getIndex2()==35){
						buffer.append("#");				//#
					}
				}
				else{
					System.out.println("error");		//不接收的token
					return 0;
				}
			}
			if(buffer.charAt(buffer.length()-1)!='#') buffer.append('#');		//结尾补充#
		}
		return 1;
	}
	
	public void outvtlist(){				//输出终结符序列
		System.out.println(buffer);
	}
	
	public void outname(){					//输出名字
		System.out.println(getName());
	}
	
	public abstract boolean start();		//抽象函数，开始分析

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
