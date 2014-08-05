package com.daren.file.entities;

import com.daren.core.api.persistence.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 应急演练管理-图片
 * Created by 张清欣 on 14-7-28.
 */
@Entity
@Table(name = "urg_dri_image")
public class ImageBean extends PersistentEntity {
    private String name;            //图片名称
    private String type;            //图片类型
    private long size;           //图片大小
    private long attach;           //图片归属（应急演练管理ID）
    private String description;    //描述
    private String filePath;        //图片路径

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
