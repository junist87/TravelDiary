package com.ciaosgarage.traveldiary.beans.dao.sqlHandlerTest;

import com.ciaosgarage.traveldiary.beans.dao.enums.SqlType;
import com.ciaosgarage.traveldiary.beans.dao.parameters.SqlMapper;
import com.ciaosgarage.traveldiary.beans.dao.parameters.attachStatement.AttachStatement;
import com.ciaosgarage.traveldiary.beans.dao.sqlHandler.SqlHandler;
import com.ciaosgarage.traveldiary.beans.dao.sqlMapperMaker.SqlMapperMaker;
import com.ciaosgarage.traveldiary.context.BeansContext;
import com.ciaosgarage.traveldiary.domain.account.Account;
import com.ciaosgarage.traveldiary.sampler.AccountSampler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BeansContext.class)
public class SqlHandlerTest {

    @Autowired
    SqlHandler sqlHandler;

    @Autowired
    SqlMapperMaker sqlMapperMaker;

    Account account01;
    Account account02;
    Account account03;
    AccountSampler sampler;

    @Before
    public void setUp() {
        assertNotNull(sqlHandler);

        sampler = new AccountSampler();
        account01 = sampler.getRandomAccount();
        account02 = sampler.getRandomAccount();
        account03 = sampler.getRandomAccount();

    }

    @Test
    public void getSql() {
        SqlMapper sqlProduct = sqlHandler.getSqlMapper(account01, SqlType.SELECT);

        String sql = sqlProduct.getSql();
        Map<String, Object> mapper = sqlProduct.getMapper();

        System.out.println(sql);
    }

    private void checkMapper(Map<String, Object> getMap, Object valueObject, AttachStatement... statements) {
        if (!getMap.equals(sqlMapperMaker.makeMapper(valueObject, swapArrays(statements)))) fail();
    }

    private List<AttachStatement> swapArrays(AttachStatement... statements) {
        List<AttachStatement> list = new ArrayList<>();
        for (AttachStatement statement : statements) {
            list.add(statement);
        }
        return list;
    }
}
