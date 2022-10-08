## SubPM
**Sub**module-like **P**ackage **M**anager Standalone Gradle

## Limitation
Since Gradle task is non-interactive, when the needed processes (git pull, git clone) needed interactive mode it'll be error.
This method suitable for those who are using public repositories, or already set up their ssh-based git (and make sure to add git domains to known-hosts before using this)

## How to use
1. Copy subpm, subpm.bat, subpm.sh to your gradle project root
2. Open your root project settings.gradle/settings.gradle.kts
3. Add this line: `includeBuild("subpm")`
4. Add subpkg.yaml according to the docs in [SubPM readme](../README.md)
5. run `subpm.bat` / `./subpm.sh`
6. to update dependency (with no repo addition), run `subpm.bat update` / `./subpm.sh update`
7. to update dependency (with no repo changes), run `subpm.bat reset` / `./subpm.sh reset`
8. to update dependency with repo addition, just run `subpm.bat` / `./subpm.sh`
