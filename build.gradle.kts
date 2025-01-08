import org.yaml.snakeyaml.Yaml
import java.io.InputStream
import java.util.Properties

plugins {
    java
    id("net.minecrell.plugin-yml.bukkit") version "0.3.0"
    id("io.github.patrick.remapper") version "1.4.2"
}

val yamlFile = file("build-version.yml")

fun readVersionFromYaml(): Map<String, Int> {
    val yaml = Yaml()
    val inputStream = yamlFile.inputStream()
    val data: Map<String, Map<String, Int>> = yaml.load(inputStream) as Map<String, Map<String, Int>>
    return data["version"] ?: throw IllegalArgumentException("Invalid YAML format")
}

fun writeVersionToYaml(version: Map<String, Int>) {
    val yaml = Yaml()
    val data = mapOf("version" to version)
    yamlFile.writeText(yaml.dump(data))
}

tasks.register("incrementVersion") {
    doLast {
        // Read the current version
        val currentVersion = readVersionFromYaml()
        println("Current version: $currentVersion")

        // Increment the patch version
        val newVersion = currentVersion.toMutableMap()
        newVersion["build"] = currentVersion["build"]!! + 1

        // Write the updated version back to YAML
        writeVersionToYaml(newVersion)
        println("Updated version: $newVersion")
    }
}

val currentVersion = readVersionFromYaml()

// Set the version dynamically
println("Building version ${currentVersion["major"]}.${currentVersion["minor"]}.${currentVersion["build"]}")

group = "me.DNFneca"


bukkit {
    name = "Leaderboard"
    main = "me.DNFneca.leaderboard.Leaderboard"
    apiVersion = "1.21"
    version = "${currentVersion["major"]}.${currentVersion["minor"]}.${currentVersion["build"]}"
    depend = listOf("ProtocolLib")

    commands {
        register("addHologram") {
            description = "This is a test command!"
            aliases = listOf("t")
//            permission = "testplugin.test"
            usage = "Just run the command!"
//             permissionMessage = "You may not test this command!"
        }
        register("addPlayerToBlacklist") {
            description = "This is a test command!"
            aliases = listOf("s")
//            permission = "testplugin.test"
            usage = "Just run the command!"
        }
        // ...
    }
}




repositories {
    mavenCentral()
    mavenLocal()
    maven ("https://repo.papermc.io/repository/maven-public/")
    maven ("https://oss.sonatype.org/content/groups/public/")
    maven ("https://repo.dmulloy2.net/repository/public/")

}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
    compileOnly(group = "com.comphenix.protocol", name = "ProtocolLib", version = "5.3.0")
    implementation("org.spigotmc:spigot:1.21.1-R0.1-SNAPSHOT:remapped-mojang")
}

val targetJavaVersion = 21
java {
    val javaVersion = JavaVersion.toVersion(targetJavaVersion)
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
    if (JavaVersion.current() < javaVersion) {
        toolchain.languageVersion = JavaLanguageVersion.of(targetJavaVersion)
    }
}

tasks.remap {
    version.set("1.21.1")
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"

    if (targetJavaVersion >= 10 || JavaVersion.current().isJava10Compatible) {
        options.release.set(targetJavaVersion)
    }
}


tasks.register<Copy>("copyPluginToTestserver") {
    dependsOn("build")
    from("${buildDir}/libs")
    into("D:\\Servers\\Minecraft\\1.21\\1\\Plugin test\\plugins")
}

tasks.processResources {
    dependsOn("incrementVersion")
    dependsOn(tasks.remap)
}