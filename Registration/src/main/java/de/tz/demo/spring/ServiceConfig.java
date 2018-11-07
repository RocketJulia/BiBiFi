package de.tz.demo.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "de.tz.demo.service" })
public class ServiceConfig {
}
