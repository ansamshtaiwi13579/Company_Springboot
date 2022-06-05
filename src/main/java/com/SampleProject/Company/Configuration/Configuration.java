package com.SampleProject.Company.Configuration;

import com.aerospike.client.AerospikeClient;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {
    @Bean
    public AerospikeClient client(){
        return new AerospikeClient("localhost", 3000);
    }
}
