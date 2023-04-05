package exp.scaffolding.module.validation.integration.spring;

import cn.hutool.core.lang.Assert;
import exp.scaffolding.module.validation.core.check.CheckedResult;
import exp.scaffolding.module.validation.group.GroupableValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Leonardo
 * @creatTime 2023/4/2 13:01
 * 分组规则校验器holder
 */
public class GroupableValidatorHolder implements ApplicationContextAware {

    /**
     * 占位作用
     */
    private static GroupableValidatorHolder groupableValidatorHolder;

    /**
     * 泛型map
     * - key: 校验的key
     * - value: group为key，校验器为value的子map
     */
    private Map<Class<?>, Map<String, GroupableValidator<?>>> validatorMap;

    public GroupableValidatorHolder(Map<String, GroupableValidator> validatorMap) {
        if (validatorMap != null) {
            groupableValidatorHolder.validatorMap = new HashMap<>(16);
            holdValidators(validatorMap);
        }
    }

    /**
     * 获取实例
     * @return 获取Validator
     */
    public static <T> GroupableValidator<T> getByClass(Class<T> vClass, String name) {
        Map<String, GroupableValidator<?>> map = groupableValidatorHolder.validatorMap.get(vClass);
        Assert.isFalse(map == null || map.size() < 1, "不存在 '{}' 类型的校验器 ", vClass.getSimpleName());
        Assert.isFalse(map.size() > 1 && StringUtils.isBlank(name), "'{}' 的校验器太多, 请指定校验器的名称", vClass.getSimpleName());
        if (StringUtils.isBlank(name)) {
            return (GroupableValidator<T>) map.values().iterator().next();
        } else {
            return (GroupableValidator<T>) map.get(name);
        }
    }

    /**
     * 获取实例
     * @return 获取Validator
     */
    public static <T> GroupableValidator<T> getByClass(Class<T> vClass) {
        return getByClass(vClass, null);
    }

    /**
     * 分组校验
     * @param target 校验目标
     * @param group  分组名称
     * @return 校验结果
     */
    public static <T> CheckedResult<T> validate(T target, String group, String validatorName) {
        GroupableValidator<T> validator = (GroupableValidator<T>) getByClass(target.getClass(), validatorName);
        Assert.notNull(validator);
        return validator.validate(target, group);
    }

    /**
     * 分组校验
     * @param target 校验目标
     * @param group  分组名称
     * @return 校验结果
     */
    public static <T> CheckedResult<T> validate(T target, String group) {
        return validate(target, group, null);
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        GroupableValidatorHolder.groupableValidatorHolder = this;
        Map<String, GroupableValidator> validatorMap = context.getBeansOfType(GroupableValidator.class);
        holdValidators(validatorMap);
    }

    /**
     * 持有验证器
     * @param validatorMap
     */
    private void holdValidators(Map<String, GroupableValidator> validatorMap) {
        if (groupableValidatorHolder.validatorMap != null) {
            return;
        }
        groupableValidatorHolder.validatorMap = new HashMap<>();
        for (Map.Entry<String, GroupableValidator> entry : validatorMap.entrySet()) {
            String validatorName = entry.getKey();
            GroupableValidator validator = entry.getValue();
            Type t = validator.getClass().getGenericSuperclass();
            Assert.isTrue(t instanceof ParameterizedType, "内部错误，泛型不属于ParameterizedType");
            Class<?> genericType = (Class<?>) ((ParameterizedType) t).getActualTypeArguments()[0];
            this.validatorMap.compute(genericType, (key, nvMap) -> {
                if (nvMap == null) {
                    nvMap = new HashMap<>(1);
                }
                nvMap.put(validatorName, validator);
                return nvMap;
            });
        }
    }

}
