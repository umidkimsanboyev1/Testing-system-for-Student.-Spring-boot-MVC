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
    @Query(value = "update AuthUser a set a.firstName = ?1,a.lastName = ?2,a.email = ?3,a.phone = ?4,a.username = ?5 ,a.picturePath = ?6 where  a.id = ?7")
    void updateUser(String firstname,String lastname,String email,String phone,String username,String photoPath,Long id);


    Optional<AuthUser>findByIdAndDeletedFalse(Long id);

    @Transactional
    @Modifying
    @Query(value = "update AuthUser a set a.picturePath=:store where a.id=:id")
    void image(@Param("store") String store, @Param("id") Long id);

}
