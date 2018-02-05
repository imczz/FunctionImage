package Frames;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import CompilationPrinciple.lexanal;
import FunctionTree.function;
import FunctionTree.tree_node;
//从函数的参数列表中选择自变量
public class funceditor extends JFrame implements ItemListener{
	  /**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	private JButton btnfunc=new JButton("确认更改");
	JCheckBox chkbox[];
	JLabel names[]; 
	JTextArea number[];
	JLabel par = new JLabel("选择自变量");
	private int num;
	int tmppnum;
	
	public funceditor(String title)
	{
		this();
		setTitle(title);
	}
	private funceditor(){
		setLayout(new FlowLayout(FlowLayout.LEADING));
		setSize(270, 400);
		setResizable(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		btnfunc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				function.getFunc().get(function.getFselected()).independent = new ArrayList<Integer>();
				tmppnum=0;
				for(int i=0;i<num;i++){
					if (chkbox[i].isSelected()){
						function.getFunc().get(function.getFselected()).independent.add(i);
						tmppnum++;
					}
					else{
						if(number[i].getText().equals("")){
							JOptionPane.showMessageDialog(null, "请把非自变量的参数初始化","选择自变量", JOptionPane.ERROR_MESSAGE);
							return;
						}
						lexanal lex=new lexanal(new StringBuffer(number[i].getText()+"#"));		//允许输入表达式
						lex.calculatetoken();
						function func1=new function("fun_exp1",lex.getTklist());
						func1.start();
						func1.calculate();
						if(!func1.getRoot().isIfnumbered()){
							JOptionPane.showMessageDialog(null, "非自变量必须填充为数字","选择自变量", JOptionPane.ERROR_MESSAGE);
							return;
						}
						else{
							function.getFunc().get(function.getFselected()).getParamtable().get(i).setPoint(new tree_node(func1.getRoot()));
							function.getFunc().get(function.getFselected()).getParamtable().get(i).getPoint().setIndexoffunction(0);	//常数
						}
					}
				}
				function.getFunc().get(function.getFselected()).setParam_num(tmppnum);
				function.setFselected(-1);
				dispose();
			}
		});

		if(function.getFselected()==-1) return;
		num=function.getFunc().get(function.getFselected()).getParamtable().size();
		if(num<=0) return;
		chkbox = new JCheckBox[num];
		names = new JLabel[num];
		number = new JTextArea[num];
		       
		JPanel checkPanel = new JPanel(new GridLayout(0,3));
		       
		for(int i=0;i<num;i++){
			number[i]=new JTextArea(1,5);
			number[i].setEditable(true);
			chkbox[i]=new JCheckBox();
			if(function.getFunc().get(function.getFselected()).independent.contains(i)){
				chkbox[i].setSelected(true);
			}
			else{
				chkbox[i].setSelected(false);
				if(function.getFunc().get(function.getFselected()).getParamtable().get(i).getPoint().ifint())
				{
					number[i].setText("" + function.getFunc().get(function.getFselected()).getParamtable().get(i).getPoint().getIntnum());
				}
				else{
					number[i].setText("" +  function.getFunc().get(function.getFselected()).getParamtable().get(i).getPoint().getFloatnum());
				}
			}
			chkbox[i].addItemListener(this);
			names[i]=new JLabel(function.getFunc().get(function.getFselected()).getParamtable().get(i).getName());
			checkPanel.add(chkbox[i]);
			checkPanel.add(names[i]);
			checkPanel.add(number[i]);
		}
		//使用panal并没有使界面看起来漂亮一些
		JPanel upper1 = new JPanel(new GridLayout(0,2));
		upper1.add(par);
		upper1.add(checkPanel);
		
		JPanel under2 = new JPanel(new GridLayout(2,0));
		under2.add(upper1);
		under2.add(btnfunc);
		
		add(under2);
	}
	
	
	@Override
	public void itemStateChanged(ItemEvent e) {			//未使用的复选框动作
		//Object source = e.getItemSelectable();
	}
}