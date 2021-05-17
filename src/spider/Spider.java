﻿package spider;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Spider {
	static String cmd1 = "爬虫.py";
	static String cmd2 = "爬虫检查.py";
    static String conv = "python";
	public int run(String code) throws IOException, InterruptedException {
		//System.out.println(System.getProperty("user.dir"));//user.dir指定了当前的路径
	    String[] command = new String[]{conv,cmd1,code};
	    BufferedReader br = null;
	    BufferedReader brError = null;
        String line = null;
        Process p;
	    System.out.println("开始爬取"+" 子线程ID:"+Thread.currentThread().getId());
	    try {
	    	p = Runtime.getRuntime().exec(command);
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            brError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            while ((line = br.readLine()) != null  || (line = brError.readLine()) != null) {
            	System.out.println(line+" 子线程ID:"+Thread.currentThread().getId());
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
	    for(int i=0;i<3;i++) {
	    	if(check(code)) {
	    		return 1;//返回1代表成功
	    	}
	    	else if(i<2){
	    		System.out.println("开始爬取"+" 子线程ID:"+Thread.currentThread().getId());
	    	    try {
	    	    	p = Runtime.getRuntime().exec(command);
	                line = null;
	                br = new BufferedReader(new InputStreamReader(p.getInputStream()));
	                brError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
	                while ((line = br.readLine()) != null  || (line = brError.readLine()) != null) {
	                	System.out.println(line+" 子线程ID:"+Thread.currentThread().getId());
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
	    	}
	    }
	    return 0;
        }

	
	public static boolean check(String code) throws IOException, InterruptedException {
		String[] command = new String[]{conv,cmd2,code};
	    BufferedReader br = null;
	    BufferedReader brError = null;
	    System.out.println("开始检查"+" 子线程ID:"+Thread.currentThread().getId());
	    try {
	    	Process p = Runtime.getRuntime().exec(command);
            String line = null;
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            brError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            while ((line = br.readLine()) != null  || (line = brError.readLine()) != null) {
            	if(line.equals("文件不存在")) {
            		System.out.println("文件不存在"+" 子线程ID:"+Thread.currentThread().getId());
                	return false;
                }
                else if(line.equals("爬取不完整")) {
                	System.out.println("爬取不完整"+" 子线程ID:"+Thread.currentThread().getId());
                	return false;
                }
                else if(line.equals("爬取完整")) {
                	System.out.println("爬取完整"+" 子线程ID:"+Thread.currentThread().getId());
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

}
