import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.6.3"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"

	kotlin("jvm") version "1.6.10"
	kotlin("plugin.spring") version "1.6.10"
	id("org.liquibase.gradle") version "2.1.1"

}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	// https://mvnrepository.com/artifact/com.fasterxml.jackson.module/jackson-module-kotlin
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.1")
	implementation("no.skatteetaten.aurora.mockmvc.extensions:mockmvc-extensions-kotlin:0.2.2")
	// https://mvnrepository.com/artifact/javax.validation/validation-api
	implementation("javax.validation:validation-api:2.0.1.Final")
	// https://mvnrepository.com/artifact/org.projectlombok/lombok
	compileOnly("org.projectlombok:lombok:1.18.22")
	implementation("org.postgresql:postgresql:42.3.3")
	// https://mvnrepository.com/artifact/org.liquibase/liquibase-core
	implementation("org.liquibase:liquibase-core:4.8.0")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.6.4")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation ("org.mapstruct:mapstruct:1.4.2.Final")



	annotationProcessor ("org.mapstruct:mapstruct-processor:1.4.2.Final")

	// https://mvnrepository.com/artifact/com.h2database/h2
	testImplementation("com.h2database:h2:2.1.210")
	// https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
	testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.2")
	testImplementation("org.mockito:mockito-core:4.3.1")
	testImplementation ("org.mockito.kotlin:mockito-kotlin:4.0.0")
	testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc:2.0.6.RELEASE")



}



tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

