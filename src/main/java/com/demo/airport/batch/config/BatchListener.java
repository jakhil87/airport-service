package com.demo.airport.batch.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

@Slf4j
public class BatchListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {

        log.info("Job started at: {}", jobExecution.getStartTime());
        log.info("Status of the Job: {}", jobExecution.getStatus());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {

        log.info("Job Ended at: {}", jobExecution.getEndTime());
        log.info("Status of the Job: {}", jobExecution.getStatus());
    }
}
