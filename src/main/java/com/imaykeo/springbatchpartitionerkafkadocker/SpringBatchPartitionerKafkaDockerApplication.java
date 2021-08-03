package com.imaykeo.springbatchpartitionerkafkadocker;

import com.imaykeo.springbatchpartitionerkafkadocker.Utils.Reflections;
import com.imaykeo.springbatchpartitionerkafkadocker.domain.Employee;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringBatchPartitionerKafkaDockerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBatchPartitionerKafkaDockerApplication.class, args);
        context.close();
    }

}
