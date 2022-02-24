package uz.master.demotest.utils;


import uz.master.demotest.exceptions.NotFoundException;

public abstract class AbstractValidator<CD, UD, K> implements Validator {

    protected final BaseUtils baseUtils;

    protected AbstractValidator(BaseUtils baseUtils) {
        this.baseUtils = baseUtils;
    }

    public abstract void validateKey(K id) throws NotFoundException;

    public abstract void validOnCreate(CD cd) throws NotFoundException;

    public abstract void validOnUpdate(UD cd) throws NotFoundException;


}
