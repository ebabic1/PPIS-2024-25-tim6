package ba.unsa.etf.ppis.tim6.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    public enum RoleName {
        ADMINISTRATOR,
        WAREHOUSE_MANAGER,
        WAREHOUSE_OPERATOR,
        MAINTENANCE_TEAM
    }
}
