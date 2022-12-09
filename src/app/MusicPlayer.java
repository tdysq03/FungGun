/*
สำหรับเล่นเพลงตามลำดับใน playlist ที่เลือก โดยจะมีการสร้าง Doubly Linked List ที่เก็บข้อมูล object MusicInfo
แล้วดึงข้อมูลออกมาแสดงผลตามความต้องการของผู้ใช้งาน
 */
package app;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.Font;
import java.awt.Color;

public class MusicPlayer implements ActionListener{
		
	private JFrame musicFrame;
	private static DoubleLinkedList list ;
	private static MusicInfo musicInfo;
	private static JButton PlayPauseButton;
	private static JLabel titleLabel,artistLabel,coverLabel;
	private static MusicAudio musicAudio;
	private static ArrayList<MusicInfo> allMusicInfo;

	// Launch the application.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try { 
					new MusicPlayer(allMusicInfo);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Create the application
	//รับ parameter เป็น ArrayList ที่เก็บข้อมูลของเพลงต่างๆ จากนั้นดึงข้อมูลมาสร้าง Doubly Linked List เพื่อใช้งาน
	public MusicPlayer(ArrayList<MusicInfo> input) {
		
		try {
				list = new DoubleLinkedList();
				allMusicInfo = input;
				for(int i=0;i<allMusicInfo.size();i++) {
					list.insert(allMusicInfo.get(i));
				}
				list.findFirst();
				musicInfo = (MusicInfo) list.retrieve();
				
			} catch (Exception e) {			
				e.printStackTrace();
			}	
		
		initialize();
	}
	
	public MusicPlayer(MusicInfo musicInfo ) {
		initialize();
	}
	
