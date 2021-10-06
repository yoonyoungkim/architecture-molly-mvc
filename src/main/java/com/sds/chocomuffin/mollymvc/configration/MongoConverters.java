package com.sds.chocomuffin.mollymvc.configration;

import com.sds.chocomuffin.mollymvc.infrastructure.repository.mongoconverter.QuestionReadingConverter;
import com.sds.chocomuffin.mollymvc.infrastructure.repository.mongoconverter.QuestionWritingConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.Arrays;

@Configuration
public class MongoConverters {
    @Bean
    public MongoCustomConversions mongoCustomConversions() {

        return new MongoCustomConversions(
                Arrays.asList(
                        new QuestionReadingConverter(),
                        new QuestionWritingConverter()));
    }

}
