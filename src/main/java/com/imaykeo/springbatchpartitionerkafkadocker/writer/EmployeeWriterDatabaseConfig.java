package com.imaykeo.springbatchpartitionerkafkadocker.writer;

import com.imaykeo.springbatchpartitionerkafkadocker.Utils.OnlyDataBase;
import com.imaykeo.springbatchpartitionerkafkadocker.Utils.Reflections;
import com.imaykeo.springbatchpartitionerkafkadocker.domain.Employee;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class EmployeeWriterDatabaseConfig {
    @Bean
    public JdbcBatchItemWriter<Employee> employeeWriter(@Qualifier("appDataSource") DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Employee>()
                .dataSource(dataSource)
                .sql(Reflections.getInsertClass(Employee.class, OnlyDataBase.class))
                .itemPreparedStatementSetter(itemPreparedStatementSetter())
                .build();
    }

    private ItemPreparedStatementSetter<Employee> itemPreparedStatementSetter() {
        return (employee, ps) -> {
            Reflections.getFillPreparedStament(employee, OnlyDataBase.class, ps);
        };
    }


}
