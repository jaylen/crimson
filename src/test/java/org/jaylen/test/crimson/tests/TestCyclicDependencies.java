package org.jaylen.test.crimson.tests;

import org.jaylen.crimson.faults.ctx.CyclicDependencyException;
import org.jaylen.test.crimson.sandbox.cyclic.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestCyclicDependencies extends AbstractContextTest {

    @Test
    void testChickenEgg() {
        Assertions.assertThrows(CyclicDependencyException.class, () -> context.get(ChickenBean.class));
        Assertions.assertThrows(CyclicDependencyException.class, () -> context.get(EggBean.class));
    }

    @Test
    void testTransientCyclicDependency() {
        Assertions.assertThrows(CyclicDependencyException.class, () -> context.get(ABean.class));
        Assertions.assertThrows(CyclicDependencyException.class, () -> context.get(BBean.class));
        Assertions.assertThrows(CyclicDependencyException.class, () -> context.get(CBean.class));
        Assertions.assertThrows(CyclicDependencyException.class, () -> context.get(DBean.class));
    }

}
