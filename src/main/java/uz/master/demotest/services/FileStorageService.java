package uz.master.demotest.services;


import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import uz.master.demotest.repositories.UploadsRepository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.*;


@Slf4j
@Service("fileService")
public class FileStorageService {
    public static final String filePath = "/JavaProject/tets/AtomsProject/src/main/resources/files";
    public static final Path PATH = Paths.get(filePath);

    private final UploadsRepository repository;

    public FileStorageService(UploadsRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void init() {
        if (!Files.exists(PATH)) {
            try {
                Files.createDirectories(PATH);
            } catch (IOException e) {
                e.printStackTrace();
                log.error(e.getMessage(), e);
            }
        }
    }

    @SneakyThrows
    public String store(@NonNull MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String generatedName = "%s.%s".formatted(System.currentTimeMillis(), extension);
        Path rootPath = Paths.get(filePath, generatedName);
        Files.copy(file.getInputStream(), rootPath, StandardCopyOption.REPLACE_EXISTING);
        uz.master.demotest.entity.action.Uploads uploadedFile = new uz.master.demotest.entity.action.Uploads(originalFilename,generatedName,file.getContentType(),(filePath + generatedName),file.getSize());
        repository.save(uploadedFile);
        return generatedName;
    }
}