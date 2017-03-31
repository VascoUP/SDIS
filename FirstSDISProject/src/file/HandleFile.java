package file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

public class HandleFile {
	private byte[] content;
	private String path;
	
	public HandleFile(String path) {
		this.path = path;
		this.content = new byte[0];
	}
	
	public byte[] getContent() {
		return content;
	}
	
	public void setContent(byte[] buffer) {
		content = buffer;
	}
	
	
	public void updateContent() {
		try {
			content = readFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void updateFile() {
		try {
			writeFile(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void writeFile(byte[] buffer) throws IOException {
		writeFile(buffer, path);
	}
	
	public byte[] readFile() throws IOException {
		return readFile(path);
	}
	
	public static void writeFile(ArrayList<byte[]> wChunks, String path) throws IOException {
		Iterator<byte[]> iter = wChunks.iterator();
		int chunkID = 0;
		
		while( iter.hasNext() ) {
			byte[] chunk = iter.next();
			
			if( ++chunkID == 1 )
				writeFile(chunk, path);
			else 
				appendToFile(chunk, path);
		}
	}
	
	
	public static void writeFile(byte[] buffer, String path) throws IOException {
		FileOutputStream output = new FileOutputStream(path);
		output.write(buffer);
		output.close();
	}	
	
	public static void appendToFile(byte[] buffer, String path) throws IOException {
		FileOutputStream output = new FileOutputStream(path, true);
		try {
		   output.write(buffer);
		} finally {
		   output.close();
		}
	}
	
	public static boolean isFile(String path) {
		return new File(path).isFile();
	}
	
	public static boolean exists(String path) {
		return new File(path).exists();
	}
	
	public static byte[] readFile(String path) throws IOException {
		if( !exists(path) )
			return null;
			
		Path p = Paths.get(path);
		byte[] buffer = Files.readAllBytes(p);
		return buffer;
	}
	
	public static String getFileName(String fileId, int chunkId) {
		return fileId + "_" + chunkId;
	}
}
