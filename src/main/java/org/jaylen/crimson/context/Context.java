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
import org.jaylen.crimson.annotations.Config;
import org.jaylen.crimson.annotations.Inject;
import org.jaylen.crimson.annotations.Param;
import org.jaylen.crimson.faults.params.ParamSetException;
import org.jaylen.crimson.faults.params.ParamsLoadException;
import org.jaylen.crimson.faults.beans.ConstructionException;
import org.jaylen.crimson.faults.beans.MissingConstructorException;
import org.jaylen.crimson.faults.ctx.CyclicDependencyException;
import org.jaylen.crimson.faults.ctx.InnerClassIsNotSupportedException;
import org.jaylen.crimson.faults.ctx.MissingComponentException;
import org.jaylen.crimson.faults.ctx.NotAnnotatedException;
import org.jaylen.crimson.faults.params.MissingParamException;
import org.jaylen.crimson.params.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class Context {

    public static final String CONTEXT_CONFIG = "crimson.configuration";
    public static final String CONTEXT_BEANS = "crimson.beans";

    public static synchronized Context create() {
        final Context context = new Context();
        context.configure();
        context.scan();
        context.resolve();
        return context;
    }

    public <T> T get(final Class<T> role) {
        return get(role, "default");
    }

    public <T> T get(final Class<T> role, final String id) {
        return role.cast(find(Unit.key(role, id)));
    }

    private void configure() {
        final ClassLoader loader = Context.class.getClassLoader();
        final URL url = loader.getResource(CONTEXT_CONFIG);
        if (url == null) {
            return;
        }
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            reader.lines().forEach(typename -> {
                try {
                    final Class<?> klass = Class.forName(typename);
                    final Config config = klass.getAnnotation(Config.class);
                    final URL u = loader.getResource(config.params());
                    if (u == null) {} else {
                        try (final BufferedReader r = new BufferedReader(new InputStreamReader(u.openStream()))) {
                            final Properties p = new Properties();
                            p.load(r);
                            properties.putAll(p);
                        } catch (final IOException fault) {
                            throw new ParamsLoadException(config.params(), klass, fault);
                        }
                    }
                } catch (final ClassNotFoundException ignored) {}
            });
        } catch (final IOException fault) {
            throw new RuntimeException("unexpected fault while reading resource " + CONTEXT_CONFIG, fault);
        }
    }

    private void scan() {
        final ClassLoader loader = Context.class.getClassLoader();
        final URL url = loader.getResource(CONTEXT_BEANS);
        if (url == null) {
            return;
        }
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            reader.lines().forEach(typename -> {
                try {
                    final Class<?> klass = Class.forName(typename);
                    if (inner(klass)) {
                        throw new InnerClassIsNotSupportedException(klass);
                    } else {
                        final Component component = klass.getAnnotation(Component.class);
                        if (component == null) {
                            throw new NotAnnotatedException(klass, Component.class);
                        } else {
                            final Unit unit = new Unit(component, klass);
                            units.put(unit.key(), unit);
                        }
                    }
                } catch (final ClassNotFoundException ignored) {}
            });
        } catch (final IOException fault) {
            throw new RuntimeException("unexpected fault while reading resource " + CONTEXT_BEANS, fault);
        }
    }

    private void resolve() {
        units.forEach((k, u) -> Arrays.stream(fields(u.impl())).forEach(f -> {
            if (f.isAnnotationPresent(Inject.class)) {
                u.dependencies().add(f);
            } else if (f.isAnnotationPresent(Param.class)) {
                u.params().add(f);
            }
        }));
    }

    private boolean inner(final Class<?> klass) {
        return klass.isMemberClass() && !Modifier.isStatic(klass.getModifiers());
    }

    private Object find(final String key, final String... path) {
        if (Arrays.asList(path).contains(key)) {
            throw new CyclicDependencyException(Stream.of(new String[]{key}, path).flatMap(Stream::of));
        }
        if (beans.containsKey(key)) {
            return beans.get(key);
        } else {
            final Object bean = make(key, Stream.of(new String[]{key}, path).flatMap(Stream::of).toArray(String[]::new));
            beans.put(key, bean);
            return bean;
        }
    }

    private Object make(final String key, final String... path) {
        if (units.containsKey(key)) {
            return create(units.get(key), path);
        } else {
            throw new MissingComponentException(key);
        }
    }

    private <T> Field[] fields(final Class<T> type, final Field... fields) {
        final Field[] items = Stream.of(type.getDeclaredFields(), fields).flatMap(Stream::of).toArray(Field[]::new);
        final Class<?> sup = type.getSuperclass();
        if (sup == null || Object.class.equals(sup)) {
            return items;
        } else {
            return fields(sup, items);
        }
    }

    private Object create(final Unit unit, final String... path) {
        final Object bean;
        try {
            final Constructor<?> constructor = unit.impl().getConstructor();
            bean = constructor.newInstance();
        } catch (final NoSuchMethodException fault) {
            throw new MissingConstructorException(unit.impl(), fault);
        } catch (final InstantiationException | IllegalAccessException | InvocationTargetException fault) {
            throw new ConstructionException(unit.impl(), fault);
        }
        try {
            for (final Field field : unit.dependencies()) {
                final Inject x = field.getAnnotation(Inject.class);
                final Class<?> role = void.class.equals(x.role()) ? field.getType() : x.role();
                final String key = Unit.key(role, x.id());
                field.setAccessible(true);
                field.set(bean, find(key, path));
            }
        } catch (final IllegalAccessException fault) {
            throw new ParamSetException(unit.impl(), fault);
        }
        try {
            for (final Field field : unit.params()) {
                final Param x = field.getAnnotation(Param.class);
                final Parser parser = x.parser().getConstructor().newInstance();
                field.setAccessible(true);
                field.set(bean, parser.parse(field.getType(), param(x.ref())));
            }
        } catch (final NoSuchMethodException | InvocationTargetException |InstantiationException fault) {
            throw new RuntimeException("unable to create parameter parser", fault);
        } catch (final IllegalAccessException fault) {
            throw new ParamSetException(unit.impl(), fault);
        }
        return bean;
    }

    private String param(final String name) {
        final String x = properties.getProperty(name);
        if (x == null) {
            throw new MissingParamException(name);
        } else {
            return x;
        }
    }

    private Context() {
        units = new ConcurrentHashMap<>();
        beans = new ConcurrentHashMap<>();
        properties = new Properties();
    }

    private final Map<String, Unit> units;
    private final Map<String, Object> beans;
    private final Properties properties;

}
