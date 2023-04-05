package exp.scaffolding.module.validation.core;

import exp.scaffolding.module.validation.core.check.CheckedResult;

/**
 * @author Leonardo
 * @creatTime 2023/4/1 1:27
 * 校验器的顶级接口
 */
public interface Validator<T, E> {

    /**
     * 检查
     * @param target     目标对象
     * @param extraParam 额外参数
     * @return 检查结果
     */
    CheckedResult<T> validate(T target, E extraParam);


}
