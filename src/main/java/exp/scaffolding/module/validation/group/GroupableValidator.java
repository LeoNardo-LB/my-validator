package exp.scaffolding.module.validation.group;

import cn.hutool.core.lang.Assert;
import exp.scaffolding.module.validation.core.AbstractRuleValidator;
import exp.scaffolding.module.validation.core.Rule;

import java.util.Collection;

/**
 * @author Leonardo
 * @creatTime 2023/4/1 15:13
 * 可分组的校验器
 */
public abstract class GroupableValidator<T> extends AbstractRuleValidator<T, String> {

    /**
     * 分组规则的map
     */
    private final GroupRuleManager ruleManager;

    /**
     * 构造方法
     */
    public GroupableValidator(GroupRuleManager ruleManager) {
        Assert.notNull(ruleManager, "规则管理器不能为空");
        this.ruleManager = ruleManager;
    }

    /**
     * 获取规则
     * @param groupId 额外参数
     * @return 获取规则
     */
    @Override
    protected Collection<? extends Rule<T>> getRules(String groupId) {
        Assert.notBlank(groupId, "获取规则的key不能为空");
        return (Collection<? extends Rule<T>>) ruleManager.getRules(super.tClass, groupId);
    }

}
