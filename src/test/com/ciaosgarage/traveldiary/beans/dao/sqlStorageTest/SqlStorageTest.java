package com.ciaosgarage.traveldiary.beans.dao.sqlStorageTest;

import com.ciaosgarage.traveldiary.beans.dao.enums.SqlType;
import com.ciaosgarage.traveldiary.beans.dao.parameters.SqlIndex;
import com.ciaosgarage.traveldiary.beans.dao.sqlStorage.SqlStorage;
import com.ciaosgarage.traveldiary.context.DaoContext;
import com.ciaosgarage.traveldiary.domain.account.Account;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoContext.class)
public class SqlStorageTest {

    @Autowired
    SqlStorage sqlStorage;


    @Before
    public void setup() {
        assertNotNull(sqlStorage);
    }

    @Test
    public void setSql() {
        SqlIndex sqlIndex = new SqlIndex(Account.class, SqlType.SELECT);
        String sql = "SELECT * FROM ACCOUNT";
        sqlStorage.setSql(sqlIndex, sql);
        assertThat(sql, is(sqlStorage.getSql(sqlIndex)));
    }


}