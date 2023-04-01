package com.sasalf.validator.core;

import java.util.List;

/**
 * @author Leonardo
 * @creatTime 2023/4/1 1:31
 * 检查结果决定者
 */
public interface ResultDecider<T> {

    /**
     * 检查结果决定器
     * @param checkingResults 检查的结果
     * @return 最终受检结果
     */
    CheckedResult<T> decision(List<CheckingResult<T>> checkingResults);

}
