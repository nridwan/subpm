package com.subpm.model

class SubpkgOption(args: Array<String>? = null) {
    var subpkgs = ArrayList<String>()

    init {
        var command: String? = null
        var count = 1
        args?.forEach {
            if (it.startsWith("--")) {
                if (count == 0) throw Error("Invalid options")
                command = it
                count = 0
            } else if (command == null) {
                throw Error("Invalid options")
            } else if (command == "--files") {
                subpkgs.add(it)
                count++
            }
        }
        if (subpkgs.size == 0) {
            subpkgs.add("subpkg.yaml")
            subpkgs.add("subpkg.local.yaml")
        }
    }
}