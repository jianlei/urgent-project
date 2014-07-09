package com.daren.middleware.couchdb.model;

import java.util.Date;

/**
 * @类描述：发布类
 * @创建人：sunlf
 * @创建时间：2014-04-05 上午11:38
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class Deploy {
    private String fileName;
    private Date createDate;
    private String version;
    private String couchdb_rev;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
