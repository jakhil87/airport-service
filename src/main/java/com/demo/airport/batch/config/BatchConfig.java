package com.demo.airport.batch.config;

import com.demo.airport.batch.utils.BatchUtils;
import com.demo.airport.batch.utils.BlankLineRecordSeparatorPolicy;
import com.demo.airport.entity.Airport;
import com.demo.airport.entity.Country;
import com.demo.airport.entity.Runway;
import com.demo.airport.repository.AirportRepository;
import com.demo.airport.repository.CountryRepository;
import com.demo.airport.repository.RunwayRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import static com.demo.airport.batch.config.BatchConfigConstants.AIRPORT_FIELDS;
import static com.demo.airport.batch.config.BatchConfigConstants.COUNTRIES_FIELDS;
import static com.demo.airport.batch.config.BatchConfigConstants.RUNWAYS_FIELDS;
import static com.demo.airport.batch.config.BatchConfigConstants.TEXT_RECORD;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
@Slf4j
public class BatchConfig {

    private final AirportRepository airportRepository;
    private final RunwayRepository runwayRepository;
    private final CountryRepository countryRepository;
    private final StepBuilderFactory sbf;
    private final JobBuilderFactory jbf;

    @Bean
    public FlatFileItemReader<Airport> airportReader() {
        FlatFileItemReader<Airport> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("/airport-details/airports.csv"));
        reader.setLineMapper(BatchUtils.defaultLineMapper(
            Airport.class, AIRPORT_FIELDS
        ));
        reader.setRecordSeparatorPolicy(new BlankLineRecordSeparatorPolicy());
        reader.setLinesToSkip(1);
        return reader;
    }

    @Bean
    public FlatFileItemReader<Runway> runwayReader() {
        FlatFileItemReader<Runway> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("/airport-details/runways.csv"));
        reader.setLineMapper(
            BatchUtils.defaultLineMapper(
                Runway.class, RUNWAYS_FIELDS
            ));
        reader.setRecordSeparatorPolicy(new BlankLineRecordSeparatorPolicy());
        reader.setLinesToSkip(1);
        return reader;
    }

    @Bean
    public FlatFileItemReader<Country> countryReader() {
        FlatFileItemReader<Country> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("/airport-details/countries.csv"));
        reader.setLineMapper(
            BatchUtils.defaultLineMapper(
                Country.class, COUNTRIES_FIELDS
            ));
        reader.setRecordSeparatorPolicy(new BlankLineRecordSeparatorPolicy());
        reader.setLinesToSkip(1);
        return reader;
    }

    @Bean
    public ItemWriter<Airport> airportWriter() {
        return airports -> {
            log.debug(TEXT_RECORD + airports);
            airportRepository.saveAll(airports);
        };

    }

    @Bean
    public ItemWriter<Runway> runwayWriter() {
        return runways -> {
            log.debug(TEXT_RECORD + runways);
            runwayRepository.saveAll(runways);
        };
    }

    @Bean
    public ItemWriter<Country> countryWriter() {
        return countries -> {
            log.debug(TEXT_RECORD + countries);
            countryRepository.saveAll(countries);
        };
    }

    @Bean
    public JobExecutionListener listener() {
        return new BatchListener();
    }


    @Bean
    public Step stepA() {
        return sbf.get("stepA")
            .<Country, Country>chunk(10)
            .reader(countryReader())
            .writer(countryWriter())
            .build();
    }

    @Bean
    public Step stepB() {
        return sbf.get("stepB")
            .<Airport, Airport>chunk(10)
            .reader(airportReader())
            .writer(airportWriter())
            .build();
    }

    @Bean
    public Step stepC() {
        return sbf.get("stepC")
            .<Runway, Runway>chunk(10)
            .reader(runwayReader())
            .writer(runwayWriter())
            .build();
    }


    @Bean
    public Job jobA() {
        return jbf.get("jobA")
            .incrementer(new RunIdIncrementer())
            .listener(listener())
            .start(stepA())
            .next(stepB())
            .next(stepC())
            .build();
    }

}
