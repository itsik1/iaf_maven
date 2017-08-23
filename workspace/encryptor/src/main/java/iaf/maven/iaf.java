package iaf.maven;

/**
 * Hello world!
 *
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class iaf {
	int choice;//
	public iaf(int itterations)
	{
		for (int i = 0; i < itterations; i++) {
			testEncryptions();
		}
	}
	public iaf() {
		System.out.println("Please choose, encryption(1) or description(2)");
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		choice = sc.nextInt();//the choice for 'encryption(1) or description(2)'.
		//		System.out.println(i);
		String s = "encryption";
		if(choice==2) s = "description";
		System.out.println("\nNow enter a file path for the "+ s);
		String path = sc.next(),location = "";
		if(path.contains("/"))
		{
			int index = path.lastIndexOf("/");
			location = path.substring(0, index);
			path = path.substring(index+1);
					
		}
		////////////////////////////////////////////////////////////////////////////////צריך לחלק בין הכתובת לשם הקובץ כדי שזה יעבוד בפוקנציות מתחת
		while(!Files.exists(Paths.get(location,path))||!Files.isReadable(Paths.get(location,path)))
		{
			System.err.println("this is not a file");
			path = sc.next();

		}
		if(choice == 1)
			encryptions(location,path);
		//					System.out.println(encryption(s));
		else
		{
			System.out.println("enter key to decode the file");
			int key = sc.nextInt();
			descriptions(location,path,key);
		}


	}
	private  void descriptions(String location, String path, int key) {
		int index = path.lastIndexOf(".");
		String file = path.substring(0, index) + "_decrypted"+path.substring(index);
		workWithFile(key,file,location,path,-1);
	}
	/***
	 * this function encrypts a given file.
	 * @param path string of file's path.
	 */
	int key;
	private void encryptions(String location, String path) {
		key = randomKey();
		System.out.println(key);
		String file = path + ".encrypted";
		workWithFile(key,file,location,path,1);

	}
	private void workWithFile(int key, String file,String location,  String path, int j) {
		try {
//			if(location != "")
//				path = location+ "\"+path;
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			String s = "",writed="";
//			System.out.println("writed: "+writed);
			if(location != "")
				path = location+"/"+path;
			BufferedReader br = new BufferedReader(new FileReader(path));
			byte[] fileByte = Files.readAllBytes(new File(path).toPath());//Files.readAllBytes(Paths.get(location,path));;
			String s2 = br.readLine();
			while((s = br.readLine())!=null)
				s2 += "\n"+s;
			if(s2== null)
				s2 = "";
//			s2 = br.toString();
			System.out.println("s2: " + s2+ "  length:"+s2.length());
//			System.out.println("key: "+ key+ "\t"+ fileByte[0]);
			System.out.println("1: "+Arrays.toString(fileByte));
			fileByte = s2.getBytes();
			System.out.println("1.1"+Arrays.toString(fileByte));
			for (int i = 0; i < fileByte.length; i++) 
				fileByte[i] +=j*key;
			System.out.println("2: "+Arrays.toString(fileByte)+ " length: "+fileByte.length);
//			System.out.println();
			String write =  new String(fileByte);//, 0, fileByte.length, "UTF-8");
			System.out.println("workWithFile: "+ write+"length:"+ write.length()/*+"\n\n"*/);
			System.out.println("Arrays.toString(write.getBytes()): "+Arrays.toString(write.getBytes())+"\n\n\n");
			bw.write(write);//System.out.println("here writes!!!!!!!!!!!!!!!!!\nwrite\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			bw.close();
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private  byte randomKey() {
		return (byte) (Math.random()*257);
	}
	//testing the project.
	public void testEncryptions()
	{
		//First we write the test file. 
		String path = "test.txt";
		String write="";
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(path));
//			int lines =1;// (int) (Math.random()*30);
//			for (int i = 0; i < lines; i++) {
//				int digits = (int) (Math.random()*30);
//				for (int j = 0; j < digits; j++) 
//					write += ""+ (char)(Math.random()*100);
//				write+="\n";
//			}
			write = "test now";
//			System.out.println(write);
			bw.write(write);
			bw.close();
			encryptions("",path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		descriptions("",path+".encrypted", key);
		FileReader fr;
		try {
			fr = new FileReader("test.txt_decrypted.encrypted");
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(fr);
			String decrepted =br.readLine(),s;
			while((s=br.readLine())!=null)
				decrepted+="\n"+s;
			if(decrepted == null)
				decrepted = "";
			if(decrepted.equals(write))
			{
				System.out.println("the two are equal, encryption + description succeeded!!");
			}
			else
			{
				System.out.println("the two aint equal:\ndecrepted:\n||"+decrepted+"||\nwrite:\n||"+write+"||\n\n"); 
			}
			br.close();
			fr.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Files.delete(Paths.get("test.txt.encrypted"));
			Files.delete(Paths.get("test.txt_decrypted.encrypted"));
			Files.delete(Paths.get("test.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		iaf f = new iaf(1);
		
	}

}


