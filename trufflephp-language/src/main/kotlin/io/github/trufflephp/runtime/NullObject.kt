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

import com.oracle.truffle.api.interop.InteropLibrary
import com.oracle.truffle.api.interop.TruffleObject
import com.oracle.truffle.api.library.ExportLibrary
import com.oracle.truffle.api.library.ExportMessage

/**
 * The PHP type for a `null` (i.e., undefined) value.
 */
@ExportLibrary(value = InteropLibrary::class)
object NullObject : TruffleObject {
    /**
     * This method is, e.g., called when using the `null` value in a string concatenation. So
     * changing it has an effect on PHP programs.
     */
    override fun toString(): String {
        return "NULL"
    }

    /**
     * [NullObject] values are interpreted as null values by other languages.
     */
    @ExportMessage
    fun isNull(): Boolean = true
}
