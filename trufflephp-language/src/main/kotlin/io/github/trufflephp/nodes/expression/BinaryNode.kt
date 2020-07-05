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

package io.github.trufflephp.nodes.expression

import com.oracle.truffle.api.dsl.NodeChild
import com.oracle.truffle.api.dsl.NodeChildren
import com.oracle.truffle.api.frame.VirtualFrame
import io.github.trufflephp.nodes.PHPTypesGen

/**
 * Abstract base node for binary expressions.
 */
@NodeChildren(
    value = [
        NodeChild(value = "leftNode", type = ExpressionNode::class),
        NodeChild(value = "rightNode", type = ExpressionNode::class)
    ]
)
abstract class BinaryNode : ExpressionNode() {
    abstract val leftNode: ExpressionNode
    abstract val rightNode: ExpressionNode

    abstract fun executeWith(frame: VirtualFrame, left: Any, right: Any): Any

    fun executeInt(frame: VirtualFrame, left: Int, right: Int): Int {
        return PHPTypesGen.expectInteger(executeWith(frame, left, right))
    }
}
