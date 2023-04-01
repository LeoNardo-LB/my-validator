package com.sasalf.validator.test;


import com.sasalf.validator.core.GroupableFuncRule;
import com.sasalf.validator.core.GroupableValidator;
import com.sasalf.validator.core.ResultDecider;

import java.util.Collection;

/**
 * @author Leonardo
 * @creatTime 2023/4/1 17:34
 */
public class PersonValidator extends GroupableValidator<TestPerson> {

    /**
     * 构造方法，需要传入 resultDecider
     * @param resultDecider  结果决策者
     * @param groupableRules 可分组的规则
     */
    public PersonValidator(ResultDecider<TestPerson> resultDecider, Collection<GroupableFuncRule<TestPerson>> groupableRules) {
        super(resultDecider, groupableRules);
    }

}
