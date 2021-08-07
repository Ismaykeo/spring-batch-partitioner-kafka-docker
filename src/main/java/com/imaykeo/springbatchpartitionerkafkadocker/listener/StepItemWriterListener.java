package com.imaykeo.springbatchpartitionerkafkadocker.listener;

import com.imaykeo.springbatchpartitionerkafkadocker.domain.Employee;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class StepItemWriterListener implements ItemWriteListener<Employee> {
    @Autowired
    KafkaTemplate<String, Employee> KafkaJsontemplate;
    private static final String KAFKA_TOPIC = "spring-batch-partitioner";

    @Override
    public void beforeWrite(List<? extends Employee> items) {
        items.forEach(i -> KafkaJsontemplate.send(KAFKA_TOPIC, i));
    }

    @Override
    public void afterWrite(List<? extends Employee> items) {
        items.forEach(i -> KafkaJsontemplate.send(KAFKA_TOPIC, i));
    }

    @Override
    public void onWriteError(Exception exception, List<? extends Employee> items) {
        items.forEach(i -> KafkaJsontemplate.send(KAFKA_TOPIC, i));
    }
}
