package com.example.checkencoding;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Base64;
import java.util.LinkedList;
import java.util.Set;

import javax.swing.JOptionPane;


import org.mozilla.universalchardet.UniversalDetector;

public class CheckEncoding {
    private String sciezka;
    private String rozszerzenie;

    public CheckEncoding() {
    }

    public String getSciezka() {
        return sciezka;
    }

    public void setSciezka(String sciezka) {
        this.sciezka = sciezka;
    }

    public String getRozszerzenie() {
        return rozszerzenie;
    }

    public void setRozszerzenie(String rozszerzenie) {
        this.rozszerzenie = rozszerzenie;
    }

    public CheckEncoding(String sciezka, String rozszerzenie) {
        this.sciezka = sciezka;
        this.rozszerzenie = rozszerzenie;
    }

    public String getEncode(String sciezka, String rozszerzenie) throws IOException {
        if (sciezka == null || rozszerzenie == null) {
            return "wrong input";
        }
        String adres = sciezka  + rozszerzenie;

        File plik = new File(adres);
        byte[] buf = new byte[4096];
        java.io.InputStream fis = java.nio.file.Files.newInputStream(Paths.get(adres));

        // (1)
        UniversalDetector detector = new UniversalDetector(null);

        // (2)
        int nread;
        while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
            detector.handleData(buf, 0, nread);
        }
        // (3)
        detector.dataEnd();

        // (4)
        String encoding = detector.getDetectedCharset();
        if (encoding != null) {
            System.out.println("Detected encoding = " + encoding);
            return "Detected encoding: " + encoding;
        } else {
            System.out.println("No encoding detected.");
        }

        // (5)
detector.reset();
        return "No encoding detected, implicity is UTF-8";

    }
}
