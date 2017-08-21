package models;

public class FileInfo {
	private String name;
	private String extension;
	private String content;
	private String fileLocation;

	public FileInfo(String name, String extension, String content, String fileLocation) {
		if (name.trim().isEmpty()) {
			this.name = "unkown";
		} else {
			this.name = name.trim();
		}
		this.extension = extension.trim();
		this.content = content.trim();
		this.fileLocation = fileLocation.trim();
	}

	public String getName() {
		return name;
	}

	public String getExtension() {
		return extension;
	}

	public String getContent() {
		return content;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	@Override
	public String toString() {
		return "FileInfo [name=" + name + ", extension=" + extension + ", content=" + content + ", fileLocation="
				+ fileLocation + "]";
	}
	
	
}
