package com.sasalf.validator.core;

/**
 * @author Leonardo
 * @creatTime 2023/4/1 1:25
 * 规则
 */
public interface Rule<T> {

    /**
     * 具体检查的动作
     * @param target 目标对象
     * - 如果通过了，调用 @{@link CheckingResult#wrapperPass(Rule, CheckState)} 来包装
     * - 如果未通过，调用 @{@link CheckingResult#wrapperUnpass(Rule, CheckState, CheckingException)} 来包装
     * @throws CheckingException 受检过程中的异常
     */
    void check(T target) throws CheckingException;

    /**
     * 获取规则id
     * @return 规则id
     */
    String ruleId();

}
