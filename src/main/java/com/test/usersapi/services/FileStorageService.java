package com.test.usersapi.services;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

@Service
public class FileStorageService {

    private static final String STORAGE_LOCATION = "pdf";
    private final Path fileStorageLocation = Path.of(STORAGE_LOCATION);

    public String saveFile(byte[] content, String filename) throws IOException {
        // Ensure the directory exists
        File storageDir = new File(STORAGE_LOCATION);
        storageDir.mkdirs();

        // Generate a unique file name to avoid collisions
        String uniqueFilename = generateUniqueFilename(filename);
        String filepath = storageDir.getPath() + File.separator + uniqueFilename;

        // Save the file to disk
        FileOutputStream outputStream = new FileOutputStream(filepath);
        outputStream.write(content);
        outputStream.close();

        // Log the saved file location
        System.out.println("File saved to: " + filepath);
        return uniqueFilename;
    }

    private String generateUniqueFilename(String filename) {
        String extension = FilenameUtils.getExtension(filename);
        String baseName = FilenameUtils.getBaseName(filename);

        File file = new File(STORAGE_LOCATION + File.separator + filename);
        if (!file.exists()) {
            return filename;
        }

        int i = 1;
        while (true) {
            String newFilename = baseName + " (" + i + ")." + extension;
            file = new File(STORAGE_LOCATION + File.separator + newFilename);
            if (!file.exists()) {
                return newFilename;
            }
            i++;
        }
    }
    
    public byte[] getPdfBytes(String fileName) throws IOException {
        String filePath = STORAGE_LOCATION + File.separator + fileName;
        File file = new File(filePath);
        try (InputStream inputStream = new FileInputStream(file);
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
			    outputStream.write(buffer, 0, bytesRead);
			}
			return outputStream.toByteArray();
		}
    }
    
    public Resource loadFileAsResource(String fileName) throws FileNotFoundException {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found " + fileName);
        }
    }
}

