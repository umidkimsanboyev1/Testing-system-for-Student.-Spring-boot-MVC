package uz.master.demotest.mappers;

import uz.master.demotest.dto.auth.AuthDto;
import uz.master.demotest.dto.auth.AuthUserCreateDto;
import uz.master.demotest.dto.auth.AuthUserUpdateDto;
import uz.master.demotest.entity.auth.AuthUser;

public interface AuthUserMapper extends BaseMapper<AuthDto, AuthUser, AuthUserCreateDto, AuthUserUpdateDto> {

}
