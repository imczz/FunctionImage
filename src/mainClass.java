import CompilationPrinciple.lexanal;
import Frames.functioncontrol;
import FunctionTree.function;

public class mainClass {
	
	public static void main(String[] args) {
		lexanal lex=new lexanal(new StringBuffer("1+3"));					//字符串装入词法分析器
		lex.calculatetoken();								//分析token
		function func1=new function("fun_exp1",lex.getTklist());		//token序列装入语义分析器
		function.initfunc();								//初始化内置函数（无中生有）
		func1.start();										//语义分析生成语法树	
		//function类有静态方法，所以需要以上的代码初始化这些静态方法
		functioncontrol functrol=new functioncontrol("函数控制台");
		functrol.setVisible(true);
		System.out.println("hello!world");
	}
}
