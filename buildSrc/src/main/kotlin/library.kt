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

/**
 * This object contains the versions of the dependencies shared by the different
 * subprojects.
 */
object Library {
    /**
     * The library for testing the projects.
     */
    val JUNIT_JUPITER = "5.6.0"

    /**
     * The library for hosting the tests.
     */
    val JUNIT_PLATFORM = "1.6.0"

    /**
     * Logging facade.
     */
    val SLF4J = "1.7.30"

    /**
     * Kotlin coroutines support
     */
    val KOTLINX_COROUTINES = "1.3.7"
}
