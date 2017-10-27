package com.ciaosgarage.traveldiary.beans.textReader;

import com.ciaosgarage.traveldiary.beans.textReader.exceptions.CannotFoundFileException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.Scanner;

@Component
public class TextReaderImpl implements TextReader {
    @Override
    public String read(String path) {
        try {
            Scanner readFile = new Scanner(new File(path));
            StringBuffer stringBuffer = new StringBuffer();
            while (readFile.hasNext()) {
                stringBuffer.append(readFile.nextLine());
            }

            return stringBuffer.toString();
        } catch (IOException e) {
            throw new CannotFoundFileException("filePath : " + path);
        }
    }

    @Override
    public void write(String path, String context) {
        try {
            PrintWriter printWriter = new PrintWriter(new File(path));
            printWriter.write(context);
            printWriter.close();
        } catch (IOException e) {
            throw new CannotFoundFileException("filePath : " + path);
        }

    }
}
