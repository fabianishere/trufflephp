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
import com.oracle.truffle.api.nodes.NodeInfo
import io.github.trufflephp.PHPLanguage
import io.github.trufflephp.TrufflePHP
import io.github.trufflephp.nodes.statement.StatementNode
import io.github.trufflephp.runtime.NullObject

/**
 * The [PHPRootNode] for a PHP script.
 */
@NodeInfo(language = TrufflePHP.LANGUAGE_ID, description = "The root node of a PHP script")
class PHPScriptRootNode(
    language: PHPLanguage,
    frameDescriptor: FrameDescriptor?,
    @Child private var body: StatementNode
) : PHPRootNode(language, frameDescriptor) {
    override fun execute(frame: VirtualFrame): Any? {
        body.executeVoid(frame)
        return NullObject
    }
}
