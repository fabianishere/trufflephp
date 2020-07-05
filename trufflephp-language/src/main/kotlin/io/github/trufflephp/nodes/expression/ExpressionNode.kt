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

import com.oracle.truffle.api.frame.VirtualFrame
import com.oracle.truffle.api.instrumentation.GenerateWrapper
import com.oracle.truffle.api.instrumentation.InstrumentableNode
import com.oracle.truffle.api.instrumentation.ProbeNode
import com.oracle.truffle.api.instrumentation.StandardTags
import com.oracle.truffle.api.instrumentation.Tag
import com.oracle.truffle.api.nodes.NodeInfo
import io.github.trufflephp.TrufflePHP
import io.github.trufflephp.nodes.PHPNode
import io.github.trufflephp.nodes.PHPTypesGen

/**
 * Abstract base class of all PHP expression nodes.
 */
@GenerateWrapper
@NodeInfo(language = TrufflePHP.LANGUAGE_ID, description = "The abstract base node for all PHP expressions")
abstract class ExpressionNode : PHPNode() {
    abstract fun execute(frame: VirtualFrame): Any?

    open fun executeInt(frame: VirtualFrame): Int = PHPTypesGen.expectInteger(execute(frame))

    open fun executeLong(frame: VirtualFrame): Long = PHPTypesGen.expectLong(execute(frame))

    open fun executeDouble(frame: VirtualFrame): Double = PHPTypesGen.expectDouble(execute(frame))

    open fun executeString(frame: VirtualFrame): String = PHPTypesGen.expectString(execute(frame))

    open fun executeBoolean(frame: VirtualFrame): Boolean = PHPTypesGen.expectBoolean(execute(frame))

    override fun hasTag(tag: Class<out Tag>): Boolean = when (tag) {
        StandardTags.ExpressionTag::class.java -> true
        else -> super.hasTag(tag)
    }

    override fun createWrapper(probe: ProbeNode): InstrumentableNode.WrapperNode {
        return ExpressionNodeWrapper(this, probe)
    }
}
