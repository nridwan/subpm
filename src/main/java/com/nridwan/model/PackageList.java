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
        others.packages.forEach((k, v) -> {
            if(v.contains("#") || !packages.containsKey(k) || !packages.get(k).contains("#")) {
                packages.put(k, v);
                return;
            }
            packages.put(k, v+"#"+packages.get(k).split("#")[1]);
        });
    }

    private HashMap<String, String> packages = new HashMap<>();

    @Override
    public String toString() {
        return "PackageList{" +
                "packages=" + packages +
                '}';
    }
}
