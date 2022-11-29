package kg.kundoluk.school.components.hateoas.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import kg.kundoluk.school.model.base.BaseEntity;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;


public class BaseResource extends RepresentationModel implements Serializable {

    @JsonProperty
    public Long id;

    public Long getBaseId() {
        return id;
    }

    public void setBaseId(Long id) {
        this.id = id;
    }
}
