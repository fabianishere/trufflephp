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

package io.github.trufflephp.parser

import de.thm.mni.ii.phpparser.ast.Basic
import de.thm.mni.ii.phpparser.ast.Expressions
import de.thm.mni.ii.phpparser.ast.Statements
import io.github.trufflephp.nodes.asLiteral
import io.github.trufflephp.nodes.asStatement
import io.github.trufflephp.nodes.compound
import io.github.trufflephp.nodes.expression.ExpressionNode
import io.github.trufflephp.nodes.intrinsic.EchoIntrinsicNode
import io.github.trufflephp.nodes.intrinsic.ExitIntrinsicNode
import io.github.trufflephp.nodes.intrinsic.PrintIntrinsicNode
import io.github.trufflephp.nodes.literal.LiteralNode
import io.github.trufflephp.nodes.statement.StatementNode

/**
 * A class to translate the abstract syntax tree (AST) of a PHP program into a Truffle execution tree.
 */
class Translator {
    fun translate(script: Basic.Script): StatementNode {
        val body: MutableList<StatementNode> = mutableListOf(
            EchoIntrinsicNode(arrayOf(script.leadingText().text().asLiteral())).asStatement()
        )
        for (statement in script.s()) {
            body += translate(statement)
        }
        return compound(*body.toTypedArray())
    }

    private fun translate(statement: Statements.Statement): StatementNode = when (statement) {
        is Statements.CompoundStmnt -> {
            val body = mutableListOf<StatementNode>()
            for (inner in statement.stmnts()) {
                body += translate(inner)
            }
            compound(*body.toTypedArray())
        }
        is Statements.ExpressionStmnt -> translate(statement.exp()).asStatement()
        else -> TODO(statement.toString())
    }

    private fun translate(expression: Expressions.Expression): ExpressionNode = when (expression) {
        is Expressions.EchoIntrinsic -> {
            val body = mutableListOf<ExpressionNode>()
            for (inner in expression.exps()) {
                body += translate(inner)
            }
            EchoIntrinsicNode(body.toTypedArray())
        }
        is Expressions.PrintIntrinsic -> PrintIntrinsicNode(translate(expression.exp()))
        is Expressions.ExitIntrinsic -> {
            val body: Expressions.Expression? = expression.exp().getOrElse { null }
            ExitIntrinsicNode(body?.let { translate(it) })
        }
        is Expressions.SimpleAssignmentExp -> translate(expression)
        is Basic.Literal -> translate(expression)
        else -> TODO(expression.toString())
    }

    private fun translate(literal: Basic.Literal): LiteralNode = when (literal) {
        is Basic.SQStringLiteral -> literal.sequence().asLiteral()
        is Basic.DecimalLiteral -> literal.value().toLong().asLiteral()
        is Basic.OctalLiteral -> literal.value().toLong(8).asLiteral()
        is Basic.BinaryLiteral -> literal.value().toLong(2).asLiteral()
        is Basic.HexadecimalLiteral -> literal.value().toLong(16).asLiteral()
        is Basic.FloatingLiteral -> {
            val builder = StringBuilder()
            builder.append(literal.digits())
            builder.append('.')
            builder.append(literal.fracDigits())

            val exponent = literal.exponent()
            if (exponent.isDefined) {
                builder.append('e')
                if (exponent.get()._1 == false) {
                    builder.append('-')
                }
                builder.append(literal.exponent().get()._2)
            }
            builder.toString().toDouble().asLiteral()
        }
        else -> TODO(literal.toString())
    }

    private fun translate(assignment: Expressions.SimpleAssignmentExp): ExpressionNode {
        TODO()
    }
}
