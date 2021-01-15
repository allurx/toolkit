/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package red.zyc.toolkit.json;

import org.junit.Test;
import red.zyc.toolkit.core.reflect.TypeToken;
import red.zyc.toolkit.json.model.Person;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static red.zyc.toolkit.json.Json.GSON_OPERATOR;
import static red.zyc.toolkit.json.Json.JACKSON_OPERATOR;

/**
 * @author zyc
 */
public class JsonTest {

    private static final List<Person> PERSONS = Stream.of(new Person("张三", 18, "12345678900", LocalDateTime.of(2021, 1, 15, 12, 0, 0)), new Person("李四", 20, "12345678901", LocalDateTime.of(2021, 1, 15, 12, 0, 0))).collect(Collectors.toList());

    private static final TypeToken<List<Person>> TYPE_TOKEN = new TypeToken<List<Person>>() {
    };

    @Test
    public void testJacksonConversion() {
        String json = JACKSON_OPERATOR.toJsonString(PERSONS);
        System.out.println(json);
        assertEquals(PERSONS, JACKSON_OPERATOR.fromJsonString(json, TYPE_TOKEN));
        System.out.println(LocalDateTime.now());
    }

    @Test
    public void testJacksonCopy() {
        List<Person> copy = JACKSON_OPERATOR.copyProperties(PERSONS, TYPE_TOKEN);
        System.out.println(JACKSON_OPERATOR.toJsonString(copy));
        assertEquals(PERSONS, copy);
    }

    @Test
    public void testJacksonCompare() {
        assertTrue(JACKSON_OPERATOR.compare(GSON_OPERATOR.toJsonString(PERSONS), JACKSON_OPERATOR.toJsonString(PERSONS)));
    }

    @Test
    public void testGsonConversion() {
        String json = GSON_OPERATOR.toJsonString(PERSONS);
        System.out.println(json);
        assertEquals(PERSONS, GSON_OPERATOR.fromJsonString(json, TYPE_TOKEN));
    }

    @Test
    public void testGsonCopy() {
        List<Person> copy = GSON_OPERATOR.copyProperties(PERSONS, TYPE_TOKEN);
        System.out.println(GSON_OPERATOR.toJsonString(copy));
        assertEquals(PERSONS, copy);
    }

    @Test
    public void testGsonCompare() {
        assertTrue(GSON_OPERATOR.compare(GSON_OPERATOR.toJsonString(PERSONS), JACKSON_OPERATOR.toJsonString(PERSONS)));
    }

}
