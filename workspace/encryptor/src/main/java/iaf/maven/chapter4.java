package iaf.maven;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class chapter4 {
	/**
	 * Hello world!
	 *
	 */

	int choice;//
	public chapter4(int itterations)
	{
	}
	public chapter4() {
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
			byte key = (byte) sc.nextInt();
			descriptions(location,path,key);
		}


	}
	public void descriptions(String location, String path, byte key) {
		int index = path.lastIndexOf(".");
		String file = path.substring(0, index) + "_decrypted"+path.substring(index);
		workWithFile(key,file,location,path,-1);
	}
	/***
	 * this function encrypts a given file.
	 * @param path string of file's path.
	 */
	byte key;
	public void encryptions(String location, String path) {
		key = randomKey();
		System.out.println(key);
		String file = path + ".encrypted";
		workWithFile(key,file,location,path,1);

	}
	private void workWithFile(byte key, String file_output,String location,  String path_input_name, int j) 
	{
		if(location != "")
			path_input_name = location+"/"+path_input_name;
		FileInputStream in = null;
		FileOutputStream out = null;

		try {
			in = new FileInputStream(path_input_name);
			out = new FileOutputStream(file_output);
			byte c[] = new byte[1];

			while (in.read(c) != -1) {
				out.write(c[0]+j*key);
			}
			in.close();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private byte randomKey() {
		return (byte) (Math.random()*257);
	}

}



