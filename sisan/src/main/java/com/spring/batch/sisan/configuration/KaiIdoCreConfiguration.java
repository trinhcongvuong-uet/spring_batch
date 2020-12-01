package com.spring.batch.sisan.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.spring.batch.sisan.dto.KaiIdoDto;
import com.spring.batch.sisan.entities.BsIdEntity;
import com.spring.batch.sisan.process.KaiIdoCreProcessor;
import com.spring.batch.sisan.reader.KaiIdoCreReader;
import com.spring.batch.sisan.writer.KaiIdoCreWriter;

@Configuration
@EnableBatchProcessing
public class KaiIdoCreConfiguration {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private KaiIdoCreReader kaiIdoCreReader;
	
	@Autowired
	private KaiIdoCreProcessor kaiIdoCreProcessor;
	
	@Autowired
	private KaiIdoCreWriter kaiIdoCreWriter;
	
	@Bean
	public Job importKaiIdoCreJob() {
		return jobBuilderFactory.get("importKaiIdoCreJob")
				.flow(importKaiIdoCreStep())
				.end().build();
	}
	
	@Bean
	public Step importKaiIdoCreStep() {
		return stepBuilderFactory.get("importKaiIdoCreStep")
				.<KaiIdoDto, BsIdEntity>chunk(1)
				.reader(kaiIdoCreReader)
				.processor(kaiIdoCreProcessor)
				.writer(kaiIdoCreWriter)
				.build();
	}
}
