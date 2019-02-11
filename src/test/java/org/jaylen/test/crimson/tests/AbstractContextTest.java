package org.jaylen.test.crimson.tests;

import org.jaylen.crimson.context.Context;
import org.junit.jupiter.api.BeforeAll;

public abstract class AbstractContextTest {

    protected static Context context;

    @BeforeAll
    static void initAll() {
        context = Context.create();
    }

}
