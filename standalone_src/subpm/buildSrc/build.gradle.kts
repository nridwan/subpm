plugins {
    `kotlin-dsl`
}
repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation("org.yaml:snakeyaml:1.33")
}