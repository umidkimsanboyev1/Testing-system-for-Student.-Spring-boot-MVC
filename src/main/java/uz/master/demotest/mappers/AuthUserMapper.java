package uz.master.demotest.mappers;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import uz.master.demotest.dto.auth.AddAdminDto;
import uz.master.demotest.dto.auth.AuthDto;
import uz.master.demotest.dto.auth.AuthUserCreateDto;
import uz.master.demotest.dto.auth.AuthUserUpdateDto;
import uz.master.demotest.entity.auth.AuthUser;

@Component
@Mapper(componentModel = "spring")
public interface AuthUserMapper extends BaseMapper<AuthUser,AuthDto, AuthUserCreateDto, AuthUserUpdateDto> {

    AuthUser fromDto(AddAdminDto dto);

}
