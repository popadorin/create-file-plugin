package helper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import models.FileInfo;

public class FileCreator {
	public void createFile(FileInfo fileInfo) throws Exception {
		// implement the creation of a file
		System.out.println(fileInfo);
		
		byte data[] = fileInfo.getContent().getBytes();
		String location = fileInfo.getFileLocation() + "\\" +
				fileInfo.getName() +
				fileInfo.getExtension();
		
		System.out.println("location:" + location);
		
		Path filePath = Paths.get(location);
		try {
			Files.write(filePath, data);
		} catch (Exception e) {
			Files.createFile(filePath);
			Files.write(filePath, data);
		}
		
	}
}
