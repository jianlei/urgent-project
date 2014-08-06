package com.daren.file.entities;

import com.daren.core.api.persistence.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * Created by 王凯冉 on 20140804.
 */
@Entity
@Table(name = "urg_dri_docment")
public class DocumentBean extends PersistentEntity {
    private String name;            //文档名称
    private String type;            //文档类型
    private long size;           //文档大小
    private long attach;           //文档归属（应急演练管理ID）
    private String description;    //描述
    private String filePath;        //文档路径

    public DocumentBean(){
        name = "";
        type = "";
        size=0;
        attach=0;
        description="";
        filePath="";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getAttach() {
        return attach;
    }

    public void setAttach(long attach) {
        this.attach = attach;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
