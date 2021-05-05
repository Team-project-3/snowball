package manager;
import java.io.IOException;

import spider.Spider;
import spider.Spider2;

public class Manager extends Thread {
	private String code;
	private int num=100;
    
    public Manager(String code){
        this.code = code;
    }
    
    public void run() {
        try {
			num = new Spider2().run(code);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	public int getResult() {
		while(num==100)
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return num;
	}
	
}