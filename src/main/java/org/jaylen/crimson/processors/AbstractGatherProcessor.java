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

import javax.annotation.processing.*;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public abstract class AbstractGatherProcessor extends AbstractProcessor {

    public AbstractGatherProcessor(final String fname) {
        this.fname = fname;
        this.entries = new ConcurrentSkipListSet<>();
    }

    @Override
    public synchronized void init(final ProcessingEnvironment env) {
        super.init(env);
        filer = env.getFiler();
        messager = env.getMessager();
    }

    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment env) {
        if (env.errorRaised()) {
            messager.printMessage(Diagnostic.Kind.WARNING, "skipping processing since errors have been detected");
        } else if (env.processingOver()) {
            try {
                final Writer writer = filer.createResource(StandardLocation.CLASS_OUTPUT, "", fname).openWriter();
                for (final String klass : entries) {
                    writer.append(klass).append("\n");
                }
                writer.close();
            } catch (final IOException fault) {
                throw new RuntimeException("unable to write a resource file", fault);
            }
        } else {
            annotations.forEach(annotation -> env.getElementsAnnotatedWith(annotation).forEach(element -> entries.add(element.asType().toString())));
        }
        return true;
    }

    protected Filer filer;
    protected Messager messager;
    protected Set<String> entries;
    protected String fname;

}
