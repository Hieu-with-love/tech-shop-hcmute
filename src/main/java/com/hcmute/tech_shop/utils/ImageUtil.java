package com.hcmute.tech_shop.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.UUID;

public class ImageUtil {
    private static final Path ROOT = Paths.get("./uploads");

    static {
        try {
            Files.createDirectories(ROOT);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    public static String saveImage(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String uniqueFileName = UUID.randomUUID().toString() + "_" + LocalDate.now() + "_" + fileName;
        Files.copy(file.getInputStream(), ROOT.resolve(uniqueFileName), StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }

    public static boolean deleteImage(String filename) throws IOException {
        Path file = ROOT.resolve(filename);
        return Files.deleteIfExists(file);
    }

    public static boolean isValidSuffixImage(String img) {
        return img.endsWith(".jpg") || img.endsWith(".jpeg") ||
                img.endsWith(".png") || img.endsWith(".gif") ||
                img.endsWith(".bmp");
    }

    public static String updateImage(MultipartFile file, String oldFilename) throws IOException {
        if (deleteImage(oldFilename)) {
            return saveImage(file);
        }
        return saveImage(file);
    }
}
