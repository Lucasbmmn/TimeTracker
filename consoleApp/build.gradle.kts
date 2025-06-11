plugins {
    application
    java
}

application {
    mainClass.set("com.lucasbmmn.timetracker.console.Main")
}

dependencies {
    implementation(project(":composeApp"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

tasks.named<Copy>("processResources") {
    from(project(":composeApp").file("src/desktopMain/composeResources"))
    into(layout.buildDirectory.dir("resources/main"))
}
