package file;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
	
	
	public static void writeFile(byte[] buffer, String path) throws IOException {
		FileOutputStream output = new FileOutputStream(path);
		output.write(buffer);
		output.close();
	}
	
	public static byte[] readFile(String path) throws IOException {
		Path p = Paths.get(path);
		byte[] buffer = Files.readAllBytes(p);
		return buffer;
	}
}
