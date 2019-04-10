package com.nsa.cubric.application.configurators;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ConfigurationProperties(prefix = "app.password")
public class PasswordStrengthConfig {

    private int strength;

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    @Profile("dev")
    @Bean
    public String devStrengthConfig(){
        System.out.println("Password strength set at: " + strength);
        return "Password configuration for dev";
    }

    @Profile("prod")
    @Bean
    public String prodStrengthConfig(){
        System.out.println("Password strength set at: " + strength);
        return "Password configuration for prod";
    }
}
