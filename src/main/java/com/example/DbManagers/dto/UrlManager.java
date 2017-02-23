package com.example.DbManagers.dto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.Resources.FilesManager.PathManager;

@Component
public class UrlManager {

	@Autowired
	private PathManager pathManager;

	public String getDescriptionUrl(String resourceFolder) {
		return "offers/" + resourceFolder + "/description";
	}

	public List<String> getGalleryUrls(String resourceFolder) {
		try {
			return Files.walk(pathManager.getImageDir(resourceFolder))
					.filter(path -> !path.toFile().isDirectory())
					.filter(path -> !path.toFile().isHidden())
					.map(path -> getImageUrl(resourceFolder, path.getFileName()))
					.collect(Collectors.toList());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private String getImageUrl(String resourceFolder, Path imageName) {
		return "offers/" + resourceFolder + "/images/" + imageName;
	}

}
