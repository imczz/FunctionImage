package FunctionTree;
import java.util.ArrayList;
//�Զ�������ݽṹ�����һ��������ֵ�ͱ����õĵ�ַ
public class paramtablenode {
	private String name;					//������
	private tree_node point;				//ֵ
	ArrayList<tree_node> list;		//����λ���б�
	
	public boolean equals(paramtablenode ptn){		//�Ƿ�ͬ��
		if(getName().equals(ptn.getName())) return true;
		else return false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public tree_node getPoint() {
		return point;
	}

	public void setPoint(tree_node point) {
		this.point = point;
	}
}
