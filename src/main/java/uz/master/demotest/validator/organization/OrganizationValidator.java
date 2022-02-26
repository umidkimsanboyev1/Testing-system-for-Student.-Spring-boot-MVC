package uz.master.demotest.validator.organization;

import org.springframework.stereotype.Component;
import uz.master.demotest.dto.organization.OrganizationCreateDto;
import uz.master.demotest.dto.organization.OrganizationUpdateDto;
import uz.master.demotest.exceptions.NotFoundException;
import uz.master.demotest.validator.AbstractValidator;


// TODO: 2/24/2022  o'zomizni exception yozishimiz kerak

@Component
public class OrganizationValidator extends AbstractValidator<OrganizationCreateDto, OrganizationUpdateDto, Long> {


    @Override
    public void validateKey(Long id) throws NotFoundException {

    }

    @Override
    public void validOnCreate(uz.master.demotest.dto.organization.OrganizationCreateDto organizationCreateDto) throws NotFoundException {

    }

    @Override
    public void validOnUpdate(OrganizationUpdateDto cd) throws NotFoundException {

    }

}
