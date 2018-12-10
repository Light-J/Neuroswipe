package com.nsa.cubric.application.services;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileHelper {
    public static void addFileToOutputStream(ZipOutputStream zipOutputStream, File fileToAdd, String nameOfFile) throws IOException {
        zipOutputStream.putNextEntry(new ZipEntry(nameOfFile));
        FileInputStream fileInputStreamFront = new FileInputStream(fileToAdd);
        IOUtils.copy(fileInputStreamFront, zipOutputStream);
        fileInputStreamFront.close();
        zipOutputStream.closeEntry();
    }
}
