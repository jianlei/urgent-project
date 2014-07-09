package ${package}.api.persistence;

/**
 * Created by dell on 14-1-16.
 */

import com.daren.core.api.persistence.IPersistentEntity;

public interface IUserBean extends IPersistentEntity {

    String getEmail();

    void setEmail(String email);

    String getName();

    void setName(String name);

    String getPassword();

    void setPassword(String password);
}
