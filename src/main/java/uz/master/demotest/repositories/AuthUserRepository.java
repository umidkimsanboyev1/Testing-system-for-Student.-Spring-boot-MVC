package uz.master.demotest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.master.demotest.entity.auth.AuthUser;
import uz.master.demotest.enums.Role;

import java.util.List;
import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    Optional<AuthUser> getAuthUsersByUsername(String username);

//    @Transactional
//    @Modifying
//    @Query(value = "update AuthUser a set a.deleted=true , a.username=:username where  a.id=:id")
//    void  deleteUser(Long id,String username);

    List<AuthUser> findAuthUserByRoleOrderByFullName(Role role);
    List<AuthUser> findAuthUserByRoleAndGroupNameOrderByFullName(Role role, String groupName);
    @Query("SELECT DISTINCT groupName FROM AuthUser order by groupName")
    List<String> findDistinctGroupNames();

    @Query("SELECT COUNT(a) FROM AuthUser a WHERE a.groupName = ?1")
    int countByGroupName(String groupname);

    boolean existsAuthUserByUsername(String username);

    List<AuthUser> findAllByGroupName(String groupName);


    Optional<AuthUser> findAuthUserByUsername(String username);
}
