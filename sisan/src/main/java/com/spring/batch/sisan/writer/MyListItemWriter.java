package com.spring.batch.sisan.writer;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.util.CollectionUtils;

public abstract class MyListItemWriter<T> implements ItemWriter<T> {

	private ExecutionContext executionContext;

	private Map<String, Integer> countMap;

	private int writeTotalCount = 0;

	@Override
	public void write(List<? extends T> items) throws Exception {
		if (CollectionUtils.isEmpty(items)) {
			return;
		}

		for (T item : items) {
			statistics(item);
			writeTargetData(item);
		}
		executionContext.put("writeTotalCount", writeTotalCount);
		executionContext.put("countMap", countMap);

	}

	private final void statistics(T item) throws IllegalArgumentException, IllegalAccessException {
		Class<? extends Object> clzz = item.getClass();
		Field[] fields = clzz.getDeclaredFields();

		for (Field field : fields) {
			field.setAccessible(true);
			if (field.get(item) == null) {
				continue;
			}
			writeTotalCount++;
			String tableName = field.getName().toUpperCase();
			String tableNameComanyCode = tableName;
			if (countMap == null) {
				countMap = new HashMap<String, Integer>();
			}
			if (!countMap.containsKey(tableNameComanyCode)) {
				countMap.put(tableNameComanyCode, 0);
			}
			Integer count = countMap.get(tableNameComanyCode);
			countMap.put(tableNameComanyCode, ++count);
		}
	}

	protected abstract void writeTargetData(T item);

	@BeforeStep
	private final void saveStepExecution(StepExecution stepExecution) {
		this.executionContext = stepExecution.getExecutionContext();
	}

}
