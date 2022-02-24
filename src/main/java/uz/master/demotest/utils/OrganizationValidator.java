package uz.master.demotest.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uz.master.demotest.dto.organization.OrganizationCreateDto;
import uz.master.demotest.dto.organization.OrganizationUpdateDto;
import uz.master.demotest.exceptions.NotFoundException;


// TODO: 2/24/2022  o'zomizni exception yozishimiz kerak

@Component
public class OrganizationValidator extends AbstractValidator<OrganizationCreateDto, OrganizationUpdateDto, Long> {


    @Autowired
    protected OrganizationValidator(BaseUtils baseUtils) {
        super(baseUtils);
    }

    @Override
    public void validateKey(Long id) throws NotFoundException {

    }

    @Override
    public void validOnCreate(OrganizationCreateDto organizationCreateDto) throws NotFoundException {

    }

    @Override
    public void validOnUpdate(OrganizationUpdateDto cd) throws NotFoundException  {

    }
}
