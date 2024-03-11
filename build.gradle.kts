plugins {
    `java-library`
}

group = "lol.koblizek"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    runtimeOnly("org.xerial:sqlite-jdbc:3.45.1.0")
    implementation("com.zaxxer:HikariCP:5.1.0")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}