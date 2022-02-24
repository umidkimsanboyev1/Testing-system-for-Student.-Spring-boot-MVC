package uz.master.demotest.configs.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import uz.master.demotest.entity.auth.AuthRole;
import uz.master.demotest.entity.auth.AuthUser;
import uz.master.demotest.entity.organization.Organization;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Getter
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private Long id;

    private String username;

    private String password;

    private AuthRole role;

    private Long organization;

    private boolean active = true;

    private boolean blocked;

    private Set<GrantedAuthority> authorities;

    public UserDetails(AuthUser user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.organization=user.getOrganizationId();
        this.active = user.isActive();
        this.blocked = user.isBlocked();
        processAuthorities(user);
    }

    private void processAuthorities(AuthUser user) {
        authorities = new HashSet<>();
        AuthRole role = user.getRole();

        if (Objects.isNull(role)) return;

        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getCode()));

        if (Objects.isNull(role.getPermissions())) return;

        role.getPermissions().forEach(authPermission -> authorities.add(new SimpleGrantedAuthority(authPermission.getCode())));

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
        return this.blocked;
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
