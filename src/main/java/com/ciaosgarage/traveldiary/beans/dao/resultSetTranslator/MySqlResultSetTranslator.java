package com.ciaosgarage.traveldiary.beans.dao.resultSetTranslator;

import com.ciaosgarage.traveldiary.beans.dao.cryptor.Cryptor;
import com.ciaosgarage.traveldiary.beans.dao.enums.DatabaseType;
import com.ciaosgarage.traveldiary.beans.dao.parameters.ColumnValue;
import com.ciaosgarage.traveldiary.beans.dao.voHandler.VoHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/*
* 어필할 것
* Reflection 을 이용하여 값을 입력한다.
*/
@Repository
public class MySqlResultSetTranslator implements ResultSetTranslator {

    @Autowired
    Cryptor cryptor;

    @Autowired
    VoHandler voHandler;

    @Override
    public DatabaseType getDatabaseType() {
        return DatabaseType.MYSQL;
    }

    @Override
    public Object translate(ResultSet resultSet, Class voInfo) throws SQLException {

        // valueObject 객체 생성
        Object newVo = getNewVo(voInfo);

        // 추출된 resultSet 에 들어있는 컬럼의 갯수구하기 -> for 문 돌리기 위해서
        int columns = resultSet.getMetaData().getColumnCount();

        // 레코드셋의 인덱스는 1~ 컬럼카운트 까지!
        for (int col = 1; col <= columns; col++) {
            // 추출할 값의 정보를 가져온다.
            String dataType = resultSet.getMetaData().getColumnTypeName(col).toLowerCase();
            String columnName = resultSet.getMetaData().getColumnName(col);
            Object value = getValue(resultSet, dataType, columnName);

            // 암호화된 값이라면, 해석한다.
            value = cryptor.decryption(voInfo, new ColumnValue(columnName, value));

            // 추출된 값을 vo 객체에 입력한다.
            newVo = voHandler.setValue(newVo, columnName, value);
        }

        return newVo;
    }

    private Object getNewVo(Class voInfo) throws SQLException {
        try {
            return voInfo.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new SQLException("!- Can't Instanced Vo -!");
        }
    }

    private Object getValue(ResultSet resultSet, String dataType, String columnName) throws SQLException {
        switch (dataType) {
            case "varchar":
                return resultSet.getString(columnName);
            case "char":
                return resultSet.getString(columnName);
            case "int":
                return resultSet.getInt(columnName);
            case "tinyint":
                return resultSet.getInt(columnName);
            case "double":
                return resultSet.getDouble(columnName);
            case "date":
                return resultSet.getDate(columnName);
            case "timestamp":
                return resultSet.getDate(columnName);
            case "geometry":
                // 구현하기
                return null;
            default:
                return null;
        }
    }
}