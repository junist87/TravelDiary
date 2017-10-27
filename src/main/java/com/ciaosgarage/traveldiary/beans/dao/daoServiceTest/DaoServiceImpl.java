package com.ciaosgarage.traveldiary.beans.dao.daoServiceTest;

import com.ciaosgarage.traveldiary.beans.dao.attachStatementHandler.AttachStatementHandler;
import com.ciaosgarage.traveldiary.beans.dao.dao.Dao;
import com.ciaosgarage.traveldiary.beans.dao.seqTableHandler.SeqTableHandler;
import com.ciaosgarage.traveldiary.beans.dao.voHandler.VoHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DaoServiceImpl implements DaoService {

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
        if (!this.isDuplicationPk(vo)) {
            String pk = seqTableHandler.getPk(vo.getClass());
            voHandler.setPrimaryKey(vo, pk);
            dao.add(vo);
        } else {
            dao.update(vo);
        }
    }


    // DB에 존재하는 값인지 확인하는 메소드
    private boolean isDuplicationPk(Object vo) {
        attachStatementHandler.addColumnCondition(voHandler.getPrimaryKeyColumnName(vo), voHandler.getPrimaryKey(vo));
        try {
            this.pullVo(vo.getClass());
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }


    @Override
    public Object pullVo(Class voInfo) throws EmptyResultDataAccessException{
        voHandler.isVo(voInfo);
        return dao.get(voInfo, attachStatementHandler.getAttachStatement());
    }

    @Override
    public List pullVoList(Class voInfo) throws EmptyResultDataAccessException{
        voHandler.isVo(voInfo);
        return dao.getList(voInfo, attachStatementHandler.getAttachStatement());
    }

}
