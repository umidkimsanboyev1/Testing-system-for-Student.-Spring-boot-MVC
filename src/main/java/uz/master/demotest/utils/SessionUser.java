package uz.master.demotest.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.master.demotest.configs.security.UserDetails;

import java.time.LocalDateTime;


@Service
public class SessionUser {
    private Long Id;
    private String code;

    public UserDetails getInstance() {
        return  (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }
    public Long getId() {
        return getInstance().getId();
    }
    public String getFullName(){return getInstance().getFullName();}
    public Long getTestId(){return getInstance().getTestId();}
    public Integer getQuesNumber(){return getInstance().getQuesNumber();}
    public LocalDateTime getTime(){return getInstance().getTime();}



}
