package uz.master.demotest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uz.master.demotest.dto.auth.AuthUserUpdateDto;
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

    @Transactional
    @Modifying
    @Query(value = "update AuthUser a set a.firstName =:firstname,a.lastName =:lastname,a.email=:email,a.phone=:phone,a.username=:username where  a.id=:id")
    void updateUser(@Param("firstname")String firstname,@Param("lastname")String lastname,@Param("email")String email,@Param("phone")String phone,@Param("username")String username,@Param("id")Long id);


    Optional<AuthUser>findByIdAndDeletedFalse(Long id);
}
