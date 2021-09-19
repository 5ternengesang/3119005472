package paperchecl;
import java.io.*;
import java.util.Scanner;
public class skbpapercheck {
	  public static void main(String[] args) {
	    	String sourcePath;
	        String targetPath;
	        String ansPath;
	    	@SuppressWarnings("resource")
			Scanner input = new Scanner(System.in);
	        System.out.println("������ԭ���ļ�:");
	        sourcePath = input.nextLine();
	        System.out.println("�����볭Ϯ�����ĵ��ļ�:");
	        targetPath = input.nextLine();
	        System.out.println("��������ļ�:");
	        ansPath = input.nextLine();
	        
	        long startTime=System.currentTimeMillis();
	        
	        String source = disposeFile(sourcePath);   //String source = disposeFile("C:\\Users\\Sternengesang\\orig.txt");
	        String target = disposeFile(targetPath);   //String target = disposeFile("C:\\Users\\Sternengesang\\orig_0.8_add.txt");




	        float similaryRate = Levenshtein_Distance(source, target);
	        String similary = String.format("%.1f", similaryRate);
	        System.out.println("�������ƶ�Ϊ" + (similary) + "%");
	        
	        
	        File file = new File(ansPath);
	        try {
	            Writer writer = new FileWriter(file,false);
	            writer.write((sourcePath) + "��" + (targetPath) + "��");
	            writer.write("�������ƶ�Ϊ   " + (similary) + "%");
	            writer.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	        long endTime=System.currentTimeMillis(); //��ȡ����ʱ��
	        System.out.println("�ܼ���ʱ"+(endTime-startTime)+"ms");
	    }

	    public static float Levenshtein_Distance(String source, String target){

	        int[][] matrix;
	        int srcLen = source.length();
	        int targetLen = target.length();
	        int i,j;
	        int temp;

	        if (srcLen == 0 || targetLen == 0){
	            return 0;
	        }

	        matrix = new int[srcLen + 1][targetLen + 1];

	        for (i = 0; i <= srcLen; i++){//��ʼ����һ��
	            matrix[i][0] = i;
	        }

	        for (j = 0; j <= targetLen; j++){//��ʼ����һ��
	            matrix[0][j] = j;
	        }

	        for (i = 1; i <= srcLen; i++){//
	            for (j = 1; j <= targetLen; j++){
	                if (source.charAt(i -1) == target.charAt(j - 1)){
	                    temp = 0;
	                }else {
	                    temp = 1;
	                }

	                matrix[i][j] = Math.min(Math.min(matrix[i - 1][j] + 1, matrix[i][j - 1] + 1), 
	                						matrix[i - 1][j - 1] + temp);
	            }
	        }

	        return (1 - (float) matrix[srcLen][targetLen] / Math.max(source.length(), target.length())) * 100F;
	    }

	    public static String disposeFile(String filePath){

	        StringBuilder buffer = new StringBuilder();
	        BufferedReader reader;
	        try {
	        	reader = new BufferedReader(new FileReader(filePath));
	            String s;
	            while((s = reader.readLine()) != null){
	                buffer.append(s.trim());
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        String[] splitAddress = buffer.toString().split("[^0-9a-zA-Z\\u4e00-\\u9fa5]");
	        StringBuilder result = new StringBuilder();
	        for (String address : splitAddress) {
	        	result.append(address);
	        }

	        return  result.toString();
	    }
}
