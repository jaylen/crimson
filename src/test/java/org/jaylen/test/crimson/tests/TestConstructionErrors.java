package org.jaylen.test.crimson.tests;

import org.jaylen.crimson.faults.beans.ConstructionException;
import org.jaylen.crimson.faults.beans.MissingConstructorException;
import org.jaylen.test.crimson.sandbox.unresolved.BadAbstractBean;
import org.jaylen.test.crimson.sandbox.unresolved.BadConstructorBean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestConstructionErrors extends AbstractContextTest {

    @Test
    void testConstructorExceptions() {
        Assertions.assertThrows(MissingConstructorException.class, () -> context.get(BadConstructorBean.class));
        Assertions.assertThrows(ConstructionException.class, () -> context.get(BadAbstractBean.class));
    }

}
