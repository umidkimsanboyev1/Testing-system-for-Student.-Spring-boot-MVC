package uz.master.demotest.dto.file;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.Resource;

@Getter
@Setter
@Builder
public class UploadsDto {
    private String originalName;
    private String newName;
    private long size;
    private String url;
    private String contentType;
    private Resource resource;
}