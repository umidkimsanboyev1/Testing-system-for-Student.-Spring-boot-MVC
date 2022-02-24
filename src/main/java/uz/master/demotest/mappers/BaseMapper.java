package uz.master.demotest.mappers;

import java.util.List;

/**
 * @param <E>  -> Entity
 * @param <D>  -> Dto
 * @param <CD> -> Create Dto
 * @param <UD> -> Update Dto
 */

public interface BaseMapper<E, D, CD, UD> extends Mapper {

    D toDto(E e);

    List<D> toDto(List<E> e);

    E fromCreateDto(CD cd);

    E fromUpdateDto(UD ud);

}
