package com.ciaosgarage.traveldiary.beans.textReaderTest;


import com.ciaosgarage.traveldiary.beans.textReader.TextReader;
import com.ciaosgarage.traveldiary.context.BeansContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sun.tools.java.ClassPath;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.util.Iterator;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BeansContext.class)
public class TestReaderTest {

    @Autowired
    TextReader textReader;


    @Before
    public void setup() {
        assertNotNull(textReader);
    }

    @Test
    public void readFile() {
        String getString = textReader.read("/Users/ciaolee/GoogleDrive/Project/TravelDiaryRebuild/src/resources/AccountAuthorization");
        System.out.println(getString);
    }
}
