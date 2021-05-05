package spider;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Spider {
	static String cmd1 = "����.py";
	static String cmd2 = "������.py";
    static String code = "SH600415";
    static String conv = "python";
    static int spiderFlag = -1;//0��ʧ�� 1���ɹ������Ե�ʱ���
    static int spiderCheck = -1;//0���ļ������� 1����ȡ������ 2����ȡ���������Ե�ʱ���
	public void main(String[] args) throws IOException, InterruptedException {
		//System.out.println(System.getProperty("user.dir"));//user.dirָ���˵�ǰ��·��
	    String[] command = new String[]{conv,cmd1,code};
	    BufferedReader br = null;
	    BufferedReader brError = null;
	    System.out.println("��ʼ��ȡ");
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
	    	System.out.println("��ȡʧ��");
	    	e.printStackTrace();
	    }finally {
	    	spiderFlag=1;
	    	System.out.println("��ȡ���");
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
		String[] command = new String[]{conv,cmd2,code};
	    BufferedReader br = null;
	    BufferedReader brError = null;
	    System.out.println("��ʼ���");
	    Process p = Runtime.getRuntime().exec(command);
        String line = null;
        br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        brError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        while ((line = br.readLine()) != null  || (line = brError.readLine()) != null) {
            if(line.equals("�ļ�������")) {
            	System.out.println(line);
            	spiderCheck = 0;
            }
            else if(line.equals("��ȡ������")) {
            	System.out.println(line);
            	spiderCheck = 1;
            }
            else if(line.equals("��ȡ����")) {
            	System.out.println(line);
            	spiderCheck = 2;
            }
            
        }
        System.out.println("������");
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