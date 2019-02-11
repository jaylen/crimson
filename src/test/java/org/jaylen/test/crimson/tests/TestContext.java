package org.jaylen.test.crimson.tests;

import org.jaylen.test.crimson.sandbox.common.EmptyBean;
import org.jaylen.test.crimson.sandbox.correct.MainBean;
import org.jaylen.test.crimson.sandbox.correct.service.ComplexService;
import org.jaylen.test.crimson.sandbox.correct.service.SimpleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestContext extends AbstractContextTest {

    @Test
    void testLeafBean() {
        final EmptyBean bean = context.get(EmptyBean.class);
        Assertions.assertNotNull(bean);
    }

    @Test
    void testImmediateDependencies() {
        final MainBean bean = context.get(MainBean.class);
        final SimpleService d = bean.defaultSimpleService();
        final SimpleService c = bean.customSimpleService();
        Assertions.assertNotNull(d);
        Assertions.assertNotNull(c);
        Assertions.assertEquals(d.name(), "default");
        Assertions.assertEquals(c.name(), "custom");
    }

    @Test
    void testInjectionById() {
        final MainBean bean = context.get(MainBean.class);
        final ComplexService x = bean.defaultComplexService();
        Assertions.assertNotNull(x);
        Assertions.assertNotNull(x.get());
    }

    @Test
    void testParams() {
        final MainBean bean = context.get(MainBean.class);
        Assertions.assertEquals(bean.param(), "hello config");
        Assertions.assertEquals(bean.port(), 12345);
        Assertions.assertTrue(bean.flag());
        Assertions.assertEquals(bean.weight(), 0.8);
    }

}
