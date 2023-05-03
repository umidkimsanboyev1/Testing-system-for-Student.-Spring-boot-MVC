package uz.master.demotest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.master.demotest.entity.auth.AuthUser;
import uz.master.demotest.enums.Role;

import java.util.List;
import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    Optional<AuthUser> getAuthUsersByUsernameAndDeletedFalse(String username);

//    @Transactional
//    @Modifying
//    @Query(value = "update AuthUser a set a.deleted=true , a.username=:username where  a.id=:id")
//    void  deleteUser(Long id,String username);

    List<AuthUser> findAuthUserByRoleAndDeletedFalseOrderById(Role role);
    List<AuthUser> findAuthUserByRoleAndDeletedFalseAndGroupNameOrderById(Role role, String groupName);

    boolean existsAuthUserByUsername(String username);



}
