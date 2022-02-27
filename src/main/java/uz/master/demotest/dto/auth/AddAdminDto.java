package uz.master.demotest.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.master.demotest.entity.auth.AuthRole;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddAdminDto {

    private Long organizationId;

    private String username;

    private String password;
}
