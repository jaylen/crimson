package org.jaylen.test.crimson.sandbox.correct.service;

public interface SimpleService {
    default String name() {
        return "not implemented";
    }
}
