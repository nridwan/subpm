package com.subpm

import com.subpm.model.PackageList
import com.subpm.model.SubpkgOption
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor

import java.io.*


abstract class SubPM : DefaultTask() {
    @get:Input
    abstract val actionType: Property<String>

    init {
        actionType.convention("install")
    }

    @TaskAction
    fun act() {
        val cwd = System.getProperty("user.dir")
        val subpkgOption = SubpkgOption()
        val yaml = Yaml(Constructor(PackageList::class.java))
        val res = PackageList()
        try {
            for (path in subpkgOption.subpkgs) {
                val file = File("$cwd/$path")
                if (file.exists()) {
                    val targetStream: InputStream = FileInputStream(file)
                    res.merge(yaml.load(targetStream))
                } else if (!path.contains(".local")) {
                    println("File $path not found")
                }
            }
            res.packages.forEach(when(actionType.get()) {
                "update" -> { (path, url) ->
                    clonePackage(File(cwd), path, url)
                }
                "reset" ->  { (path, url) ->
                    clonePackage(File(cwd), path, url, reset = true)
                }
                else -> { (path, url) ->
                    clonePackage(File(cwd), path, url, clone = true)
                }
            })
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }

    private fun clonePackage(cwd: File, path: String, url: String?, clone: Boolean = true, reset: Boolean = false) {
        if (url == null) return
        val urlParts = url.split("#").toTypedArray()
        val file = File(cwd, path)
        if (reset && file.exists()) {
            file.deleteRecursively()
        }
        if (!file.exists() && clone) {
            try {
                val builder = ProcessBuilder("git", "clone", urlParts[0], path)
                builder.redirectErrorStream(true)
                builder.redirectInput(ProcessBuilder.Redirect.INHERIT)
                builder.redirectOutput(ProcessBuilder.Redirect.INHERIT)
                builder.directory(cwd)
                val process = builder.start()
                process.waitFor()
            } catch (t: Throwable) {
                t.printStackTrace()
                return
            }
        } else {
            try {
                val builder = ProcessBuilder("git", "fetch")
                builder.redirectErrorStream(true)
                builder.redirectInput(java.lang.ProcessBuilder.Redirect.INHERIT)
                builder.redirectOutput(java.lang.ProcessBuilder.Redirect.INHERIT)
                builder.directory(cwd)
                val process: java.lang.Process = builder.start()
                process.waitFor()
            } catch (t: Throwable) {
                t.printStackTrace()
                return
            }
        }
        if (!file.exists()) {
            println("Error, subpackage $path ($url) cannot be resolved")
            return
        }
        if (urlParts.size > 1) {
            checkoutPkg(file, path, urlParts[1])
        }
    }

    private fun checkoutPkg(cwd: File, path: String, hash: String) {
        try {
            val builder = ProcessBuilder("git", "checkout", hash)
            builder.redirectErrorStream(true)
            builder.redirectInput(ProcessBuilder.Redirect.INHERIT)
            builder.directory(cwd)
            val process = builder.start()
            process.waitFor()
        } catch (t: Throwable) {
            t.printStackTrace()
            return
        }
        if (isBranch(cwd)) {
            try {
                val builder = ProcessBuilder("git", "reset", "--hard", "origin/$hash")
                builder.redirectErrorStream(true)
                builder.redirectInput(java.lang.ProcessBuilder.Redirect.INHERIT)
                builder.directory(cwd)
                val process: Process = builder.start()
                process.waitFor()
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }
    }

    private fun isBranch(cwd: File): Boolean {
        return try {
            val builder = ProcessBuilder("git", "symbolic-ref", "-q", "HEAD")
            builder.redirectErrorStream(true)
            builder.directory(cwd)
            val process: Process = builder.start()
            process.waitFor()
            val reader = BufferedReader(InputStreamReader(process.getInputStream()))
            val sb = StringBuilder()
            var line: String? = null
            while (reader.readLine().also { line = it } != null) {
                sb.append(line)
                sb.append(java.lang.System.getProperty("line.separator"))
            }
            val result: String = sb.toString()
            result.trim { it <= ' ' }.length > 0
        } catch (t: Throwable) {
            t.printStackTrace()
            false
        }
    }
}