package uz.master.demotest.services;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.master.demotest.mappers.Mapper;
import uz.master.demotest.validator.Validator;

/**
 * @param <R> repository

 */

public abstract class AbstractService<
        R extends JpaRepository> {
    protected final R repository;

    protected AbstractService(R repository) {
        this.repository = repository;

           }
}
