package com.frame;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import com.data.Label;


public class AnalyseDialog{
	private Map<Label, ArrayList<Integer>> table;
	private String picPath =  "./resource/pie.jpg";
	private ArrayList<Label> labels;
	private int picW=400, picH=400;
	private JLabel imageLabel;
	private ImageIcon image;
	private JDialog jDialog;
	private Label selectLabel;
	
	public AnalyseDialog(ArrayList<Label> labels, Map<Label, ArrayList<Integer>> table) {
		this.table = table;
		this.labels = labels;
		
		if(labels.size() < 1) {
			this.selectLabel = null;
		} else {
			this.selectLabel = labels.get(0);
		}
		
		genPic();
		
		image = new ImageIcon(picPath);
        imageLabel = new JLabel();
	}
	
	public void show(Frame frame) {
		
		 Logger logger2 = LogManager.getLogger(AddLabelDialog.class.getName());
	        logger2.info("用户进入统计分析对话框"); 
        //1.弹出对话框
        jDialog = new JDialog(frame, "统计分析", true);
        jDialog.setBounds(600,250,picW+20,picH+100);
        
        //Box box = Box.createVerticalBox();
        JPanel jp = new JPanel();
        
        image.getImage().flush();
        imageLabel.setIcon(image);
        JComboBox<String> jComboBox = new JComboBox<>();
        
        imageLabel.setBounds(0, 0, picW, picH);
        
        for(Label label : this.labels) {
        	jComboBox.addItem(label.getContent());
        }
        jComboBox.setSize(20, 20);
        jComboBox.addItemListener(new ComboBoxListen());
        
        jp.setSize(50,  50);
        jp.add(jComboBox);
        
        jDialog.getContentPane().add(jp, BorderLayout.NORTH);
        jDialog.getContentPane().add(imageLabel, BorderLayout.CENTER);
        
        
        jDialog.setVisible(true);
	}
	
	private void genPic() {
		DefaultPieDataset pds = new DefaultPieDataset();
		if (selectLabel == null) {
			createPieChart(pds, picPath);
			return;
		}
		
		int len = table.get(selectLabel).size()-1;
		ArrayList<String> options = selectLabel.getOptions();
		ArrayList<Integer> labelSum = table.get(selectLabel);
		
		System.out.print("len:");
		System.out.println(len);
		for(int i=0; i<len; ++i) {
			pds.setValue(options.get(i), labelSum.get(i));
		}
		pds.setValue("未标注", labelSum.get(len));
		
		createPieChart(pds, picPath);
	}
	
	public void flush() {
		genPic();
		image.getImage().flush();
		imageLabel.setIcon(image);
		jDialog.repaint();
	}
	
	private class ComboBoxListen implements ItemListener{
		

		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			if(e.getStateChange() == ItemEvent.SELECTED){
				String selectString = (String) e.getItem();
	            for(Label label : labels) {
	            	if(label.getContent().equals(selectString)) {
	            		selectLabel = label;
	            		flush();
	            	}
	            }
	        }
		}
	}

	private void createPieChart(DefaultPieDataset pds, String filePath) {
        try {
            // 分别是:显示图表的标题、需要提供对应图表的DateSet对象、是否显示图例、是否生成贴士以及是否生成URL链接
            JFreeChart chart = ChartFactory.createPieChart("", pds, false, false, true);
            // 如果不使用Font,中文将显示不出来
            Font font = new Font("宋体", Font.BOLD, 20);
            // 设置图片标题的字体
            chart.getTitle().setFont(font);
            // 得到图块,准备设置标签的字体
            PiePlot plot = (PiePlot) chart.getPlot();
            // 设置标签字体
            plot.setLabelFont(font);
            plot.setStartAngle(new Float(3.14f / 2f));
            // 设置plot的前景色透明度
            plot.setForegroundAlpha(0.7f);
            // 设置plot的背景色透明度
            plot.setBackgroundAlpha(0.0f);
            // 设置标签生成器(默认{0})
            // {0}:key {1}:value {2}:百分比 {3}:sum
            plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}({1}占{2})"));
            // 将内存中的图片写到本地硬盘
            ChartUtilities.saveChartAsJPEG(new File(filePath), chart, picW, picH);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
