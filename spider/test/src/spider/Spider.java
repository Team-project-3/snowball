package spider;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Spider {
	//E:\\学习\\团队项目开发\\workspace\\test\\
	static String cmd1 = "爬虫.exe";
	static String cmd2 = "爬虫检查.exe";
    static String code = "SH600415";
    static int spiderFlag = -1;//0：失败 1：成功
    static int spiderCheck = -1;//0：文件不存在 1：爬取不完整 2：爬取完整
	public void main(String[] args) throws IOException, InterruptedException {
		//System.out.println(System.getProperty("user.dir"));//user.dir指定了当前的路径
	    String[] command = new String[]{cmd1,code};
	    BufferedReader br = null;
	    BufferedReader brError = null;
	    System.out.println("开始爬取");
	    try {
	    	Process p = Runtime.getRuntime().exec(command);
            String line = null;
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            brError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            while ((line = br.readLine()) != null  || (line = brError.readLine()) != null) {
                System.out.println(line);
            }
	    }catch(Exception e){
	    	spiderFlag=0;
	    	System.out.println("爬取失败");
	    	e.printStackTrace();
	    }finally {
	    	spiderFlag=1;
	    	System.out.println("爬取完成");
	    	if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
	    }
	    //check();
	    
	    }	
	public void check() throws IOException {
		String[] command = new String[]{cmd2,code};
	    BufferedReader br = null;
	    BufferedReader brError = null;
	    System.out.println("开始检查");
	    Process p = Runtime.getRuntime().exec(command);
        String line = null;
        br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        brError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        while ((line = br.readLine()) != null  || (line = brError.readLine()) != null) {
            if(line.equals("文件不存在")) {
            	System.out.println(line);
            	spiderCheck = 0;
            }
            else if(line.equals("爬取不完整")) {
            	System.out.println(line);
            	spiderCheck = 1;
            }
            else if(line.equals("爬取完整")) {
            	System.out.println(line);
            	spiderCheck = 2;
            }
            
        }
        System.out.println("检查结束");
        if (br != null) {
            try {
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}
	public int getResult() {
        return spiderFlag;
    }
	public int getCheck() {
        return spiderCheck;
    }
}
