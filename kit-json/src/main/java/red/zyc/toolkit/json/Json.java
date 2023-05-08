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
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import red.zyc.toolkit.common.constant.TimeFormatConstant;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author zyc
 */
public final class Json {

    /**
     * 默认配置的Jackson操作器，支持序列化、反序列化jdk8的数据类型以及打印美化后的json字符串。
     *
     * @see TimeFormatConstant#DATETIME_FORMAT
     * @see TimeFormatConstant#DATE_FORMAT
     * @see TimeFormatConstant#TIME_FORMAT
     */
    public static final JacksonOperator JACKSON_OPERATOR = new JacksonOperator(new ObjectMapper()).configure(objectMapper -> {

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
     * 默认配置的Gson操作器，支持序列化、反序列化jdk8的数据类型以及打印美化后的json字符串。
     *
     * @see TimeFormatConstant#DATETIME_FORMAT
     * @see TimeFormatConstant#DATE_FORMAT
     * @see TimeFormatConstant#TIME_FORMAT
     */
    public static final GsonOperator GSON_OPERATOR = new GsonOperator(new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) -> new JsonPrimitive(src.format(DateTimeFormatter.ofPattern(TimeFormatConstant.DATETIME_FORMAT))))
            .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (src, typeOfSrc, context) -> new JsonPrimitive(src.format(DateTimeFormatter.ofPattern(TimeFormatConstant.DATE_FORMAT))))
            .registerTypeAdapter(LocalTime.class, (JsonSerializer<LocalTime>) (src, typeOfSrc, context) -> new JsonPrimitive(src.format(DateTimeFormatter.ofPattern(TimeFormatConstant.TIME_FORMAT))))
            .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) -> LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ofPattern(TimeFormatConstant.DATETIME_FORMAT)))
            .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, type, jsonDeserializationContext) -> LocalDate.parse(json.getAsString(), DateTimeFormatter.ofPattern(TimeFormatConstant.DATE_FORMAT)))
            .registerTypeAdapter(LocalTime.class, (JsonDeserializer<LocalTime>) (json, type, jsonDeserializationContext) -> LocalTime.parse(json.getAsString(), DateTimeFormatter.ofPattern(TimeFormatConstant.TIME_FORMAT)))
            .serializeNulls()
            .setPrettyPrinting()
            .create());

    private Json() {
    }

}
