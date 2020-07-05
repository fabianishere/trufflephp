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
    `kotlin-convention`
    kotlin("kapt")
}

/* Build configuration */
repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib"))

    api("org.graalvm.truffle:truffle-api:20.1.0")
    kapt("org.graalvm.truffle:truffle-api:20.1.0")
    kapt("org.graalvm.truffle:truffle-dsl-processor:20.1.0")

    implementation("de.thm.mni.ii:phpparser:1.0.0")
}
