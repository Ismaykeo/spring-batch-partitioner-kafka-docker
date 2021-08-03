package com.imaykeo.springbatchpartitionerkafkadocker.reader;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.imaykeo.springbatchpartitionerkafkadocker.Utils.OnlyResource;
import com.imaykeo.springbatchpartitionerkafkadocker.Utils.Reflections;
import com.imaykeo.springbatchpartitionerkafkadocker.domain.Employee;

@Configuration
public class FileEmployeeReaderConfig {
	@Bean
	public FlatFileItemReader<Employee> fileEmployeeReader() {
		return new FlatFileItemReaderBuilder<Employee>().name("fileEmployeeReader")
				.resource(new FileSystemResource("files/employee.csv")).delimited()
				.names(Reflections.getFieldsNames(Employee.class, OnlyResource.class)).linesToSkip(1).targetType(Employee.class)
				.build();
	}
}
