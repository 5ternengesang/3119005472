
package sizeyunsuan;
import java.io.BufferedWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Handler;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.sound.sampled.spi.AudioFileReader;



public class Arithmetic {
	
	public static void main(String[] args){
	
	        creatArith();//产生运算式
	}
	//产生随机数
	public static int getNumber(int num1){  
	        int number = 0;  
	        Random num = new Random();  
	        number = num.nextInt(num1+1);  
	        return number;  
	} 
	//随机产生运算符号
	public static char getOperator(){  
        char operator = 0;  
        Random ran = new Random();  
        int i = ran.nextInt(4);  
        switch(i){  
            case 0:  
                operator = '+';  
                break;  
            case 1:  
                operator = '-';  
                break;  
            case 2:  
                operator = '*';  
                break;  
            case 3:  
                operator = '/';  
                break;  
        }  
        return operator;  
     } 
	
	//产生随机运算式
	static ScriptEngine js = new ScriptEngineManager().getEngineByName("JavaScript");
	public static void creatArith(){
		boolean flag=true;
		int num=0;
		int num1=0;
		do {
			System.out.print("输入需要产生的四则运算的个数：");
			Scanner arith_num = new Scanner(System.in);
			num=arith_num.nextInt();
			System.out.print("输入需要产生的四则运算数字范围：");
			Scanner arith_num1 = new Scanner(System.in);
			num1=arith_num1.nextInt();
			if(num>=1&&num1>1) {
				flag = true;
			}
			else {
				flag=false;
				System.out.println("输入有误");
			}
		}while(flag==false);
		ArrayList<String> arithList = new ArrayList<String>();//运算式
		for(int i=0;i<num;i++)
		{
			//产生4个运算数
			int num_number = 4;			
			
			String arr = "";
			for(int j=0;j<num_number;j++){
				int number = getNumber(num1);
				String operator = String.valueOf(getOperator());
				arr += number + operator;
			}
			arr =arr.substring(0,arr.length()-1);
			//将arr转化为数组
			char[] arr_str= arr.toCharArray();
			String arrString = "";
			ArrayList<String> arrslist1 = new ArrayList<String>();
			ArrayList<String> arrslist2 = new ArrayList<String>();
			ArrayList<String> arrslist = new ArrayList<String>();
			//存储运算符
			for(int j=0;j<arr_str.length;j++) {
				if(arr_str[j]=='+'||arr_str[j]=='-'||arr_str[j]=='*'||arr_str[j]=='/') {	
					arrString=String.valueOf(arr_str[j]);
					arr_str[j]=',';
					arrslist1.add(arrString);
				}
			}
			//存储运算数
			String s = new String(arr_str);
			String[] ss = s.split(",");
			for(int k=0;k<ss.length;k++) {
				arrslist2.add(ss[k]);
			}
			//生成运算式
			String[] arrs1 = arrslist1.toArray(new String[arrslist1.size()]);//运算符
			String[] arrs2 = arrslist2.toArray(new String[arrslist2.size()]);//运算数
			
			for(int m=0;m<arrs1.length;m++){
				arrslist.add(arrs2[m]);//运算数
				arrslist.add(arrs1[m]);//运算符
			}
			arrslist.add(arrs2[arrs2.length-1]);
			String[] arrs = arrslist.toArray(new String[arrslist2.size()]);//运算式
			arr="";//初始化为空
			check_arith(arrs);
			for(int t=0;t<arrs.length;t++) {
				arr+=arrs[t];
			}
			try {
				check_arith_question(arrs,arr);
			} catch (ScriptException e) {
				e.printStackTrace();
			}
			arr="";
			for(int t=0;t<arrs.length;t++) {
				arr+=arrs[t];
			}
			arithList.add(arr);
			
		}
		//输出运算式
		
		for(String arr: arithList){
			try {
				System.out.println(arr+"="+js.eval(arr));
			} catch (ScriptException e) {
				e.printStackTrace();
			} 	
		}
		try {
			filewriter(arithList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//判断是否产生负数与小数
	public static void  check_arith(String arr[]){
		for(int i=0;i<arr.length;i++){
			//判断是否产生负数	
			if(arr[i].contains("-")){
				if(Integer.valueOf(arr[i-1])<Integer.valueOf(arr[i+1])){
					arr[i]="+";
				}
			}
			//判断是否小数
			if(arr[i].contains("/")){
				int a = Integer.valueOf(arr[i-1]);
				int b = Integer.valueOf(arr[i+1]);
				int x = a%b;
				if(x!=0){
					arr[i]="+";
				}
			}
		}
	}
	//判断结果是否产生小数
	public static void  check_arith_question(String arr[],String arrString) throws ScriptException{
			for(int i=0;i<arr.length;i++){
				String str = js.eval(arrString).toString();
				if(Integer.valueOf(str)<0) {
					if(arr[i].contains("-")){
						arr[i]="+";
					}
				}
							
			}
	}
	//存储运算式
	//封装目的地
	public static void filewriter(ArrayList<String> arithList) throws IOException {
		//封装存储位置
		BufferedWriter BufferedWriterbw = new BufferedWriter(new FileWriter("result.txt"));
		  BufferedWriterbw.newLine();
		  for(String s : arithList){
		        //写数据
			  try {
				BufferedWriterbw.write(s+"="+js.eval(s));
			} catch (ScriptException e) {
				e.printStackTrace();
			}
			  BufferedWriterbw.newLine();
			  BufferedWriterbw.flush();
		  }
	}
    
}


