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

package org.jaylen.crimson.params;

import org.jaylen.crimson.faults.params.MissingParserException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class SimpleParser implements Parser {

    private final static Map<Class<?>, Function<String, Object>> parsers = new HashMap<>();

    public Object parse(final Class<?> type, final String text) throws MissingParserException {
        final Optional<Class<?>> p = parsers.keySet().stream().filter(type::isAssignableFrom).findFirst();
        if (p.isPresent()) {
            return parsers.get(p.get()).apply(text);
        } else {
            throw new MissingParserException(type);
        }
    }

    public SimpleParser() {
        parsers.put(String.class, t -> t);
        parsers.put(boolean.class, Boolean::parseBoolean);
        parsers.put(byte.class, SimpleParser::parseByte);
        parsers.put(short.class, SimpleParser::parseShort);
        parsers.put(int.class, SimpleParser::parseInt);
        parsers.put(long.class, SimpleParser::parseLong);
        parsers.put(float.class, Float::parseFloat);
        parsers.put(double.class, Double::parseDouble);
        parsers.put(char.class, t -> t.charAt(0));
        parsers.put(Boolean.class, Boolean::parseBoolean);
        parsers.put(Byte.class, SimpleParser::parseByte);
        parsers.put(Short.class, SimpleParser::parseShort);
        parsers.put(Integer.class, SimpleParser::parseInt);
        parsers.put(Long.class, SimpleParser::parseLong);
        parsers.put(Float.class, Float::parseFloat);
        parsers.put(Double.class, Double::parseDouble);
        parsers.put(Character.class, t -> t.charAt(0));
    }

    private static Object parseByte(final String text) {
        if (text.startsWith("0x")) {
            return Byte.parseByte(text.substring(2), 16);
        } else if (text.startsWith("0b")) {
            return Byte.parseByte(text.substring(2), 2);
        } else {
            return Byte.parseByte(text);
        }
    }

    private static Object parseShort(final String text) {
        if (text.startsWith("0x")) {
            return Short.parseShort(text.substring(2), 16);
        } else if (text.startsWith("0b")) {
            return Short.parseShort(text.substring(2), 2);
        } else {
            return Short.parseShort(text);
        }
    }

    private static Object parseInt(final String text) {
        if (text.startsWith("0x")) {
            return Integer.parseInt(text.substring(2), 16);
        } else if (text.startsWith("0b")) {
            return Integer.parseInt(text.substring(2), 2);
        } else {
            return Integer.parseInt(text);
        }
    }

    private static Object parseLong(final String text) {
        if (text.startsWith("0x")) {
            return Long.parseLong(text.substring(2), 16);
        } else if (text.startsWith("0b")) {
            return Long.parseLong(text.substring(2), 2);
        } else {
            return Long.parseLong(text);
        }
    }

}
