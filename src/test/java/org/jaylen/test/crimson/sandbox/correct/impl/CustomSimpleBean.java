package org.jaylen.test.crimson.sandbox.correct.impl;

import org.jaylen.crimson.annotations.Component;
import org.jaylen.test.crimson.sandbox.correct.service.SimpleService;

/**
 * Uses id {@code "custom"}.
 */
@Component(id = "custom", role = SimpleService.class)
public class CustomSimpleBean implements SimpleService {
    @Override
    public String name() {
        return "custom";
    }
}
