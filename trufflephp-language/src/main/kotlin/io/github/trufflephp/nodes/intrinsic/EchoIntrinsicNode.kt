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
 * See the License for the specific nodes governing permissions and
 * limitations under the License.
 */

package io.github.trufflephp.nodes.intrinsic

import com.oracle.truffle.api.CompilerAsserts
import com.oracle.truffle.api.frame.VirtualFrame
import com.oracle.truffle.api.nodes.ExplodeLoop
import com.oracle.truffle.api.nodes.Node
import io.github.trufflephp.nodes.PHPTypesGen
import io.github.trufflephp.nodes.expression.ExpressionNode
import io.github.trufflephp.runtime.NullObject

/**
 * After converting each of its expressions' values to strings, if necessary, echo concatenates them in order given,
 * and writes the resulting string to STDOUT. Unlike print, it does not produce a result.
 */
class EchoIntrinsicNode(@Node.Children private val body: Array<ExpressionNode>) : ExpressionNode() {
    @ExplodeLoop
    override fun execute(frame: VirtualFrame): Any? {
        CompilerAsserts.compilationConstant<Any>(body.size)

        val builder = StringBuilder()
        for (expr in body) {
            builder.append(PHPTypesGen.asImplicitString(expr.execute(frame)))
        }

        print(builder.toString())
        return NullObject
    }
}
