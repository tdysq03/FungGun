/*
เป็นหน้าสำหรับแสดงผลและแก้ไขข้อมูลเพลงในแต่ละ playlist
 */
package app;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

import javax.swing.*;
public class ManageList implements ActionListener{

	private JFrame playlistFrame;
	private static ArrayList<MusicInfo> original,favorite,dataForSort;
	private static int position;

	//Launch the application.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManageList window = new ManageList();
					window.playlistFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Create the application.
	public ManageList() {
		
		initialize();
	}

	//Initialize the contents of the frame.
	private void initialize()  {
		Data data = new Data();
		original = new ArrayList<MusicInfo>();
		favorite = new ArrayList<MusicInfo>();
		try {
			original = data.getMusicInfo("AllMusicInfo.csv");
			favorite = data.getMusicInfo("Favorite.csv");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//create frame
		playlistFrame = new JFrame();
		playlistFrame.setBounds(0, 0, 600, 600);
		playlistFrame.setResizable(false);
		playlistFrame.setLocationRelativeTo(null);
		playlistFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/images/icon.png")));
		playlistFrame.setTitle("FungGun!");
		playlistFrame.getContentPane().setLayout(null);	
		playlistFrame.setUndecorated(true);
		playlistFrame.setVisible(true);


		//header for original playlist
		JLabel listLabel = new JLabel("List of songs");
		listLabel.setFont(new Font("Arial Black", Font.PLAIN, 20));
		listLabel.setBounds(35, 30, 260, 29);
		playlistFrame.getContentPane().add(listLabel);

		//create panel for show original playlist
		JPanel originalPanel = new JPanel();
		originalPanel.setBounds(35, 61, 260, 415);
		originalPanel.setLayout(null);
		originalPanel.setOpaque(false);
		playlistFrame.getContentPane().add(originalPanel);

		//แสดงชื่อเพลงจาก original playlist ทั้งหมด
		int a=30;
		for(int i = 0; i < original.size(); i++) {
			String info = Integer.toString(i+1)+". "+original.get(i).getArtist()+" : "+original.get(i).getTitle();
			JLabel songList = new JLabel(info);
			songList.setBounds(10, (a*i)+10, 230, 25);
			songList.setFont(new Font("Arial", Font.PLAIN, 12));
			originalPanel.add(songList);
		}

		//ปุ่ม Sort by song title
		JButton titleSortButton = new JButton("Sort by song title");
		titleSortButton.setForeground(Color.WHITE);
		titleSortButton.setBackground(Color.DARK_GRAY);
		titleSortButton.setFont(new Font("Arial", Font.PLAIN, 11));
		titleSortButton.setName("sortTitle");
		titleSortButton.setBounds(35, 488, 220, 25);
		titleSortButton.addActionListener(this);
		playlistFrame.getContentPane().add(titleSortButton);

		//ปุ่ม Sort by artist
		JButton artistSortButton = new JButton("Sort by artist");
		artistSortButton.setForeground(Color.WHITE);
		artistSortButton.setBackground(Color.DARK_GRAY);
		artistSortButton.setFont(new Font("Arial", Font.PLAIN, 11));
		artistSortButton.setName("sortArtist");
		artistSortButton.setBounds(35, 522, 220, 25);
		artistSortButton.addActionListener(this);
		playlistFrame.getContentPane().add(artistSortButton);

		//ปุ่ม Search
		JButton searchButton = new JButton("Search");
		searchButton.setName("search");
		searchButton.setForeground(Color.WHITE);
		searchButton.setFont(new Font("Arial", Font.PLAIN, 11));
		searchButton.setBackground(Color.GRAY);
		searchButton.setBounds(35, 556, 220, 25);
		searchButton.addActionListener(this);
		playlistFrame.getContentPane().add(searchButton);

		//header for favorite playlist
		JLabel favLabel = new JLabel("Your favorite");
		favLabel.setFont(new Font("Arial Black", Font.PLAIN, 20));
		favLabel.setBounds(305, 30, 260, 29);
		playlistFrame.getContentPane().add(favLabel);

		//create panel for show favorite playlist
		JPanel favPanel = new JPanel();
		favPanel.setLayout(null);
		favPanel.setOpaque(false);
		favPanel.setBounds(305, 61, 269, 415);
		playlistFrame.getContentPane().add(favPanel);

		//แสดงชื่อเพลงจาก favorite playlist ทั้งหมด
		int b=30;
		for(int i = 0; i < favorite.size(); i++) {
			String info = Integer.toString(i+1)+". "+favorite.get(i).getArtist()+" : "+favorite.get(i).getTitle();
			JLabel songList = new JLabel(info);
			songList.setBounds(10, (b*i)+10, 230, 25);
			songList.setFont(new Font("Arial", Font.PLAIN, 12));
			favPanel.add(songList);
		}

		//ปุ่ม Add a song to favorite
		JButton addButton = new JButton("Add a song to favorite");
		addButton.setName("add");
		addButton.setForeground(Color.WHITE);
		addButton.setFont(new Font("Arial", Font.PLAIN, 11));
		addButton.setBackground(new Color(95, 158, 160));
		addButton.setBounds(305, 488, 220, 25);
		addButton.addActionListener(this);
		playlistFrame.getContentPane().add(addButton);

		//ปุ่ม Delete a song from favorite
		JButton deleteButton = new JButton("Delete a song from favorite");
		deleteButton.setName("delete");
		deleteButton.setForeground(Color.WHITE);
		deleteButton.setFont(new Font("Arial", Font.PLAIN, 11));
		deleteButton.setBackground(new Color(204, 153, 153));
		deleteButton.setBounds(305, 522, 220, 25);
		deleteButton.addActionListener(this);
		playlistFrame.getContentPane().add(deleteButton);
		JButton homeButton = new JButton("");

		//create home button
		homeButton.setName("home");
		homeButton.setIcon(new ImageIcon(MusicPlayer.class.getResource("/images/home.png")));
		homeButton.setFont(new Font("Arial Black", Font.PLAIN, 11));
		homeButton.setBounds(560, 560, 26, 26);
		homeButton.setOpaque(false);
		homeButton.setContentAreaFilled(false);
		homeButton.setBorderPainted(false);
		homeButton.setFocusPainted(false);
		homeButton.addActionListener(this);
		playlistFrame.getContentPane().add(homeButton);

		//create exit button
		JButton exitButton = new JButton("");
		exitButton.setIcon(new ImageIcon(MainMenu.class.getResource("/images/exit.png")));
		exitButton.setName("exit");
		exitButton.setBackground(Color.DARK_GRAY);
		exitButton.setBounds(560, 15, 20, 20);
		exitButton.addActionListener(this);
		playlistFrame.getContentPane().add(exitButton);

		//create background
		JLabel background = new JLabel("");
		background.setBounds(0, 0, 600, 600);
		background.setIcon(new ImageIcon(this.getClass().getResource("/images/bg_Playlist.png")));
		playlistFrame.getContentPane().add(background);

		//JOptionPane setting
		UIManager.put("OptionPane.background", Color.white);
		UIManager.put("Panel.background", Color.white);
		UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 12));
		UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 9));
	}
	
	//sort จากชื่อของนักร้อง (bubble sort)
	public void sortByArtist(){
		dataForSort=original;
		for(int i = 0; i<dataForSort.size(); i++) {  
			for (int j = i+1; j<dataForSort.size(); j++){  
				if(original.get(i).getArtist().toLowerCase().compareTo(dataForSort.get(j).getArtist().toLowerCase())>0)   
				{  
					MusicInfo temp = dataForSort.get(i);  
					dataForSort.set(i,dataForSort.get(j));  
					dataForSort.set(j,temp);
				}
			}  
		} 
		
		String text="";
		for(int i=0;i<dataForSort.size();i++) {
			text += " \n"+Integer.toString(i+1)+
			    ". Artist   : "+dataForSort.get(i).getArtist()+"\n"+
                 "Title : " + dataForSort.get(i).getTitle()+"\n"+
                 "---------------------------------";
		}
		JOptionPane.showMessageDialog(null, text+"\n\n", "Sort by Artist",JOptionPane.PLAIN_MESSAGE);
		
	}

	//sort จากชื่อเพลง (bubble sort)
	public void sortByTitle()
	{
		dataForSort=original;
		for(int i = 0; i<dataForSort.size(); i++) {  
			for (int j = i+1; j<dataForSort.size(); j++){  
				if(original.get(i).getTitle().toLowerCase().compareTo(dataForSort.get(j).getTitle().toLowerCase())>0)   
				{  
					MusicInfo temp = dataForSort.get(i);  
					dataForSort.set(i,dataForSort.get(j));  
					dataForSort.set(j,temp);
				}
			}  
		} 
		
		String text="";
		for(int i=0;i<dataForSort.size();i++) {
			text += " \n"+Integer.toString(i+1)+
			    ". Title   : "+dataForSort.get(i).getTitle()+"\n"+
                 "Artist : " + dataForSort.get(i).getArtist()+"\n"+
                 "---------------------------------";
		}
		JOptionPane.showMessageDialog(null, text+"\n\n", "Sort by Title",JOptionPane.PLAIN_MESSAGE);
		
	}

	//หา key จากชื่อเพลงหรือชื่อนักร้อง
	public static boolean findKey(String key){
		boolean found=false;
		int i = 0;
		while (!found && i<original.size()) {
			if (original.get(i).getArtist().toLowerCase().equals(key.toLowerCase()) || original.get(i).getTitle().toLowerCase().equals(key.toLowerCase())) {
				found = true;
				position = i;
			}else
				found = false;
			i++;
		}
			return found;

	}

	//จัดเก็บข้อมูลของ favorite playlist ลงในไฟล์ csv
	public void addSong(String song) throws IOException {
		Writer output;
		String current = System.getProperty("user.dir");
		output = new BufferedWriter(new FileWriter(current+"\\data\\"+"Favorite.csv", false));
		output.append(song);
		output.close();
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		String allSong;
		if(event.getSource() instanceof JButton) {
			String button = ((JButton)event.getSource()).getName();
			//switch button's name
			switch (button) {

				//sort ชื่อเพลง
				case "sortTitle":
					sortByTitle();
					break;

				//sort ชื่อนักร้อง
				case "sortArtist":
					sortByArtist();
					break;

				//search
				case "search":
					String key = JOptionPane.showInputDialog(null,"Search");
					if(key==null) {
						break;
					}else if (key.isEmpty()){
						JOptionPane.showMessageDialog(null, "You left the text field blank!");
					}else if(key!=null){
						if (findKey(key)) {
							JOptionPane.showMessageDialog(null, "Found! "+original.get(position).getArtist()+" : "+original.get(position).getTitle());
						}else {
							JOptionPane.showMessageDialog(null,"Not Found!");
						}
					}
					break;

				//add a song to favorite playlist
				//เพิ่มเพลงลงใน favorite playlist
				case "add":
					String num1 = JOptionPane.showInputDialog(null,"ADD\nEnter song number (from original playlist)");
					if(num1==null) {
						break;
					}else if (num1.isEmpty()){
						JOptionPane.showMessageDialog(null, "You left the text field blank!");
					}else if(num1!=null){
						try {
							int add = Integer.parseInt(num1);
							if(add>original.size()||add<0) {
								JOptionPane.showMessageDialog(null, "Please enter valid number!!");
								break;
							}
							add--;
							favorite.add(original.get(add));
							allSong ="";
							for(int i=0;i<favorite.size();i++) {
								allSong += favorite.get(i).getTitle()+","+favorite.get(i).getArtist()+","+favorite.get(i).getFilePath()+","+favorite.get(i).getCoverPath()+"\n";
							}
							addSong(allSong);
				        } catch (NumberFormatException e) {
				        	JOptionPane.showMessageDialog(null, "Please enter valid number!!");
				        } catch (IOException e) {
							e.printStackTrace();
						}
					}
					new ManageList();
					playlistFrame.dispose();
					break;

				//delete a song from favorite playlist
				//ลบเพลงออกจาก favorite playlist
				case "delete":
					String num2 = JOptionPane.showInputDialog(null,"DELETE\nEnter song number (from favorite playlist)");
					if(num2==null) {
						break;
					}else if (num2.isEmpty()){
						JOptionPane.showMessageDialog(null, "You left the text field blank!");
					}else if(num2!=null){
						try {
							int del = Integer.parseInt(num2);
							if(del>favorite.size()||del<0) {
								JOptionPane.showMessageDialog(null, "Please enter valid number!!");
								break;
							}
							del--;
							favorite.remove(del);
							allSong ="";
							for(int i=0;i<favorite.size();i++) {
								allSong += favorite.get(i).getTitle()+","+favorite.get(i).getArtist()+","+favorite.get(i).getFilePath()+","+favorite.get(i).getCoverPath()+"\n";
							}
							addSong(allSong);
				        } catch (NumberFormatException e) {
				        	JOptionPane.showMessageDialog(null, "Please enter valid number!!");
				        } catch (IOException e) {
							e.printStackTrace();
						}
					}
					new ManageList();
					playlistFrame.dispose();
					break;			

				//Home
				//ไปยังหน้า home
				case "home":
					new MainMenu();
					playlistFrame.dispose();
					
						break;

				//Exit
				//จบการทำงาน
				case "exit": 		
					playlistFrame.dispose();
					System.exit(0);
					break;
			}	
		}
	}
}
