package uz.master.demotest.dto.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import uz.master.demotest.dto.BaseDto;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProjectCreateDto implements BaseDto {
    private String name;
    private String description;
    private MultipartFile tz;
    private LocalDateTime deadline;
}
