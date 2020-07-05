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

import com.oracle.truffle.api.frame.FrameDescriptor
import com.oracle.truffle.api.nodes.NodeInfo
import com.oracle.truffle.api.nodes.RootNode
import io.github.trufflephp.PHPLanguage
import io.github.trufflephp.TrufflePHP

/**
 * The root of all execution trees of the TrufflePHP implementation.
 *
 * It is a Truffle requirement that the tree root extends [RootNode].
 */
@NodeInfo(language = TrufflePHP.LANGUAGE_ID, description = "The root of all PHP execution trees")
abstract class PHPRootNode(language: PHPLanguage, frameDescriptor: FrameDescriptor? = null) :
    RootNode(language, frameDescriptor)
