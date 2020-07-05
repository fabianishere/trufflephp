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
 * A class to keep track of the namespaces declared in a PHP program.
 */
class NamespaceRegistry {
    /**
     * The internal mapping of namespaces.
     */
    private val mapping = mutableMapOf<String, Namespace>()

    /**
     * The root namespace.
     */
    val root = Namespace(null)

    /**
     * Obtain the namespace with the specified fully qualified name. If the namespace does not exist yet, it will be
     * created.
     *
     * @param name The fully qualified namespace to obtain.
     */
    operator fun get(name: String): Namespace = mapping.getOrPut(name) { Namespace(name) }

    /**
     * Resolve a namespace relative to another namespace.
     */
    fun resolve(namespace: Namespace, name: String): Namespace =
        if (name.isEmpty())
            namespace
        else
            get(namespace.name + name)
}
