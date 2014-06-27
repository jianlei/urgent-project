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

package com.daren.core.impl.persistence;

import com.daren.core.api.persistence.IBizEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Base entity for domain entities that can be identified by key.
 *
 * @author Aliaksei Papou
 */
@MappedSuperclass

public abstract class BizEntity extends PersistentEntity implements IBizEntity {
    @Column(name = "key_", unique = true)
    private String key;

    @Override
    public String getBizKey() {
        return key;
    }

    @Override
    public void setBizKey(String key) {
        this.key = key;
    }

    @Override
    public Serializable getIdentifier() {
        return key;
    }
}
