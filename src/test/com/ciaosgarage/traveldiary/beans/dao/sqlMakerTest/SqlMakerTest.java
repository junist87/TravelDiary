package com.ciaosgarage.traveldiary.beans.dao.sqlMakerTest;


import com.ciaosgarage.traveldiary.beans.dao.sqlMaker.SqlMaker;
import com.ciaosgarage.traveldiary.context.BeansContext;
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
@ContextConfiguration(classes = BeansContext.class)
public class SqlMakerTest {
    @Autowired
    private SqlMaker sqlMaker;

    @Before
    public void setUp() {
        assertNotNull(sqlMaker);
    }

    @Test
    public void update() {
        String sql;
        sql = sqlMaker.update(Account.class);
        System.out.println(sql);
        assertThat(sql, is("UPDATE tblAccount SET email = :email, nickname = :nickname, password = :password, authorization = :authorization WHERE pk = :pk"));
    }

    @Test
    public void insert() {
        String sql;
        sql = sqlMaker.insert(Account.class);
        System.out.println(sql);
        assertThat(sql, is("INSERT INTO tblAccount (pk, email, nickname, password, authorization) VALUES (:pk, :email, :nickname, :password, :authorization)"));
    }

    @Test
    public void delete() {
        String sql;
        sql = sqlMaker.delete(Account.class);
        System.out.println(sql);
        assertThat(sql, is("DELETE FROM tblAccount WHERE pk = :pk"));
    }

    @Test
    public void select() {
        String sql;
        sql = sqlMaker.select(Account.class);
        System.out.println(sql);
        assertThat(sql, is("SELECT * FROM tblAccount"));
    }
}
