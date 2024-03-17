package hu.hl.gombnyomo_240226;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class Main {
	static Vector<Integer> x= new Vector<Integer>();
	static Vector<Integer> y= new Vector<Integer>();
	static final Timer timer= new Timer();
	static int meik;
	public static void main(String[] args) throws AWTException, ParserConfigurationException, SAXException, IOException {
		File file = new File("config.xml");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.parse(file);
		int i0= 0;
		while (i0<1 /*document.getElementsByTagName("config").getLength()*/) {
			Element config= (Element) document.getElementsByTagName("config").item(i0);
			int i1= 0;
			while (i1<config.getElementsByTagName("point").getLength()) {
				Element point= (Element) config.getElementsByTagName("point").item(i1);
				int i2= 0;
				while (i2<Integer.valueOf(point.getAttribute("n"))) {
					x.add(Integer.valueOf(point.getAttribute("x")));
					y.add(Integer.valueOf(point.getAttribute("y")));
					i2++;
				}
				i1++;
			}
			i0++;
		}
		
		Robot bot= new Robot();

		JFrame frame= new JFrame();
		frame.setBounds(x.firstElement(), y.firstElement(), 200, 128);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setVisible(true);
		
		JCheckBox checkbox;
		frame.add(checkbox= new JCheckBox());
		checkbox.setBounds(8, 8, 96, 24);
		checkbox.setText("Megz");
		checkbox.setEnabled(true);
		checkbox.setVisible(true);
		checkbox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (((JCheckBox) event.getSource()).isSelected()) {
					System.out.println("timer start");
					meik= 0;
				} else {
					System.out.println("timer stop");
				}
			}
		});
		
/*		
		JComboBox<String> combobox;
		frame.add(combobox= new JComboBox<String>());
		combobox.setBounds(8, 32, 96, 24);
		combobox.addItem("1736; 768");
		combobox.setVisible(true);
		combobox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
//				x= Integer.valueOf(((String) combobox.getSelectedItem()).split(";")[0].trim());
//				y= Integer.valueOf(((String) combobox.getSelectedItem()).split(";")[1].trim());
//				frame.setBounds(x-48, y-48, 200, 128);
				checkbox.setEnabled(true);
				checkbox.requestFocus();
			}
		});
*/		
		timer.scheduleAtFixedRate(
			new TimerTask() {
				public void run() {
					if (checkbox.isSelected()) {
						frame.setBounds(x.get(meik)-48, y.get(meik)-48, 200, 128);
						bot.mouseMove(x.get(meik), y.get(meik));
						bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);     
						bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);		
						meik= ++meik % x.size();
					}
				}
			},							
			1000,
			1000
		);
	}
}