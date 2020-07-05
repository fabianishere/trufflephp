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

import com.oracle.truffle.api.Assumption
import com.oracle.truffle.api.RootCallTarget
import com.oracle.truffle.api.Truffle
import com.oracle.truffle.api.dsl.Cached
import com.oracle.truffle.api.dsl.Specialization
import com.oracle.truffle.api.interop.InteropLibrary
import com.oracle.truffle.api.interop.TruffleObject
import com.oracle.truffle.api.library.ExportLibrary
import com.oracle.truffle.api.library.ExportMessage
import com.oracle.truffle.api.nodes.DirectCallNode
import com.oracle.truffle.api.nodes.IndirectCallNode
import com.oracle.truffle.api.utilities.CyclicAssumption
import io.github.trufflephp.PHPLanguage
import io.github.trufflephp.nodes.PHPUndefinedFunctionRootNode

/**
 * The representation of a function in the TrufflePHP implementation.
 *
 * @param language The language instance to create the function definition for.
 * @property name The name of the function.
 */
@ExportLibrary(InteropLibrary::class)
class FunctionDef(language: PHPLanguage, val name: String) : TruffleObject {
    /**
     * Manages the assumption that the [.callTarget] is stable. We use the utility class
     * [CyclicAssumption], which automatically creates a new [Assumption] when the old
     * one gets invalidated.
     */
    val callTargetStable = CyclicAssumption(name)

    /** The current implementation of this function.  */
    var callTarget: RootCallTarget = Truffle.getRuntime().createCallTarget(PHPUndefinedFunctionRootNode(language, null))
        set(value) {
            field = value
            callTargetStable.invalidate()
        }

    /**
     * [FunctionDef] instances are always visible as executable to other languages.
     */
    @ExportMessage
    fun isExecutable(): Boolean {
        return true
    }

    /**
     * We allow languages to execute this function. We implement the interop execute message that
     * forwards to a function dispatch.
     */
    @ExportMessage
    internal class Execute {
        companion object {
            @Specialization(
                limit = "INLINE_CACHE_SIZE",
                guards = ["function.getCallTarget() == cachedTarget"],
                assumptions = ["callTargetStable"]
            )
            @JvmStatic
            fun doDirect(
                function: FunctionDef,
                arguments: Array<Any?>,
                @Cached("function.getCallTargetStable().getAssumption()") callTargetStable: Assumption,
                @Cached("function.getCallTarget()") cachedTarget: RootCallTarget,
                @Cached("create(cachedTarget)") callNode: DirectCallNode
            ): Any {

                /* Inline cache hit, we are safe to execute the cached call target. */
                return callNode.call(*arguments)
            }

            /**
             * Slow-path code for a call, used when the polymorphic inline cache exceeded its maximum
             * size specified in `INLINE_CACHE_SIZE`. Such calls are not optimized any
             * further, e.g., no method inlining is performed.
             */
            @Specialization(replaces = ["doDirect"])
            @JvmStatic
            fun doIndirect(
                function: FunctionDef,
                arguments: Array<Any?>,
                @Cached callNode: IndirectCallNode
            ): Any {
                /*
                 * SL has a quite simple call lookup: just ask the function for the current call target,
                 * and call it.
                 */
                return callNode.call(function.callTarget, arguments)
            }
        }
    }

    companion object {
        @JvmField
        val INLINE_CACHE_SIZE: Int = 2
    }
}
