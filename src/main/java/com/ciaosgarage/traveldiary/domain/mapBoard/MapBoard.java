package com.ciaosgarage.traveldiary.domain.mapBoard;

import com.ciaosgarage.traveldiary.beans.dao.vo.ColumnConfig;
import com.ciaosgarage.traveldiary.beans.dao.vo.DbTable;
import com.ciaosgarage.traveldiary.beans.dao.vo.RwType;

@DbTable
public class MapBoard {
    @ColumnConfig(rwType = RwType.INSERTONLY)
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
