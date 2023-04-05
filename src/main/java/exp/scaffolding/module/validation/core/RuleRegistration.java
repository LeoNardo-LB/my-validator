package exp.scaffolding.module.validation.core;

import java.util.Set;

/**
 * @author Leonardo
 * @creatTime 2023/4/5 13:01
 * 规则注册信息
 */
public interface RuleRegistration<T> {

    /**
     * 获取规则set
     * @return 规则set
     */
    Set<? extends Rule<T>> getRules();

}
