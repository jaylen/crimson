package org.jaylen.test.crimson.sandbox.correct.impl;

import org.jaylen.crimson.annotations.Component;
import org.jaylen.crimson.annotations.Inject;
import org.jaylen.test.crimson.sandbox.common.EmptyBean;
import org.jaylen.test.crimson.sandbox.correct.service.ComplexService;

@Component(role = ComplexService.class)
public class ComplexBean implements ComplexService {

    @Inject
    private EmptyBean defaultEmptyBean;

    @Override
    public Object get() {
        return defaultEmptyBean;
    }
}
