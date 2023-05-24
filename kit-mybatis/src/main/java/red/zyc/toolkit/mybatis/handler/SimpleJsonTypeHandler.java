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

package red.zyc.toolkit.mybatis.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import red.zyc.toolkit.json.JsonOperator;

/**
 * 通过简单配置的{@link ObjectMapper}序列化反序列对象。
 *
 * @param <T> mapper方法返回实体的类型
 * @author zyc
 */
public class SimpleJsonTypeHandler<T> extends AbstractJsonTypeHandler<T, ObjectMapper> {

    public SimpleJsonTypeHandler(Class<T> clazz) {
        super(JsonOperator.JACKSON_OPERATOR.with(ObjectMapper::copy), clazz);
    }
}
