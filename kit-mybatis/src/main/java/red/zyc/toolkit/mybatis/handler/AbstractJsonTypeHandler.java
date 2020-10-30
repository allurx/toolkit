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

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import red.zyc.toolkit.json.JsonOperator;

import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * json类型处理器基类，提供了一些基本方法序列化反序列对象。
 *
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
        ps.setString(i, jsonOperator.toJsonString(parameter));
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return jsonOperator.fromJsonString(rs.getString(columnName), type);
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return jsonOperator.fromJsonString(rs.getString(columnIndex), type);
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return jsonOperator.fromJsonString(cs.getString(columnIndex), type);
    }

}
