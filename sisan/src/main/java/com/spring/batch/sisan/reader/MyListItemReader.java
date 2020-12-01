package com.spring.batch.sisan.reader;

import java.util.List;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

public abstract class MyListItemReader<T> implements ItemReader<T> {
	
	protected ExecutionContext stepExecutionContext;
	
	protected ExecutionContext jobExecutionContext;
	
	private List<T> list;
	
	private boolean exhausted;
	
	@Nullable
	@Override
	public T read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (!exhausted) {
			list = getTargetData();
			exhausted = true;
			stepExecutionContext.put("readTotalCount", list.size());
		}
		
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		// TODO Auto-generated method stub
		return list.remove(0);
	}
	
	protected abstract List<T> getTargetData();
	
	@BeforeStep
	private final void saveStepExecution(StepExecution stepExecution) {
		this.stepExecutionContext = stepExecution.getExecutionContext();
		this.jobExecutionContext = stepExecution.getJobExecution().getExecutionContext();
	}

}
