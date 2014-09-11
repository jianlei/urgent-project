package com.daren.workflow.engine;

/**
 * @类描述：为了支持blueprint的配置，客户化配置类
 * @创建人： sunlingfeng
 * @创建时间：2014/9/11
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;

import javax.sql.DataSource;

public class ConfigurationFactory {

    DataSource dataSource;
    String databaseSchemaUpdate;
    boolean jobExecutorActivate = true;
    String history;
    String activityFontName;
    String labelFontName;

    public StandaloneProcessEngineConfiguration getConfiguration() {
        StandaloneProcessEngineConfiguration conf =
                new StandaloneProcessEngineConfiguration();
        conf.setDataSource(dataSource);
        conf.setDatabaseSchemaUpdate(databaseSchemaUpdate);
        conf.setJobExecutorActivate(jobExecutorActivate);
        conf.setHistory(history);
        conf.setActivityFontName(activityFontName);
        conf.setLabelFontName(labelFontName);
        return conf;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setDatabaseSchemaUpdate(String databaseSchemaUpdate) {
        this.databaseSchemaUpdate = databaseSchemaUpdate;
    }

    public void setJobExecutorActivate(boolean jobExecutorActivate) {
        this.jobExecutorActivate = jobExecutorActivate;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getActivityFontName() {
        return activityFontName;
    }

    public void setActivityFontName(String activityFontName) {
        this.activityFontName = activityFontName;
    }

    public String getLabelFontName() {
        return labelFontName;
    }

    public void setLabelFontName(String labelFontName) {
        this.labelFontName = labelFontName;
    }
}