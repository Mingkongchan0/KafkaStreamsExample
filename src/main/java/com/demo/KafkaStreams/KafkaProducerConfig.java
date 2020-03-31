package com.demo.KafkaStreams;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
@Log4j2
public class KafkaProducerConfig {
    public Producer<String, Integer> producer()
    {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, IKafkaConstants.KAFKA_BROKERS);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, IKafkaConstants.PRODUCER_CLIENT_ID);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        return new KafkaProducer<>(props);
    }
    @Bean
    public void produce(){}

    {
        final Producer<String, Integer> createProducer = producer();
        for (int i = 0; i < IKafkaConstants.MESSAGE_COUNT; i++) {
            RandomDataGenerator rdg = new RandomDataGenerator();
            Integer r = rdg.nextInt(1, 100);
            ProducerRecord<String, Integer> record = new ProducerRecord<>(IKafkaConstants.TOPIC_NAME, r);
            createProducer.send(record);

            RecordMetadata metadata = null;
            try {
                metadata = createProducer.send(record).get();
            } catch (InterruptedException | ExecutionException e) {
                log.error(e);
            }
            assert metadata != null;
            log.info("sent record(key={} value={}) " +
                            "meta(partition={}, offset={})\n", record.key(), record.value(), metadata.partition(),
                    metadata.offset());

        }
        createProducer.flush();
        createProducer.close();
        try {
            Thread.sleep(5500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
