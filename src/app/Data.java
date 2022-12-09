/*
สำหรับอ่านข้อมูลจากไฟล์ที่ต้องการแล้วสร้างเป็น object MusicInfo จากนั้น add ทั้งหมดลงใน ArrayList
 */
package app;
import java.io.*;
import java.util.*;

public class Data {

	private String current;

	public Data() {
		current = System.getProperty("user.dir");
	}

	//read data from a file and add to ArrayList
	//เรียกเมื่อต้องการอ่านข้อมูลจากไฟล์ที่ส่ง argument เข้ามา (รับ parameter เป็นชื่อไฟล์)
	public ArrayList<String> readDataFromFile(String fileName) throws IOException {
		ArrayList<String> dataFromFile = new ArrayList<String>();
		File inFile = new File(current+"\\data\\"+fileName);
		FileReader fileReader = new FileReader(inFile);
		BufferedReader bufReader = new BufferedReader(fileReader);
		String line = bufReader.readLine();
		while(line !=null) {
			dataFromFile.add(line);
			line = bufReader.readLine();
			}
		bufReader.close();	
		return dataFromFile;
	}
	
	//get music information from method readDataFromFile and convert to Object than add to ArrayList
	//นำข้อมูลของเพลงที่อ่านได้จากไฟล์มาสร้างเป็น object แล้วเก็บใน ArrayList (รับ parameter เป็นชื่อไฟล์)
	public ArrayList<MusicInfo> getMusicInfo(String fileName) throws IOException {		
		ArrayList<String> data = new ArrayList<String>();
		data = readDataFromFile(fileName);
		ArrayList<app.MusicInfo> musicInfo = new ArrayList<MusicInfo>();
		String[] arrayOfStr;
		for(int i = 0;i<data.size();i++) {
			arrayOfStr = data.get(i).split(",");
			musicInfo.add(new MusicInfo(arrayOfStr[0],arrayOfStr[1],arrayOfStr[2],arrayOfStr[3]));
		   }
		return musicInfo;
		}
}