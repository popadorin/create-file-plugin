package models;

public class FileInfo {
	private String fileLocation;
	private String name;
	private String content;

	public FileInfo(String name, String content, String fileLocation) {
		if (name.trim().isEmpty()) {
			this.name = "unkown";
		} else {
			this.name = name.trim();
		}
		this.content = content.trim();
		this.fileLocation = fileLocation.trim();
	}

	public String getName() {
		return name;
	}

	public String getContent() {
		return content;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	@Override
	public String toString() {
		return "FileInfo [fileLocation=" + fileLocation + ", name=" + name + ", content="
				+ content + "]";
	}
	
	
}
