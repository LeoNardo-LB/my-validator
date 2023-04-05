package exp.scaffolding.module.validation.core;

import java.util.Set;

/**
 * @author Leonardo
 * @creatTime 2023/4/2 14:32
 */
public interface RuleBuilder {

    /**
     * 构建规则
     * @return 构建完毕的规则集
     */
    Set<? extends RuleRegistration<?>> build();

}
