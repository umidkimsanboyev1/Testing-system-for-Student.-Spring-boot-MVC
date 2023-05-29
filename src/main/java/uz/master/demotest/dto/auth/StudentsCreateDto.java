package uz.master.demotest.dto.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class StudentsCreateDto {

    private MultipartFile file;

}
