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

package things.thing;

import rx.Observable;
import things.exceptions.ActionException;

import java.util.Map;

/**
 * If you need more control over how Actions are executed (e.g. everthing should be executed in a Fork/Join threadpool, or different actions need to be executed differently)
 * you can implement this interface.
 *
 * Default ActionManager is {@link things.thing.DefaultActionManager}.
 */
public interface ActionManager {
    Observable<? extends Thing<?>> execute(String actionName, Observable<? extends Thing<?>> things, Map<String, String> parameters) throws ActionException;
}
