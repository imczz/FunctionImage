package CompilationPrinciple;
import java.util.ArrayList;
//有限状态自动机，实现抽象父类的getchar方法
public class FiniteStateMachine extends AutomataMachine{
	
	ArrayList<Integer> err_state;				//错误的状态（便与调试）
	StringBuffer buffer;						//字符缓冲区
	
	public FiniteStateMachine(int mode,String args){			//构造方法
		start_state=0;										//起始状态0
		buffer=new StringBuffer();							//初始化缓冲区
		err_state=new ArrayList<Integer>();					//初始化错误状态集合
		if(args=="KTiT"){								//KT关键字;iT标识符
			name=args;
			
			end_state.add(2);
			
			err_state.add(3);
		}
		else if(args=="cT"){					//字符
			name=args;
			
			end_state.add(3);
			
			err_state.add(4);
			err_state.add(5);
		}
		else if(args=="sT"){					//字符串
			name=args;
			
			end_state.add(2);
			
			err_state.add(3);
		}
		else if(args=="CT"){					//常数
			name=args;
			
			end_state.add(3);
			end_state.add(4);
			
			err_state.add(5);
			err_state.add(6);
		}
		else if(args=="PT"){					//界符
			name=args;
			
			for(int i=1;i<=44;i++){
				if(i!=1 && i!=5 && i!=9 && i!=12 && i!=15 && i!= 18 && i!=21 && i!=24 && i!=28)
				end_state.add(i);
			}
			
			err_state.add(101);
		}
		else if(args=="others"){				//其他字符
			name=args;

			end_state.add(1);
			end_state.add(2);
			
			err_state.add(3);
		}
		
		if(mode==0){			//未准备好
			useable=0;
		}
		else if(mode==1){		//状态转换表的自动机
			useable=1;
		}
		else if(mode==2){		//if else临时自动机
			useable=2;
		}
	}
	
