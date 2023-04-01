package com.sasalf.validator.core;

import lombok.EqualsAndHashCode;

import java.util.function.Consumer;

/**
 * @author Leonardo
 * @creatTime 2023/4/1 13:33
 * 函数式校验
 */
@EqualsAndHashCode
public class FunctionalRule<T> implements Rule<T> {

    /**
     * 规则名称
     */
    private final String ruleId;

    /**
     * function
     */
    private final Consumer<T> consumer;

    /**
     * constructor
     * @param consumer function
     */
    public FunctionalRule(String ruleId, Consumer<T> consumer) {
        this.ruleId = ruleId;
        this.consumer = consumer;
    }

    @Override
    public void check(T target) {
        consumer.accept(target);
    }

    @Override
    public String ruleId() {
        return ruleId;
    }

}
