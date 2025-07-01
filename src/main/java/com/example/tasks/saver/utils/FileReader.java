package com.example.tasks.saver.utils;


import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

@Slf4j
public class FileReader {
    private FileReader() {
    }

    public static String readFromFileFromResources(String fileName) {
        ClassLoader classLoader = DatabaseUtils.class.getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

        StringBuffer buffer = new StringBuffer("");
        try (Stream<String> stream = Files.lines(Paths.get(file.toURI()))) {
            stream.forEach(buffer::append);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    public static String getCurrentDirectory() {
        String dir = Paths.get(".").toAbsolutePath().normalize().toString();
        return dir;
    }

    public static boolean createRootDirectory(String name) {
        File dir = new File(getCurrentDirectory() + System.getProperty("file.separator") + name);
        if (!dir.exists()) {
            boolean create = dir.mkdirs();
            return create;
        }
        return true;
    }

    public static String createDirectoriesFromCurrent(String... dirs) {
        File directory = new File(getCurrentDirectory());
        new File(directory.getParent()).setWritable(true);
        for (String dir : dirs) {
            directory = new File(directory.getAbsolutePath() + File.separator + dir);
            if (!directory.exists()) {
                boolean create = directory.mkdirs();
                if (create) {
                    new File(directory.getParent()).setWritable(true);
                } else {
                    return "";
                }
            }
        }
        return directory.getPath();
    }

    public static String createDirectoriesFromCurrent1(String[] dirs) {
        if (dirs == null || dirs.length == 0) {
            return "";

        }
        String path = "";
        boolean start = false;
        File directory = new File(path);
        for (String dir : dirs) {
            if (dir.contains(":")) {
                path = dir;
                start = true;
                continue;
            }
            if (start) {
                directory = new File(path + File.separator + dir);
                start = false;
            } else {
                directory = new File(directory.getAbsolutePath() + File.separator + dir);
            }
            directory.setWritable(true);
            if (!directory.exists()) {
                boolean create = directory.mkdirs();
                if (create) {
                    continue;
                } else {
                    return "";
                }
            }
        }
        return directory.getPath();
    }

    public static boolean isEmptyDirectory(String name) {
        File dir = new File(name);
        if (!dir.exists() || !dir.isDirectory()) createRootDirectory(name);
        try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(Paths.get(dir.toURI()))) {
            return !dirStream.iterator().hasNext();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }


    public static void copyFile(String sourcePath, String destinationPath) throws IOException {
        Path FROM = Paths.get(sourcePath);
        Path TO = Paths.get(destinationPath);
        //overwrite existing file, if exists
        CopyOption[] options = new CopyOption[]{
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.COPY_ATTRIBUTES
        };
        Files.copy(FROM, TO, options);
    }
}
