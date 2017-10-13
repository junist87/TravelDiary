package com.ciaosgarage.traveldiary.domain.mapBoard;

import com.ciaosgarage.traveldiary.beans.dao.valueObject.DbTable;
import com.ciaosgarage.traveldiary.beans.dao.valueObject.PrimaryKey;
import com.ciaosgarage.traveldiary.beans.dao.valueObject.RwType;

@DbTable
public class MapBoard {
    @PrimaryKey(rwType = RwType.INSERTONLY)
    private String pk;
    private String title;

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
