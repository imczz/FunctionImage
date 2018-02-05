package FunctionTree;
import java.util.ArrayList;

import CompilationPrinciple.token;
//�﷨���Ľڵ�
public class tree_node {
	/*�������*/
	private int indexoffunction;//0���� -1���� >0����
	
	/*�Ƿ���ֵ���*/
	private boolean ifnumbered;
	
	/*���������*/
	private long intnum;
	
	/*����Ǹ�����*/
	private double floatnum;
	
	/*���ڵ㲻���ڵ�˫��*/
	tree_node parent;
	
	/*������������*/
	private int param_num;
	
	paramtablenode ptn;				//������
	private int ptnum;
	
	
	/*�����б�*/
	ArrayList<tree_node> parameter;
		
	function belongs;
	
	/*�չ��췽��*/
	public tree_node(){
		setIndexoffunction(-2);
		setIfnumbered(false);
		setIntnum(0);
		setFloatnum(0);
		setParam_num(0);
		setPtnum(-1);
		parameter = new ArrayList<tree_node>();
	}
	
	public tree_node(tree_node tn){				//���ƹ��췽��
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
	
	public tree_node(char ch){					//���ַ�����
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
	
	public tree_node(token tk){					//��token����
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
	
	public boolean ifint(){						//�Ƿ�Ϊ����
		if(getFloatnum()==0) return true;
		else return false;
	}
	
	public double retfn(){						//���ش洢�ĸ����͵�ֵ
		if(ifint()) return (double)getIntnum();
		else return getFloatnum();
	}
	
	public void belong(function func){			//�ݹ��ÿ���ڵ㶼���������һ������
		if(func==null) return;
		belongs = func;
		for(int i=0;i<parameter.size();i++){
			parameter.get(i).belong(func);
		}
	}
	
	public void unnumed(){						//�ݹ�ʹÿ���ڵ㶼���Ϊδ��ֵ
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
