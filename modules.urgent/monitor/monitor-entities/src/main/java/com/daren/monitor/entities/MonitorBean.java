package com.daren.monitor.entities;

import com.daren.core.api.persistence.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Created by dell on 14-1-16.
 */
@Entity
@Table(name = "urg_monitor")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class MonitorBean extends PersistentEntity {

    private String name;//监控点名称
    private String type;//类型
    private String ipAddress;//IP地址
    private String port;//端口
    private String channel;//通道号
    private String connectionProtocol;//连接协议
    private String serial;//编号
    private String affiliation;//所属企业

    public MonitorBean() {
        name = "";
        type = "";
        ipAddress = "";
        port = "";
        channel = "";
        connectionProtocol = "";
        serial = "";
        affiliation = "";
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

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getConnectionProtocol() {
        return connectionProtocol;
    }

    public void setConnectionProtocol(String connectionProtocol) {
        this.connectionProtocol = connectionProtocol;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }
}
