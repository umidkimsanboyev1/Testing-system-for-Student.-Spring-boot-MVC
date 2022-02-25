package uz.master.demotest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.master.demotest.entity.auth.Token;

public interface TokenRepository extends JpaRepository<Token,Long> {

}
