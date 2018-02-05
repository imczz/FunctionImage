package CompilationPrinciple;
//Syntax analysis�﷨����
public abstract class synanal {
	protected StringBuffer buffer;		//�ս������
	private String name;				//������������
	
	
	public synanal(){			//���췽��
		buffer = new StringBuffer();
	}
	
	public synanal(StringBuffer vtlist){	//���췽��
		buffer = new StringBuffer(vtlist);
	}
	
	public synanal(String name,tokenlist tks){		//�����ֵĹ��췽��
		buffer = new StringBuffer();
		pretreatment(name,tks);						//Ԥ����
		this.setName(name);
	}
	
	public void setname(String str){
		setName(str);
	}
	
	public void setbuffer(StringBuffer vtlist){
		buffer = new StringBuffer(vtlist);
	}
	
	public int pretreatment(String name,tokenlist tks){		//��token����Ԥ������ս������
		token atk;
		if(name.equals("fun_exp")){						//�������ʽ
			for(int i=0;i<tks.getTk().size();i++){
				atk=tks.getTk().get(i);
				if(atk.getIndex1()==0 || atk.getIndex1()==4){
					buffer.append("d");					//��ʶ��
				}
				else if(atk.getIndex1()==3){
					buffer.append("i");					//����
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
					System.out.println("error");		//�����յ�token
					return 0;
				}
			}
			if(buffer.charAt(buffer.length()-1)!='#') buffer.append('#');		//��β����#
		}
		return 1;
	}
	
	public void outvtlist(){				//����ս������
		System.out.println(buffer);
	}
	
	public void outname(){					//�������
		System.out.println(getName());
	}
	
	public abstract boolean start();		//����������ʼ����

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
