package ${package}.entities;

import ${package}.api.persistence.IUserBean;
import com.daren.core.impl.persistence.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Created by dell on 14-1-16.
 */
@Entity
@Table(name = "app_user")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class UserBeanImpl extends PersistentEntity implements IUserBean {

    private String name;
    private String password;
    private String email;

    public UserBeanImpl() {

    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    public static void main(String[] args) throws Exception {

    }
}
