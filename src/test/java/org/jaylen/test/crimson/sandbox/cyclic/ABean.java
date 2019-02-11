package org.jaylen.test.crimson.sandbox.cyclic;

import org.jaylen.crimson.annotations.Component;
import org.jaylen.crimson.annotations.Inject;

@Component
public class ABean {
    @Inject
    private BBean bean;
}
