package uz.master.demotest.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Bekpulatov Shoxruh, Thu 11:01 PM. 2/24/2022
 */
@Getter
@AllArgsConstructor
public enum ActionTexts {
    USER_JOINED("New user joined to task"),
    USER_REMOVED("User removed from task"),
    TASK_COLUMN_UPDATED("Task Column updated"),
    TASK_UPDATED("Task ");

    private String text;
}
