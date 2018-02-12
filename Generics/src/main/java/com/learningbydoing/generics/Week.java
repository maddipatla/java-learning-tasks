package com.learningbydoing.generics;

/**
 * @author Maddipatla Chandra Babu
 * @date 07-Feb-2018
 *
 */
public enum Week implements GenericEnum {

    MONDAY((byte)1),
    TUESDAY((byte)2),
    WEDNESDAY((byte)3),
    THURSDAY((byte)4),
    FRIDAY((byte)5),
    SATURDAY((byte)6),
    SUNDAY((byte)7);

    private Byte value;

    /**
     *
     */
    private Week(Byte value) {
        this.value = value;
    }

    public Byte getValue() {
        return value;
    }

}
