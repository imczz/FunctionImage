package CompilationPrinciple;
import java.util.ArrayList;
//�����ࡰ�Զ�����
public abstract class AutomataMachine {
	String name;					//����
	int start_state;				//��ʼ״̬��Ĭ��Ϊ0��
	ArrayList<Integer> Q;			//״̬����
	ArrayList<Integer> end_state;	//���������գ�״̬����
	ArrayList<Character> sigma;		//��ĸ��
	ArrayList<ArrayList<Integer>> delta;		//��δʹ�ã�״̬ת����
	int useable;					//�÷� 0 �����ã�1 ת����ģʽ��2 if_elseģʽ��
	protected int state;			//��ǰ״̬
	
	public int retstate(){			//���ص�ǰ״̬
		return state;
	}
	
	public AutomataMachine(){		//���췽��
		useable = 0;
		state=0;
		Q=new ArrayList<Integer>();
		end_state=new ArrayList<Integer>();
		sigma=new ArrayList<Character>();
	}
	
	abstract public int getchar(char ch);		//����һ���ַ�
}
