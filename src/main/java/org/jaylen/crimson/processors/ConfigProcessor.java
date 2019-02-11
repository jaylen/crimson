/*
 * Copyright 2019 Yury Khrustalev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jaylen.crimson.processors;

import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;

import static org.jaylen.crimson.context.Context.CONTEXT_CONFIG;

@SupportedAnnotationTypes("org.jaylen.crimson.annotations.Config")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ConfigProcessor extends AbstractGatherProcessor {
    public ConfigProcessor() {
        super(CONTEXT_CONFIG);
    }
}
