package exp.scaffolding.module.validation.prepare;

import exp.scaffolding.module.validation.core.ResultDecider;
import exp.scaffolding.module.validation.core.SimpleDeciderImpl;
import exp.scaffolding.module.validation.core.check.CheckingResult;
import exp.scaffolding.module.validation.group.GroupRuleManager;
import exp.scaffolding.module.validation.group.GroupableRuleBuilder;
import exp.scaffolding.module.validation.group.GroupableValidator;

/**
 * @author Leonardo
 * @creatTime 2023/4/1 17:34
 * 测试用例
 */
public class TestPersonValidator extends GroupableValidator<TestPerson> {

    /**
     * 构造方法
     * @param ruleManager
     */
    public TestPersonValidator(GroupRuleManager ruleManager) {
        super(ruleManager);
        ruleManager.register(new GroupableRuleBuilder<>(TestPerson.class).builder()
                                     .addGroups("g1", "g2")
                                     .addRules("用户名长度限制", p -> {
                                         if (p.getName().length() > 6) {
                                             CheckingResult.unpass("用户名长度不能大于6");
                                         }
                                         return true;
                                     })
                                     .addRules(null, p -> {
                                         if (p.getId().equals("12321")) {
                                             CheckingResult.unpass("主键重复");
                                         }
                                         return true;
                                     })
                                     .build());
    }

    /**
     * 获取结果决定器, 由子类实现
     * @return property value of resultDecider
     */
    @Override
    protected ResultDecider<TestPerson> getResultDecider() {
        return new SimpleDeciderImpl<>();
    }

}
