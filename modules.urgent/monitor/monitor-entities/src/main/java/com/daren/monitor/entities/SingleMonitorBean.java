package com.daren.monitor.entities;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @类描述：单兵设备实体
 * @创建人： WKR
 * @创建时间：14-9-28
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
@Entity
@Table(name = "urg_single_monitor")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class SingleMonitorBean extends MonitorBean {
    private String equipmentNumber;//设备编号
    private String equipmentId;//设备编号
    private String pagIP;//海康DEMO上的参数，应该是所谓的网管服务器IP
    private String pagPort;//海康DEMO上的参数，应该是所谓的网管服务器端口
    private String streamingMediaIP;//海康DEMO上的参数，流媒体服务器IP
    private String streamingMediaPort;//海康DEMO上的参数，流媒体服务器Port
    private String jd;//海康DEMO上的参数，流媒体服务器Port
    private String wd;//海康DEMO上的参数，流媒体服务器Port

    public SingleMonitorBean() {
        equipmentNumber = "";
        equipmentId = "";
        pagIP = "";
        pagPort = "";
        streamingMediaIP = "";
        streamingMediaPort = "";
        jd = "124.343323";
        wd = "43.179628";
    }

    public String getJd() {
        return jd;
    }

    public void setJd(String jd) {
        this.jd = jd;
    }

    public String getWd() {
        return wd;
    }

    public void setWd(String wd) {
        this.wd = wd;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getPagPort() {
        return pagPort;
    }

    public void setPagPort(String pagPort) {
        this.pagPort = pagPort;
    }

    public String getEquipmentNumber() {
        return equipmentNumber;
    }

    public void setEquipmentNumber(String equipmentNumber) {
        this.equipmentNumber = equipmentNumber;
    }

    public String getPagIP() {
        return pagIP;
    }

    public void setPagIP(String pagIP) {
        this.pagIP = pagIP;
    }

    public String getStreamingMediaIP() {
        return streamingMediaIP;
    }

    public void setStreamingMediaIP(String streamingMediaIP) {
        this.streamingMediaIP = streamingMediaIP;
    }

    public String getStreamingMediaPort() {
        return streamingMediaPort;
    }

    public void setStreamingMediaPort(String streamingMediaPort) {
        this.streamingMediaPort = streamingMediaPort;
    }
}
