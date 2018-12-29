package Frames;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import CompilationPrinciple.lexanal;
import FunctionTree.function;
//函数控制台
public class functioncontrol extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton newfunc=new JButton(" 新 建 函 数 ");
	private JButton editfunc=new JButton(" 编 辑 函 数 ");
	private JButton seefunc=new JButton(" 查 看 函 数 ");
	private JButton calexp=new JButton(" 计 算 方 程 ");
	private JTextArea result=new JTextArea(1,20);
	private JButton delfunc=new JButton(" 删 除 函 数 ");
	private JButton fxgraph=new JButton(" 函 数 图 像 ");
	private JButton parameq=new JButton(" 参 数 方 程 ");
	private JButton exit=new JButton(" 退            出 ");
	private JTextArea fname=new JTextArea(1,20);
	private JTextArea exp=new JTextArea(1,20);
	private JLabel label1= new JLabel("函数名");
	private JLabel label2= new JLabel("表达式");
	private JLabel label3= new JLabel("结果");
	private List funclist= new List();
	StringBuffer tmpbuf;
	
	public functioncontrol(String title)
	{
		this();
		setTitle(title);
	}
	
	private functioncontrol(){
		setLayout(new FlowLayout(FlowLayout.CENTER));
		setSize(260, 400);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		fname.setEditable(true);
		exp.setEditable(true);
		result.setEditable(false);
		funclist.setSize(240, 200);
		JPanel panal1 = new JPanel(new GridLayout(0,1));
		JPanel panal2 = new JPanel(new GridLayout(0,2));
		JPanel panal3 = new JPanel(new GridLayout(0,1));
		panal1.add(label1);
		panal1.add(fname);
		panal1.add(label2);
		panal1.add(exp);
		panal1.add(label3);
		panal1.add(result);
		panal2.add(newfunc);
		panal2.add(editfunc);
		panal2.add(seefunc);
		panal2.add(calexp);
		panal2.add(delfunc);
		panal2.add(fxgraph);
		panal2.add(parameq);
		panal2.add(exit);
		panal3.add(funclist);
		add(panal1);
		add(panal2);
		add(panal3);
		rstlist();				//控制列表
		newfunc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(fname.getText().equals("")) return;
				if(function.ifexistfunc(fname.getText())!=-1){
					JOptionPane.showMessageDialog(newfunc, "函数名重复","重复", JOptionPane.PLAIN_MESSAGE);
					return;
				}
				if(exp.getText().equals("")){
					JOptionPane.showMessageDialog(newfunc, "表达式不能为空","空表达式", JOptionPane.ERROR_MESSAGE);
					return;
				}
				lexanal lex=new lexanal(new StringBuffer("(" + exp.getText() + ")"));
				lex.calculatetoken();
				function func1=new function(fname.getText(),lex.getTklist());
				func1.start();
				function.addfunc(func1);
				func1.setsource(exp.getText());
				rstlist();
			}
		});
		editfunc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int funcnum;
				if(fname.getText().equals("")) return;
				funcnum=function.ifexistfunc(fname.getText());
				if(funcnum!=-1){
					if(funcnum<function.getSelfstart()){
						JOptionPane.showMessageDialog(newfunc, "内置函数不能修改","修改", JOptionPane.ERROR_MESSAGE);
						return;				//内置函数
					}
					if(function.getFunc().get(funcnum).getParam_num()<=0) return;
					function.setFselected(funcnum);
					funceditor fedit=new funceditor("编辑函数");
					fedit.pack();
					fedit.setVisible(true);
					rstlist();
				}
			}
		});
		seefunc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int funcnum;
				if(fname.getText().equals("")) return;
				funcnum=function.ifexistfunc(fname.getText());
				if(funcnum!=-1){
					exp.setText(function.getFunc().get(funcnum).retsource());
				}		
			}
		});
		calexp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(exp.getText().equals("")) return ;
				lexanal lex=new lexanal(new StringBuffer("(" + exp.getText()+")#"));
				lex.calculatetoken();
				function func1=new function("fun_exp1",lex.getTklist());
				func1.start();
				if(func1.calculate()==1){
					tmpbuf=new StringBuffer();
					tmpbuf.append("结果是: ");
					if(func1.getRoot().getFloatnum()==0){
						tmpbuf.append(func1.getRoot().getIntnum());
					}
					else{
						tmpbuf.append(func1.getRoot().getFloatnum());
					}
					JOptionPane.showMessageDialog(null, tmpbuf ,"结果", JOptionPane.PLAIN_MESSAGE);
					result.setText(tmpbuf.toString());
				}
				else{
					JOptionPane.showMessageDialog(newfunc, "不能计算","错误", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		delfunc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int funcnum;
				if(fname.getText().equals("")) return;
				funcnum=function.ifexistfunc(fname.getText());
				if(funcnum!=-1){
					if(funcnum<function.getSelfstart()){
						JOptionPane.showMessageDialog(newfunc, "内置函数不能删除","删除", JOptionPane.ERROR_MESSAGE);
						return;				//内置函数
					}
					tmpbuf=new StringBuffer();
					tmpbuf.append("确认删除函数 " + function.getFunc().get(funcnum).getFuncname() + " 吗?");
					int n = JOptionPane.showConfirmDialog(null, tmpbuf.toString(), "确认对话框", JOptionPane.YES_NO_OPTION);   
					if (n == JOptionPane.YES_OPTION) {   
					    function.getFunc().remove(funcnum); 
					}
					else if(n== JOptionPane.NO_OPTION){
						;
					}
				}
				rstlist();
			}
		});
		fxgraph.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int funcnum;
				if(fname.getText().equals("")){
					JOptionPane.showMessageDialog(newfunc, "请输入一元函数的名字","函数名", JOptionPane.ERROR_MESSAGE);
					return;
				}
				funcnum=function.ifexistfunc(fname.getText());
				if(funcnum!=-1){
					if(funcnum<function.getSelfstart()){
						//JOptionPane.showMessageDialog(newfunc, "自定义函数才能画图","画图", JOptionPane.ERROR_MESSAGE);
						return;
					}
					if(function.getFunc().get(funcnum).getParam_num()!=1){
						JOptionPane.showMessageDialog(newfunc, "函数必须是一元函数","一元函数", JOptionPane.ERROR_MESSAGE);
						return;
					}
					function.setFselected(funcnum);
					funcgraph fgraph=new funcgraph("函数图像");
					fgraph.setVisible(true);
				}
			}
		});
		parameq.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int funcnum1,funcnum2;
				if(fname.getText().equals("") || exp.getText().equals("")){
					JOptionPane.showMessageDialog(newfunc, "请输入两个一元函数的名字","函数名", JOptionPane.ERROR_MESSAGE);
					return;
				}
				funcnum1=function.ifexistfunc(fname.getText());
				funcnum2=function.ifexistfunc(exp.getText());
				if(funcnum1!=-1 && funcnum2!=-1){
					if(funcnum1<function.getSelfstart() || funcnum2<function.getSelfstart()){
						JOptionPane.showMessageDialog(newfunc, "自定义函数才能画图","画图", JOptionPane.ERROR_MESSAGE);
						return;
					}
					if(function.getFunc().get(funcnum1).getParam_num()!=1 || function.getFunc().get(funcnum2).getParam_num()!=1){
						JOptionPane.showMessageDialog(newfunc, "两个函数都应该是一元函数","一元函数", JOptionPane.ERROR_MESSAGE);
						return;
					}
					function.setFselected(funcnum1);
					function.setFselected2(funcnum2);
					parametric p2graph=new parametric("函数图像");
					p2graph.setVisible(true);
				}
			}
		});
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		funclist.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fname.setText(function.getFunc().get(funclist.getSelectedIndex()+function.getSelfstart()).getFuncname());
			}
		});
	}
	
	public void rstlist(){
		StringBuffer tmpstr;
		funclist.removeAll();
		function tmpfunc;
		for(int i=function.getSelfstart();i<function.getFunc().size();i++){
			tmpstr = new StringBuffer();
			tmpfunc=function.getFunc().get(i);
			tmpstr.append(tmpfunc.getFuncname() + "(");
			for(int i1=0;i1<tmpfunc.getParam_num();i1++){
				if(i1!=0)tmpstr.append(", ");
				tmpstr.append(tmpfunc.getParamtable().get(tmpfunc.getIndependent().get(i1)).getName());
			}
			tmpstr.append(" );");
			funclist.add(tmpstr.toString());
		}
	}
}
