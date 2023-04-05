package exp.scaffolding.module.validation.group;

import exp.scaffolding.module.validation.core.Rule;
import exp.scaffolding.module.validation.core.RuleRegistration;
import lombok.*;

import java.util.Set;

/**
 * @author Leonardo
 * @creatTime 2023/4/5 13:02
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class GroupRulePackage<T> implements RuleRegistration<T> {

    /**
     * 目标类型
     */
    private Class<T> tClass;

    /**
     * 规则包所属的分组
     */
    private Set<String> groups;

    /**
     * 规则
     */
    private Set<Rule<T>> rules;

    /**
     * 获取规则set
     * @return 规则set
     */
    @Override
    public Set<? extends Rule<T>> getRules() {
        return rules;
    }

    /**
     * 获取分组
     * @return 分组信息
     */
    public Set<String> getGroups() {
        return groups;
    }

}
