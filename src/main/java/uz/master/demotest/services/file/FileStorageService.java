package uz.master.demotest.services.file;


import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Service("fileService")
public class FileStorageService {
    public static final String UPLOAD_DIRECTORY = "/uploads/";

    private final Path rootLocation;


    public FileStorageService() {
        this.rootLocation = Paths.get(UPLOAD_DIRECTORY);
    }

    @PostConstruct
    public void init() {
        if (!Files.exists(rootLocation)) {
            try {
                Files.createDirectories(rootLocation);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String store(MultipartFile file) {
        try {
//            String originalFilename = file.getOriginalFilename();
            String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            String generatedName = "%s.%s".formatted(System.currentTimeMillis() + System.nanoTime(), extension);
            String path = UPLOAD_DIRECTORY + generatedName;
            Path rootPath = Paths.get(UPLOAD_DIRECTORY, generatedName);
            Files.copy(file.getInputStream(), rootPath, StandardCopyOption.REPLACE_EXISTING);
            return path;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Async
    public void storeAsync(MultipartFile file, String fileName) {
        try {
            Files.copy(file.getInputStream(), Paths.get(UPLOAD_DIRECTORY, fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Resource loadAsResource(String filename) {
        Resource resource = new FileSystemResource(Paths.get(UPLOAD_DIRECTORY, filename));
        return resource;
    }

}
