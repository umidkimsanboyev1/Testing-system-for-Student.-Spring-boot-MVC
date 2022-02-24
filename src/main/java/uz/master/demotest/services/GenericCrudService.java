package uz.master.demotest.services;


import org.springframework.stereotype.Service;
import uz.master.demotest.dto.BaseDto;
import uz.master.demotest.dto.GenericDto;
import uz.master.demotest.entity.BaseEntity;

import java.io.Serializable;

/**
 * @param <E>  -> Entity
 * @param <D>  -> Dto
 * @param <CD> -> Create Dto
 * @param <UD> -> Update Dto
 * @param <K>  -> class that defines the primary key for your pojo class
 */
public interface GenericCrudService<
        E extends BaseEntity,
        D extends GenericDto,
        CD extends BaseDto,
        UD extends GenericDto,
        K extends Serializable> extends GenericService<D, K> {

    K create(CD createDto);

    Void delete(K id);

    Void update(UD updateDto);

}
