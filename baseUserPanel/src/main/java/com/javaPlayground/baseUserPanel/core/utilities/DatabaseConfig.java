package com.javaPlayground.baseUserPanel.core.utilities;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.javaPlayground.baseUserPanel.dataAccess")
public class DatabaseConfig {
    // Additional database configurations can be added here if needed
}
