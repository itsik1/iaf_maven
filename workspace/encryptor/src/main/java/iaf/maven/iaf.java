package iaf.maven;

/**
 * Hello world!
 *
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
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
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			BufferedReader br = new BufferedReader(new FileReader(path));
			String s = "";
			System.out.println("S start:");
			while((s=br.readLine())!=null)
				System.out.println(s);
			System.out.println("S end\n");
			byte[] fileByte = Files.readAllBytes(Paths.get(location,path));//path));
			System.out.println("path: "+ path);
//			System.out.println(Arrays.toString(fileByte)+"\n\nhere:\n\n");
			System.out.println("1: "+Arrays.toString(fileByte));
			for (int i = 0; i < fileByte.length; i++) 
				fileByte[i] +=j*key;
			System.out.println("2: "+Arrays.toString(fileByte)+"\n\n");
			String write =  new String(fileByte, 0, fileByte.length, "UTF-8");
//			System.out.println(write);
			bw.write(write);//System.out.println("here writes!!!!!!!!!!!!!!!!!\nwrite\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			bw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private  int randomKey() {
		return (int) (Math.random()*1000);
	}
	//testing the project.
	public void testEncryptions()
	{
		//First we write the test file. 
		String path = "test.txt";
		String write="";
		try {
			FileWriter fw = new FileWriter(path);
			BufferedWriter bw = new BufferedWriter(fw);
			int lines = (int) (Math.random()*30);
			for (int i = 0; i < lines; i++) {
				int digits = (int) (Math.random()*30);
				for (int j = 0; j < digits; j++) 
					write += ""+ (char)(Math.random()*100);
				write+="\n";
			}
//			System.out.println(write);
			bw.write(write);
			encryptions("",path);
			bw.close();
			fw.close();
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
			String decrepted = "",s;
			while((s=br.readLine())!=null)
				decrepted+=s+"\n";
			if(decrepted.equals(write))
			{
				System.out.println("the two are equal, encryption + description succeeded!!");
			}
			else
			{
				System.out.println("the two aint equal:\ndecrepted:\n"+decrepted+"\nwrite:\n"+write+"\n\n"); 
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
			Files.delete(Paths.get("test.txt"));
			Files.delete(Paths.get("test.txt.encrypted"));
			Files.delete(Paths.get("test.txt_decrypted.encrypted"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		iaf f = new iaf(1);
		
	}

}


