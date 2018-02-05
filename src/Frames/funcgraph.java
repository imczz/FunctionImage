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
//һԪ����ͼ��
public class funcgraph extends JFrame{
	private static final long serialVersionUID = 1L;
	
	public funcgraph(String title)
	{
		this();
		setTitle(title);
	}
	
	private funcgraph(){
		setLayout(new FlowLayout(FlowLayout.LEADING));
		setSize(270, 400);
		setResizable(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		FuncGraphOne component=new FuncGraphOne(); 
		setContentPane(component);
	}
}

class FuncGraphOne extends JComponent  
{ 
 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void paint(Graphics g) 
	{
		int w=getSize().width;     
	    int h=getSize().height;
	    boolean ifgrid=true;		//����
	    int rc=2;
	    int rline=1;
	    int kk=40;					//�����任����λ���ȵ����أ�
	    double x1;
	    double y1;
	    int funcnum;
	    
	    Point center=new Point(w/2,h/2);
		Graphics2D g2=(Graphics2D) g; 
		g2.setColor(Color.BLACK); 										//��ɫ��������
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
		
		if(ifgrid){														//����
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
		
		
		
		ArrayList<tree_node> tnlist=new ArrayList<tree_node>();			//��ʱ�����б�
	    tree_node tn=new tree_node();
	    tn.setIndexoffunction(0);
	    tn.setIfnumbered(true);
	    tn.setIntnum(0);
	    tn.setFloatnum(0);
	    tn.setParam_num(0);
	    tn.setPtnum(-1);
	    tnlist.add(tn);
		funcnum=function.getFselected();
		function func1=new function(function.getFunc().get(funcnum));
		g2.setColor(Color.BLUE); 										//��ɫ��ͼ��
		for(int i=0;i<w;i++){
			x1 = (i-center.getX()) / kk;
			tn.setFloatnum(x1);
			func1.getRoot().unnumed();				//׼�����¼���
			func1.calculate(tnlist);
			y1=func1.getRoot().retfn();
			if(y1!=(double)1/0){
				Ellipse2D.Double ellipse2=new Ellipse2D.Double(center.getX() + x1*kk - rline,
															center.getY() - y1*kk - rline,
															rline + rline,
															rline + rline);  
				g2.draw(ellipse2); 
				g2.fill(ellipse2);
			}
		}
		
		g2.setColor(Color.RED); 										//��ɫ�ĺ�����
		g2.drawString(function.getFunc().get(funcnum).getFuncname() + " = " + function.getFunc().get(funcnum).getSourceexp(), 50, 50);
		
	} 
}

class coord{
	public static Point center2D(int cx,int cy, int x1,int y1){			//����任
		Point tmp=new Point(0,0);
		tmp.x = x1 - cx;
		tmp.y = cy - y1;
		return tmp;
	}
}