package com.example.Resources;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourcesDeleter extends SimpleFileVisitor<Path> {

	final private Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

		Files.delete(file);
		log.info(file + " deleted");
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException e) throws IOException {

		if (e == null) {
			Files.delete(dir);
			log.info(dir + " deleted");
			return FileVisitResult.CONTINUE;
		} else {
			throw e;
		}
	}
	
}