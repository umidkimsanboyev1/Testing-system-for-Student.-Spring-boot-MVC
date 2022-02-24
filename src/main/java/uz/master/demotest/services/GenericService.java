package uz.master.demotest.services;


import uz.master.demotest.dto.GenericDto;

import java.io.Serializable;
import java.util.List;


/**
 * @param <D> -> Dto
 * @param <K> -> class that defines the primary key for your pojo class
 */
public interface GenericService<
        D extends GenericDto,
        K extends Serializable> extends BaseService {

    List<D> getAll();

    D get(K id);

}
