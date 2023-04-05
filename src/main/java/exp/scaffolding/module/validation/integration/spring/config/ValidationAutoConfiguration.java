package exp.scaffolding.module.validation.integration.spring.config;

import exp.scaffolding.module.validation.group.GroupRuleManager;
import exp.scaffolding.module.validation.integration.spring.GroupableValidatorHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Leonardo
 * @creatTime 2023/4/2 12:37
 * 自动配置类，用于整合spring环境
 */
@Configuration
public class ValidationAutoConfiguration {

    @Bean
    public GroupRuleManager groupRuleManager(){
        return new GroupRuleManager();
    }

    @Bean
    public GroupableValidatorHolder groupableValidatorHolder(){
        return new GroupableValidatorHolder(null);
    }

}
