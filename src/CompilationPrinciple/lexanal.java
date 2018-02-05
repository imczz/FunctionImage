package CompilationPrinciple;
//Lexical analysis�ʷ�����
public class lexanal {
	StringBuffer buffer;
	int nump;				//ָ��
	int mode;				//ģʽ0��׼��������token;ģʽ1������ĳ��token��
	int number;				//���ַ���
	FiniteStateMachine DFA;			//ʶ��token���Զ���
	private tokenlist tklist;
	
	public lexanal(){		//���췽��
		nump=0;
		mode=0;
		number=0;
	}
	
	public lexanal(StringBuffer buff){		//װ���ַ��������Ĺ��췽��
		nump=0;
		mode=0;
		number=buff.length();
		buffer=buff;
		setTklist(new tokenlist());
	}
	
	public int calculatetoken(){			//����token
		char tmpch=0;				//��ǰ�ַ�

		int tmpstate=0;				//��ʱ״̬
		if(number==0) return 0;		//û�п��Խ��յ�����
		while(nump<number)			//���Լ�������
		{
			tmpch=buffer.charAt(nump);		//����һ���ַ�
			if(mode==0){			//�µ���ʼ,����һ���Զ������յ�ǰ�ַ�
				if(tmpch>='a' && tmpch<='z' || tmpch>='A' && tmpch<='Z')
				{
					DFA=new FiniteStateMachine(2,"KTiT");			//�����Զ�����������2ģʽ��Ԥ��ifelse����
				}
				else if(tmpch>='0' && tmpch<='9')
				{
					DFA=new FiniteStateMachine(2,"CT");
				}
				else if(tmpch=='\'')
				{
					DFA=new FiniteStateMachine(2,"cT");
				}
				else if(tmpch=='\"')
				{
					DFA=new FiniteStateMachine(2,"sT");
				}
				else if(tokenlist.strtable.contains(tmpch))
				{
					DFA=new FiniteStateMachine(2,"PT");
				}
				else
				{
					DFA=new FiniteStateMachine(2,"others");
				}
				tmpstate=DFA.getchar(tmpch);			//״̬ת��
				if(DFA.name=="others"){			
					if(tmpstate==1) mode=0;				//�����ǻس������Ʊ��
					else System.out.println("err ?");	//������
				}
				else if(DFA.name=="PT"){				//����п���ֱ�ӽ��ձ���(,)
					if(DFA.end_state.contains(DFA.retstate())){
						//System.out.println(DFA.buffer);
						getTklist().addtoken(DFA.name,DFA.buffer);	//װ��tokenlist
						mode=0;								//׼����ʼ�µĽ���
					}
					else mode=1;
				}
				else mode=1;					//��ǰû��token�������տ���
			}
			else if(mode==1){					//��������
				if(DFA.err_state.contains( (Integer)DFA.retstate() )){
					System.out.println("error!");		//�Ƿ����
				}
				else{		//û�д���
					if(DFA.end_state.contains( (Integer)Math.abs(DFA.retstate()) )){	//�Ѿ�Ϊ����״̬
						//System.out.println(DFA.buffer);
						getTklist().addtoken(DFA.name,DFA.buffer);		//װ��tokenlist
						if(tmpstate<0) nump--;						//����һ�������β��ǰ��һ������ԭ��̤����
						mode=0;
					}
					else
					{
						tmpstate=DFA.getchar(tmpch);				//����һ���ַ�
						if(DFA.end_state.contains( (Integer)Math.abs(DFA.retstate()) )){	//Ϊ����״̬
							//System.out.println(DFA.buffer);
							getTklist().addtoken(DFA.name,DFA.buffer);	//װ��tokenlist
							if(tmpstate<0) nump--;
							mode=0;
						}
					}
				}
			}
			nump++;			//׼��������һ���ַ�
		}
		return 1;			//�������
	}

	public tokenlist getTklist() {
		return tklist;
	}

	public void setTklist(tokenlist tklist) {
		this.tklist = tklist;
	}
}
