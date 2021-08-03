package com.imaykeo.springbatchpartitionerkafkadocker.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.imaykeo.springbatchpartitionerkafkadocker.domain.Employee;

@Configuration
public class SplitEmployeeConfig {
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	@Value("${app.core.chunk}")
	private int chunk;
	@Bean 
	public Step splitEmployee(@Qualifier("fileEmployeeReader") FlatFileItemReader<Employee> fileEmployeeReader, MultiResourceItemWriter<Employee> employeeWriter) {
		return stepBuilderFactory
				.get("splitEmployee")
				.<Employee, Employee>chunk(chunk)
				.reader(fileEmployeeReader)
				.writer(employeeWriter)
				.build();
				
	}
}
