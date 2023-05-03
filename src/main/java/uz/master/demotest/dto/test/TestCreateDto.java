package uz.master.demotest.dto.test;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class TestCreateDto {
    private MultipartFile file;
    private String name;
    private Long subjectId;

    private Integer numberOfQuestion;
    // time for one and all question in minutes
    private Integer timeForOneQues;

}
