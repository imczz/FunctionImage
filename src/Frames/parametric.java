package Frames;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;

import FunctionTree.function;
import FunctionTree.tree_node;
//参数方程的图像（两个一元函数）
public class parametric extends JFrame{
	private static final long serialVersionUID = 1L;
	
	public parametric(String title)
	{
		this();
		setTitle(title);
	}
	
	private parametric(){
		setLayout(new FlowLayout(FlowLayout.LEADING));
		setSize(270, 400);
		setResizable(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		ParamGraph component=new ParamGraph(); 
		setContentPane(component);
	}
}

class ParamGraph extends JComponent  
{ 
 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void paint(Graphics g) 
	{
		int w=getSize().width;     
	    int h=getSize().height;
	    boolean ifgrid=true;		//网格
	    int rc=2;
	    int rline=1;
	    int kk=40;					//比例变换（单位长度的像素）
	    double x1;
	    double y1;
	    int funcnum1,funcnum2;
	    double upper=5;				//下限
	    double lower=-5;			//上限
	    double step=0.01;			//步长
	    
	    Point center=new Point(w/2,h/2);
		Graphics2D g2=(Graphics2D) g; 
		g2.setColor(Color.BLACK); 										//黑色
		g2.drawLine(0, h/2, w, h/2);
		g2.drawLine(0, h/2-1, w, h/2-1);
		g2.drawLine(0, h/2+1, w, h/2+1);
		g2.drawLine(w/2, 0, w/2, h);
		g2.drawLine(w/2-1, 0, w/2-1, h);
		g2.drawLine(w/2+1, 0, w/2+1, h);
		Ellipse2D.Double ellipse=new Ellipse2D.Double(center.getX()-2*rc,center.getY()-2*rc,4*rc,4*rc);  
		g2.draw(ellipse); 
		g2.fill(ellipse); 
		Ellipse2D.Double ellipse1=new Ellipse2D.Double(center.getX()+kk-rc,center.getY()-rc,rc+rc,rc+rc);  
		g2.draw(ellipse1); 
		g2.fill(ellipse1);
		
		if(ifgrid){														//网格
			for(int i=(int)center.getX();i<w;i+=kk){
				g2.drawLine(i, 0, i, h);
			}
			for(int i=(int)center.getX();i>0;i-=kk){
				g2.drawLine(i, 0, i, h);
			}
			for(int j=(int)center.getY();j<h;j+=kk){
				g2.drawLine(0, j, w, j);
			}
			for(int j=(int)center.getY();j>0;j-=kk){
				g2.drawLine(0, j, w, j);
			}
		}
		
		ArrayList<tree_node> tnlist=new ArrayList<tree_node>();			//临时参数列表
	    tree_node tn=new tree_node();
	    tn.setIndexoffunction(0);
	    tn.setIfnumbered(true);
	    tn.setIntnum(0);
	    tn.setFloatnum(0);
	    tn.setParam_num(0);
	    tn.setPtnum(-1);
	    tnlist.add(tn);
		funcnum1=function.getFselected();
		funcnum2=function.getFselected2();
		function func1=new function(function.getFunc().get(funcnum1));
		function func2=new function(function.getFunc().get(funcnum2));
		g2.setColor(Color.BLUE); 										//蓝色
		for(double ij=lower;ij<upper;ij+=step){
			tn.setFloatnum(ij);
			func1.getRoot().unnumed();
			func1.calculate(tnlist);
			func2.getRoot().unnumed();
			func2.calculate(tnlist);
			x1=func1.getRoot().retfn();
			y1=func2.getRoot().retfn();
			if(x1!=(double)1/0 && y1!=(double)1/0){
				Ellipse2D.Double ellipse2=new Ellipse2D.Double(center.getX() + x1*kk - rline,
															center.getY() - y1*kk - rline,
															rline + rline,
															rline + rline);  
				g2.draw(ellipse2); 
				g2.fill(ellipse2);
			}
		}
		g2.setColor(Color.RED); 										//函数体
		g2.drawString(function.getFunc().get(funcnum1).getFuncname() + " = " + function.getFunc().get(funcnum1).getSourceexp(), 50, 50);
		g2.drawString(function.getFunc().get(funcnum2).getFuncname() + " = " + function.getFunc().get(funcnum2).getSourceexp(), 50, 100);
	} 
}