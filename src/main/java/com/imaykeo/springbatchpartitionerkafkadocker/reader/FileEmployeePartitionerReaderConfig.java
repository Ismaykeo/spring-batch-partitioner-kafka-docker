package com.imaykeo.springbatchpartitionerkafkadocker.reader;

import com.imaykeo.springbatchpartitionerkafkadocker.Utils.OnlyDataBase;
import com.imaykeo.springbatchpartitionerkafkadocker.Utils.Reflections;
import com.imaykeo.springbatchpartitionerkafkadocker.domain.Employee;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class FileEmployeePartitionerReaderConfig {
    @StepScope
    @Bean
    public FlatFileItemReader<Employee> fileEmployeePartitionerReader(
            @Value("#{stepExecutionContext['file']}") Resource resource
    ) {
        return new FlatFileItemReaderBuilder<Employee>().name("fileEmployeeReader")
                .resource(resource).delimited()
                .names(Reflections.getFieldsNames(Employee.class, OnlyDataBase.class))
                .addComment("--")
                .fieldSetMapper(fieldSetMapper())
                .build();
    }

    private FieldSetMapper<Employee> fieldSetMapper() {
        return fieldSet -> {
            try {
                return Reflections.extractFieldSet(fieldSet, Employee.class, OnlyDataBase.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        };
    }
}
