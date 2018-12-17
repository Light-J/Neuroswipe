package com.nsa.cubric.application.configurators;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public class DBConfiguration {

    private String url;
    private String username;
    private String password;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Profile("dev")
    @Bean
    public String devDatabaseConnection(){
        System.out.println("DB connection for dev - MySQL");
        System.out.println(url);
        System.out.println(username);
        return "Database connection for dev - local MySQL";
    }


    @Profile("prod")
    @Bean
    public String prodDatabaseConnection(){
        System.out.println("DB connection for prod - MySQL");
        System.out.println(url);
        System.out.println(username);
        return "Database connection for dev - remote MySQL";
    }
}
