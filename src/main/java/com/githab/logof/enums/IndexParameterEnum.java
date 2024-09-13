package com.githab.logof.enums;

import com.githab.logof.config.Constants;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IndexParameterEnum {
    NAME(Constants.INDEX_NAME),
    UNIQUE(Constants.INDEX_UNIQUE),
    INDEX_COLUMNS(Constants.INDEX_INDEX_COLUMNS);

    private final String fieldTitle;

    public static IndexParameterEnum findByTitle(String title) {
        for (IndexParameterEnum fieldParameterEnum : IndexParameterEnum.values()) {
            if (fieldParameterEnum.getFieldTitle().equalsIgnoreCase(title)) {
                return fieldParameterEnum;
            }
        }
        return null;
    }
}
