package org.jaylen.test.crimson.tests;

import org.jaylen.test.crimson.sandbox.common.ParamBean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestParams extends AbstractContextTest {

    @Test
    void testPrimitiveTypes() {
        final ParamBean bean = context.get(ParamBean.class);
        Assertions.assertFalse(bean.b());
        Assertions.assertEquals(bean.x(), 0b01111001);
        Assertions.assertEquals(bean.s(), 42);
        Assertions.assertEquals(bean.i(), 65000);
        Assertions.assertEquals(bean.l(), 0x7fffffff);
        Assertions.assertEquals(bean.f(), 3.14f);
        Assertions.assertEquals(bean.d(), 2.7181);
        Assertions.assertEquals(bean.c(), 'y');
    }

    @Test
    void testTypes() {
        final ParamBean bean = context.get(ParamBean.class);
        Assertions.assertEquals(bean.string(), "string");
        Assertions.assertFalse(bean.booleanValue());
        Assertions.assertEquals(bean.byteValue().byteValue(), 0b01111001);
        Assertions.assertEquals(bean.shortValue().shortValue(), 42);
        Assertions.assertEquals(bean.integerValue().intValue(), 65000);
        Assertions.assertEquals(bean.longValue().longValue(), 0x7fffffff);
        Assertions.assertEquals(bean.floatValue().floatValue(), 3.14f);
        Assertions.assertEquals(bean.doubleValue().doubleValue(), 2.7181);
        Assertions.assertEquals(bean.characterValue().charValue(), 'y');
    }

}
