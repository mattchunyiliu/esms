package kg.kundoluk.school.model.references;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import kg.kundoluk.school.model.base.TimedEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties({"createdAt", "updatedAt"})
@Entity
@Table(name = "roles")
public class Role extends TimedEntity {


    private static final long serialVersionUID = 4852642461636449745L;

    @Override
    public void prePersist() {
        super.prePersist();
    }

    @NotEmpty
    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false, unique = true)
    private String code;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role that = (Role) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return code;
    }
}
