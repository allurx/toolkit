package red.zyc.toolkit.mybatis.handler;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import red.zyc.toolkit.json.JacksonOperator;
import red.zyc.toolkit.json.Json;

/**
 * 通过配置的{@link ObjectMapper}将对象的类型信息保存在json字符串中以便反序列化时能够准确的还原原始对象。
 * 支持泛型对象的序列化和反序列化。
 *
 * @author zyc
 * @see GenericJsonTypeHandler#JACKSON_OPERATOR
 */
public class GenericJsonTypeHandler<T> extends AbstractJsonTypeHandler<T, ObjectMapper> {

    /**
     * json操作器
     */
    private static final JacksonOperator JACKSON_OPERATOR = Json.JACKSON_OPERATOR.with(ObjectMapper::copy)
            .configure(objectMapper -> objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY));

    public GenericJsonTypeHandler(Class<T> clazz) {
        super(JACKSON_OPERATOR, clazz);
    }
}
