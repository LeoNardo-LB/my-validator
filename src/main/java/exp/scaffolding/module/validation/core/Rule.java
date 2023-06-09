package exp.scaffolding.module.validation.core;

import exp.scaffolding.module.validation.core.check.CheckState;
import exp.scaffolding.module.validation.core.check.CheckingException;
import exp.scaffolding.module.validation.core.check.CheckingResult;

/**
 * @author Leonardo
 * @creatTime 2023/4/1 1:25
 * 规则
 */
public interface Rule<T> {

    /**
     * 具体检查的动作
     * @param target 目标对象
     *               - 校验过程中不通过:
     *                  方式1: 调用 {@link CheckingResult#unpass(String)}
     *                  方式2: 抛出 @link {@link CheckingException} 异常
     *                  方式3: 返回false(不推荐, 建议使用上面两种, 携带更多错误信息)
     *               - 如果通过了，调用 @{@link CheckingResult#wrapperPass(Rule, CheckState)} 来包装
     *               - 如果未通过，调用 @{@link CheckingResult#wrapperUnpass(Rule, CheckState, CheckingException)} 来包装
     * @return 检查成功返回true, 失败返回false不推荐
     * @throws CheckingException 受检过程中的异常
     */
    boolean check(T target) throws CheckingException;

    /**
     * 获取规则id
     * @return 规则id
     */
    String getRuleId();

    /**
     * 获取描述
     * @return
     */
    default String description(){
        return getRuleId();
    }

}
