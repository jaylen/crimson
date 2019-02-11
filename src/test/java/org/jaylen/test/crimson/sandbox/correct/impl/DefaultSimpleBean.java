package org.jaylen.test.crimson.sandbox.correct.impl;

import org.jaylen.crimson.annotations.Component;
import org.jaylen.test.crimson.sandbox.correct.service.SimpleService;

/**
 * Uses default id {@code "default"}.
 */
@Component(role = SimpleService.class)
public class DefaultSimpleBean implements SimpleService {
    @Override
    public String name() {
        return "default";
    }
}
