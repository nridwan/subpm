package com.nridwan;

import com.nridwan.model.Option;
import com.nridwan.model.PackageList;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) {
        Option option = new Option(args);
        String cwd = System.getProperty("user.dir");
        Yaml yaml = new Yaml(new Constructor(PackageList.class));
        PackageList res = new PackageList();
        try {
            for(String path : option.getSubpkgs()) {
                File file = new File(cwd+"/"+path);
                if(file.exists()) {
                    InputStream targetStream = new FileInputStream(file);
                    res.merge(yaml.load(targetStream));
                } else if(!path.contains(".local")) {
                    System.out.println("File "+path+" not found");
                }
            }
            boolean clone = !option.getActionType().equals("update");
            boolean reset = option.getActionType().equals("reset");
            res.getPackages().forEach((path, url) -> clonePackage(new File(cwd), path, url, clone, reset));
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private static void clonePackage(File cwd, String path, String url, boolean clone, boolean reset) {
        if(url == null) return;
        String[] urlParts = url.split("#");
        File file = new File(cwd, path);
        if (reset && file.exists()) {
            deleteDirectory(file);
        }
        if (!file.exists() && clone) {
            try {
                ProcessBuilder builder = new ProcessBuilder("git", "clone", urlParts[0], path);
                builder.redirectErrorStream(true);
                builder.redirectInput(ProcessBuilder.Redirect.INHERIT);
                builder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
                builder.directory(cwd);
                Process process = builder.start();
                process.waitFor();
            } catch (Throwable t) {
                t.printStackTrace();
                return;
            }
        } else {
            try {
                ProcessBuilder builder = new ProcessBuilder("git", "fetch");
                builder.redirectErrorStream(true);
                builder.redirectInput(ProcessBuilder.Redirect.INHERIT);
                builder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
                builder.directory(cwd);
                Process process = builder.start();
                process.waitFor();
            } catch (Throwable t) {
                t.printStackTrace();
                return;
            }
        }
        if (!file.exists()) {
            System.out.println("Error, subpackage " + path + " (" + url + ") cannot be resolved");
            return;
        }
        if (urlParts.length > 1) {
            checkoutPkg(file, path, urlParts[1]);
        }
    }

    private static void checkoutPkg(File cwd, String path, String hash) {
        try {
            ProcessBuilder builder = new ProcessBuilder("git", "checkout", hash);
            builder.redirectErrorStream(true);
            builder.redirectInput(ProcessBuilder.Redirect.INHERIT);
            builder.directory(cwd);
            Process process = builder.start();
            process.waitFor();
        } catch (Throwable t) {
            t.printStackTrace();
            return;
        }
        try {
            ProcessBuilder builder = new ProcessBuilder("git", "pull");
            builder.redirectErrorStream(true);
            builder.redirectInput(ProcessBuilder.Redirect.INHERIT);
            builder.directory(cwd);
            Process process = builder.start();
            process.waitFor();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private static boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }

}
