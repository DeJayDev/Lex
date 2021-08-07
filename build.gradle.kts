plugins {
    id("java")
    id("io.freefair.lombok") version "6.0.0-m2"
}

group = "dev.dejay"
version = "0.1.0"

repositories {
    mavenCentral()
    maven(url="https://papermc.io/repo/repository/maven-public/")
    maven(url="https://oss.sonatype.org/content/groups/public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")

    compileOnly("javax.annotation:javax.annotation-api:1.3.2")
    annotationProcessor("javax.annotation:javax.annotation-api:1.3.2")

    compileOnly("org.projectlombok:lombok:1.18.20")
    annotationProcessor("org.projectlombok:lombok:1.18.20")

    implementation("com.konghq:unirest-java:3.11.09")

}

(tasks.getByName("processResources") as ProcessResources).apply {
    expand("version" to rootProject.version)
}
