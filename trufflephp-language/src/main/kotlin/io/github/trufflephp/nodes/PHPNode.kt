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

package io.github.trufflephp.nodes

import com.oracle.truffle.api.CompilerDirectives.CompilationFinal
import com.oracle.truffle.api.dsl.ImportStatic
import com.oracle.truffle.api.dsl.TypeSystemReference
import com.oracle.truffle.api.instrumentation.InstrumentableNode
import com.oracle.truffle.api.nodes.Node
import com.oracle.truffle.api.source.SourceSection

/**
 * The abstract base node of all PHP language nodes.
 */
@TypeSystemReference(PHPTypes::class)
@ImportStatic(PHPGuards::class)
abstract class PHPNode : Node(), InstrumentableNode {
    @CompilationFinal
    private var sourceSection: SourceSection? = null

    override fun getSourceSection(): SourceSection? = sourceSection

    fun clearSourceSection() {
        this.sourceSection = null
    }

    fun assignSourceSection(sourceSection: SourceSection) {
        this.sourceSection = sourceSection
    }

    override fun isInstrumentable(): Boolean = sourceSection != null
}
