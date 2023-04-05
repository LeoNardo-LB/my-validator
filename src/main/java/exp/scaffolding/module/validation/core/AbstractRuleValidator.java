package exp.scaffolding.module.validation.core;

import cn.hutool.core.lang.Assert;
import exp.scaffolding.module.validation.core.check.CheckState;
import exp.scaffolding.module.validation.core.check.CheckedResult;
import exp.scaffolding.module.validation.core.check.CheckingException;
import exp.scaffolding.module.validation.core.check.CheckingResult;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author Leonardo
 * @creatTime 2023/4/1 1:29
 * 基于规则的校验器
 */
public abstract class AbstractRuleValidator<T, E> implements Validator<T, E> {

    /**
     * 目标class
     */
    protected final Class<T> tClass;

    /**
     * 构建方法，指定校验的类型
     */
    public AbstractRuleValidator() {
        Type t = this.getClass().getGenericSuperclass();
        Assert.isTrue(t instanceof ParameterizedType, "子类实现指定的泛型必须为ParameterizedType类型");
        this.tClass = (Class<T>) ((ParameterizedType) t).getActualTypeArguments()[0];
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
        Optional.ofNullable(getRules(extraParam)).ifPresent(rules -> decision.set(getResultDecider().decision(
                rules.stream().map(rule -> {
                    try {
                        Assert.isTrue(rule.check(target), () -> {
                            throw new CheckingException();
                        });
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
    protected abstract Collection<? extends Rule<T>> getRules(E extraParam);

    /**
     * 获取结果决定器, 由子类实现
     * @return property value of resultDecider
     */
    protected ResultDecider<T> getResultDecider(){
        return new FastFailResultDecider<>();
    }

}
