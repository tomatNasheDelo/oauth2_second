plugins {
    id("org.springframework.boot") version "3.4.3" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false
}

allprojects {
    group = "ru.demo.oauth2"
    version = "0.0.1-SNAPSHOT"
    repositories { mavenCentral() }
}
