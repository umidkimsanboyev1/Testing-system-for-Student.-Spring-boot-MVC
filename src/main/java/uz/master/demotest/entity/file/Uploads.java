package uz.master.demotest.entity.action;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.io.Resource;
import uz.master.demotest.entity.Auditable;

import javax.persistence.Entity;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Uploads extends Auditable {

    private String originalName;
    private String generatedName;
    private String contentType;
    private long size;
    private String path;

    public Uploads(String originalFilename, String generatedName, String contentType, String s, long size) {
        this.originalName=originalFilename;
        this.generatedName=generatedName;
        this.contentType=contentType;
        this.path=s;
        this.size=size;
    }
}