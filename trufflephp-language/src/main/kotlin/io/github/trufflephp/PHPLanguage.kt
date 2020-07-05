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

package io.github.trufflephp

import com.oracle.truffle.api.CallTarget
import com.oracle.truffle.api.Truffle
import com.oracle.truffle.api.TruffleLanguage
import com.oracle.truffle.api.TruffleLogger
import com.oracle.truffle.api.instrumentation.ProvidedTags
import com.oracle.truffle.api.instrumentation.StandardTags
import de.thm.mni.ii.phpparser.PHPParser
import io.github.trufflephp.nodes.PHPScriptRootNode
import io.github.trufflephp.parser.Translator
import io.github.trufflephp.runtime.PHPContext

/**
 * A GraalVM implementation of the PHP programming language.
 */
@TruffleLanguage.Registration(
    name = "PHP",
    id = TrufflePHP.LANGUAGE_ID,
    implementationName = TrufflePHP.FORMAL_NAME,
    version = TrufflePHP.VERSION,
    characterMimeTypes = [TrufflePHP.DEFAULT_MIME_TYPE],
    defaultMimeType = TrufflePHP.DEFAULT_MIME_TYPE
)
@ProvidedTags(
    value = [
        StandardTags.StatementTag::class,
        StandardTags.ExpressionTag::class,
        StandardTags.CallTag::class
    ]
)
class PHPLanguage : TruffleLanguage<PHPContext>() {
    /**
     * Construct a [PHPContext] for the specified [TruffleLanguage.Env].
     */
    override fun createContext(env: Env): PHPContext = PHPContext(this, env)

    /**
     * Process the specified [TruffleLanguage.ParsingRequest].
     */
    override fun parse(request: ParsingRequest): CallTarget {
        return when (val res = PHPParser.parse(request.source.characters.toString()) as PHPParser.Result) {
            is PHPParser.Success -> {
                val translator = Translator()
                val root = PHPScriptRootNode(this, null, translator.translate(res.script()))
                Truffle.getRuntime().createCallTarget(root)
            }
            is PHPParser.Failure -> throw IllegalArgumentException(res.fullMsg())
            else -> throw IllegalStateException()
        }
    }

    /**
     * Determine whether [target] is an object of this language.
     */
    override fun isObjectOfLanguage(target: Any?): Boolean = false

    companion object {
        /**
         * The logging instance available to this language.
         */
        private val LOGGER = TruffleLogger.getLogger(TrufflePHP.LANGUAGE_ID)
    }
}
