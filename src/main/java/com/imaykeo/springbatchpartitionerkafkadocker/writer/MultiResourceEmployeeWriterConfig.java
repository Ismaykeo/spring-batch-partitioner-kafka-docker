package com.imaykeo.springbatchpartitionerkafkadocker.writer;

import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.MultiResourceItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.builder.MultiResourceItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.imaykeo.springbatchpartitionerkafkadocker.Utils.OnlyResource;
import com.imaykeo.springbatchpartitionerkafkadocker.Utils.Reflections;
import com.imaykeo.springbatchpartitionerkafkadocker.domain.Employee;

@Configuration
public class MultiResourceEmployeeWriterConfig {
	@Bean
	public MultiResourceItemWriter<Employee> multiResourceEmployeeWriter() {
		return new MultiResourceItemWriterBuilder<Employee>().name("multiResourceEmployeeWriter")
				.delegate(employeeWriter()).resource(new FileSystemResource("files/employee-tmp"))
				.itemCountLimitPerResource(10000).build();

	}
	private FlatFileItemWriter<Employee> employeeWriter(){
		
		return new FlatFileItemWriterBuilder<Employee>()
				.name("employeeWriter")
				.delimited()
				.names(Reflections.getFieldsNames(Employee.class, OnlyResource.class))
				.build();
	}
}
