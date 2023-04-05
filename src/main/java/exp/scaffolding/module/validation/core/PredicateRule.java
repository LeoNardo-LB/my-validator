package exp.scaffolding.module.validation.core;

import cn.hutool.core.lang.Assert;
import lombok.Getter;

import java.util.function.Predicate;

/**
 * @author Leonardo
 * @creatTime 2023/4/3 21:00
 */
public class PredicateRule<T> extends BaseRule<T> {

    /**
     * predicate
     */
    @Getter
    private final Predicate<T> predicate;

    /**
     * constructor
     * @param ruleId    规则id
     * @param predicate function
     */
    public PredicateRule(String ruleId, Predicate<T> predicate) {
        super(ruleId);
        Assert.notNull(predicate, "断言规则不能为空");
        this.predicate = predicate;
    }

    /**
     * 断言检查
     * @param target 目标对象
     * @return true成功；false失败
     */
    @Override
    public boolean check(T target) {
        return predicate.test(target);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

}
