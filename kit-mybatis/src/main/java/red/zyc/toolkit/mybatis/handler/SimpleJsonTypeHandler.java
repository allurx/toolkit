package red.zyc.toolkit.mybatis.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import red.zyc.toolkit.json.Json;

/**
 * 通过简单配置的{@link ObjectMapper}将对象序列化反序列。
 *
 * @param <T> mapper方法返回实体的类型
 * @author zyc
 */
public class SimpleJsonTypeHandler<T> extends AbstractJsonTypeHandler<T, ObjectMapper> {

    public SimpleJsonTypeHandler(Class<T> clazz) {
        super(Json.JACKSON_OPERATOR.with(ObjectMapper::copy), clazz);
    }
}
