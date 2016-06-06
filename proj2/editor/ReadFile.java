package editor;

import java.io.*;
import java.util.LinkedList;


/**
 * Created by LouisCho on 6/4/16.
 */
public class ReadFile {
	String path;
	LinkedList<Character> buffer = new LinkedList<>();
	public ReadFile(String p) {
		path = p;
		try {
			File inputFile = new File(path);
			FileReader reader = new FileReader(inputFile);
			BufferedReader bufferedReader = new BufferedReader(reader);

			// Keep reading from the file until input read() returns -1, which means the end of the file
			// was reached.
			int intRead = -1;

			while ((intRead = bufferedReader.read()) != -1) {
				char charRead = (char) intRead;
				buffer.addLast(charRead);
			}

		} catch(FileNotFoundException fileNotFoundException) {
			System.out.println("File not found! Exception was: " + fileNotFoundException);
		} catch(IOException ioException) {
			System.out.println("Error when reading; exception was: " + ioException);
		}
	}

	public LinkedList returnBuffer() {
		return buffer;
	}
}


