package helper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import models.FileInfo;

public class FileCreator {
	public void createFile(FileInfo fileInfo) throws Exception {
		System.out.println(fileInfo);
		
		byte data[] = fileInfo.getContent().getBytes();
		String location = fileInfo.getFileLocation() + "\\" +
				fileInfo.getName();
		
		Path filePath = Paths.get(location);

		if (!Files.isDirectory(filePath.getParent())) {
			Files.createDirectories(filePath.getParent());
		}
		
		if (!Files.isRegularFile(filePath)) {
			Files.createFile(filePath);
		}
		
		Files.write(filePath, data);
		
	}
}
