package com.devtalk.consultation.consultationservice.consultation.domain.consultation;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

//@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ProductProceedType {
    F2F, NF2F, ALL , NULL

//    PHONE("01", "phone"),
//    VIDEO("02", "video"),
//    OFFLINE("03", "offline");
//
//    private final String code;
//    private final String mean;
//
//    public static ProcessMean ofCode(String code) {
//        if (code != null) {
//            for (ProcessMean pm : ProcessMean.values()) {
//                if (pm.code.equals(code)) {
//                    return pm;
//                }
//            }
//        }
//        throw new InvalidInputException(INVALID_INPUT_VALUE);
//    }
//
//    public static ProcessMean ofMean(String means) {
//        if (means != null) {
//            for (ProcessMean pm : ProcessMean.values()) {
//                if (pm.mean.equals(means)) {
//                    return pm;
//                }
//            }
//        }
//        throw new InvalidInputException(INVALID_INPUT_VALUE);
//    }

}
