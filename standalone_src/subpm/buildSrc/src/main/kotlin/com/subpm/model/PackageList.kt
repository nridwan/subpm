package com.subpm.model

import java.util.HashMap

class PackageList {
    fun merge(others: PackageList) {
        packages.putAll(others.packages)
    }

    var packages = HashMap<String, String>()
    override fun toString(): String {
        return "PackageList{" +
                "packages=" + packages +
                '}'
    }
}