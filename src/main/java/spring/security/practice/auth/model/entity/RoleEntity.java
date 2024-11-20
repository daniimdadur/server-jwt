package spring.security.practice.auth.model.entity;

import jakarta.persistence.*;
import lombok.*;
import spring.security.practice.util.CommonUtil;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "t_roles")
public class RoleEntity {

    @Id
    @Column(name = "rid", length = 36)
    private String id;

    @Column(name = "name", length = 64)
    private String name;

    @PrePersist
    private void generateId() {
        if (this.id == null) {
            this.id = CommonUtil.getUUID();
        }
    }

    public RoleEntity(String name) {
        this.name = name;
    }
}
