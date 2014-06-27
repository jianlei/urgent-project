package com.daren.resource;

/**
 * @类描述：资源接口类
 * @创建人：sunlf
 * @创建时间：2014-04-06 上午11:15
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class Resource {
    private byte[] content;
    private String contentType;
    private long length;
    private String name;

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
