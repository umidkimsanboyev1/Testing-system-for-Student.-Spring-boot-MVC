package uz.master.demotest.enums;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Getter
@AllArgsConstructor
public enum Role {

    ADMIN,
    TEACHER,
    STUDENT;





}
