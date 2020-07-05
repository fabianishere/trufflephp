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

package io.github.trufflephp.nodes.statement

import com.oracle.truffle.api.frame.VirtualFrame
import com.oracle.truffle.api.instrumentation.GenerateWrapper
import com.oracle.truffle.api.instrumentation.InstrumentableNode
import com.oracle.truffle.api.instrumentation.ProbeNode
import com.oracle.truffle.api.instrumentation.StandardTags
import com.oracle.truffle.api.instrumentation.Tag
import com.oracle.truffle.api.nodes.NodeInfo
import io.github.trufflephp.TrufflePHP
import io.github.trufflephp.nodes.PHPNode

/**
 * Abstract base class of statements in the PHP language.
 */
@GenerateWrapper
@NodeInfo(language = TrufflePHP.LANGUAGE_ID, description = "The abstract base node for all PHP statements")
abstract class StatementNode : PHPNode() {
    abstract fun executeVoid(frame: VirtualFrame)

    override fun hasTag(tag: Class<out Tag>): Boolean = when (tag) {
        StandardTags.StatementTag::class.java -> true
        else -> super.hasTag(tag)
    }

    override fun createWrapper(probe: ProbeNode): InstrumentableNode.WrapperNode {
        return StatementNodeWrapper(this, probe)
    }
}
