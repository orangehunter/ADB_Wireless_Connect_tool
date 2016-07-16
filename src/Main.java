import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.TextArea;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class Main {

	private JFrame frame;
	private JComboBox comboBox;
	private JLabel lblProfile;
	private JButton btnNewProfile;
	private JButton btnDeleteProfile;
	private JButton btnBrowse;
	private JLabel lblPath;
	private JTextField pathShow;
	private String consloneText;
	private TextArea conslone;

	public Variable variable;
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
	public void reNewConslone(String s){
		conslone.append("\n"+s);
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		variable=new Variable();
		
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.setBackground(Color.LIGHT_GRAY);
		frame.setBounds(100, 100, 735, 400);
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
		if (variable.dtats.size()==0) {
			ProfileDtats profileDtats=new ProfileDtats("Default");
		}
		
		lblProfile = new JLabel("Profile.");
		
		btnNewProfile = new JButton("New");
		btnNewProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		btnDeleteProfile = new JButton("Delete");
		btnDeleteProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		pathShow = new JTextField();
		pathShow.setEditable(false);
		pathShow.setColumns(10);
		
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
	                    pathShow.setText(file.getPath());
	                }
			}
		});
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(conslone, GroupLayout.PREFERRED_SIZE, 469, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblProfile)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(comboBox, 0, 151, Short.MAX_VALUE))
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(btnNewProfile, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(btnDeleteProfile, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)))
						.addComponent(lblPath)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(pathShow, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnBrowse)))
					.addContainerGap(41, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(conslone, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblProfile)
								.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(5)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnNewProfile)
								.addComponent(btnDeleteProfile))
							.addGap(5)
							.addComponent(lblPath)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(pathShow, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnBrowse))
							.addGap(2)))
					.addContainerGap(121, Short.MAX_VALUE))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
}
