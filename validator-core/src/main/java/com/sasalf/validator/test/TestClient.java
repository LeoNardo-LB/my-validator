package com.sasalf.validator.test;

import com.google.common.collect.Sets;
import com.sasalf.validator.core.CheckingResult;
import com.sasalf.validator.core.FastFailResultDecider;
import com.sasalf.validator.core.GroupableFuncRule;

import java.util.HashSet;

/**
 * @author Leonardo
 * @creatTime 2023/4/1 17:33
 */
public class TestClient {

    public static void main(String[] args) {
        // 加载person规则
        HashSet<GroupableFuncRule<TestPerson>> personRules = Sets.newHashSet();
        // 规则列表
        personRules.add(new GroupableFuncRule<>("nameLength", person -> {
            // 长度大于10
            if (person.getName().length() > 10) {
                CheckingResult.doUnpass("超过10了!! 通过不了!!!!!");
            }
        }, Sets.newHashSet("g1")));
        personRules.add(new GroupableFuncRule<>("uniqueId", person -> {
            // id重复
            if (person.getId().equals("12321")) {
                CheckingResult.doUnpass("超过10了!! 通过不了!!!!!");
            }
        }, Sets.newHashSet("g1")));

        // 构建person校验器
        PersonValidator validator = new PersonValidator(new FastFailResultDecider<>(), personRules);

        // 构建测试对象
        TestPerson testPerson = new TestPerson();
        testPerson.setId("12321");
        testPerson.setName("你真他娘是一个大大大大大大大的天才");

        // 指定2的分组
        validator.validate(testPerson, "2");
        validator.validate(testPerson, "g1");
    }

}
