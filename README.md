## SubPM
**Sub**module-like **P**ackage **M**anager

## Motivation
I need a small tool that I can use to checkout projects 
submodules at its latest condition without affecting repo 
changelog, since submodules is locking the version using hash
and needed to be commited to the repo

## Other Versions
Please see [standalone_src](standalone_src/README.md) docs

## How to Build
run `./gradlew release`\
Note: if you build on lower JDK version, you can run it in higher JDK, but not otherwise.
(optional), preferable to build using JDK 1.8\
Output jar will be in build\libs\subpm_proguard.jar

## How to use
### Option 1:
1. place subpkg.yaml inside your target directory. please refer to subpkg.yaml.example for sample
2. (Optional) you can also add subpkg.local.yaml to override subpkg.yaml implementations
3. run `java -jar path/to/subpm.jar`
### Option 2:
1. place any yaml that fulfill the subpkg.yaml format inside your target directory, you can have multiple files of these
2. run `java -jar path/to/subpm.jar --files file1.yaml file2.yaml etc.yaml`\

Note: place the file in orders, this ensures the latter will always override the previous file
