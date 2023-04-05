package exp.scaffolding.module.validation.group;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Sets;
import exp.scaffolding.module.validation.core.PredicateRule;
import exp.scaffolding.module.validation.core.Rule;
import exp.scaffolding.module.validation.core.RuleBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Leonardo
 * 分组规则构建者
 */
public class GroupableRuleBuilder<T> implements RuleBuilder {

    private static final String RANDOM_PREFIX = "_RANDOM_";

    private final List<GroupBuilder<T>> builders;

    /**
     * tClass
     */
    private final Class<T> tClass;

    /**
     * 不允许通过构造器构建
     */
    public GroupableRuleBuilder(Class<T> tClass) {
        this.builders = new ArrayList<>();
        this.tClass = tClass;
    }

    public GroupBuilder<T> builder() {
        GroupBuilder<T> builder = new GroupBuilder<>(this);
        builders.add(builder);
        return builder;
    }

    /**
     * 构建规则
     * @return 构建完毕的规则集
     */
    @Override
    public Set<GroupRulePackage<?>> build() {
        try {
            return builders.stream().map(builder -> new GroupRulePackage<>(tClass, builder.groups, builder.rules)).collect(Collectors.toSet());
        } finally {
            builders.clear();
        }
    }

    public static class GroupBuilder<T> {

        /**
         * 分组
         */
        private final Set<String> groups = new HashSet<>();

        /**
         * 规则
         */
        private final Set<Rule<T>> rules = new HashSet<>();

        /**
         * 所属的 GroupableRuleBuilder
         */
        private final GroupableRuleBuilder<T> groupableRuleBuilder;

        /**
         * @param groupableRuleBuilder builder
         */
        public GroupBuilder(GroupableRuleBuilder<T> groupableRuleBuilder) {
            this.groupableRuleBuilder = groupableRuleBuilder;
        }

        /**
         * 添加分组
         * @param groups 分组
         * @return this
         */
        public GroupBuilder<T> addGroups(String... groups) {
            return addGroups(Sets.newHashSet(groups));
        }

        /**
         * 添加分组
         * @param groups 分组
         * @return this
         */
        public GroupBuilder<T> addGroups(Collection<String> groups) {
            Assert.isTrue(this.groups.addAll(groups));
            return this;
        }

        /**
         * 添加规则内容
         * @param ruleContent 规则内容
         * @return this
         */
        public GroupBuilder<T> addRules(String ruleId, Predicate<T> ruleContent) {
            Assert.notNull(ruleContent, "规则内容不能为空");
            if (StringUtils.isBlank(ruleId)) {
                ruleId = RANDOM_PREFIX + RandomUtil.randomString(16);
            }
            rules.add(new PredicateRule<>(ruleId, ruleContent));
            return this;
        }

        /**
         * 添加规则内容
         * @param rule 规则内容
         * @return this
         */
        public GroupBuilder<T> addRules(PredicateRule<T> rule) {
            return addRules(rule.getRuleId(), rule.getPredicate());
        }

        /**
         * 添加规则内容
         * @param rules 规则内容s
         * @return this
         */
        public GroupBuilder<T> addRuleContents(Collection<PredicateRule<T>> rules) {
            for (PredicateRule<T> rule : rules) {
                this.addRules(rule);
            }
            return this;
        }

        /**
         * 添加规则内容
         * @param rules 规则内容s
         * @return this
         */
        public GroupBuilder<T> addRuleContents(PredicateRule<T>... rules) {
            return this.addRuleContents(Sets.newHashSet(rules));
        }

        /**
         * 开启下一分组
         * @return GroupBuilder
         */
        public GroupBuilder<T> nextGroups(String... groups) {
            return nextGroups(Sets.newHashSet(groups));
        }

        /**
         * 开启下一分组
         * @return GroupBuilder
         */
        public GroupBuilder<T> nextGroups(Set<String> groups) {
            GroupBuilder<T> builder = groupableRuleBuilder.builder();
            builder.groups.addAll(groups);
            return builder;
        }

        /**
         * 执行构建
         * @return GroupRulePackage
         */
        public Set<GroupRulePackage<?>> build() {
            return groupableRuleBuilder.build();
        }

    }

}
