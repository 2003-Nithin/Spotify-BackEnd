package spotify_app.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${file.upload.dir}")
    private String uploadDir;

    public String storeFile(MultipartFile file) {
        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFileName = file.getOriginalFilename();
            String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFileName;

            Path filePath = uploadPath.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), filePath,
                    StandardCopyOption.REPLACE_EXISTING);

            return uniqueFileName;

        } catch (IOException e) {
            throw new RuntimeException("Could not store file: " + e.getMessage());
        }
    }
}