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

package things.dropwizard;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by markus on 22/05/14.
 */
public class HelloWorldConfiguration extends Configuration {
    @NotEmpty
    private String defaultName = "Stranger";
    @NotEmpty
    private String template;

    @JsonProperty
    public String getDefaultName() {
        return defaultName;
    }

    @JsonProperty
    public String getTemplate() {
        return template;
    }

    @JsonProperty
    public void setDefaultName(String name) {
        this.defaultName = name;
    }

    @JsonProperty
    public void setTemplate(String template) {
        this.template = template;
    }
}
