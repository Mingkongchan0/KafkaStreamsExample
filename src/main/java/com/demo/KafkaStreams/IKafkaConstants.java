package com.demo.KafkaStreams;

public interface IKafkaConstants {
    String KAFKA_BROKERS = "localhost:9092";
    Integer MESSAGE_COUNT=100;
    String PRODUCER_CLIENT_ID="KStreamProducer";
    String CONSUMER_CLIENT_ID="KStreamConsumer";
    String TOPIC_NAME="Number";
    String AUTO_COMMIT_FALSE="false";
    String GROUP_ID_CONFIG="consumerGroup1";
    Integer MAX_NO_MESSAGE_FOUND_COUNT=100;
    String OFFSET_RESET_LATEST="latest";
    String OFFSET_RESET_EARLIEST="earliest";
    Integer MAX_POLL_RECORDS=1;
    Integer TIMEOUT_MS=1000;
}
