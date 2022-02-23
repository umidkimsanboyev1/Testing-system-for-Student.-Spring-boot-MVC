package uz.master.demotest.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Permission {
    MANAGER_DELETE("manager_delete"),
    MANAGER_CREATE("manager_create"),
    ADMIN_DELETE("admin_delete"),
    ADMIN_CREATE("admin_create"),

    USER_CREATE("user_create"),
    USER_DELETE("user_delete");

    private final String value;

}
