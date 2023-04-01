package com.sasalf.validator.core;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Leonardo
 * @creatTime 2023/4/1 1:23
 * 检查过程中结果
 */
@Getter
@Setter
public class CheckingResult<T> {

    /**
     * 检查是否通过
     */
    private Boolean pass;

    /**
     * 对应的规则
     */
    private Rule<T> theRule;

    /**
     * 状态
     */
    private CheckState state;

    /**
     * 检查异常
     */
    private CheckingException ex;

    /**
     * 通过
     * @param state CheckState
     * @return CheckingResult
     */
    public static <T> CheckingResult<T> wrapperPass(Rule<T> rule, CheckState state) {
        CheckingResult<T> result = new CheckingResult<T>();
        result.setTheRule(rule);
        result.setPass(true);
        result.setState(state);
        return result;
    }

    /**
     * 执行为通过的动作
     * @param message 未通过信息
     */
    public static void doUnpass(String message) {
        throw new CheckingException(message);
    }

    /**
     * 包装未通过的信息
     * @param state CheckState
     * @return CheckingResult
     */
    public static <T> CheckingResult<T> wrapperUnpass(Rule<T> rule, CheckState state, CheckingException ex) {
        CheckingResult<T> result = new CheckingResult<T>();
        result.setTheRule(rule);
        result.setPass(false);
        result.setState(state);
        result.setEx(ex);
        return result;
    }

    // /**
    //  * 构建
    //  * @param state CheckState
    //  * @return CheckingResult
    //  */
    // public static <T> CheckingResult<T> of(Rule<T> rule, Boolean pass, CheckState state, CheckingException ex) {
    //     CheckingResult<T> result = new CheckingResult<T>();
    //     result.setTheRule(rule);
    //     result.setPass(pass);
    //     result.setState(state);
    //     result.setEx(ex);
    //     return result;
    // }

    /**
     * 获取失败信息
     * @return 失败信息
     */
    public String getFailMessage() {
        return ex.getMessage();
    }

    /**
     * 获取带有规则名称的失败信息
     * @return 带有规则名称的失败信息, 返回格式为: "规则 '%s' 校验失败: %s"
     */
    public String getFailMessageWithRuleName() {
        return String.format("规则 '%s' 校验失败: %s", theRule.ruleId(), ex.getMessage());
    }

}
