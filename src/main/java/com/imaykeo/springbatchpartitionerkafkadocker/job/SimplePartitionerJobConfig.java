package com.imaykeo.springbatchpartitionerkafkadocker.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing
@Configuration
public class SimplePartitionerJobConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Bean
    public Job simplePartitionerJob(
            @Qualifier("splitEmployee") Step splitEmployee,
            @Qualifier("migrateEmployeeManager") Step migrateEmployeeManager
    ) {
        return jobBuilderFactory
                .get("simplePartitionerJob")
                .start(splitEmployee)
                .next(migrateEmployeeManager)
                .incrementer(new RunIdIncrementer())
                .build();
    }
}