	// Initialize the contents of the frame.
	private void initialize() {

		//ส่งข้อมูลไปที่ MusicAudio เพื่อเล่นเพลง
		musicAudio = new MusicAudio();
		musicAudio.playMusic(musicInfo.getFilePath());

		//create frame
		musicFrame = new JFrame();
		musicFrame.setBounds(0, 0, 600, 600);
		musicFrame.setResizable(false);
		musicFrame.setLocationRelativeTo(null);
		musicFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/images/icon.png")));
		musicFrame.setTitle("FungGun!");
		musicFrame.getContentPane().setLayout(null);
		musicFrame.setUndecorated(true);
		musicFrame.setVisible(true);

		//show Song Title
		titleLabel = new JLabel("Song Title",SwingConstants.RIGHT);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
		titleLabel.setBounds(81, 66, 391, 30);
		titleLabel.setText(musicInfo.getTitle());
		musicFrame.getContentPane().add(titleLabel);

		//show Artist name
		artistLabel = new JLabel("Artist name",SwingConstants.RIGHT);
		artistLabel.setFont(new Font("Arial", Font.PLAIN, 11));
		artistLabel.setBounds(81, 97, 391, 14);
		artistLabel.setText(musicInfo.getArtist());
		musicFrame.getContentPane().add(artistLabel);

		//show cover photo
		coverLabel = new JLabel("");
		coverLabel.setIcon(new ImageIcon(this.getClass().getResource(musicInfo.getCoverPath())));
		coverLabel.setBounds(141, 132, 320, 323);
		musicFrame.getContentPane().add(coverLabel);

		//Shuffle Button สำหรับสุ่มเพลง
		JButton ShuffleButton = new JButton("");
		ShuffleButton.setName("shuffle");
		ShuffleButton.setIcon(new ImageIcon(this.getClass().getResource("/images/shuffle.png")));
		ShuffleButton.setHorizontalTextPosition (SwingConstants.CENTER);	
		ShuffleButton.setOpaque(false);
		ShuffleButton.setContentAreaFilled(false);
		ShuffleButton.setBorderPainted(false);
		ShuffleButton.setBounds(155, 508, 35, 35);
		ShuffleButton.addActionListener(this);
		musicFrame.getContentPane().add(ShuffleButton);

		//Previous Button สำหรับดึงข้อมูลของเพลงก่อนหน้ามาแสดง
		JButton PrevButton = new JButton("");
		PrevButton.setName("prev");
		PrevButton.setIcon(new ImageIcon(this.getClass().getResource("/images/prev.png")));
		PrevButton.setHorizontalTextPosition (SwingConstants.CENTER);	
		PrevButton.setOpaque(false);
		PrevButton.setContentAreaFilled(false);
		PrevButton.setBorderPainted(false);
		PrevButton.setBounds(215, 508, 35, 35);
		PrevButton.addActionListener(this);
		musicFrame.getContentPane().add(PrevButton);

		//ปุ่มกดเพื่อหยุดและเล่นเพลง
		PlayPauseButton = new JButton("");
		PlayPauseButton.setName("pause");
		PlayPauseButton.setIcon(new ImageIcon(this.getClass().getResource("/images/pause.png")));
		PlayPauseButton.setHorizontalTextPosition (SwingConstants.CENTER);	
		PlayPauseButton.setOpaque(false);
		PlayPauseButton.setContentAreaFilled(false);
		PlayPauseButton.setBorderPainted(false);
		PlayPauseButton.setBounds(275, 500, 50, 50);
		PlayPauseButton.addActionListener(this);
		musicFrame.getContentPane().add(PlayPauseButton);

		//Next Button สำหรับดึงข้อมูลของเพลงต่อไปมาแสดง
		JButton NextButton = new JButton("");
		NextButton.setName("next");
		NextButton.setIcon(new ImageIcon(this.getClass().getResource("/images/next.png")));
		NextButton.setHorizontalTextPosition (SwingConstants.CENTER);	
		NextButton.setOpaque(false);
		NextButton.setContentAreaFilled(false);
		NextButton.setBorderPainted(false);
		NextButton.setBounds(350, 508, 35, 35);
		NextButton.addActionListener(this);
		musicFrame.getContentPane().add(NextButton);

		//ปุ่มกดเพื่อดูลำดับของเพลง
		JButton ListButton = new JButton("");
		ListButton.setName("list");
		ListButton.setIcon(new ImageIcon(this.getClass().getResource("/images/list.png")));
		ListButton.setHorizontalTextPosition (SwingConstants.CENTER);	
		ListButton.setOpaque(false);
		ListButton.setContentAreaFilled(false);
		ListButton.setBorderPainted(false);
		ListButton.setBounds(410, 508, 35, 35);
		ListButton.addActionListener(this);
		musicFrame.getContentPane().add(ListButton);

		//create exit button
		JButton exitButton = new JButton("");
		exitButton.setIcon(new ImageIcon(this.getClass().getResource("/images/exit.png")));
		exitButton.setName("exit");
		exitButton.setBackground(Color.DARK_GRAY);
		exitButton.setBounds(560, 15, 20, 20);
		exitButton.addActionListener(this);
		musicFrame.getContentPane().add(exitButton);

		//create home button
		JButton homeButton = new JButton("");
		homeButton.setName("home");
		homeButton.setIcon(new ImageIcon(this.getClass().getResource("/images/home.png")));
		homeButton.setFont(new Font("Arial Black", Font.PLAIN, 11));
		homeButton.setBounds(10, 563, 26, 26);
		homeButton.setOpaque(false);
		homeButton.setContentAreaFilled(false);
		homeButton.setBorderPainted(false);
		homeButton.setFocusPainted(false);
		homeButton.addActionListener(this);
		musicFrame.getContentPane().add(homeButton);

		//create background
		JLabel background = new JLabel("");
		background.setBounds(0, 0, 600, 600);
		background.setHorizontalTextPosition (SwingConstants.CENTER);	
		background.setIcon(new ImageIcon(this.getClass().getResource("/images/bg_Play.png")));
		musicFrame.getContentPane().add(background);

		//JOptionPane setting
		UIManager.put("OptionPane.background", Color.white);
		UIManager.put("Panel.background", Color.white);
		UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 12));
		UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 9));
	}

	//ดึงข้อมูลจาก Doubly Linked List ของเพลงก่อนหน้ามาแสดง
	public void previous() throws Exception {
		list.findPrev();
		musicInfo = (MusicInfo) list.retrieve();
		newAudio();
		changeLabel();
	}

	//ดึงข้อมูลจาก Doubly Linked List ของเพลงต่อไปมาแสดง
	public void next() throws Exception {
		list.findNext();
		musicInfo = (MusicInfo) list.retrieve();
		newAudio();
		changeLabel();
		
	}

	//สุ่มลำดับเพลงใหม่ และเก็บข้อมูลลง Doubly Linked List
	public void shuffle() throws Exception {
		list.delete();
		
		int quantity = allMusicInfo.size();
		ArrayList<Integer> number = new ArrayList<Integer>();	  
		for(int i = 0;i<quantity;i++) {
			number.add(i);
		   }
		int[] queue = new int[quantity]; 
		Random randNum = new Random();
		for(int i = 0;i<quantity;i++) {
			int index = randNum.nextInt(number.size());
			queue[i] = number.get(index);
			number.remove(index);
		   	}

		for(int i=0;i<allMusicInfo.size();i++) {
			list.insert(allMusicInfo.get(queue[i]));
		}
		list.findFirst();
		musicInfo = (MusicInfo) list.retrieve();
		newAudio();
		changeLabel();
	}

	//เปลี่ยนข้อมูลที่แสดงผล
	public void changeLabel() {
		titleLabel.setText(musicInfo.getTitle());
		artistLabel.setText(musicInfo.getArtist());
		coverLabel.setIcon(new ImageIcon(this.getClass().getResource(musicInfo.getCoverPath())));
		PlayPauseButton.setName("pause");
		PlayPauseButton.setIcon(new ImageIcon(this.getClass().getResource("/images/pause.png")));
	}

	//สำหรับเปิดเพลงใหม่
	public void newAudio() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		musicAudio.stop();
		musicAudio = new MusicAudio();
		musicAudio.playMusic(musicInfo.getFilePath());
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() instanceof JButton) {
			String button = ((JButton)event.getSource()).getName();
			//switch button's name
			switch (button) {

				//กดปุ่มสุ่ม
				case "shuffle":
					try {
						shuffle();
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;

				//กดปุ่มเล่นเพลงก่อนหน้า
				case "prev":
					try {
						previous();
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
					}
					break;

				//กดปุ่มเพื่อเล่นเพลง
				case "play":
					PlayPauseButton.setName("pause");
					PlayPauseButton.setIcon(new ImageIcon(this.getClass().getResource("/images/pause.png")));
					try {
						musicAudio.resumeAudio();
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
						e.printStackTrace();
					}
					break;
					
				//กดปุ่มเพื่อหยุดเพลงชั่วคราว
				case "pause":
					PlayPauseButton.setName("play");
					PlayPauseButton.setIcon(new ImageIcon(this.getClass().getResource("/images/play.png")));
					musicAudio.pause();
					break;

				//กดปุ่มเล่นเพลงถัดไป
				case "next": 		
					try {
						next();
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
					}
					break;

				//กดดูรายชื่อเพลง
				case "list": 
					ArrayList<MusicInfo>songList = list.getSongList();
					String text="";
					for(int i=0;i<songList.size();i++) {
						text += " \n"+Integer.toString(i+1)+
						    ". Song   : "+songList.get(i).getTitle()+"\n"+
			                 "Artist : " + songList.get(i).getArtist()+"\n"+
			                 "---------------------------------";
					}
					JOptionPane.showMessageDialog(null, text+"\n\n", "Playlist",JOptionPane.PLAIN_MESSAGE);
					break;

				//Home
				//ไปยังหน้า home
				case "home":
					new MainMenu();
					try {
						musicAudio.stop();
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
						e.printStackTrace();
					}
					
					musicFrame.dispose();
					
						break;

				//Exit
				//จบการทำงาน
				case "exit": 		
				try {
					musicAudio.stop();
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
					e.printStackTrace();
				}
					musicFrame.dispose();
					System.exit(0);
					break;
			}	
		}
	}

}
