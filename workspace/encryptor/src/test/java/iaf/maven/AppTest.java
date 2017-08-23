package iaf.maven;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }
    public void testEncryptions()
	{
    	iaf check = new iaf(1);
		//First we write the test file. 
		String path = "test.txt";
		String write="";
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(path));
			write = "bw.write(write);\nbw.close();\nencryptions(";
			bw.write(write);
			bw.close();
			check.encryptions("",path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		check.descriptions("",path+".encrypted", check.key);
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
			br.close();
			fr.close();
			assert(decrepted.equals(write)||(decrepted+"\n").equals(write)||(decrepted).equals(write+"\n"));
//			{
//				System.out.println("the two are equal, encryption + description succeeded!!");
//			}
//			else
//			{
//				System.out.println(write.length()+decrepted.length());
//				System.out.println("the two aint equal:\ndecrepted:\n||"+decrepted+"||\nwrite:\n||"+write+"||\n\n"); 
//				for (int i = 0; i < write.length()&&decrepted.length()>i; i++) {
//					if(write.charAt(i)!=decrepted.charAt(i))
//						System.out.println(i+ ":i, "+ (int)write.charAt(i)+ " " +(int)decrepted.charAt(i));
//				}
//				System.out.println();
//			}
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

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
}
