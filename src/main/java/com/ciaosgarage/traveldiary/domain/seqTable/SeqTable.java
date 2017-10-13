package com.ciaosgarage.traveldiary.domain.seqTable;

import com.ciaosgarage.traveldiary.beans.dao.valueObject.PrimaryKey;
import com.ciaosgarage.traveldiary.beans.dao.valueObject.RwType;

import java.sql.Date;

public class SeqTable {
    @PrimaryKey(rwType = RwType.INSERTONLY)
    private Integer pk;
    private Date createDate;
    private String targetPk;
    private String tablename;
    private Integer status;

    public Integer getPk() {
        return pk;
    }

    public void setPk(Integer pk) {
        this.pk = pk;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public String getTargetPk() {
        return targetPk;
    }

    public void setTargetPk(String targetPk) {
        this.targetPk = targetPk;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public SeqTableStatus getStatus() {
        return SeqTableStatus.getSeqTableStatus(status);
    }

    public void setStatus(SeqTableStatus status) {
        this.status = SeqTableStatus.getIntValue(status);
    }
}
