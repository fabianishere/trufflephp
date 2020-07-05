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

package io.github.trufflephp.nodes

import io.github.trufflephp.nodes.expression.ExpressionNode
import io.github.trufflephp.nodes.literal.BooleanLiteralNode
import io.github.trufflephp.nodes.literal.DoubleLiteralNode
import io.github.trufflephp.nodes.literal.IntegerLiteralNode
import io.github.trufflephp.nodes.literal.LongLiteralNode
import io.github.trufflephp.nodes.literal.StringLiteralNode
import io.github.trufflephp.nodes.statement.CompoundNode
import io.github.trufflephp.nodes.statement.ExpressionStatementNode
import io.github.trufflephp.nodes.statement.StatementNode

/**
 * Construct a boolean literal with the specified value.
 */
fun Boolean.asLiteral(): BooleanLiteralNode = BooleanLiteralNode(this)

/**
 * Construct a string literal with the specified value.
 */
fun String.asLiteral(): StringLiteralNode = StringLiteralNode(this)

/**
 * Construct an integer literal with the specified value.
 */
fun Int.asLiteral(): IntegerLiteralNode = IntegerLiteralNode(this)

/**
 * Construct a long integer literal with the specified value.
 */
fun Long.asLiteral(): LongLiteralNode = LongLiteralNode(this)

/**
 * Construct a double literal with the specified value.
 */
fun Double.asLiteral(): DoubleLiteralNode = DoubleLiteralNode(this)

/**
 * Convert the specified expression into a statement.
 */
fun ExpressionNode.asStatement(): StatementNode = ExpressionStatementNode(this)

/**
 * Chain the specified statements into a single compound statement.
 */
fun compound(vararg statements: StatementNode): StatementNode {
    return when (statements.size) {
        0 -> CompoundNode(emptyArray())
        1 -> statements[0]
        else -> CompoundNode(statements)
    }
}
