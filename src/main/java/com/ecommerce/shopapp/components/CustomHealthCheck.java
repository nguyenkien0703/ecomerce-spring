package com.ecommerce.shopapp.components;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

@Component
public class CustomHealthCheck  implements HealthIndicator {

    @Override
    public Health health() {
        // Implement your custom health check logic here

        try{
            String computerName = InetAddress.getLocalHost().getHostName();
            return Health.up().withDetail("computerName", computerName).build();
        }catch(Exception e){
            //throw new RuntimeException(e);
            return Health.down().withDetail("error", e.getMessage()).build();

        }
    }
}
