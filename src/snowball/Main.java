package snowball;

import com.frame.MainFrame;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame jFrame = new JFrame("数据标注辅助软件");
        MainFrame mainFrame = new MainFrame(jFrame);
        mainFrame.buildFrame();
    }
}
