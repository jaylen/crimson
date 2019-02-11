package org.jaylen.test.crimson.sandbox.correct;

import org.jaylen.crimson.annotations.Component;
import org.jaylen.crimson.annotations.Inject;
import org.jaylen.crimson.annotations.Param;
import org.jaylen.test.crimson.sandbox.correct.service.ComplexService;
import org.jaylen.test.crimson.sandbox.correct.service.SimpleService;

/**
 * Main component which will require a number
 * of dependencies to be injected.
 */
@Component
public class MainBean {

    /**
     * This will be initialised with a singleton instance
     * with id {@code "default"} of type which implements
     * {@link SimpleService}.
     */
    @Inject
    private SimpleService defaultSimpleService;

    /**
     * This will be initialised with a singleton instance
     * with id {@code "custom"} of type which implements
     * {@link SimpleService}.
     */
    @Inject(id = "custom")
    private SimpleService customSimpleService;

    @Inject
    private ComplexService defaultComplexService;

    @Param(ref = "main.beam.param")
    private String param;

    @Param(ref = "main.beam.port")
    private int port;

    @Param(ref = "main.beam.flag")
    private boolean flag;

    @Param(ref = "main.beam.weight")
    private double weight;

    public String param() {
        return param;
    }

    public int port() {
        return port;
    }

    public boolean flag() {
        return flag;
    }

    public double weight() {
        return weight;
    }

    public SimpleService defaultSimpleService() {
        return defaultSimpleService;
    }

    public SimpleService customSimpleService() {
        return customSimpleService;
    }

    public ComplexService defaultComplexService() {
        return defaultComplexService;
    }

}
