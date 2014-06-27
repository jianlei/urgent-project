package com.daren.core.impl.persistence;

import com.daren.core.api.exception.StaleEntityException;
import com.daren.core.api.persistence.IPersistentEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * Base entity for all persistence entities in the system.
 *
 * @author Aliaksei Papou
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class PersistentEntity implements IPersistentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "id_")
    private long id;

    @Version
    @Column(name = "version_")
    private long version;
    private Date creationDate = new Date();// 创建日期
    private String createBy;    // 创建者
    private String updateBy;    // 更新者
    private Date updateDate;// 更新日期

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public long getVersion() {
        return version;
    }

    @Override
    public void setVersion(long vers) {
        if (vers < version) {
            throw new StaleEntityException(this);
        }
        this.version = vers;
    }

    @Override
    public String getCreateBy() {
        return createBy;
    }

    @Override
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @Override
    public String getUpdateBy() {
        return updateBy;
    }

    @Override
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Override
    public Date getUpdateDate() {
        return updateDate;
    }

    @Override
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (other == null) {
            return false;
        } else if (getClass().isAssignableFrom(other.getClass())) {
            if (getIdentifier() == null) {
                return false;
            } else {
                final PersistentEntity pe = (PersistentEntity) other;
                return getIdentifier().equals(pe.getIdentifier());
            }
        }

        return false;
    }

    public int hashCode() {
        if (getIdentifier() != null) {
            return getIdentifier().hashCode();
        }
        return super.hashCode();
    }

    public Serializable getIdentifier() {
        if (id == -1L) {
            return null;
        } else {
            return id;
        }
    }
}