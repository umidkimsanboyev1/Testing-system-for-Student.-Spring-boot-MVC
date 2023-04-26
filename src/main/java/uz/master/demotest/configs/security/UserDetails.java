package uz.master.demotest.configs.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import uz.master.demotest.entity.auth.AuthRole;
import uz.master.demotest.entity.auth.AuthUser;
import uz.master.demotest.enums.Role;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Getter
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private Long id;

    private String username;

    private String password;

    private Role role;


    private boolean active = true;

    private boolean blocked;

    private String fullName;
    private Long testId;

    private Set<GrantedAuthority> authorities;

    public UserDetails(AuthUser user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.active = user.isActive();
        this.blocked = user.isBlocked();
        this.fullName=user.getFullName();
        this.testId = user.getTestId();
        processAuthorities(user);
    }

    private void processAuthorities(AuthUser user) {
        authorities = new HashSet<>();
        Role role = user.getRole();

        if (Objects.isNull(role)) return;

        authorities.add(new SimpleGrantedAuthority("ROLE_"));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.blocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }
}
