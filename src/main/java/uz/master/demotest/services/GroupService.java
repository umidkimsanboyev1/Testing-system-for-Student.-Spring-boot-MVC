package uz.master.demotest.services;

import org.springframework.stereotype.Service;
import uz.master.demotest.dto.GroupsDTO;
import uz.master.demotest.entity.Groups;
import uz.master.demotest.repositories.AuthUserRepository;
import uz.master.demotest.repositories.GroupRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final AuthUserRepository authUserRepository;

    public GroupService(GroupRepository groupRepository, AuthUserRepository authUserRepository) {
        this.groupRepository = groupRepository;
        this.authUserRepository = authUserRepository;
    }


    public List<String> getAllGroups() {
        List<String> distinctGroupNames = authUserRepository.findDistinctGroupNames();
        distinctGroupNames.removeIf(s -> s == null);
        distinctGroupNames.removeIf(groupName -> groupName != null && (groupName.equals("ADMIN") || groupName.equals("DEKANAT") || groupName.equals("TEACHER")));
        return distinctGroupNames;
    }

    public List<GroupsDTO> getAllGroupsDTO() {
        List<String> allGroups = getAllGroups();
        List<GroupsDTO> results = new ArrayList<>();
        for (String allGroup : allGroups) {
            GroupsDTO dto = new GroupsDTO();
            dto.setName(allGroup);
            dto.setCount(authUserRepository.countByGroupName(allGroup));
            results.add(dto);
        }
        return results;
    }
}
