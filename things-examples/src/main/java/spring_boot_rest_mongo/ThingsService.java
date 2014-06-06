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

package spring_boot_rest_mongo;

import org.springframework.boot.SpringApplication;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner
 * Date: 11/05/14
 * Time: 8:23 PM
 */
public class ThingsService {

    public static void main(String[] args) {

        SpringApplication.run(ExampleMongoConfig.class);

    }
}
