package CompilationPrinciple;
import java.util.ArrayList;
//抽象类“自动机”
public abstract class AutomataMachine {
	String name;					//名字
	int start_state;				//起始状态（默认为0）
	ArrayList<Integer> Q;			//状态集合
	ArrayList<Integer> end_state;	//结束（接收）状态集合
	ArrayList<Character> sigma;		//字母表
	ArrayList<ArrayList<Integer>> delta;		//（未使用）状态转换表
	int useable;					//用法 0 不可用；1 转换表模式；2 if_else模式；
	protected int state;			//当前状态
	
	public int retstate(){			//返回当前状态
		return state;
	}
	
	public AutomataMachine(){		//构造方法
		useable = 0;
		state=0;
		Q=new ArrayList<Integer>();
		end_state=new ArrayList<Integer>();
		sigma=new ArrayList<Character>();
	}
	
	abstract public int getchar(char ch);		//接收一个字符
}
