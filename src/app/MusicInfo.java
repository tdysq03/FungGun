/*
สำหรับสร้าง object ที่เก็บข้อมูลต่างๆของเพลง ได้แก่ ชื่อเพลง, ชื่อนักร้อง, path ของไฟล์ wav ของเพลง, path ของไฟล์รูปปก
 */
package app;
public class MusicInfo {
	
	private String	title,artist,filePath,coverPath;

	//constructor ตั้งค่าของข้อมูลต่างๆ
	public MusicInfo(String title,String artist,String filePath,String coverPath) {
		this.title = title;
		this.artist = artist;
		this.filePath = filePath;
		this.coverPath = coverPath;
		}

	//คืนค่าข้อมูลชื่อเพลง
	public String getTitle(){
		return title;
		}

	//คืนค่าข้อมูลชื่อนักร้อง
	public String getArtist(){
		return artist;
		}

	//คืนค่าข้อมูล path ของไฟล์ wav ของเพลง
	public String getFilePath(){
		return filePath;
		}

	//คืนค่าข้อมูล path ของไฟล์รูปปก
	public String getCoverPath(){
		return coverPath;
		}
}