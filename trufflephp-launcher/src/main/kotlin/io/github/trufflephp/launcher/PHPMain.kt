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

package io.github.trufflephp.launcher

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.file
import io.github.trufflephp.TrufflePHP
import java.io.InputStreamReader
import kotlin.system.exitProcess
import org.graalvm.polyglot.Context
import org.graalvm.polyglot.PolyglotException
import org.graalvm.polyglot.Source

/**
 * Main entry point of the program.
 *
 * @param args The command line arguments passed to the program.
 */
fun main(args: Array<String>): Unit = PHPMain().main(args)

/**
 * The main command line interface for the TrufflePHP implementation.
 */
class PHPMain : CliktCommand(
    name = "php",
    help = "PHP is a widely-used general-purpose scripting nodes that is especially suited for Web development and can be embedded into HTML."
) {
    private val interactive by option(
        "-a",
        "--interactive",
        help = "Run PHP interactively. This lets you enter snippets of PHP code that directly get executed. When readline support is enabled you can edit the lines and also have history support."
    ).flag()
    private val version by option("-v", "--version", help = "Version number").flag()
    private val run by option("-r", "--run", help = "Run PHP code without using script tags '<?..?>'")
    private val file by option("-f", "--file", help = "Parse and execute file").file()

    override fun run() {
        if (version) {
            println(TrufflePHP.VERSION)
            exitProcess(0)
        }

        val source = if (file != null)
            Source.newBuilder(TrufflePHP.LANGUAGE_ID, file).build()
        else if (run != null)
            Source.newBuilder(TrufflePHP.LANGUAGE_ID, InputStreamReader(run!!.byteInputStream()), "<stdin>").build()
        else
            Source.newBuilder(TrufflePHP.LANGUAGE_ID, InputStreamReader(System.`in`), "<stdin>").build()
        exitProcess(executeSource(source))
    }

    private fun executeSource(source: Source): Int {
        val err = System.err
        val context = try {
            Context.newBuilder(TrufflePHP.LANGUAGE_ID).build()
        } catch (e: IllegalArgumentException) {
            err.println(e.message)
            return 1
        }

        try {
            val result = context.eval(source)

            if (!result.isNull) {
                println(result.toString())
            }

            return 0
        } catch (ex: PolyglotException) {
            if (ex.isInternalError) {
                // for internal errors we print the full stack trace
                ex.printStackTrace()
            } else {
                err.println(ex.message)
            }
            return 1
        } finally {
            context.close()
        }
    }
}
