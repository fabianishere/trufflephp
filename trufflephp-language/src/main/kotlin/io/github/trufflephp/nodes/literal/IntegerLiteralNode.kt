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

package io.github.trufflephp.nodes.literal

import com.oracle.truffle.api.frame.VirtualFrame
import com.oracle.truffle.api.nodes.NodeInfo

/**
 * Constant literal for a [Int] value.
 */
@NodeInfo(shortName = "literal")
class IntegerLiteralNode(private val value: Int) : LiteralNode() {
    override fun execute(frame: VirtualFrame): Int = value

    override fun executeInt(frame: VirtualFrame): Int = value
}
