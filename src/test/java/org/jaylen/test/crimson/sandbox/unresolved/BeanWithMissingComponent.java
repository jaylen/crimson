package org.jaylen.test.crimson.sandbox.unresolved;

import org.jaylen.crimson.annotations.Component;
import org.jaylen.crimson.annotations.Inject;

@Component
public class BeanWithMissingComponent {

    /**
     * This will fail to be injected.
     */
    @Inject
    private MissingBean missingBean;

}
