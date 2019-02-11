package org.jaylen.test.crimson.sandbox.common;

import org.jaylen.crimson.annotations.Component;
import org.jaylen.crimson.annotations.Param;

@Component
public class ParamBean {

    @Param(ref = "param.bean.string")
    private String string;

    @Param(ref = "param.bean.boolean")
    private boolean b;

    @Param(ref = "param.bean.byte")
    private byte x;

    @Param(ref = "param.bean.short")
    private short s;

    @Param(ref = "param.bean.int")
    private int i;

    @Param(ref = "param.bean.long")
    private long l;

    @Param(ref = "param.bean.float")
    private float f;

    @Param(ref = "param.bean.double")
    private double d;

    @Param(ref = "param.bean.char")
    private char c;

    @Param(ref = "param.bean.boolean")
    private Boolean booleanValue;

    @Param(ref = "param.bean.byte")
    private Byte byteValue;

    @Param(ref = "param.bean.short")
    private Short shortValue;

    @Param(ref = "param.bean.int")
    private Integer integerValue;

    @Param(ref = "param.bean.long")
    private Long longValue;

    @Param(ref = "param.bean.float")
    private Float floatValue;

    @Param(ref = "param.bean.double")
    private Double doubleValue;

    @Param(ref = "param.bean.char")
    private Character characterValue;

    public String string() {
        return string;
    }

    public boolean b() {
        return b;
    }

    public byte x() {
        return x;
    }

    public short s() {
        return s;
    }

    public int i() {
        return i;
    }

    public long l() {
        return l;
    }

    public float f() {
        return f;
    }

    public double d() {
        return d;
    }

    public char c() {
        return c;
    }

    public Boolean booleanValue() {
        return booleanValue;
    }

    public Byte byteValue() {
        return byteValue;
    }

    public Short shortValue() {
        return shortValue;
    }

    public Integer integerValue() {
        return integerValue;
    }

    public Long longValue() {
        return longValue;
    }

    public Float floatValue() {
        return floatValue;
    }

    public Double doubleValue() {
        return doubleValue;
    }

    public Character characterValue() {
        return characterValue;
    }

}
