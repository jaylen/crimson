package org.jaylen.test.crimson.tests;

import org.jaylen.crimson.faults.ctx.MissingComponentException;
import org.jaylen.test.crimson.sandbox.unresolved.BeanWithMissingComponent;
import org.jaylen.test.crimson.sandbox.unresolved.BeanWithMissingId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestUnresolvedComponents extends AbstractContextTest {

    @Test
    void testMissingComponent() {
        Assertions.assertThrows(MissingComponentException.class, () -> context.get(BeanWithMissingComponent.class));
    }

    @Test
    void testMissingId() {
        Assertions.assertThrows(MissingComponentException.class, () -> context.get(BeanWithMissingId.class));
    }

}
