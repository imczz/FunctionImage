package FunctionTree;
import java.util.ArrayList;
//自定义的数据结构，存放一个变量的值和被引用的地址
public class paramtablenode {
	private String name;					//变量名
	private tree_node point;				//值
	ArrayList<tree_node> list;		//引用位置列表
	
	public boolean equals(paramtablenode ptn){		//是否同名
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
