/*
เป็นหน้าหลักของโปรแกรมที่จะสามารถไปที่ส่วนต่างๆ โดยจะมีการสร้าง JFrame ขึ้นมา
และมีปุ่มเพื่อกดเพื่อเริ่มโปรแกรมและไปยังส่วนจัดการ playlist ของเพลงได้
เมื่อกดปุ่ม Start จะทำการส่ง ArrayList ของ MusicInfo ที่เก็บข้อมูลของเพลงต่าง ๆ ไปยัง MusicPlayer เพื่อเล่นเพลงตาม playlist ที่เลือก
ส่วนปุ่ม List of songs จะสร้างไปยัง ManageList
 */
package app;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
public class MainMenu implements ActionListener{
	private JFrame frame;
	private static Data data;

	// Launch the application.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new MainMenu();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Create the application.
	public MainMenu() {
		initialize();
	}

	//Initialize the contents of the frame.
	private void initialize() {
		//create frame
		frame = new JFrame();
		frame.setBounds(0, 0, 600, 600);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/images/icon.png")));
		frame.setTitle("FungGun!");
		frame.getContentPane().setLayout(null);	
		frame.setUndecorated(true);
		frame.setVisible(true);

		//create Start Button
		JButton startButton = new JButton("Start");
		startButton.setName("start");
		startButton.setForeground(Color.WHITE);
		startButton.setBackground(Color.DARK_GRAY);
		startButton.setFont(new Font("Arial Black", Font.PLAIN, 14));
		startButton.setBounds(200, 420, 200, 35);
		startButton.setFocusPainted(false);
		startButton.addActionListener(this);
		frame.getContentPane().add(startButton);

		//create List of songs Button
		JButton songListButton = new JButton("List of songs");
		songListButton.setName("list");
		songListButton.setForeground(Color.WHITE);
		songListButton.setFont(new Font("Arial Black", Font.PLAIN, 14));
		songListButton.setFocusPainted(false);
		songListButton.setBackground(Color.DARK_GRAY);
		songListButton.setBounds(200, 472, 200, 35);
		songListButton.addActionListener(this);
		frame.getContentPane().add(songListButton);

		//create Exit Button
		JButton exitButton = new JButton("");
		exitButton.setIcon(new ImageIcon(this.getClass().getResource("/images/exit.png")));
		exitButton.setName("exit");
		exitButton.setBackground(Color.DARK_GRAY);
		exitButton.setBounds(560, 15, 20, 20);
		exitButton.addActionListener(this);
		frame.getContentPane().add(exitButton);

		//create background
		JLabel background = new JLabel("");
		background.setBounds(0, 0, 600, 600);
		background.setIcon(new ImageIcon(this.getClass().getResource("/images/bg_MainMenu.png")));
		frame.getContentPane().add(background);

		//JOptionPane setting
		UIManager.put("OptionPane.background", Color.white);
		UIManager.put("Panel.background", Color.white);
		UIManager.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 14));
		UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 12));
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() instanceof JButton) {
			String button = ((JButton)event.getSource()).getName();
			//switch button's name
			switch (button) {

				//Start
				//เริ่มการเล่นเพลง
				case "start":
					data = new Data();
					String[] options = {"Original","My Favorite"};

					//choose between original playlist or favorite playlist
					//เลือก playlist ระหว่าง original playlist หรือ favorite playlist
			        int x = JOptionPane.showOptionDialog(null, "Select Playlist","",JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
					if(x==0) {
						try {
							//create MusicPlayer with original playlist
							new MusicPlayer(data.getMusicInfo("AllMusicInfo.csv"));
						} catch (IOException e) {
							e.printStackTrace();
						}
					}else if(x==1) {
						try {
							//create MusicPlayer with favorite playlist
							new MusicPlayer(data.getMusicInfo("Favorite.csv"));
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					frame.dispose();
			        break;

				//create ManageList for edit playlist
				//ไปยังหน้าจัดการกับ playlist
				case "list":
					new ManageList();
					frame.dispose();
					break;

				//Exit
				//จบการทำงาน
				case "exit":
					frame.dispose();
					System.exit(0);
					break;
			}	
		}
	}
}