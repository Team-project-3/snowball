﻿package spider;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Spider2 {
	
	static String cmd1 = "爬虫.py";
	static String cmd2 = "爬虫检查.py";
    static String conv = "python";
	public int run(String code) throws IOException, InterruptedException {
		//System.out.println(System.getProperty("user.dir"));//user.dir指定了当前的路径
	    String[] command = new String[]{conv,cmd1,code};

        Process p = Runtime.getRuntime().exec(command);
	    System.out.println(code+":开始爬取");
	    new InputStreamRunnable(p.getErrorStream()).start();
	    new InputStreamRunnable(p.getInputStream()).start();
	    p.waitFor();
	    p.destroy();
	    for(int i=0;i<3;i++) {
	    	if(check(code)) {
	    		return 1;//返回1代表成功
	    	}
	    	else if(i<2){
	    		p = Runtime.getRuntime().exec(command);
	    	    System.out.println(code+":开始爬取");
	    	    new InputStreamRunnable(p.getErrorStream()).start();
	    	    new InputStreamRunnable(p.getInputStream()).start();
	    	    p.waitFor();
	    	    p.destroy();
	    	}
	    }
	    return 0;
        }

	
	public static boolean check(String code) throws IOException, InterruptedException {
		String[] command = new String[]{conv,cmd2,code};
	    BufferedReader br = null;
	    BufferedReader brError = null;
	    System.out.println(code+":开始检查");
	    try {
	    	Process p = Runtime.getRuntime().exec(command);
            String line = null;
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            brError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            while ((line = br.readLine()) != null  || (line = brError.readLine()) != null) {
            	if(line.equals("文件不存在")) {
            		System.out.println(code+":文件不存在");
                	return false;
                }
                else if(line.equals("爬取不完整")) {
                	System.out.println(code+":爬取不完整");
                	return false;
                }
                else if(line.equals("爬取完整")) {
                	System.out.println(code+":爬取完整");
                	return true;
                }
            }
	    }catch(Exception e){
	    	e.printStackTrace();
	    }finally {
	    	if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
	    }
	    

	    
        return false;
	    }
	
	class InputStreamRunnable extends Thread {
	    BufferedReader bReader = null;
	    public InputStreamRunnable(InputStream is) {
	        try {
	            bReader = new BufferedReader(new InputStreamReader(new BufferedInputStream(is)));
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }
	    public void run() {
	        String line;
	        try {
	            while ((line = bReader.readLine()) != null) {
	            	System.out.println(line);
	            }
	            bReader.close();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		Spider2 s=new Spider2();
		s.run("SH600415");
	}
}