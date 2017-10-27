package com.ciaosgarage.traveldiary.beans.textReader;

import java.nio.file.Path;

public interface TextReader {
    String read(String path);

    void write(String path, String context);
}
