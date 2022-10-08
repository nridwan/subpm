package com.nridwan.model;

import java.util.HashMap;

public class PackageList {
    public HashMap<String, String> getPackages() {
        return packages;
    }

    public void setPackages(HashMap<String, String> packages) {
        this.packages = packages;
    }

    public void merge(PackageList others) {
        packages.putAll(others.packages);
    }

    private HashMap<String, String> packages = new HashMap<>();

    @Override
    public String toString() {
        return "PackageList{" +
                "packages=" + packages +
                '}';
    }
}
