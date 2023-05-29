package uz.master.demotest.dto.test;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TestCreateDto {


    private MultipartFile file;

    private String name;
    private Integer numberOfQuestion;
    // time for one and all question in minutes
    private Integer timeForOneQues;

}
