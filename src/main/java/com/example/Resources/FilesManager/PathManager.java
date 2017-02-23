package com.example.Resources.FilesManager;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;

@Component
public class PathManager {
	
	final private String ROOT_DIRECTORY = "D:/server/upload-dir";

	public Path getRootDir(String resourceFolder) {
		return Paths.get(ROOT_DIRECTORY, resourceFolder);
	}
	
	public Path getImageDir(String resourceFolder) {
		return getRootDir(resourceFolder).resolve("images");
	}
	
	public Path getImageFile(String resourceFolder, String imageName) {
		return getImageDir(resourceFolder).resolve(imageName);
	}

	public Path getDescriptionFile(String resourceFolder) {
		return getRootDir(resourceFolder).resolve("desc.txt");
	}
	
}
