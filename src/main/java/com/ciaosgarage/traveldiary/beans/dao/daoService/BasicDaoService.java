package com.ciaosgarage.traveldiary.beans.dao.daoService;

import com.ciaosgarage.traveldiary.beans.dao.attachStatementHandler.AttachStatementHandler;
import com.ciaosgarage.traveldiary.beans.dao.dao.Dao;
import com.ciaosgarage.traveldiary.beans.dao.seqTableHandler.SeqTableHandler;
import com.ciaosgarage.traveldiary.beans.dao.voHandler.VoHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BasicDaoService implements DaoService {

    @Autowired
    VoHandler voHandler;

    @Autowired
    SeqTableHandler seqTableHandler;

    @Autowired
    Dao dao;

    @Autowired
    AttachStatementHandler attachStatementHandler;

    @Override
    public void pushVo(Object vo) {
        voHandler.isVo(vo);
        if (voHandler.getPrimaryKey(vo) == null) {
            String pk = seqTableHandler.getPk(vo.getClass());
            voHandler.setPrimaryKey(vo, pk);
            dao.add(vo);
        } else {
            dao.update(vo);
        }
    }


    @Override
    public Object pullVo(Class voInfo) {
        voHandler.isVo(voInfo);
        return dao.get(voInfo, attachStatementHandler.getAttachStatement());
    }

    @Override
    public List pullVoList(Class voInfo) {
        voHandler.isVo(voInfo);
        return dao.getList(voInfo, attachStatementHandler.getAttachStatement());
    }

}
