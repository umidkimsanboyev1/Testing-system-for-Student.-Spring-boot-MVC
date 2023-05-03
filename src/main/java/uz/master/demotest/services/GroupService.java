package uz.master.demotest.services;

import org.springframework.stereotype.Service;
import uz.master.demotest.entity.Groups;
import uz.master.demotest.repositories.GroupRepository;

import java.util.List;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }


    public List<Groups> getAllGroups() {
        List<Groups> allByDeletedFalse = groupRepository.findAllByDeletedFalse();
        return allByDeletedFalse;
    }

}
