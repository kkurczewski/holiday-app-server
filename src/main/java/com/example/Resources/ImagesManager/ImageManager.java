package com.example.Resources.ImagesManager;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.Resources.FilesManager.PathManager;

@Component
public class ImageManager {

	final private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private PathManager pathManager;

	public static final int DEFAULT_WIDTH = 1200;
	public static final int DEFAULT_HEIGHT = 800;
	
	public void saveImagesOnDisk(MultipartFile[] multipartFiles, String resourceFolder) throws IOException {
		
		List<BufferedImage> images = convertMultipartToImages(multipartFiles);

		int imageIndex = 1;
		for (BufferedImage image : images) {

			image = scaleImage(image, DEFAULT_WIDTH, DEFAULT_HEIGHT);

			String fileName = "img_" + imageIndex + ".jpg";
			File file = pathManager.getImageFile(resourceFolder, fileName).toFile();
			ImageIO.write(image, "jpg", file);

			imageIndex++;
		}
	}

	public InputStream getScaledImage(String hashcode, String fileName, int width, int height) throws IOException {

		File file = pathManager.getImageFile(hashcode, fileName).toFile();

		if (width == -1) width = DEFAULT_WIDTH;
		if (height == -1) height = DEFAULT_HEIGHT;

		log.info("Requested image with sizes: " + width + "x" + height);

		BufferedImage image = scaleImage(ImageIO.read(file), width, height);

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ImageIO.write(image, "jpeg", byteArrayOutputStream);
		return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
	}

	private BufferedImage scaleImage(BufferedImage inputImage, int width, int height) throws IOException {
		Image buff = inputImage.getScaledInstance(width, height, BufferedImage.SCALE_FAST);
		BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		scaledImage.getGraphics().drawImage(buff, 0, 0, null);
		return scaledImage;
	}

	private List<BufferedImage> convertMultipartToImages(MultipartFile[] multipartFiles) throws IOException {
		List<BufferedImage> images = new ArrayList<>();

		for (MultipartFile mFile : multipartFiles) {
			if (!mFile.isEmpty()) {
				images.add(ImageIO.read(mFile.getInputStream()));
			}
		}
		return images;
	}

}
