package com.demo.airport.batch.utils;

import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;

import java.util.stream.IntStream;

import static org.springframework.batch.item.file.transform.DelimitedLineTokenizer.DELIMITER_COMMA;

public class BatchUtils {

    private BatchUtils() {
        // Private constructor for static class
    }
    public static <T> DefaultLineMapper<T> defaultLineMapper(Class<T> clazz, String... fieldNames){
        DefaultLineMapper<T> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(delimitedLineTokenizer(fieldNames));
        lineMapper.setFieldSetMapper(fieldSetMapper(clazz));
        return lineMapper;
    }
    private static DelimitedLineTokenizer delimitedLineTokenizer(String ... names) {
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(DELIMITER_COMMA);
        tokenizer.setNames(names);
        tokenizer.setIncludedFields(IntStream.range(0, names.length).toArray());
        return tokenizer;
    }

    private static <T> BeanWrapperFieldSetMapper<T> fieldSetMapper(Class<T> clazz) {
        BeanWrapperFieldSetMapper<T> mapper =  new BeanWrapperFieldSetMapper<>();
        mapper.setTargetType(clazz);
        return mapper;
    }
}
