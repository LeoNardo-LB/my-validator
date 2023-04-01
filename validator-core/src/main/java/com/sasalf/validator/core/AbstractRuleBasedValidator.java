package com.sasalf.validator.core;

import cn.hutool.core.lang.Assert;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author Leonardo
 * @creatTime 2023/4/1 1:29
 * 基于规则的校验器
 */
public abstract class AbstractRuleBasedValidator<T, E> implements Validator<T, E> {

    /**
     * 检查结果决定者
     */
    private final ResultDecider<T> resultDecider;

    /**
     * 构造方法，需要传入 resultDecider
     * @param resultDecider 结果决策者
     */
    protected AbstractRuleBasedValidator(ResultDecider<T> resultDecider) {
        Assert.notNull(resultDecider, "resultDecider 不能为null");
        this.resultDecider = resultDecider;
    }

    /**
     * 验证
     * @param target 目标对象
     * @return 验证结果
     */
    @Override
    public CheckedResult<T> validate(T target, E extraParam) {
        AtomicReference<CheckedResult<T>> decision = new AtomicReference<>(CheckedResult.success(null));
        // 如果getRules可以获取到rule，则执行判断，否则放行
        Optional.ofNullable(getRules(extraParam)).ifPresent(rules -> decision.set(resultDecider.decision(
                rules.stream().map(rule -> {
                    try {
                        rule.check(target);
                        return CheckingResult.wrapperPass(rule, CheckState.CHECKING);
                    } catch (CheckingException e) {
                        return CheckingResult.wrapperUnpass(rule, CheckState.CHECKING, e);
                    }
                }).map(result -> {
                    try {
                        return postProcess(result);
                    } catch (CheckingException e) {
                        return CheckingResult.wrapperUnpass(result.getTheRule(), CheckState.POST_PROCESSING, e);
                    }
                }).collect(Collectors.toList())))
        );
        return decision.get();
    }

    /**
     * 检查结果后置处理
     * @param result 检查结果
     * @return 检查结果后置处理的结果
     */
    protected CheckingResult<T> postProcess(CheckingResult<T> result) {
        return result;
    }

    /**
     * 获取规则
     * @param extraParam 额外参数
     * @return 获取规则
     */
    protected abstract Set<? extends Rule<T>> getRules(E extraParam);

}
