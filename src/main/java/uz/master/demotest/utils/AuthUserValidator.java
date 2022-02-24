package uz.master.demotest.utils;

import org.springframework.stereotype.Component;
import uz.master.demotest.dto.auth.AuthUserCreateDto;
import uz.master.demotest.dto.auth.AuthUserUpdateDto;
import uz.master.demotest.exceptions.NotFoundException;

@Component
public class AuthUserValidator extends AbstractValidator<AuthUserCreateDto, AuthUserUpdateDto,Long>implements Validator {

    @Override
    public void validateKey(Long id) throws NotFoundException {

    }

    @Override
    public void validOnCreate(AuthUserCreateDto authUserCreateDto) throws NotFoundException {

    }

    @Override
    public void validOnUpdate(AuthUserUpdateDto cd) throws NotFoundException {

    }
}
