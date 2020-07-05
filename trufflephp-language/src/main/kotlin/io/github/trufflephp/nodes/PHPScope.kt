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

package io.github.trufflephp.nodes

import com.oracle.truffle.api.frame.FrameDescriptor
import com.oracle.truffle.api.frame.FrameSlot
import com.oracle.truffle.api.frame.FrameSlotKind

/**
 * This class manages the variable scope of the PHP language.
 *
 * @property parent The parent scope of this scope or `null` if this is a top-scope.
 */
class PHPScope(private val parent: PHPScope? = null) {
    /**
     * The [FrameDescriptor] to keep track of the [FrameSlot]s.
     */
    val frameDescriptor: FrameDescriptor = parent?.frameDescriptor ?: FrameDescriptor()

    /**
     * Find an identifier with the specified [name] in the scope hierarchy.
     */
    fun find(name: String): FrameSlot? {
        return null
    }

    /**
     * Define a variable with the specified name in the
     */
    fun define(name: String, kind: FrameSlotKind): FrameSlot {
        TODO()
    }
}
