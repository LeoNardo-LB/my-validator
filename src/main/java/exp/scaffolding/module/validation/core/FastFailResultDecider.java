package exp.scaffolding.module.validation.core;

import cn.hutool.core.lang.Assert;
import exp.scaffolding.module.validation.core.check.CheckedResult;
import exp.scaffolding.module.validation.core.check.CheckingResult;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Leonardo
 * @creatTime 2023/4/1 16:36
 * 快速失败抛出异常决定者
 */
public class FastFailResultDecider<T> implements ResultDecider<T> {

    /**
     * 检查后决定
     * @param checkingResults 检查的结果
     * @return 受检结果
     */
    @Override
    public CheckedResult<T> decision(List<CheckingResult<T>> checkingResults) {
        String errorMessage = checkingResults.stream()
                                      .filter(result -> !result.getPass()).map(CheckingResult::getFailMessageWithRuleName)
                                      .collect(Collectors.joining("], ["));
        Assert.isTrue(errorMessage.trim().length() == 0, "校验不通过，失败原因：[{}]", errorMessage);
        return CheckedResult.success(checkingResults);
    }

}
