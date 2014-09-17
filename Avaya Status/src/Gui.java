import java.awt.AWTException;
import java.awt.CheckboxMenuItem;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URL;

import javax.swing.*;

public class Gui{
	
	JFrame frame;
	JFrame frame2;
	static JFrame frame3;
	JLabel popuptext;
	JLabel status1;
	JLabel time1;
	String status;
	String time;
	static TrayIcon trayIcon;
	static MenuItem time2 = new MenuItem("");
	static MenuItem status2 = new MenuItem("");
	
    public Gui() {
        /* Use an appropriate Look and Feel */
        try {
        	UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        //Schedule a job for the event-dispatching thread:
        //adding TrayIcon.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
	
	public void openFrame(){
		frame = new JFrame();
		frame.setTitle("Avaya Status Checker");
		frame.setSize(250,100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		
		status1 = new JLabel("Geo Work");
		time1 = new JLabel("1:45:23");
		JLabel statusLabel = new JLabel("Status:");
		JLabel timeLabel = new JLabel("Time:");
		
		panel.add(statusLabel);
		panel.add(status1);
		panel.add(timeLabel);
		panel.add(time1);
		
		frame.getContentPane().add(panel);
		
		frame.setVisible(true);
	}
	
	public void StatusPopup(){
		frame2 = new JFrame("!!!Attention!!!");
		frame2.setAlwaysOnTop(true);
		
		popuptext = new JLabel();
		frame2.add(popuptext);
		
		frame2.setLocationByPlatform(true);
		//frame2.pack();
		frame2.setSize(250,100);
		frame2.setVisible(true);
		
	}
	
	public static void showOptions(){
		JButton button1 = new JButton("Cancel");
		JButton button2 = new JButton("Save");
		JLabel label = new JLabel("Ldap:");
		JTextField textField = new JTextField();
		
		frame3 = new JFrame();
		frame3.setTitle("Options");
		frame3.setSize(250,100);
		frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame3.getContentPane().setLayout(new BoxLayout(frame3.getContentPane(), BoxLayout.X_AXIS));
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		panel.add(label);
		panel.add(textField);
		panel.add(button1);
		panel.add(button2);
		
		frame3.getContentPane().add(panel);
		
		frame3.setVisible(true);
	}
	
	public void updatePopup(String content){
		if(popuptext != null){
		popuptext.setText(content);
		frame2.pack();
		}
	}
	
	public void closePopup(){
		try{
		frame2.dispose();
		} catch (Exception e){
			
		}
	}
	
	public void setStatus(String value){
		status1.setText(value);
	}
	
	public void setTime(String value){
		time1.setText(value);
	}
	
	public String getTime(){
		return time2.getLabel();
	}
	
	public String getStatus(){
		return status2.getLabel();
	}
	
	public static void showWarning(){
		trayIcon.displayMessage("Attention", status2.getLabel() + " " + time2.getLabel(), TrayIcon.MessageType.WARNING);
	}
	
	public static void UpdateMenu(String timeValue, String codeValue){
		time2.setLabel("Time: " + timeValue);
		status2.setLabel("Status: " + codeValue);
	}
	
	public static void UpdateTime(String time){
		time2.setLabel("Time: " + time);
	}
	
    
    private static void createAndShowGUI() {
        //Check the SystemTray support
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        final PopupMenu popup = new PopupMenu();
         trayIcon =
                new TrayIcon(createImage("images/Avaya.png", "tray icon"));
        final SystemTray tray = SystemTray.getSystemTray();
        
        // Create a popup menu components
//        MenuItem aboutItem = new MenuItem("About");
//        CheckboxMenuItem cb1 = new CheckboxMenuItem("Set auto size");
//        CheckboxMenuItem cb2 = new CheckboxMenuItem("Set tooltip");
//        Menu displayMenu = new Menu("Display");
//        MenuItem errorItem = new MenuItem("Error");
//        MenuItem warningItem = new MenuItem("Warning");
//        MenuItem infoItem = new MenuItem("Info");
//        MenuItem noneItem = new MenuItem("None");
        MenuItem configure = new MenuItem("Configure");
        MenuItem exitItem = new MenuItem("Exit");
        
        //Add components to popup menu
        popup.add(status2);
        popup.add(time2);
/*        popup.add(aboutItem);
        popup.addSeparator();
        popup.add(cb1);
        popup.add(cb2);*/
        popup.addSeparator();
        popup.add(configure);
        popup.addSeparator();
/*        popup.add(displayMenu);
        displayMenu.add(errorItem);
        displayMenu.add(warningItem);
        displayMenu.add(infoItem);
        displayMenu.add(noneItem);*/
        popup.add(exitItem);
        
        trayIcon.setPopupMenu(popup);
        
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
            return;
        }
        
        trayIcon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "This dialog box is run from System Tray");
            }
        });
        
        configure.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		//Show the configure Window
        		showOptions();
        	}
        });
        
/*        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "This dialog box is run from the About menu item");
            }
        });
        
        cb1.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                int cb1Id = e.getStateChange();
                if (cb1Id == ItemEvent.SELECTED){
                    trayIcon.setImageAutoSize(true);
                } else {
                    trayIcon.setImageAutoSize(false);
                }
            }
        });
        
        cb2.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                int cb2Id = e.getStateChange();
                if (cb2Id == ItemEvent.SELECTED){
                    trayIcon.setToolTip("Sun TrayIcon");
                } else {
                    trayIcon.setToolTip(null);
                }
            }
        });*/
        
        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MenuItem item = (MenuItem)e.getSource();
                //TrayIcon.MessageType type = null;
                System.out.println(item.getLabel());
                if ("Error".equals(item.getLabel())) {
                    //type = TrayIcon.MessageType.ERROR;
                    trayIcon.displayMessage("Sun TrayIcon Demo",
                            "This is an error message", TrayIcon.MessageType.ERROR);
                    
                } else if ("Warning".equals(item.getLabel())) {
                    //type = TrayIcon.MessageType.WARNING;
                    trayIcon.displayMessage("Sun TrayIcon Demo",
                            "This is a warning message", TrayIcon.MessageType.WARNING);
                    
                } else if ("Info".equals(item.getLabel())) {
                    //type = TrayIcon.MessageType.INFO;
                    trayIcon.displayMessage("Sun TrayIcon Demo",
                            "This is an info message", TrayIcon.MessageType.INFO);
                    
                } else if ("None".equals(item.getLabel())) {
                    //type = TrayIcon.MessageType.NONE;
                    trayIcon.displayMessage("Sun TrayIcon Demo",
                            "This is an ordinary message", TrayIcon.MessageType.NONE);
                }
            }
        };
        
/*        errorItem.addActionListener(listener);
        warningItem.addActionListener(listener);
        infoItem.addActionListener(listener);
        noneItem.addActionListener(listener);*/
        
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tray.remove(trayIcon);
                System.exit(0);
            }
        });
    }
    
    //Obtain the image URL
    protected static Image createImage(String path, String description) {
        URL imageURL = Gui.class.getResource(path);
        
        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }

}
