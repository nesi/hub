/*
 * Things
 *
 * Copyright (c) 2014, Markus Binsteiner. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */

package hub.types.dynamic;

import com.google.common.collect.Maps;
import things.model.types.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @author: Markus Binsteiner
 */
@Value(typeName = "auditrecord")
public class AuditRecord implements Serializable {

    private final Map<String, BigDecimal> coreHours = Maps.newConcurrentMap();

    private final String username;

    public AuditRecord(String username) {
        this.username = username;
    }

    public void addJob(String name, BigDecimal corehours) {
        this.coreHours.put(name, corehours);
    }

    public Map<String, BigDecimal> getCoreHours() {
        return coreHours;
    }

    public String getUsername() {
        return username;
    }
}
