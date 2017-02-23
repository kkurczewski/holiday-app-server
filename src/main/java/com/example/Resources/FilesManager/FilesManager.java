package com.example.Resources.FilesManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FilesManager {

	@Autowired
	private PathManager pathManager;
	
	public void saveDescription(String description, String hashcode) throws IOException {
		Files.write(pathManager.getDescriptionFile(hashcode), description.getBytes(), StandardOpenOption.CREATE_NEW);
	}
	
	public BufferedReader getDescription(String hashcode) throws IOException {
		return Files.newBufferedReader(pathManager.getDescriptionFile(hashcode));
	}
	
}
