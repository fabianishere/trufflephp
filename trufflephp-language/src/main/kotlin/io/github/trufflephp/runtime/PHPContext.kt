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

import com.oracle.truffle.api.TruffleLanguage
import io.github.trufflephp.PHPLanguage

/**
 * The shared context of a PHP program.
 *
 * @param language The [PHPLanguage] instance the context belongs to.
 * @param env The environment in which the context exists.
 */
class PHPContext(private val language: PHPLanguage, private val env: TruffleLanguage.Env) {
    /**
     * The registry to keep track of the namespaces in the PHP program.
     */
    val namespaces: NamespaceRegistry = NamespaceRegistry()
}
