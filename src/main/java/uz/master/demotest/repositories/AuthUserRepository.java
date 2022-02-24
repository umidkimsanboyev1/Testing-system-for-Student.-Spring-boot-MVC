package uz.master.demotest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.master.demotest.entity.auth.AuthUser;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser,Long> {
    Optional<AuthUser> getAuthUserByUsername(String username);
}
