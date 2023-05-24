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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import red.zyc.toolkit.common.constant.TimeFormatConstant;
import red.zyc.toolkit.core.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
     * 返回当前执行json操作的主体对象，例如{@link ObjectMapper}或者{@link Gson}
     *
     * @return json操作的主体对象
     */
    J subject();

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

    /**
     * 比较传入的所有json字符串所表示的json对象是否一致，对于本身就相同的字符串来说，可以直接
     * 通过{@link String#equals}方法直接进行比较，但是有些情况下json字符串可能包含一些
     * 转义字符，此时就可以通过该方法来比较json字符串是否代表相同的json对象。
     *
     * @param jsons 待比较的json字符串数组，数组必须至少包含一个元素。
     * @return 所有json字符串所表示的json对象是否一致
     */
    boolean compare(String... jsons);

    /**
     * 默认配置的Jackson操作器，支持序列化、反序列化jdk8的时间类型以及打印美化后的json字符串。
     *
     * @see TimeFormatConstant#DATETIME_FORMAT
     * @see TimeFormatConstant#DATE_FORMAT
     * @see TimeFormatConstant#TIME_FORMAT
     */
    JacksonOperator JACKSON_OPERATOR = new JacksonOperator(new ObjectMapper()).configure(objectMapper -> {

        JavaTimeModule javaTimeModule = new JavaTimeModule();

        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(TimeFormatConstant.DATETIME_FORMAT)));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(TimeFormatConstant.DATE_FORMAT)));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(TimeFormatConstant.TIME_FORMAT)));

        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(TimeFormatConstant.DATETIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(TimeFormatConstant.DATE_FORMAT)));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(TimeFormatConstant.TIME_FORMAT)));
        objectMapper.registerModule(javaTimeModule);

        objectMapper.enable(SerializationFeature.INDENT_OUTPUT).disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    });

    /**
     * 默认配置的Gson操作器，支持序列化、反序列化jdk8的时间类型以及打印美化后的json字符串。
     *
     * @see TimeFormatConstant#DATETIME_FORMAT
     * @see TimeFormatConstant#DATE_FORMAT
     * @see TimeFormatConstant#TIME_FORMAT
     */
    GsonOperator GSON_OPERATOR = new GsonOperator(new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) -> new JsonPrimitive(src.format(DateTimeFormatter.ofPattern(TimeFormatConstant.DATETIME_FORMAT))))
            .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (src, typeOfSrc, context) -> new JsonPrimitive(src.format(DateTimeFormatter.ofPattern(TimeFormatConstant.DATE_FORMAT))))
            .registerTypeAdapter(LocalTime.class, (JsonSerializer<LocalTime>) (src, typeOfSrc, context) -> new JsonPrimitive(src.format(DateTimeFormatter.ofPattern(TimeFormatConstant.TIME_FORMAT))))
            .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) -> LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ofPattern(TimeFormatConstant.DATETIME_FORMAT)))
            .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, type, jsonDeserializationContext) -> LocalDate.parse(json.getAsString(), DateTimeFormatter.ofPattern(TimeFormatConstant.DATE_FORMAT)))
            .registerTypeAdapter(LocalTime.class, (JsonDeserializer<LocalTime>) (json, type, jsonDeserializationContext) -> LocalTime.parse(json.getAsString(), DateTimeFormatter.ofPattern(TimeFormatConstant.TIME_FORMAT)))
            .serializeNulls()
            .setPrettyPrinting()
            .create());

}
