package exp.scaffolding.module.validation.group;

import cn.hutool.core.lang.Assert;
import exp.scaffolding.module.validation.core.Rule;
import exp.scaffolding.module.validation.core.RuleManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Leonardo
 * @creatTime 2023/4/5 14:34
 */
@Slf4j
public class GroupRuleManager implements RuleManager<GroupRulePackage<?>, GroupKey> {

    /**
     * key: 对应的class类
     * value: groupId 与 断言规则组成的子map
     */
    private final Map<Class<?>, Map<String, Set<? extends Rule<?>>>> groupRuleMap = new ConcurrentHashMap<>();

    /**
     * 添加规则
     * @param groupRulePackage 规则包的set
     */
    @Override
    public void register(Set<GroupRulePackage<?>> groupRulePackage) {
        for (GroupRulePackage<?> rulePackage : groupRulePackage) {
            if (rulePackage.getTClass() == null) {
                log.warn("注册失败, 分组规则的类为空: {}", rulePackage);
                continue;
            }
            Class<?> tClass = rulePackage.getTClass();
            Set<String> groups = rulePackage.getGroups();
            Set<? extends Rule<?>> rules = rulePackage.getRules();
            groupRuleMap.compute(tClass, (key, map) -> {
                if (map == null) {
                    map = new ConcurrentHashMap<>(groups.size());
                }
                for (String group : groups) {
                    map.put(group, rules);
                }
                return map;
            });
        }
    }

    /**
     * 删除规则
     * @param key 删除规则的key
     */
    @Override
    public void unregister(GroupKey key) {
        Assert.notEmpty(key.getRuleIds(), "取消注册的ruleId集合不能为空");
        Set<String> ruleIds = key.getRuleIds();
        filterByClassesAndGroups(key.getClasses(), key.getGroups()).forEach(sets -> {
            for (Set<? extends Rule<?>> set : sets) {
                set.removeIf(rule -> ruleIds.contains(rule.getRuleId()));
            }
        });
    }

    /**
     * 获取rule
     * @param key 获取规则的key
     * @return rule
     */
    @Override
    public Set<? extends Rule<?>> getRules(GroupKey key) {
        Set<String> ruleIds = key.getRuleIds();
        return filterByClassesAndGroups(key.getClasses(), key.getGroups()).flatMap(sets -> sets.stream().flatMap(rules -> {
            if (CollectionUtils.isEmpty(ruleIds)) {
                return rules.stream();
            }
            return rules.stream().filter(rule -> ruleIds.contains(rule.getRuleId()));
        })).collect(Collectors.toSet());
    }

    /**
     * 获取rule
     * @param tClass 获取规则的class
     * @param group  获取规则的group
     * @return rule
     */
    public Set<? extends Rule<?>> getRules(Class<?> tClass, String group) {
        Map<String, Set<? extends Rule<?>>> map = groupRuleMap.get(tClass);
        if (map == null) {
            return null;
        }
        return map.get(group);
    }

    /**
     * 通过类名s与groups来筛选stream
     * @param classes 类
     * @param groups  分组
     * @return stream
     */
    private Stream<Collection<? extends Set<? extends Rule<?>>>> filterByClassesAndGroups(Set<Class<?>> classes, Set<String> groups) {
        return groupRuleMap.entrySet().stream().filter(entry -> {
            if (CollectionUtils.isEmpty(classes)) {
                return true;
            }
            return classes.contains(entry.getClass());
        }).map(Map.Entry::getValue).map(groupMap -> {
            if (CollectionUtils.isEmpty(groups)) {
                return groupMap.values();
            }
            return groups.stream().map(groupMap::get).collect(Collectors.toSet());
        });
    }

}
