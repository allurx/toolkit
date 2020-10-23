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

import java.lang.reflect.Type;
import java.util.function.Consumer;

/**
 * json操作器基类
 *
 * @param <O> json操作器的类型
 * @param <J> json操作的主体类型
 * @author zyc
 */
public abstract class AbstractJsonOperator<O extends JsonOperator<J>, J> implements JsonOperator<J> {

    protected final O jsonOperator;

    protected final J subject;

    @SuppressWarnings("unchecked")
    public AbstractJsonOperator(J subject) {
        if (subject == null) {
            throw new IllegalArgumentException("json操作实例不能为空");
        }
        this.jsonOperator = (O) this;
        this.subject = subject;
    }

    @Override
    public O configure(Consumer<J> consumer) {
        consumer.accept(subject);
        return jsonOperator;
    }

    @Override
    public J subject() {
        return subject;
    }

    @Override
    public <T> T fromJsonString(String json, TypeToken<T> typeToken) {
        return fromJsonString(json, typeToken.getType());
    }

    @Override
    public <T> T copyProperties(Object target, Class<T> clazz) {
        return fromJsonString(toJsonString(target), clazz);
    }

    @Override
    public <T> T copyProperties(Object target, Type type) {
        return fromJsonString(toJsonString(target), type);
    }

    @Override
    public <T> T copyProperties(Object target, TypeToken<T> typeToken) {
        return fromJsonString(toJsonString(target), typeToken.getType());
    }
}
