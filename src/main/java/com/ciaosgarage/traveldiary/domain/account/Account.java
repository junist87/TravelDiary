package com.ciaosgarage.traveldiary.domain.account;

import com.ciaosgarage.traveldiary.beans.dao.vo.*;

@DbTable
public class Account {
    @ColumnConfig(columnType = ColumnType.PRIMARYKEY, rwType = RwType.INSERTONLY)
    private String pk;

    @ColumnConfig(cryptOption = CryptOption.ON)
    private String email;
    @ColumnConfig(cryptOption = CryptOption.ON)
    private String nickname;
    @ColumnConfig(cryptOption = CryptOption.ON)
    private String password;
    private Integer authorization;

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getPk() {
        return pk;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAuthorization() {
        return authorization;
    }

    public void setAuthorization(Integer authorization) {
        this.authorization = authorization;
    }
}
