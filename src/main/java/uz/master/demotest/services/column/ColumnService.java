package uz.master.demotest.services.column;


import uz.master.demotest.dto.column.ColumnCreateDto;
import uz.master.demotest.dto.column.ColumnDto;
import uz.master.demotest.dto.column.ColumnUpdateDto;
import uz.master.demotest.entity.column.ProjectColumn;
import uz.master.demotest.mappers.ColumnMapper;
import uz.master.demotest.repositories.ColumnRepository;
import uz.master.demotest.services.AbstractService;
import uz.master.demotest.services.GenericCrudService;
import uz.master.demotest.services.GenericService;
import uz.master.demotest.utils.ColumnValidator;


import java.util.List;
import java.util.Optional;

public class ColumnService extends AbstractService<
        ColumnRepository,
        ColumnMapper,
        ColumnValidator>
        implements GenericService<ColumnDto, Long>,
        GenericCrudService<ProjectColumn, ColumnDto, ColumnCreateDto, ColumnUpdateDto, Long> {


    protected ColumnService(ColumnRepository repository, ColumnMapper mapper, ColumnValidator validator) {
        super(repository, mapper, validator);
    }

    @Override
    public Long create(ColumnCreateDto createDto) {
        ProjectColumn column = mapper.fromCreateDto(createDto);
        ProjectColumn save = repository.save(column);
        return save.getId();
    }

    @Override
    public Void delete(Long id) {
        Optional<ProjectColumn> byId = repository.findById(id);

        return ;
    }

    @Override
    public Void update(ColumnUpdateDto updateDto) {
        return null;
    }

    @Override
    public List<ColumnDto> getAll() {
        return null;
    }

    @Override
    public ColumnDto get(Long id) {
        return null;
    }
}
