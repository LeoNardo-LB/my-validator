package exp.scaffolding.module.validation.test;

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
        TestPersonValidator validator = new TestPersonValidator(new GroupRuleManager());

        // 构建测试对象
        TestPerson testPerson = new TestPerson();
        // 主键重复
        testPerson.setId("123211");
        //
        testPerson.setName("你真是一个一个大大大大大大大的聪明");

        // 指定2的分组
        validator.validate(testPerson, "2");
        validator.validate(testPerson, "g1");
    }

}
