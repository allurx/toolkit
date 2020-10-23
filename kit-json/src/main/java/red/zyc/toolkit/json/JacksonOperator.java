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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Type;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * 使用Jackson库操作json
 *
 * @author zyc
 */
public class JacksonOperator extends AbstractJsonOperator<JacksonOperator, ObjectMapper> {

    public JacksonOperator(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public JacksonOperator with(Supplier<ObjectMapper> supplier) {
        return new JacksonOperator(supplier.get());
    }

    @Override
    public JacksonOperator with(UnaryOperator<ObjectMapper> unaryOperator) {
        return new JacksonOperator(unaryOperator.apply(subject));
    }

    @Override
    public String toJsonString(Object src) {
        try {
            return subject.writeValueAsString(src);
        } catch (JsonProcessingException e) {
            throw new JsonException(e.getMessage(), e);
        }
    }

    @Override
    public <T> T fromJsonString(String json, Class<T> clazz) {
        try {
            return subject.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new JsonException(e.getMessage(), e);
        }
    }

    @Override
    public <T> T fromJsonString(String json, Type type) {
        try {
            return subject.readValue(json, subject.getTypeFactory().constructType(type));
        } catch (JsonProcessingException e) {
            throw new JsonException(e.getMessage(), e);
        }
    }
}
