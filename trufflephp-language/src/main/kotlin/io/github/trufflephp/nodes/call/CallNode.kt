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

package io.github.trufflephp.nodes.call

import com.oracle.truffle.api.CompilerAsserts
import com.oracle.truffle.api.frame.VirtualFrame
import com.oracle.truffle.api.instrumentation.StandardTags
import com.oracle.truffle.api.instrumentation.Tag
import com.oracle.truffle.api.interop.InteropLibrary
import com.oracle.truffle.api.nodes.ExplodeLoop
import com.oracle.truffle.api.nodes.NodeInfo
import io.github.trufflephp.TrufflePHP
import io.github.trufflephp.nodes.expression.ExpressionNode

/**
 * A function call.
 */
@NodeInfo(language = TrufflePHP.LANGUAGE_ID, shortName = "call", description = "A function call")
class CallNode(@Child private var receiver: ExpressionNode, @Children private val args: Array<ExpressionNode>) :
    ExpressionNode() {
    @Child
    private var library: InteropLibrary = InteropLibrary.getFactory().createDispatched(3)

    @ExplodeLoop
    override fun execute(frame: VirtualFrame): Any? {
        val function = receiver.execute(frame)

        /*
         * The number of arguments is constant for one invoke node. During compilation, the loop is
         * unrolled and the execute methods of all arguments are inlined. This is triggered by the
         * ExplodeLoop annotation on the method. The compiler assertion below illustrates that the
         * array length is really constant.
         */
        CompilerAsserts.compilationConstant<Any>(args.size)

        val argumentValues = args.map { it.execute(frame) }
        return library.execute(function, argumentValues)
    }

    override fun hasTag(tag: Class<out Tag>): Boolean {
        return if (tag == StandardTags.CallTag::class.java)
            true
        else super.hasTag(tag)
    }
}
