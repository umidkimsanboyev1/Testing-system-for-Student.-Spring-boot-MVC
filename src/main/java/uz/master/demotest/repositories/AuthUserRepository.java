package uz.master.demotest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import uz.master.demotest.entity.auth.AuthUser;

import java.util.List;
import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser,Long> {
    Optional<AuthUser> getAuthUsersByUsernameAndDeletedFalse(String username);
    List<AuthUser> findAllByDeletedFalse();
    List<AuthUser>findAllByOrganizationIdAndDeletedFalse(Long id);
    @Transactional
    @Modifying
    @Query(value = "update AuthUser a set a.deleted=true , a.username=:username where  a.id=:id")
    void  deleteUser(Long id,String username);
}
