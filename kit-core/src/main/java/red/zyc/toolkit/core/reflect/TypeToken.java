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

package red.zyc.toolkit.core.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * 用来获取类的泛型参数的帮助类，换句话说就是获取某个{@link ParameterizedType}类型对象运行时其泛型参数的具体类型。
 * 由于java泛型擦除机制，如果我们想获取{@code new ArrayList<String>}这个对象运行时的泛型参数{@code String}，
 * 这几乎是很难做到的。而通过{@link TypeToken}你只需要构造一个它的匿名子类，我们就能获取它运行时的泛型参数：
 * <pre>
 *     TypeToken&lt;List&lt;String&gt;&gt; stringList = new TypeToken&lt;List&lt;String&gt;&gt;(){};
 *     stringList.getType()返回的泛型参数为: java.util.List&lt;java.lang.String&gt;
 * </pre>
 *
 * @param <T> 需要捕获的明确类型
 * @author zyc
 */
public abstract class TypeToken<T> {

    /**
     * {@link T}运行时的类型
     */
    private final Type type;

    protected TypeToken() {
        type = capture();
    }

    private TypeToken(Type type) {
        this.type = type;
    }

    /**
     * 通过已知对象的{@link Type}实例化{@link TypeToken}
     *
     * @param type 对象的{@link Type}
     * @param <T>  对象的运行时类型
     * @return 该对象的 {@link TypeToken}
     */
    public static <T> TypeToken<T> of(Type type) {
        return new TypeToken<T>(type) {
        };
    }

    /**
     * @return 对象运行时的类型
     */
    public final Type getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeToken<?> typeToken = (TypeToken<?>) o;
        return Objects.equals(type, typeToken.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TypeToken.class.getSimpleName() + "[", "]")
                .add("type=" + type)
                .toString();
    }

    /**
     * @return {@link T}运行时被注解的类型
     */
    private Type capture() {
        Class<?> clazz = getClass();
        Type superclass = clazz.getGenericSuperclass();
        if (!(superclass instanceof ParameterizedType)) {
            throw new IllegalArgumentException(String.format("%s必须是参数化类型", superclass));
        }
        return ((ParameterizedType) superclass).getActualTypeArguments()[0];
    }

}
