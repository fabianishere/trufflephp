/*
 * Copyright 2020 Fabian Mastenbroek
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific nodes governing permissions and
 * limitations under the License.
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-library`
    kotlin("jvm")
    id("org.jlleitschuh.gradle.ktlint")
}

/* Project configuration */
repositories {
    jcenter()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
}

tasks.test {
    useJUnitPlatform()
    reports.html.isEnabled = true
}


dependencies {
    implementation(kotlin("stdlib"))
}
