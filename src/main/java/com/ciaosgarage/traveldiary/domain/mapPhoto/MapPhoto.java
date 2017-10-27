package com.ciaosgarage.traveldiary.domain.mapPhoto;

import com.ciaosgarage.traveldiary.beans.dao.vo.ColumnConfig;
import com.ciaosgarage.traveldiary.beans.dao.vo.DbTable;
import com.ciaosgarage.traveldiary.beans.dao.vo.RwType;

@DbTable
public class MapPhoto {
    @ColumnConfig(rwType = RwType.INSERTONLY)
    String pk;
    String photopath;

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getPhotopath() {
        return photopath;
    }

    public void setPhotopath(String photopath) {
        this.photopath = photopath;
    }
}
