package red.zyc.toolkit.mybatis.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import red.zyc.toolkit.json.JsonOperator;

import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @param <T> mapper方法返回实体的类型
 * @param <J> json操作的主体类型
 * @author zyc
 */
public abstract class AbstractJsonTypeHandler<T, J> extends BaseTypeHandler<T> {

    /**
     * json操作器
     */
    private final JsonOperator<J> jsonOperator;

    /**
     * 返回实体的类型
     */
    private final Type type;

    public AbstractJsonTypeHandler(JsonOperator<J> jsonOperator, Type type) {
        this.jsonOperator = jsonOperator;
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, toJsonString(parameter));
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return parseJson(rs.getString(columnName));
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return parseJson(rs.getString(columnIndex));
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return parseJson(cs.getString(columnIndex));
    }

    /**
     * 将json字符串反序列化为目标对象
     *
     * @param json json字符串
     * @return 目标对象
     */
    public T parseJson(String json) {
        return jsonOperator.fromJsonString(json, type);
    }

    /**
     * 将目标对象序列化为json字符串
     *
     * @param o 目标对象
     * @return json字符串
     */
    public String toJsonString(T o) {
        return jsonOperator.toJsonString(o);
    }
}
