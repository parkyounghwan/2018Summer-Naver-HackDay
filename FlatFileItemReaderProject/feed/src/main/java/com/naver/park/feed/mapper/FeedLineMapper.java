package com.naver.park.feed.mapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;


public class FeedLineMapper<T> implements LineMapper<T> {

	private LineTokenizer tokenizer;
	private FieldSetMapper<T> fieldSetMapper;
	private String header;
	private char quoteCharacter = '\001';

	public void setHeader(String header) {
		this.header = header;
	}

	public void setQuoteCharacter(char quoteCharacter) {
		this.quoteCharacter = quoteCharacter;
	}

	public T mapLine(String line, int lineNumber) throws Exception {
		try{
			return fieldSetMapper.mapFieldSet(tokenizer.tokenize(line));
		}
		catch(Exception ex){
			ex.printStackTrace();
			throw new FlatFileParseException("Parsing error at line: " + lineNumber +	", input=[" + line + "]", ex, line, lineNumber);
		}
	}

	public void setLineTokenizer() {

		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer(DelimitedLineTokenizer.DELIMITER_TAB);
		String[] headers = StringUtils.splitPreserveAllTokens(header, "\t");

		for (int i=0; i < headers.length; i++) {
			headers[i] = headers[i].toLowerCase().trim();
		}

		lineTokenizer.setNames(headers);
		lineTokenizer.setQuoteCharacter(quoteCharacter);

		lineTokenizer.setNames(headers);
		lineTokenizer.setQuoteCharacter('\001');
		this.tokenizer = lineTokenizer;

	}

	public void setFieldSetMapper(FieldSetMapper<T> fieldSetMapper) {
		setLineTokenizer();
		this.fieldSetMapper = fieldSetMapper;
	}

}