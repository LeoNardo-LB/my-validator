package exp.scaffolding.module.validation.test;

import com.google.common.collect.Sets;
import exp.scaffolding.module.validation.group.GroupKey;
import exp.scaffolding.module.validation.group.GroupRuleManager;
import exp.scaffolding.module.validation.prepare.TestPerson;
import exp.scaffolding.module.validation.prepare.TestPersonValidator;

/**
 * @author Leonardo
 * @creatTime 2023/4/1 17:33
 */
public class TestClient {

    public static void main(String[] args) {

        // 构建person校验器
        GroupRuleManager ruleManager = new GroupRuleManager();
        TestPersonValidator validator = new TestPersonValidator(ruleManager);

        // 构建测试对象
        TestPerson testPerson = new TestPerson();
        // 主键重复
        testPerson.setId("12321");
        //
        testPerson.setName("你真是一个一个大大大大大大大的聪明");

        // 指定2的分组
        System.out.println(validator.validate(testPerson, "g1"));
        System.out.println(validator.validate(testPerson, "g2"));

        ruleManager.unregister(new GroupKey(null, null, Sets.newHashSet("用户名长度限制")));
        System.out.println(validator.validate(testPerson, "g1"));
        System.out.println(validator.validate(testPerson, "g2"));
    }

}
