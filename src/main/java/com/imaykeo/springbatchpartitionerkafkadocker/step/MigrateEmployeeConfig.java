package com.imaykeo.springbatchpartitionerkafkadocker.step;

import com.imaykeo.springbatchpartitionerkafkadocker.domain.Employee;
import com.imaykeo.springbatchpartitionerkafkadocker.listner.StepItemWriterListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
public class MigrateEmployeeConfig {
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    @Qualifier("transactionManagerApp")
    private PlatformTransactionManager transactionManagerApp;
    @Value("${app.core.grisize}")
    private int grisize;
    @Value("${app.core.chunk}")
    private int chunk;

    @Bean
    public Step migrateEmployeeManager(
            @Qualifier("employeePartitioner") Partitioner employeePartitioner,
            @Qualifier("fileEmployeePartitionerReader") ItemStreamReader<Employee> fileEmployeePartitionerReader,
            @Qualifier("employeeWriter") JdbcBatchItemWriter<Employee> employeeWriter,
            TaskExecutor taskExecutor,
            StepItemWriterListener stepItemWriterListener) {
        return stepBuilderFactory
                .get("migrateEmployeeManager")
                .partitioner("migrateEmployee.manager", employeePartitioner)
                .step(migrateEmployee(fileEmployeePartitionerReader, employeeWriter, stepItemWriterListener))
                .gridSize(grisize)
                .taskExecutor(taskExecutor)
                .build();
    }

    private Step migrateEmployee(
            ItemReader<Employee> fileEmployeeReader,
            JdbcBatchItemWriter<Employee> employeeWriter,
            StepItemWriterListener stepItemWriterListener) {
        return stepBuilderFactory
                .get("migrateEmployee")
                .<Employee, Employee>chunk(chunk)
                .reader(fileEmployeeReader)
                .writer(employeeWriter)
                .listener(stepItemWriterListener)
                .transactionManager(transactionManagerApp)
                .build();
    }

}
