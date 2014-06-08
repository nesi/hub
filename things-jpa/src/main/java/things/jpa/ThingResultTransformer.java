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

package things.jpa;

import org.hibernate.transform.ResultTransformer;

import java.util.List;

/**
 * Created by markus on 21/05/14.
 */
public class ThingResultTransformer implements ResultTransformer {

    @Override
    public List transformList(List collection) {
        return null;
    }

    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {
        return null;
    }
}
