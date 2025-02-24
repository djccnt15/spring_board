package com.djccnt15.spring_board.config.db;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"com.djccnt15.spring_board.db.repository"})
public class JpaConfig {
}
