package com.vnpts.tracebility_v2.util;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.yaml.snakeyaml.Yaml;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.Random;

public class FileUtils {

    public static boolean createDir(String dirStr) {
        File dir = new File(dirStr);
        return dir.mkdirs();
    }

    private static Integer getRandomImageLength() {
        Random r = new Random();
        int low = 5;
        int high = 30;
        return r.nextInt(high - low) + low;
    }

    public static String generateImageName(String fileName) {
        int length = getRandomImageLength();
        return ZonedDateTime.now().getYear()
                + RandomStringUtils.random(length, true, true)
                + RandomStringUtils.random(10, true, false)
                + RandomStringUtils.random(5, false, true)
                + getExtensionName(fileName);
    }

    public static String generateName() {
        int length = getRandomImageLength();
        return ZonedDateTime.now().getYear()
                + RandomStringUtils.random(length, true, true)
                + RandomStringUtils.random(10, true, false)
                + RandomStringUtils.random(5, false, true);
    }

    public static String getExtensionName(String fileName) {
        return "." + FilenameUtils.getExtension(fileName).toLowerCase();
    }

    public static ResponseEntity<Resource> returnFile(HttpServletRequest request, String dir, String file, String original) throws MalformedURLException {
        Resource resource = new UrlResource("file:///" + dir + file);
        String contentType = request.getServletContext().getMimeType(file);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + original + "\"")
            .body(resource);
    }


    public static <T> T loadYamlFile(String fileName, Class<T> tClass) {
        Yaml yaml = new Yaml();
        InputStream inputStream = FileUtils.class
            .getClassLoader()
            .getResourceAsStream(fileName);
        T loaded = yaml.loadAs(inputStream, tClass);
        return loaded;
    }

    public static byte[] returnFile(String dir, String fileName) throws IOException {
        if (fileName == null || "".equals(fileName)) return null;
        Path path = Paths.get(dir + File.separator + fileName);
        return Files.readAllBytes(path);
    }
}
