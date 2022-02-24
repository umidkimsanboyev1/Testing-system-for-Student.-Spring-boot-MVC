package uz.master.demotest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.master.demotest.entity.auth.AuthRole;


import java.util.Optional;


public interface AuthRoleRepository extends JpaRepository<AuthRole,Long> {
    Optional<AuthRole> findAuthRoleByCode(String code);

}
