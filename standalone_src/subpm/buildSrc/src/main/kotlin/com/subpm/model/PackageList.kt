package com.subpm.model

import java.util.HashMap

class PackageList {
    fun merge(others: PackageList) {
        others.packages.forEach { k: String, v: String? ->
            if (v?.contains("#") === true || !packages.containsKey(k) || packages.get(k)?.contains("#") !== true) {
                packages.put(k, v!!)
                return@forEach
            }
            packages.put(k, v + "#" + packages.get(k)!!.split("#".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray().get(1))
        }
    }

    var packages = HashMap<String, String>()
    override fun toString(): String {
        return "PackageList{" +
                "packages=" + packages +
                '}'
    }
}