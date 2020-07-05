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
import com.oracle.truffle.api.frame.VirtualFrame
import io.github.trufflephp.PHPLanguage

/**
 * A node which represents the reference to an unknown function node.
 */
class PHPUndefinedFunctionRootNode(
    language: PHPLanguage,
    frameDescriptor: FrameDescriptor?
) : PHPRootNode(language, frameDescriptor) {
    /**
     * Execute the undefined function which will throw a [RuntimeException].
     */
    override fun execute(frame: VirtualFrame?): Any {
        throw RuntimeException("Unknown function")
    }
}
