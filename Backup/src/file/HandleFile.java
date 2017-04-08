package file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 
 * This class builds the handler to a file
 *
 */
public class HandleFile {
	private byte[] content; 	//File's content
	private String path;		//File's pathname
	
	/**
	 * HandleFile's constructor
	 * @param path File's pathname
	 */
	public HandleFile(String path) {
		this.path = path;
		this.content = new byte[0];
	}
	
	/**
	 * Gets the file's content
	 * @return The file's content
	 */
	public byte[] getContent() {
		return content;
	}	
	
	/**
	 * Sets the file's content
	 * @param buffer New content that will replace the older file's content
	 */
	public void setContent(byte[] buffer) {
		content = buffer;
	}
	
	/**
	 * Updates the content's value reading the file's content
	 */
	public void updateContent() {
		try {
			content = readFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Updates the file writing the new content
	 */
	public void updateFile() {
		try {
			writeFile(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the content by reading the file
	 * @return The content read
	 * @throws IOException This class is the general class of exceptions produced by failed or interrupted I/O operations
	 */
	public byte[] readFile() throws IOException {
		return readFile(path);
	}
	
	/**
	 * Writes the content to the file
	 * @param buffer Content that will be written
	 * @throws IOException This class is the general class of exceptions produced by failed or interrupted I/O operations
	 */
	public void writeFile(byte[] buffer) throws IOException {
		writeFile(buffer, path);
	}

	/**
	 * Creates a new file or appends if the file already exists and then writes the respective content
	 * @param buffer Content that will be written
	 * @param path File's path
	 * @throws IOException This class is the general class of exceptions produced by failed or interrupted I/O operations
	 */
	public static void appendToFile(byte[] buffer, String path) throws IOException {
		FileOutputStream output = new FileOutputStream(path, true);
		try {
		   output.write(buffer);
		} finally {
		   output.close();
		}
	}
	
	/**
	 * Tests whether the file or directory denoted by this abstract pathname exists
	 * @param path File's path used to test if the file exists or not
	 * @return true if and only if the file or directory denoted by this abstract pathname exists, false otherwise
	 */
	public static boolean exists(String path) {
		return new File(path).exists();
	}
	
	/**
	 * Gets the file's name using the file ID and chunk ID
	 * @param fileId File ID
	 * @param chunkId Chunk ID
	 * @return The file's name
	 */
	public static String getFileName(String fileId, int chunkId) {
		return "assets/" + fileId + "_" + chunkId;
	}
	
	/**
	 * Tests whether the file denoted by this abstract pathname is a normal file
	 * @param path File's path used to see if the file is "normal"
	 * @return true if and only if the file denoted by this abstract pathname exists and is a normal file, false otherwise
	 */
	public static boolean isFile(String path) {
		return new File(path).isFile();
	}

	/**
	 * Reads the content from a file
	 * @param path File's pathname
	 * @return The content read from the file
	 * @throws IOException This class is the general class of exceptions produced by failed or interrupted I/O operations
	 */
	public static byte[] readFile(String path) throws IOException {
		if( !exists(path) )
			return null;
			
		Path p = Paths.get(path); //Converts a path string, or a sequence of strings that when joined form a path string, to a Path
		byte[] buffer = Files.readAllBytes(p); //Reads all the bytes from a file
		return buffer;
	}

	/**
	 * Writes the chunks to a file
	 * @param wChunks Chunks to be written
	 * @param path File's pathname
	 * @throws IOException This class is the general class of exceptions produced by failed or interrupted I/O operations
	 */
	public static void writeFile(byte[][] wChunks, String path) throws IOException {
		int chunkID = 0;
		for( byte[] chunk : wChunks ) {
			if( ++chunkID == 1 ) //If it's the first chunk
				writeFile(chunk, path);
			else 
				appendToFile(chunk, path); //Other chunks are appended to the file
		}
	}

	/**
	 * Writes the content to a file
	 * @param buffer Buffer/Content taht will be written
	 * @param path File's pathname
	 * @throws IOException This class is the general class of exceptions produced by failed or interrupted I/O operations
	 */
	public static void writeFile(byte[] buffer, String path) throws IOException {
		FileOutputStream output = new FileOutputStream(path);
		output.write(buffer);
		output.close();
	}
	
	/**
	 * Deletes a file
	 * @param path File that will be deleted
	 */
	public static void deleteFile(String path) {
		File file = new File(path);
		file.delete();
	}
}
