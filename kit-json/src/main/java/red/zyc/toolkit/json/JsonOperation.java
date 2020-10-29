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

import red.zyc.toolkit.core.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * 定义了一些基本的json操作
 *
 * @author zyc
 */
public interface JsonOperation {

    /**
     * 将java对象转换为json字符串
     *
     * @param target java对象
     * @return json字符串
     */
    String toJsonString(Object target);

    /**
     * 将json字符串转换成指定{@link Class}的java对象
     *
     * @param json  json字符串
     * @param clazz 需要转换成java对象的{@link Class}
     * @param <T>   需要转换成java对象的类型
     * @return java对象
     */
    <T> T fromJsonString(String json, Class<T> clazz);

    /**
     * 将json字符串转换成指定{@link Type}的java对象
     *
     * @param json json字符串
     * @param type 需要转换成java对象的{@link Type}
     * @param <T>  需要转换成java对象的类型
     * @return java对象
     */
    <T> T fromJsonString(String json, Type type);

    /**
     * 将json字符串转换成指定{@link TypeToken#getType()}的java对象
     *
     * @param json      json字符串
     * @param typeToken 需要转换成java对象的{@link TypeToken}
     * @param <T>       需要转换成java对象的类型
     * @return java对象
     */
    <T> T fromJsonString(String json, TypeToken<T> typeToken);

}
