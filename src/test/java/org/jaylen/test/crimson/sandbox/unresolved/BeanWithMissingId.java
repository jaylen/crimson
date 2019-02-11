package org.jaylen.test.crimson.sandbox.unresolved;

import org.jaylen.crimson.annotations.Component;
import org.jaylen.crimson.annotations.Inject;
import org.jaylen.test.crimson.sandbox.common.EmptyBean;

@Component
public class BeanWithMissingId {

    @Inject(id = "missing")
    private EmptyBean emptyBean;

}
