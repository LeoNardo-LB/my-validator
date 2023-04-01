package com.sasalf.validator.core;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Leonardo
 * @creatTime 2023/4/1 1:24
 * 最终检查结果
 */
@Getter
@Setter
public class CheckedResult<T> {

    /**
     * 检查是否成功
     */
    private Boolean success;

    /**
     * 信息
     */
    private String message;

    /**
     * 检查过程的结果List
     */
    private List<CheckingResult<T>> checkingResults;

    /**
     * 通过
     * @return CheckedResult
     */
    public static <T> CheckedResult<T> success(List<CheckingResult<T>> checkingResults) {
        CheckedResult<T> result = new CheckedResult<T>();
        result.setSuccess(true);
        result.setCheckingResults(checkingResults);
        return result;
    }

    /**
     * 未通过
     * @return CheckedResult
     */
    public static <T> CheckedResult<T> fail(String message, List<CheckingResult<T>> checkingResults) {
        CheckedResult<T> result = new CheckedResult<T>();
        result.setSuccess(false);
        result.setMessage(message);
        result.setCheckingResults(checkingResults);
        return result;
    }

    /**
     * 构建
     * @return CheckedResult
     */
    public static <T> CheckedResult<T> of(Boolean success, String message, List<CheckingResult<T>> checkingResults) {
        CheckedResult<T> result = new CheckedResult<T>();
        result.setSuccess(success);
        result.setMessage(message);
        result.setCheckingResults(checkingResults);
        return result;
    }

}
