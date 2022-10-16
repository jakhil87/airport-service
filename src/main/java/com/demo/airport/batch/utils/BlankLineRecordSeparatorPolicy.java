package com.demo.airport.batch.utils;

import org.springframework.batch.item.file.separator.SimpleRecordSeparatorPolicy;

public class BlankLineRecordSeparatorPolicy extends SimpleRecordSeparatorPolicy {

    @Override
    public boolean isEndOfRecord(final String line) {
        return line.trim().length() != 0 && super.isEndOfRecord(line);
    }

    @Override
    public String postProcess(final String data) {
        if (data.trim().length() == 0) {
            return null;
        }
        return super.postProcess(data);
    }
}