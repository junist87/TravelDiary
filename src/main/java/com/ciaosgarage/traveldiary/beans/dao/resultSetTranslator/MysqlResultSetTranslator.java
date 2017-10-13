package com.ciaosgarage.traveldiary.beans.dao.resultSetTranslator;

import com.ciaosgarage.traveldiary.beans.dao.parameters.DatabaseType;
import org.springframework.stereotype.Repository;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class MysqlResultSetTranslator implements ResultSetTranslator {

    @Override
    public DatabaseType getDatabaseType() {
        return DatabaseType.MYSQL;
    }

    @Override
    public Object translator(ResultSet resultSet, Class voInfo) throws SQLException {

        // valueObject 객체 생성
        Object newVo = getNewVo(voInfo);

        // 추출된 resultSet 에 들어있는 컬럼의 갯수구하기 -> for 문 돌리기 위해서
        int columns = resultSet.getMetaData().getColumnCount();

        // 레코드셋의 인덱스는 1~ 컬럼카운트 까지!
        for (int col = 1; col <= columns; col++) {
            String dataType = resultSet.getMetaData().getColumnTypeName(col).toLowerCase();
            String rcdName = resultSet.getMetaData().getColumnName(col);
            newVo = insertData(newVo, dataType, rcdName, resultSet);
        }
        // 예외전환 -> SQL문을 만드는 과정에서의 오류를 SQLException 으로 변환하여 상위로 넘긴다
        return newVo;
    }

    private Object getNewVo(Class voInfo) throws SQLException {
        try {
            Object newVo = voInfo.newInstance();
            return newVo;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new SQLException("!- Can't Instanced Vo -!");
        }
    }

    private Object insertData(Object newVo, String dataType, String rcdName, ResultSet resultSet) throws SQLException {
        // vo의 필드 타입과 데이터베이스의 데이터 타입 매칭 시작
        try {
            Field targetProperty = newVo.getClass().getDeclaredField(rcdName);
            targetProperty.setAccessible(true);

            switch (dataType) {
                case "varchar":
                    targetProperty.set(newVo, resultSet.getString(rcdName));
                    break;
                case "char":
                    targetProperty.set(newVo, resultSet.getString(rcdName));
                    break;
                case "int":
                    targetProperty.set(newVo, resultSet.getInt(rcdName));
                    break;
                case "tinyint":
                    targetProperty.set(newVo, resultSet.getInt(rcdName));
                    break;
                case "double":
                    targetProperty.set(newVo, resultSet.getDouble(rcdName));
                    break;
                case "date":
                    targetProperty.set(newVo, resultSet.getDate(rcdName));
                    break;
                case "timestamp":
                    targetProperty.set(newVo, resultSet.getDate(rcdName));
                    break;
                case "geometry":
                    // 구현하기
                    break;
                default:
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new SQLException("!- Not Matching DataType -!");
        }

        return newVo;
    }

}
