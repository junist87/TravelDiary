package com.ciaosgarage.traveldiary.beans.cryptor;

public interface Cryptor {
    String encryption(String str);

    String decryption(String str);
}
