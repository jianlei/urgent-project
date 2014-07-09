package com.daren.middleware.couchdb.config;

/**
 * @类描述：CouchDB配置文件
 * @创建人：sunlf
 * @创建时间：2014-04-25 下午3:32
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class CouchdbConfig {
    private static CouchdbConfig couchdbConfig = new CouchdbConfig();

    //The database name.
    private String dbName;
    //To create a new database if it does not already exist
    private boolean createDbIfNotExist;
    //The protocol to use (i.e http or https)
    private String protocol;
    //The database host address
    private String host;
    //The database listening port
    private int port;
    //The Username credential
    private String username;
    //The Password credential
    private String password;

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public boolean isCreateDbIfNotExist() {
        return createDbIfNotExist;
    }

    public void setCreateDbIfNotExist(boolean createDbIfNotExist) {
        this.createDbIfNotExist = createDbIfNotExist;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获得couchdb的配置属性
     *
     * @return
     */
    public static CouchdbConfig getCouchdbConfig() {
        if (couchdbConfig != null)
            return couchdbConfig;
        else
            return null;
    }

    /**
     * blueprint init method
     */
    public void init() {
        couchdbConfig.setCreateDbIfNotExist(createDbIfNotExist);
        couchdbConfig.setHost(host);
        couchdbConfig.setDbName(dbName);
        couchdbConfig.setPort(port);
        couchdbConfig.setProtocol(protocol);
        couchdbConfig.setUsername(username);
        couchdbConfig.setPassword(password);
    }
}
