package exp.scaffolding.module.validation.core;

import java.util.Set;

/**
 * @author Leonardo
 * @creatTime 2023/4/2 17:19
 * @param <T> 规则注册信息
 * @param <E> 删除/获取规则的key
 */
public interface RuleManager<T extends RuleRegistration<?>, E> {

    /**
     * 添加规则
     * @param ruleRegistration 规则
     */
    void register(Set<T> ruleRegistration);

    /**
     * 删除规则
     * @param key 规则id
     */
    void unregister(E key);

    /**
     * 获取rule
     * @param key 获取规则的key
     * @return rule
     */
    Set<? extends Rule<?>> getRules(E key);

}
