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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * json操作器
 *
 * @param <J> json操作的主体类型
 * @author zyc
 * @see Gson
 * @see ObjectMapper
 */
public interface JsonOperator<J> extends JsonOperation {

    /**
     * 使用指定的json操作实体构造一个新的{@link JsonOperator}
     *
     * @param supplier 操作json的主体
     * @return 实现类必须返回一个新的 {@link JsonOperator}对象，避免多个线程使用同一个{@link JsonOperator}
     * 产生预料之外的结果。
     */
    JsonOperator<J> with(Supplier<J> supplier);

    /**
     * 通过当前的json操作实体构造一个新的{@link JsonOperator}
     *
     * @param unaryOperator 操作json的主体
     * @return 实现类必须返回一个新的 {@link JsonOperator}对象，避免多个线程使用同一个{@link JsonOperator}
     * 产生预料之外的结果。
     */
    JsonOperator<J> with(UnaryOperator<J> unaryOperator);

    /**
     * 配置执行json操作的主体对象，注意该配置会对全局json操作生效，如果只是
     * 想对本次json操作进行额外的配置，可以通过先调用{@link #with(Supplier)}方法
     * 传入一个新的{@link J 执行json操作的主体对象}然后再调用这个方法即可。
     *
     * @param consumer 配置操作
     * @return 返回自身以便链式调用
     */
    JsonOperator<J> configure(Consumer<J> consumer);

    /**
     * 返回当前执行json操作的主体对象，例如{@link ObjectMapper}或者{@link Gson}
     *
     * @return json操作的主体对象
     */
    J subject();

    /**
     * 将目标对象中的属性拷贝到另一个{@link Class}类型的新对象中
     *
     * @param target 目标对象
     * @param clazz  新对象的{@link Class}
     * @param <T>    新对象的类型
     * @return 新对象
     */
    <T> T copyProperties(Object target, Class<T> clazz);

    /**
     * 将目标对象中的属性拷贝到另一个{@link Type}类型的新对象中
     *
     * @param target 目标对象
     * @param type   新对象的{@link Type}
     * @param <T>    新对象的类型
     * @return 新对象
     */
    <T> T copyProperties(Object target, Type type);

    /**
     * 将目标对象中的属性拷贝到另一个{@link TypeToken#getType()}类型的新对象中
     *
     * @param target    源对象
     * @param typeToken 新对象的{@link TypeToken}
     * @param <T>       新对象的类型
     * @return 新对象
     */
    <T> T copyProperties(Object target, TypeToken<T> typeToken);

}
