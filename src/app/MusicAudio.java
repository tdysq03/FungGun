/*
* เพื่อจัดการกับไฟล์เสียงให้ทำงานไปพร้อมกับ MusicPlayer
* */
package app;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MusicAudio {
	private Long currentFrame;
	private static Clip clip;
	private static AudioInputStream audioInputStream;
	private static String filePath,current;


	//สร้าง Thread ใหม่เพื่อให้เล่นเพลงไปพร้อมกับ MusicPlayer
	public void playMusic(String inputMusicPath) {
		new Thread() {
			public void run() {
				try
				{
					current = System.getProperty("user.dir");
					filePath = current+inputMusicPath;
					MusicAudio audioPlayer = new MusicAudio();
					audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
					clip = AudioSystem.getClip();
					clip.open(audioInputStream);
					clip.loop(Clip.LOOP_CONTINUOUSLY);
					while (true){
						audioPlayer.play();
					}
				}
				catch (Exception ex){
			         ex.printStackTrace();
				}
			}
	   }.start();
	}
	//play the audio
	public void play(){
		clip.start();
	}

	//pause the audio
	public void pause(){
		this.currentFrame = clip.getMicrosecondPosition();
		clip.stop();
		clip.close();
	}

	//resume the audio
	public void resumeAudio() throws UnsupportedAudioFileException,IOException, LineUnavailableException {
		clip.close();
		resetAudioStream();
		clip.setMicrosecondPosition(currentFrame);
		this.play();
	}

	//top the audio
	public void stop() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		currentFrame = 0L;
		clip.stop();
		clip.close();
	}

	//reset audio stream
	public void resetAudioStream() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
		clip.open(audioInputStream);
	}
}