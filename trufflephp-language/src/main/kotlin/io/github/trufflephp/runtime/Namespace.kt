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
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.trufflephp.runtime

/**
 * A namespace where classes, interfaces, functions and constants live.
 *
 * @property name The fully qualified name of the namespace or `null` if it is the root namespace.
 */
class Namespace(val name: String?) {
    /**
     * The functions registered in this namespace.
     */
    private val functions = mutableMapOf<String, FunctionDef>()

    /**
     * Lookup a function in this namespace.
     */
    fun findFunction(name: String): FunctionDef? = functions[name]

    /**
     * Register a function in this namespace.
     *
     * @param def The [FunctionDef] to register in this namespace.
     */
    fun registerFunction(def: FunctionDef) {
        functions[def.name] = def
    }
}
