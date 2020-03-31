package com.demo.KafkaStreams;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Log4j2
public class KafkaConsumerConfig {

    private static Consumer<String, Integer> consumer() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, IKafkaConstants.KAFKA_BROKERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, IKafkaConstants.TOPIC_NAME);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class.getName());
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 8000);

        final Consumer<String, Integer> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singleton(IKafkaConstants.TOPIC_NAME));

        return consumer;
    }

    @Bean
    void consume() {
        int noMsg = 0;
        Duration duration = Duration.ofSeconds(10);
        try {
            while (true) {
                ConsumerRecords<String, Integer> records = consumer().poll(duration);
                for (ConsumerRecord<String, Integer> record : records)
                    if (record.value() % 2 == 0) {
                        log.info("Record value {} is even(offset = {})", record.value(), record.offset());
                        noMsg++;
                    } else {
                        log.info("Record value {} is odd(offset = {})", record.value(), record.offset());
                        noMsg++;
                    }
                    if(noMsg > IKafkaConstants.MAX_NO_MESSAGE_FOUND_COUNT)
                    {
                        break;
                    }
            }
        }finally{
            consumer().commitAsync();
            consumer().close();
        }

    }
}
        /*for(int i =0; i < IKafkaConstants.MESSAGE_COUNT; i++){
            ConsumerRecords<String, Integer> consumerRecord = createConsumer.poll(duration);
            //consumerRecord.forEach(record -> log.info("Record key = {}, value = {}, partition = {}, offset = {}", record.key(), record.value(), record.partition(), record.offset()));

        }*/


        /*while(true){
            ConsumerRecords<String, Integer> consumerRecord = createConsumer.poll(duration);
            if (consumerRecord.count() == 0){
                noMsg++;
                if (noMsg > 100)
                    break;

            }
            consumerRecord.forEach(record ->{
                log.debug("Record key = {}, value = {}, offset = {}, partition = {}" + record.key(), record.value(), record.offset(), record.partition());
            });
            //Commits offset of the record to Kafka broker
            consumer().commitAsync();

        }*/