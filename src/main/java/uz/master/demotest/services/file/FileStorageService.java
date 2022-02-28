package uz.master.demotest.services.file;



import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import uz.master.demotest.dto.file.UploadsDto;
import uz.master.demotest.entity.action.Uploads;
import uz.master.demotest.repositories.UploadsRepository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.*;
import java.util.Optional;


@Slf4j
@Service("fileService")
public class FileStorageService {
    public static final String UNICORN_UPLOADS_B_4_LIB = "/uploads/project/";
    public static final Path PATH = Paths.get(UNICORN_UPLOADS_B_4_LIB);

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
        Path rootPath = Paths.get(UNICORN_UPLOADS_B_4_LIB, generatedName);
        Files.copy(file.getInputStream(), rootPath, StandardCopyOption.REPLACE_EXISTING);
        Uploads uploadedFile = new Uploads(originalFilename,generatedName,file.getContentType(),(UNICORN_UPLOADS_B_4_LIB+ generatedName),file.getSize());
        repository.save(uploadedFile);
        return generatedName;
    }


    public UploadsDto loadResource(@NonNull String fileName) throws NoSuchFileException {
        Optional<Uploads> uploads = repository.findByGeneratedName(fileName);
        if (uploads.isEmpty())throw new NoSuchFileException("not found");
        FileSystemResource resource = new FileSystemResource(UNICORN_UPLOADS_B_4_LIB + fileName);
        return UploadsDto.builder().resource(resource).originalName(uploads.get().getOriginalName()).newName(uploads.get().getGeneratedName()).contentType(uploads.get().getContentType()).size(uploads.get().getSize()).build();
    }
}
