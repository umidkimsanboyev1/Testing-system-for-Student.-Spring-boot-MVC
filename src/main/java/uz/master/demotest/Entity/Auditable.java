package uz.master.demotest.Entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Bekpulatov Shoxruh, Wed 2:56 PM. 2/23/2022
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable implements Serializable,BaseEntity



{

    private String created_at = new Date().toString();
    private String updated_at;
    private boolean isDeleted = false;

}
