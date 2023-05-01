package uz.master.demotest.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddStudentDto {

    public String fullName;
    public String username;
    public String groupName;
    public String password1;
    public String password2;

}
