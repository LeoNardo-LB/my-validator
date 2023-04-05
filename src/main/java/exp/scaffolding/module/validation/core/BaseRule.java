package exp.scaffolding.module.validation.core;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author Leonardo
 * @creatTime 2023/4/1 13:33
 * 函数式校验
 */
public abstract class BaseRule<T> implements Rule<T> {

    /**
     * 规则id
     */
    private final String ruleId;

    /**
     * constructor
     */
    public BaseRule(String ruleId) {
        this.ruleId = ruleId;
    }

    @Override
    public String getRuleId() {
        return ruleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BaseRule<?> baseRule = (BaseRule<?>) o;

        return new EqualsBuilder().append(ruleId, baseRule.ruleId).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(ruleId).toHashCode();
    }

}
