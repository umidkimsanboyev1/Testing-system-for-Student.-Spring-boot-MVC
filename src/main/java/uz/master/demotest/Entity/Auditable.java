package uz.master.demotest.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author Bekpulatov Shoxruh, Wed 2:56 PM. 2/23/2022
 */
@Getter
@Setter
public class Auditable {

    private String created_at = new Date().toString();
    private String updated_at;
    private boolean isDeleted = false;

}
