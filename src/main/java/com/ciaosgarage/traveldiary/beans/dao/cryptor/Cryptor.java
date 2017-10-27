package com.ciaosgarage.traveldiary.beans.dao.cryptor;

import com.ciaosgarage.traveldiary.beans.dao.parameters.ColumnValue;

/**
 * 필드값의 암호화를 담당하는 클래스
 */
public interface Cryptor {
    /**
     * 해당 값의 암호화를 확인하고, 암호화 대상 필드값이라면 그값을 암호화 하여 리턴한다.
     * 대상 필드가 아니면 원래 값을 리턴한다.
     *
     * @param voInfo     값이 포함되는 객체의 클래스
     * @param value      조사대상이 되는 값
     * @return 처리된 키값
     */
    Object encryption(Class voInfo, ColumnValue value);

    /**
     * 해당 값의 암호화를 확인하고, 암호화 대상 필드값이라면 그값을 해석 하여 리턴한다.
     * 대상 필드가 아니면 원래 값을 리턴한다.
     *
     * @param voInfo     값이 포함되는 객체의 클래스
     * @param value      조사대상이 되는 값
     * @return 처리된 키값
     */
    Object decryption(Class voInfo, ColumnValue value);
}
