/*
 * Copyright (C) 2012 "Lexaden.com"
 *     contact@lexaden.com [http://www.lexaden.com]
 *
 *     This file is part of Lexaden Administration.
 *
 *     Lexaden Administration is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as
 *     published by the Free Software Foundation, either version 3 of the
 *     License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.daren.core.api.persistence;

import javax.persistence.MappedSuperclass;

/**
 * @类描述：工作流业务数据抽象基类
 * @创建人：sunlf
 * @创建时间：2014-9-9 下午1:01:59
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
@MappedSuperclass

public abstract class WorkflowEntity extends PersistentEntity {
     private String processInstanceId;//流程实例id
     private  WorkflowStaus status;//流程状态，“处理中”，“结束”

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String instanceId) {
        this.processInstanceId = instanceId;
    }

    public WorkflowStaus getStatus() {
        return status;
    }

    public void setStatus(WorkflowStaus status) {
        this.status = status;
    }
}
