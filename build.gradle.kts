plugins {
    id("java")
}

group = "ru.wteam"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("org.hibernate.orm:hibernate-core:7.1.0.Final")

    implementation("org.postgresql:postgresql:42.7.7")

    implementation("org.apache.logging.log4j:log4j-api:2.24.3")
    implementation("org.apache.logging.log4j:log4j-core:2.24.3")

    // testcontainers
    testImplementation("org.testcontainers:testcontainers:1.21.3")
    testImplementation("org.testcontainers:junit-jupiter:1.21.3")
    testImplementation("org.testcontainers:postgresql:1.21.3")

    // https://mvnrepository.com/artifact/org.mockito/mockito-core
    testImplementation("org.mockito:mockito-core:5.18.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.18.0")

    // https://mvnrepository.com/artifact/org.slf4j/slf4j-api
//    implementation("org.slf4j:slf4j-api:2.0.17")

}

tasks.test {
    useJUnitPlatform()
}


