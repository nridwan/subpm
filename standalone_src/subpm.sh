SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
cd $SCRIPT_DIR
if [ "$1" "==" "" ]; then
  ./gradlew -b ./subpm/build.gradle.kts install
else
  ./gradlew -b ./subpm/build.gradle.kts $1
fi