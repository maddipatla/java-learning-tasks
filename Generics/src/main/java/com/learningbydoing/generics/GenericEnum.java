package com.learningbydoing.generics;

import com.learningbydoing.exception.EnumNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Maddipatla Chandra Babu
 * @date 12-Feb-2018
 */
public interface GenericEnum {

    static final Logger logger = LogManager.getLogger(GenericEnum.class);

    <T> T getValue();

    public static GenericEnum getInstance(Class<? extends GenericEnum> enumType,
                                          Object name) {
        for (GenericEnum constant : enumType.getEnumConstants()) {
            if (constant.getValue().equals(name))
                return constant;
        }
        logger.warn("Enum not found for : {} and value: {}", enumType, name);
        throw new EnumNotFoundException("Enum not found");
    }

}
