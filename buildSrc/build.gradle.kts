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

plugins {
    `kotlin-dsl`
    idea
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}


/* Project configuration */
repositories {
    jcenter()
    maven {
        url = uri("https://plugins.gradle.org/m2/")
    }
}

dependencies {
    implementation(kotlin("gradle-plugin", version = "1.3.72"))
    implementation("org.jlleitschuh.gradle:ktlint-gradle:9.2.1")
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:0.10.1")
}
