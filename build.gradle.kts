plugins {
    id("java")
    id("io.github.rancraftplayz.remapper") version ("1.0.2")
}

group = "com.eteryun"
version = "1.0"

subprojects {
    apply(plugin = "java")
    apply(plugin = "io.github.rancraftplayz.remapper")

    repositories {
        mavenCentral()
        maven {
            url = uri("https://repo.repsy.io/mvn/eteryun/eteryun")
            credentials() {
                username = "${project.properties["eteryun.username"]}"
                password = "${project.properties["eteryun.password"]}"
            }
        }
        maven { url = uri("https://repo.spongepowered.org/maven") }
        maven { url = uri("https://maven.elmakers.com/repository/") }
        maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
        maven { url = uri("https://repo.eteryun.com.br/") }
    }

    dependencies {
        implementation("com.eteryun:eteryun-api:1.0")

        remapLib("org.spigotmc:spigot:1.18.2-R0.1-SNAPSHOT:remapped-mojang")
        implementation("org.spigotmc:spigot:1.18.2-R0.1-SNAPSHOT:remapped-mojang")
        implementation("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
        implementation("org.spongepowered:mixin:0.8.5")
    }

    spigot {
        version = "1.18.2"
    }

    tasks.remapJar {
        dependsOn("jar")
    }
}

tasks.create("buildAll") {
    group = "build"
    description = "Builds all subprojects"
    dependsOn(subprojects.map { "${it.name}:jar" }.toList())
}

tasks.create("copyAll") {
    group = "build"
    description = "Copies all subprojects jar files to the build folder"
    dependsOn("buildAll")
    dependsOn(subprojects.map { "${it.name}:remapJar" }.toList())
    doFirst {
        subprojects.forEach {
            val jar = file("${it.buildDir}/libs/${it.name}-${it.project.version}.jar")
            val buildJar = file("E:\\eteryun\\server\\modules\\${it.name}.jar")
            System.out.println("Copying ${jar.absolutePath} to ${buildJar.absolutePath}")
            if (jar.exists()) {
                buildJar.parentFile.mkdirs()
                jar.copyTo(buildJar, overwrite = true)
            }
        }
    }
}