package exp.scaffolding.module.validation.group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * @author Leonardo
 * @creatTime 2023/4/5 15:21
 */
@Getter
@Setter
@AllArgsConstructor
public class GroupKey {

    private Set<Class<?>> classes;

    private Set<String> groups;

    private Set<String> ruleIds;

}
