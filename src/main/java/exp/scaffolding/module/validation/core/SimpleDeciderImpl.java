package exp.scaffolding.module.validation.core;

import exp.scaffolding.module.validation.core.check.CheckedResult;
import exp.scaffolding.module.validation.core.check.CheckingResult;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Leonardo
 * @creatTime 2023/4/1 16:43
 * 简单的决定器实现
 */
public class SimpleDeciderImpl implements ResultDecider<String> {

    /**
     * 检查结果决定器
     * @param checkingResults 检查的结果
     * @return 最终受检结果
     */
    @Override
    public CheckedResult<String> decision(List<CheckingResult<String>> checkingResults) {
        String errorMessage = checkingResults.stream()
                                      .filter(result -> !result.getPass())
                                      .map(CheckingResult::getFailMessageWithRuleName)
                                      .collect(Collectors.joining("; "));
        if (StringUtils.isBlank(errorMessage)) {
            return CheckedResult.success(checkingResults);
        }
        return CheckedResult.fail(errorMessage, checkingResults);
    }

}