	public int state_change1(int tmpstate, char ch)   //if else临时自动机的转换函数（不改变当前状态，试探性的接收）
	{
		//int tmpstate = 0;
		if(name=="KTiT"){
			if(tmpstate==0)
			{
				if(ch>='a' && ch<='z' || ch>='A' && ch<='Z') tmpstate=1;
				else tmpstate=3;			//err
			}
			else if(tmpstate==1)
			{
				if(ch>='a' && ch<='z' || ch>='A' && ch<='Z' || ch>='0' && ch<='9' || ch=='_') tmpstate=1;
				else tmpstate=-2;
			}
			else
			{
				return 0;
			}
		}
		else if(name=="cT"){
			if(tmpstate==0)
			{
				if(ch=='\'') tmpstate=1;
				else tmpstate=4;				//err
			}
			else if(tmpstate==1)
			{
				tmpstate=2;
				//else tmpstate=2;
			}
			else if(tmpstate==2)
			{
				if(ch=='\'') tmpstate=3;
				else tmpstate=5;			//err
			}
			else
			{
				return 0;
			}
		}
		else if(name=="sT"){
			if(tmpstate==0)
			{
				if(ch=='\"') tmpstate=1;
				else tmpstate=3;				//err
			}
			else if(tmpstate==1)
			{
				if(ch!='\"') tmpstate=1;
				else tmpstate=2;
			}
			else
			{
				return 0;
			}
		}
		else if(name=="CT"){
			if(tmpstate==0)
			{
				if(ch>='0' && ch<='9') tmpstate=1;
				//else if(ch=='.')  tmpstate=2;
				else tmpstate=5;				//err
			}
			else if(tmpstate==1)
			{
				if(ch>='0' && ch<='9') tmpstate=1;
				else if(ch=='.')  tmpstate=2;
				else tmpstate=-3;
			}
			else if(tmpstate==2)
			{
				if(ch>='0' && ch<='9') tmpstate=2;
				else if(ch=='.')  tmpstate=6;			//err
				else tmpstate=-4;						//back one step
			}
			else
			{
				return 0;
			}
		}
		else if(name=="PT"){
			if(tmpstate==0)
			{
				if(ch=='>') tmpstate=1;
				else if(ch=='<')  tmpstate=5;
				else if(ch=='=')  tmpstate=9;
				else if(ch=='!')  tmpstate=12;
				else if(ch=='|')  tmpstate=15;
				else if(ch=='&')  tmpstate=18;
				else if(ch=='+')  tmpstate=21;
				else if(ch=='-')  tmpstate=24;
				else if(ch==':')  tmpstate=28;
				else if(ch=='*')  tmpstate=31;
				else if(ch=='/')  tmpstate=32;
				else if(ch=='(')  tmpstate=33;
				else if(ch==')')  tmpstate=34;
				else if(ch=='[')  tmpstate=35;
				else if(ch==']')  tmpstate=36;
				else if(ch=='{')  tmpstate=37;
				else if(ch=='}')  tmpstate=38;
				else if(ch==';')  tmpstate=39;
				else if(ch==',')  tmpstate=40;
				else if(ch=='.')  tmpstate=41;
				else if(ch=='%')  tmpstate=42;
				else if(ch=='?')  tmpstate=43;
				else if(ch=='^')  tmpstate=44;
				else tmpstate=101;				//err
			}
			else if(tmpstate==1)
			{
				if(ch=='=') tmpstate=2;
				else if(ch=='>')  tmpstate=3;
				else tmpstate=-4;
			}
			else if(tmpstate==5)
			{
				if(ch=='=') tmpstate=6;
				else if(ch=='<')  tmpstate=7;
				else tmpstate=-8;
			}
			else if(tmpstate==9)
			{
				if(ch=='=') tmpstate=10;
				else tmpstate=-11;
			}
			else if(tmpstate==12)
			{
				if(ch=='=') tmpstate=13;
				else tmpstate=-14;
			}
			else if(tmpstate==15)
			{
				if(ch=='|') tmpstate=16;
				else tmpstate=-17;
			}
			else if(tmpstate==18)
			{
				if(ch=='&') tmpstate=19;
				else tmpstate=-20;
			}
			else if(tmpstate==21)
			{
				if(ch=='+') tmpstate=22;
				else tmpstate=-23;
			}
			else if(tmpstate==24)
			{
				if(ch=='-') tmpstate=25;
				if(ch=='>') tmpstate=26;
				else tmpstate=-27;
			}
			else if(tmpstate==28)
			{
				if(ch==':') tmpstate=29;
				else tmpstate=-30;
			}
			else
			{
				return 0;
			}
		}
		if(name=="others"){
			if(tmpstate==0)
			{
				if(ch==' ' || ch=='\t' || ch=='\r' || ch== '\n' || ch=='\0') tmpstate=1;
				else if(ch>='a' && ch<='z' || ch>='A' && ch<='Z') tmpstate=-2;
				else if(ch>='0' && ch<='9') tmpstate=-2;
				else if(tokenlist.strtable.contains(ch)) tmpstate=-2;
				else tmpstate=3;			//err
			}
			else
			{
				return 0;
			}
		}
		return tmpstate;
	}
	
	public int getchar(char ch){				//
		//int tmpstate=0;
		if(useable==0){							//未开机
			System.out.println("automata machine must be prepared before use.");
		}
		else if(useable==2){					//ifelse的自动机
			state = state_change1(state,ch);	//下一个状态
			
			if(!end_state.contains(new Integer(Math.abs(state)))){		//不是结束状态
				if(err_state.contains(new Integer(state))) return -2;	//如果不是错误的状态
				buffer.append(ch);									//缓冲区增加当前字符
				return 0;						//下一个
			}
			else{						//结束状态
				if(state==0){			
					System.out.println("0?");		//刚开始就结束了?
				}
				if(state>0) buffer.append(ch);		//如果为正则接收，为负说明不属于当前token，需要再次接收
				return state;						//返回当前状态
			}
		}
		return -1;								//待开发
	}
}
