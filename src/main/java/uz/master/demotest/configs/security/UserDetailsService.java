package uz.master.demotest.configs.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.master.demotest.entity.auth.AuthUser;
import uz.master.demotest.repositories.UserRepository;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository repository;

    public UserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser user = repository.getAuthUserByUsername(username).orElseThrow(() -> {throw new UsernameNotFoundException("User Not Found");});
        return new uz.master.demotest.configs.security.UserDetails(user);
    }
}
