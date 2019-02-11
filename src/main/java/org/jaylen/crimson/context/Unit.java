/*
 * Copyright 2019 Yury Khrustalev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jaylen.crimson.context;

import org.jaylen.crimson.annotations.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

public class Unit {

    public Unit(final Component component, final Class<?> impl) {
        this.key = Unit.key(void.class.equals(component.role()) ? impl : component.role(), component.id());
        this.impl = impl;
        this.dependencies = new ArrayList<>();
        this.params = new ArrayList<>();
    }

    public static String key(final Class<?> role, final String id) {
        return role.getCanonicalName() + ":" + id;
    }

    public String key() {
        return key;
    }

    public Class<?> impl() {
        return impl;
    }

    public Collection<Field> dependencies() {
        return dependencies;
    }

    public Collection<Field> params() {
        return params;
    }

    private final String key;
    private final Class<?> impl;
    private final Collection<Field> dependencies;
    private final Collection<Field> params;

}
