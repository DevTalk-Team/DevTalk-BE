package com.devtalk.consultation.consultationservice.global.converter;

import com.devtalk.consultation.consultationservice.consultation.domain.consultation.ProcessMean;
import org.springframework.core.convert.converter.Converter;

public class ProcessMeanConverter implements Converter<String, ProcessMean> {
    @Override
    public ProcessMean convert(String mean) {
        return ProcessMean.ofMean(mean);
    }
}
