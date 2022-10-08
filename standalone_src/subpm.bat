@echo off
set mode=install
if "%*" == "" (
    .\gradlew -b .\subpm\build.gradle.kts install
) else (
    .\gradlew -b .\subpm\build.gradle.kts %*
)