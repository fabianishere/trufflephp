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

package io.github.trufflephp.nodes.intrinsic

import com.oracle.truffle.api.frame.VirtualFrame
import io.github.trufflephp.nodes.PHPTypesGen
import io.github.trufflephp.nodes.control.ExitException
import io.github.trufflephp.nodes.expression.ExpressionNode

/**
 * An intrinsic that outputs a message and terminates the current script.
 */
class ExitIntrinsicNode(@Child private var body: ExpressionNode?) : ExpressionNode() {
    override fun execute(frame: VirtualFrame): Any? {
        val res = body?.execute(frame)
        val (message, code) = when (res) {
            null -> null to 0
            is Int -> null to res
            else -> PHPTypesGen.asImplicitString(res) to 0
        }
        throw ExitException(message, code)
    }
}
