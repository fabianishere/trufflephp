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

import com.oracle.truffle.api.dsl.ImplicitCast
import com.oracle.truffle.api.dsl.TypeSystem
import io.github.trufflephp.runtime.NullObject

/**
 * A description of the PHP type system for Truffle.
 */
@TypeSystem(value = [Int::class, Long::class, Double::class, Boolean::class, String::class, NullObject::class])
abstract class PHPTypes {
    companion object {
        @ImplicitCast
        @JvmStatic
        fun toString(value: Int): String = value.toString()

        @ImplicitCast
        @JvmStatic
        fun toLong(value: Int): Long = value.toLong()

        @ImplicitCast
        @JvmStatic
        fun toString(value: Long): String = value.toString()

        @ImplicitCast
        @JvmStatic
        fun toString(value: Double): String = value.toString()

        @ImplicitCast
        @JvmStatic
        fun toString(value: Boolean): String = value.toString()
    }
}
