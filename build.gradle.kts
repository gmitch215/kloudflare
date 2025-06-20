import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig.Mode

plugins {
    kotlin("multiplatform") version "2.1.21"
    kotlin("plugin.serialization") version "2.1.21"
    //noinspection GradleDependency
    kotlin("native.cocoapods") version "2.1.21"
    id("org.jetbrains.dokka") version "2.0.0"
    id("com.android.library") version "8.10.1"
    id("com.vanniktech.maven.publish") version "0.32.0"

    `maven-publish`
    jacoco
    signing
}

val v = "0.1.0"

group = "dev.gmitch215"
version = "${if (project.hasProperty("snapshot")) "$v-SNAPSHOT" else v}${project.findProperty("suffix")?.toString()?.run { "-${this}" } ?: ""}"

val desc = "Multiplatform API for Cloudflare"
description = desc

repositories {
    google()
    mavenCentral()
    mavenLocal()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

kotlin {
    configureSourceSets()
    applyDefaultHierarchyTemplate()
    withSourcesJar()

    jvm()
    js {
        browser {
            commonWebpackConfig {
                outputFileName = "${project.name}-${project.version}.js"
            }

            webpackTask {
                if (!project.hasProperty("snapshot"))
                    mode = Mode.PRODUCTION

                output.library = "kloudflare"
            }

            testTask {
                useKarma {
                    useSourceMapSupport()

                    compilation.dependencies {
                        implementation(npm("karma-detect-browsers", "^2.3"))
                    }

                    useChromeHeadless()
                    useChromiumHeadless()
                    useFirefoxHeadless()
                }
            }
        }

        binaries.library()
        binaries.executable()
        generateTypeScriptDefinitions()
    }

    mingwX64()
    macosX64()
    macosArm64()
    linuxX64()
    linuxArm64()

    iosX64()
    iosArm64()
    androidTarget {
        publishAllLibraryVariants()
    }

    tvosArm64()
    tvosX64()
    watchosArm32()
    watchosArm64()

    sourceSets {
        val ktorVersion = "3.1.3"

        commonMain.dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.1")
            implementation("io.ktor:ktor-client-core:$ktorVersion")
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.2")
        }

        jvmMain.dependencies {
            implementation("io.ktor:ktor-client-jetty:$ktorVersion")
        }

        androidMain.dependencies {
            implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
        }

        mingwMain.dependencies {
            implementation("io.ktor:ktor-client-winhttp:$ktorVersion")
        }

        appleMain.dependencies {
            implementation("io.ktor:ktor-client-darwin:$ktorVersion")
        }

        linuxMain.dependencies {
            implementation("io.ktor:ktor-client-cio:$ktorVersion")
        }

        jsMain.dependencies {
            implementation("io.ktor:ktor-client-js:$ktorVersion")
        }
    }
}

fun KotlinMultiplatformExtension.configureSourceSets() {
    sourceSets
        .matching { it.name !in listOf("main", "test") }
        .all {
            val srcDir = if ("Test" in name) "test" else "main"
            val resourcesPrefix = if (name.endsWith("Test")) "test-" else ""
            val platform = when {
                (name.endsWith("Main") || name.endsWith("Test")) && "android" !in name -> name.dropLast(4)
                else -> name.substringBefore(name.first { it.isUpperCase() })
            }

            kotlin.srcDir("src/$platform/$srcDir")
            resources.srcDir("src/$platform/${resourcesPrefix}resources")

            languageSettings.apply {
                progressiveMode = true
            }
        }
}

android {
    compileSdk = 34
    namespace = "dev.gmitch215.kloudflare"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

tasks {
    clean {
        delete("kotlin-js-store")
    }

    named("jsBrowserProductionLibraryDistribution") {
        dependsOn("jsProductionExecutableCompileSync")
    }

    named("jsBrowserProductionWebpack") {
        dependsOn("jsProductionLibraryCompileSync")
    }

    register("jvmJacocoTestReport", JacocoReport::class) {
        dependsOn("jvmTest")

        classDirectories.setFrom(layout.buildDirectory.file("classes/kotlin/jvm/"))
        sourceDirectories.setFrom("src/commonMain/kotlin/", "src/jvmMain/kotlin/")
        executionData.setFrom(layout.buildDirectory.files("jacoco/jvmTest.exec"))

        reports {
            xml.required.set(true)
            xml.outputLocation.set(layout.buildDirectory.file("jacoco.xml"))

            html.required.set(true)
            html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
        }
    }
}

signing {
    val signingKey: String? by project
    val signingPassword: String? by project

    if (signingKey != null && signingPassword != null)
        useInMemoryPgpKeys(signingKey, signingPassword)

    sign(publishing.publications)
}

publishing {
    publications {
        filterIsInstance<MavenPublication>().forEach {
            it.apply {
                pom {
                    name = "kloudflare"

                    licenses {
                        license {
                            name = "MIT License"
                            url = "https://opensource.org/licenses/MIT"
                        }
                    }

                    developers {
                        developer {
                            id = "gmitch215"
                            name = "Gregory Mitchell"
                            email = "me@gmitch215.dev"
                        }
                    }

                    scm {
                        connection = "scm:git:git://github.com/gmitch215/kloudflare.git"
                        developerConnection = "scm:git:ssh://github.com/gmitch215/kloudflare.git"
                        url = "https://github.com/gmitch215/kloudflare"
                    }
                }
            }
        }
    }

    repositories {
        maven {
            name = "CalculusGames"
            credentials {
                username = System.getenv("NEXUS_USERNAME")
                password = System.getenv("NEXUS_PASSWORD")
            }

            val releases = "https://repo.calcugames.xyz/repository/maven-releases/"
            val snapshots = "https://repo.calcugames.xyz/repository/maven-snapshots/"
            url = uri(if (version.toString().endsWith("SNAPSHOT")) snapshots else releases)
        }

        if (!version.toString().endsWith("SNAPSHOT")) {
            maven {
                name = "GithubPackages"
                credentials {
                    username = System.getenv("GITHUB_ACTOR")
                    password = System.getenv("GITHUB_TOKEN")
                }

                url = uri("https://maven.pkg.github.com/gmitch215/kloudflare")
            }
        }
    }
}

mavenPublishing {
    coordinates(project.group.toString(), project.name, project.version.toString())

    pom {
        name.set("kloudflare")
        description.set(desc)
        url.set("https://github.com/gmitch215/kloudflare")
        inceptionYear.set("2025")

        licenses {
            license {
                name.set("MIT License")
                url.set("https://opensource.org/licenses/MIT")
            }
        }

        developers {
            developer {
                id = "gmitch215"
                name = "Gregory Mitchell"
                email = "me@gmitch215.dev"
            }
        }

        scm {
            connection = "scm:git:git://github.com/gmitch215/kloudflare.git"
            developerConnection = "scm:git:ssh://github.com/gmitch215/kloudflare.git"
            url = "https://github.com/gmitch215/kloudflare"
        }
    }

    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, true)
    signAllPublications()
}