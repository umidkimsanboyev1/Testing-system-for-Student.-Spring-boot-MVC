package uz.master.demotest.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.master.demotest.configs.security.UserDetails;


@Service
public class SessionUser {
    private Long Id;
    private Long orgId;
    private String code;

    public UserDetails getInstance() {
        return  (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }
    public Long getId() {
        return getInstance().getId();
    }



}
