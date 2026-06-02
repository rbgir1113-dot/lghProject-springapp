package com.app.springapp.typehandler;

import com.app.springapp.domain.enums.SocialProvider;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(SocialProvider.class)
public class SocialProviderTypeHandler extends BaseTypeHandler<SocialProvider> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, SocialProvider parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getValue());
    }

    @Override
    public SocialProvider getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return SocialProvider.fromValue(rs.getString(columnName));
    }

    @Override
    public SocialProvider getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return SocialProvider.fromValue(rs.getString(columnIndex));
    }

    @Override
    public SocialProvider getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return SocialProvider.fromValue(cs.getString(columnIndex));
    }
}
