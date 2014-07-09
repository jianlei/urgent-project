package com.daren.middleware.couchdb.api;

import com.daren.core.api.IEntity;
import org.lightcouch.Attachment;

import java.util.Map;

/**
 * @类描述：Couchdb的基础实现接口类
 * @创建人：sunlf
 * @创建时间：下午2:02
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public interface ICouchdbEntity extends IEntity {
    public String getId();

    public String getRevision();

    public Map<String, Attachment> getAttachments();

    public void setId(String id);

    public void setRevision(String revision);

    public void setAttachments(Map<String, Attachment> attachments);

    public void addAttachment(String name, Attachment attachment);

}
