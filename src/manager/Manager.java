package manager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import spider.Spider;
import spider.Spider2;

public class Manager extends Thread {

	private static List<String> codes=new ArrayList<String>();
	private static Map<String, String> states = new HashMap<>();//状态 0失败 1成功
	private String code;
	
	private final Object lock = new Object();
    private boolean pause = false;
    
	public Manager(String code){
        this.code = code;
        codes.add(code);
        states.put(code,"下载中");
    }
	public Manager(){
		
    }
	
    public void run() {
    	/*
    	try {
        	states.put(code, new Spider2().run(code));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
    	super.run();
        while(true){
            while (pause){
                onPause();
            }
            try {
            	if(new Spider2().run(code)==1)
            	states.put(code, "已完成");
            	if(states.get(code).equals("已完成")) break;
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
                break;
            }
        }


        
    }

    public String getResult() {
		while(states.get(code).equals("下载中"))
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return states.get(code);
	}
    public List<String> getCodes() {
    	//System.out.println(codes);
    	return codes;
    }
    public Map<String, String> getStates() {
    	//System.out.println(states);
    	return states;
    }

    /*
	调用该方法实现线程的暂停
     */
    void pauseThread(){
        pause = true;
    }

    /*
    调用该方法实现恢复线程的运行
     */
    void resumeThread(){
        pause =false;
        synchronized (lock){
            lock.notify();
        }
    }
    
    /*
    这个方法只能在run 方法中实现，不然会阻塞主线程，导致页面无响应
     */
    void onPause() {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
}