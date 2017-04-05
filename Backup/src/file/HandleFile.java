package file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class HandleFile {

	private static boolean exists(String path) {
		return new File(path).exists();
	}

	public static byte[] readFile(String path) throws IOException {
		if( !exists(path) )
			return null;
			
		Path p = Paths.get(path);
		return Files.readAllBytes(p);
	}

}
