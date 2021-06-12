plugins {
   java
   kotlin("jvm") version "1.4.20"
   id("com.github.johnrengelman.shadow") version "5.2.0"
   id("io.izzel.taboolib") version "1.3"
}

group = "org.meowcat"
version = "1.0.0-rc-fix-2"
val mccoroutine = "1.5.0"
repositories {
   mavenCentral()
   maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
   maven("https://oss.sonatype.org/content/groups/public/")
   maven("https://maven.aura-dev.team/repository/auradev-releases/")
   maven("https://jitpack.io")
}

val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileKotlin.kotlinOptions.jvmTarget = "1.8"

val javaTarget = 8 // Sponge targets a minimum of Java 8
java {
   sourceCompatibility = JavaVersion.toVersion(javaTarget)
   targetCompatibility = JavaVersion.toVersion(javaTarget)
}

taboolib {
   tabooLibVersion = "5.62"
   loaderVersion = "2.12"
   classifier = null
   builtin = true
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {

   val excluded = setOf(
      "org.spigotmc",
      "net.md-5",
      "org.jetbrains.kotlin:kotlin-stdlib",
      "org.jetbrains.kotlin:kotlin-reflect",
      "org.jetbrains.kotlin:kotlin-stdlib-common",
      "org.jetbrains:annotations"
   )
   dependencyFilter.exclude {
      it.moduleGroup in excluded ||
         it.moduleName in excluded ||
         "${it.moduleGroup}:${it.moduleName}" in excluded
      // println("${it.moduleGroup}:${it.moduleName}  shouldExclude:$shouldExclude")
   }
   relocate("io.izzel.taboolib.loader", "${project.group}.module.internal.boot")
}

dependencies {
   compileOnly("org.jetbrains.kotlin:kotlin-stdlib:1.4.20")
   compileOnly("com.github.MilkBowl:VaultAPI:1.7")
   compileOnly("org.spigotmc:spigot-api:1.16.3-R0.1-SNAPSHOT") {
      isTransitive = false
   }
   // coroutine
   implementation("com.github.shynixn.mccoroutine:mccoroutine-bukkit-api:$mccoroutine")
   implementation("com.github.shynixn.mccoroutine:mccoroutine-bukkit-core:$mccoroutine")
   implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.0")
}

tasks.withType<ProcessResources> {
   filesMatching("plugin.yml") {
      expand(
         "main" to "${project.group}.module.internal.boot.PluginBoot",
         "version" to project.version,
         "libVersion" to taboolib.tabooLibVersion,
         "libDownload" to true,
         "loaderVersion" to taboolib.loaderVersion
      )
   }
}
