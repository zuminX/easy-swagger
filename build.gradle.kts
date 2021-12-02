import org.apache.tools.ant.filters.EscapeUnicode
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun properties(key: String) = project.findProperty(key).toString()

plugins {
  id("java")
  id("org.jetbrains.kotlin.jvm") version "1.6.0"
  id("org.jetbrains.intellij") version "1.3.0"
  id("org.jetbrains.changelog") version "1.3.1"
}

val pluginVersion: String by project

group = properties("pluginGroup")
version = properties("pluginVersion")

repositories {
  mavenLocal()
  maven(url = "https://maven.aliyun.com/repository/public")
  maven(url = "https://maven-central.storage-download.googleapis.com/repos/central/data/")
  maven(url = "https://repo.eclipse.org/content/groups/releases/")
  maven(url = "https://www.jetbrains.com/intellij-repository/releases")
  mavenCentral()
}

dependencies {
  implementation("cn.hutool:hutool-core:5.7.16")
}

intellij {
  pluginName.set(properties("pluginName"))
  version.set(properties("platformVersion"))
  type.set(properties("platformType"))

  plugins.set(properties("platformPlugins").split(',').map(String::trim).filter(String::isNotEmpty))
}

changelog {
  val date = LocalDate.now(ZoneId.of("Asia/Shanghai"))
  val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")

  version.set(pluginVersion)
  header.set(provider { "v${version.get()} (${date.format(formatter)})" })
  headerParserRegex.set(Regex("v\\d(\\.\\d+)+"))
  groups.set(emptyList())
}

tasks {
  runIde {
    autoReloadPlugins.set(false)
  }

  patchPluginXml {
    version.set(pluginVersion)
    sinceBuild.set(properties("pluginSinceBuild"))
    untilBuild.set(properties("pluginUntilBuild"))
  }

  wrapper {
    gradleVersion = properties("gradleVersion")
    distributionType = Wrapper.DistributionType.ALL
  }

  properties("javaVersion").let {
    withType<JavaCompile> {
      sourceCompatibility = it
      targetCompatibility = it
      options.encoding = "UTF-8"
    }
    withType<KotlinCompile> {
      kotlinOptions.jvmTarget = it
    }
  }

  withType<ProcessResources> {
    filesMatching("**/*.properties") {
      filter(EscapeUnicode::class)
    }
  }
}
