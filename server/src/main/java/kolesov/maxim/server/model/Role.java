package kolesov.maxim.server.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class Role implements GrantedAuthority {

    private final String name;

    public Role(RoleType type) {
        this.name = type.name();
    }

    @Override
    public String getAuthority() {
        return String.format("ROLE_%s", name);
    }

}
