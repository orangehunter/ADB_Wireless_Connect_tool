import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import java.awt.TextArea;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class Main {

	private JFrame frame;
	public static JComboBox<String> comboBox;
	private JLabel lblProfile;
	private JButton btnConnect;
	private JButton btnDisconnect;
	private JButton btnBrowse;
	private JButton btnWifiHost;
	private JLabel lblPath;
	private JTextField pathShow;
	private String consloneText;
	private static TextArea conslone;
	private JButton btnReflash;
	private JLabel lblPort;
	private JTextField textPort;
	private JButton btnSetPort;

	public static Variable variable;
	public DataContraller dataContraller;
	public AdbContraller adbContraller;

	public File test;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}
	public static void appendConslone(String s,boolean withTime){
		if(withTime){
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			Calendar calendar=Calendar.getInstance();
			conslone.append("\n"+sdf.format(calendar.getTime())+"  "+s);
		}else{
			conslone.append("\n"+s);
		}
	}
	public Image getSourceImage(String name){
		return new ImageIcon(this.getClass().getResource("/"+name)).getImage();
	}
	public ImageIcon getImageIcon(String name){
		return new ImageIcon(getSourceImage(name));
	}
	public void reFlashDevices() {
		adbContraller.getDevices();//讀取裝置清單
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		variable=new Variable();
		adbContraller=new AdbContraller();
		dataContraller=new DataContraller();
		variable=dataContraller.readData();

		frame = new JFrame();
		frame.setTitle("Android Wireless Debug Helper");
		frame.setIconImage(getSourceImage("icon_main.png"));
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.setBackground(Color.LIGHT_GRAY);
		frame.setBounds(100, 100, 770, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);




		conslone = new TextArea();
		conslone.setBackground(Color.WHITE);
		conslone.setEditable(false);
		consloneText="";
		conslone.setText(consloneText);
		conslone.append("WellCome to Android Wireless Debug Helper 1.0"
				+ "\nMade by EXL Workshop Michael Liu."
				+ "\n"+"##### ### ### ###   "
				+ "\n"+" #  #  #   #   #    "
				+ "\n"+" #      # #    #    "
				+ "\n"+" ###     #     #    "
				+ "\n"+" #      # #    #    "
				+ "\n"+" #  #  #   #   #  # "
				+ "\n"+"##### ### ### ##### ");

		comboBox = new JComboBox();


		lblProfile = new JLabel("Devices.");

		btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//TODO
			}
		});

		btnDisconnect = new JButton("Disconnect");
		btnDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO
			}
		});

		pathShow = new JTextField();
		pathShow.setEditable(false);
		pathShow.setColumns(10);
		if (variable.adbPath.equals("")) {
			appendConslone("You need to setup adb.exe path at first time.",false);
			textPort.setText("5555");
		}
		pathShow.setText(variable.adbPath);

		lblPath = new JLabel("abd.exe path");

		btnBrowse = new JButton("...");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser Filechoose=new JFileChooser();
				FileNameExtensionFilter adbexefilter = new FileNameExtensionFilter(
						"Android SDK adb tool(adb.exe)", "exe");
				Filechoose.setFileFilter(adbexefilter);
				int retval=Filechoose.showOpenDialog(new JFrame());
				if (retval == JFileChooser.APPROVE_OPTION) {
					//... The user selected a file, get it, use it.
					File file = Filechoose.getSelectedFile();
					test=file;
					pathShow.setText(file.getPath());
					variable.adbPath=file.getPath();
					appendConslone("Set adb.exe Path to:"+variable.adbPath,true);
					dataContraller.saveData(variable);
				}
			}
		});


		btnReflash = new JButton("");
		btnReflash.setIcon(getImageIcon("renew.png"));
		btnReflash.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				reFlashDevices();
			}
		});

		lblPort = new JLabel("Port.");

		textPort = new JTextField();
		textPort.setColumns(10);
		textPort.setText(variable.port);

		btnSetPort = new JButton("Set");
		btnSetPort.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					if (Integer.valueOf(textPort.getText())>65535||Integer.valueOf(textPort.getText())<1) {
						Integer.valueOf("Force throw Exception");
					}
				}catch (NumberFormatException es) {
					appendConslone("Port is only avaliable from 1~65535", true);
					textPort.setText("5555");
				}
				variable.port=textPort.getText();
				String showPort=variable.port;
				if (variable.port.equals("5555")) {
					showPort="Default(5555)";
				}
				appendConslone("Set connect port to:"+showPort, true);
				dataContraller.saveData(variable);
			}
		});

		btnWifiHost = new JButton("Start Wifi Hosting");

		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(conslone, GroupLayout.DEFAULT_SIZE, 539, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblPath, Alignment.LEADING)
								.addComponent(lblProfile, Alignment.LEADING)
								.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
										.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
												.addComponent(btnWifiHost, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
														.addGap(2)
														.addComponent(btnConnect, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
														.addGap(4)
														.addComponent(btnDisconnect, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
												.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
														.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
																.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
																		.addComponent(lblPort)
																		.addPreferredGap(ComponentPlacement.RELATED)
																		.addComponent(textPort))
																.addComponent(comboBox, Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																.addComponent(pathShow, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE))
														.addPreferredGap(ComponentPlacement.RELATED)
														.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
																.addComponent(btnSetPort, 0, 0, Short.MAX_VALUE)
																.addComponent(btnReflash, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																.addComponent(btnBrowse, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
										.addGap(6)))
						.addContainerGap())
				);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
						.addGap(10)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(conslone, GroupLayout.PREFERRED_SIZE, 271, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(lblPath)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
												.addComponent(pathShow, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(btnBrowse))
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addGroup(groupLayout.createSequentialGroup()
														.addGap(4)
														.addComponent(lblProfile)
														.addGap(1)
														.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
												.addGroup(groupLayout.createSequentialGroup()
														.addGap(19)
														.addComponent(btnReflash, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(lblPort)
												.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
														.addComponent(textPort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(btnSetPort)))
										.addGap(12)
										.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
												.addComponent(btnConnect, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
												.addComponent(btnDisconnect, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btnWifiHost)))
						.addGap(10))
				);
		frame.getContentPane().setLayout(groupLayout);


	}
}
