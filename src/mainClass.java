import CompilationPrinciple.lexanal;
import Frames.functioncontrol;
import FunctionTree.function;

public class mainClass {
	
	public static void main(String[] args) {
		lexanal lex=new lexanal(new StringBuffer("1+3"));					//�ַ���װ��ʷ�������
		lex.calculatetoken();								//����token
		function func1=new function("fun_exp1",lex.getTklist());		//token����װ�����������
		function.initfunc();								//��ʼ�����ú������������У�
		func1.start();										//������������﷨��	
		//function���о�̬������������Ҫ���ϵĴ����ʼ����Щ��̬����
		functioncontrol functrol=new functioncontrol("��������̨");
		functrol.setVisible(true);
		System.out.println("hello!world");
	}
}
