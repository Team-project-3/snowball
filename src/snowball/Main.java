package snowball;

import com.frame.MainFrame;



import javax.swing.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
	static Logger logger = LogManager.getLogger(Main.class.getName());
    public static void main(String[] args) {	
    
    	
        JFrame jFrame = new JFrame("数据标注辅助软件");
        MainFrame mainFrame = new MainFrame(jFrame);
        mainFrame.buildFrame();
        logger.entry();
        logger.error("Did it again!");   //error级别的信息，参数就是你输出的信息
        logger.info("我是info信息");    //info级别的信息
        logger.debug("我是debug信息");
        logger.warn("我是warn信息");
        logger.fatal("我是fatal信息");
        logger.exit();    //和entry()对应的结束方法，和logger.trace("exit");一个意思
    }
}
