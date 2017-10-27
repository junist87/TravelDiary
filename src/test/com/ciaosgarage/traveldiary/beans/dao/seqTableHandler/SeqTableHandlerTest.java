package com.ciaosgarage.traveldiary.beans.dao.seqTableHandler;

import com.ciaosgarage.traveldiary.beans.dao.dao.Dao;
import com.ciaosgarage.traveldiary.context.BeansContext;
import com.ciaosgarage.traveldiary.domain.account.Account;
import com.ciaosgarage.traveldiary.domain.mapBoard.MapBoard;
import com.ciaosgarage.traveldiary.domain.mapPhoto.MapPhoto;
import com.ciaosgarage.traveldiary.domain.seqTable.SeqTable;
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
public class SeqTableHandlerTest {

    @Autowired
    SeqTableHandler handler;

    @Autowired
    Dao dao;

    @Before
    public void setUp() {
        dao.deleteAll(Account.class);
        dao.deleteAll(MapBoard.class);
        dao.deleteAll(MapPhoto.class);
        dao.deleteAll(SeqTable.class);

        assertNotNull(handler);
        assertNotNull(dao);

    }

    @Test
    public void getPk() {


        long accountLastPk = 0;
        long mapPhotoLastPk = 0;
        long mapBoardLastPk = 0;

        for (int i = 0; i < 100; i++) {
            int swt = (int) (Math.random() * 3);
            switch (swt) {
                case 0:
                    handler.getPk(Account.class);
                    accountLastPk += 1;
                    break;
                case 1:
                    handler.getPk(MapPhoto.class);
                    mapPhotoLastPk += 1;
                    break;
                case 2:
                    handler.getPk(MapBoard.class);
                    mapBoardLastPk += 1;
                    break;
                default:
            }
        }

        assertThat(handler.getPk(Account.class), is(Long.toHexString(accountLastPk + 1)));
        assertThat(handler.getPk(MapPhoto.class), is(Long.toHexString(mapPhotoLastPk + 1)));
        assertThat(handler.getPk(MapBoard.class), is(Long.toHexString(mapBoardLastPk + 1)));
    }

}
