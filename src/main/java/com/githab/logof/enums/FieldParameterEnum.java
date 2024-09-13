package com.githab.logof.enums;

import com.githab.logof.config.Constants;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FieldParameterEnum {
    NAME(Constants.FIELD_NAME),
    DATA_TYPE(Constants.FIELD_DATA_TYPE),
    PRIMARY(Constants.FIELD_PRIMARY),
    MANDATORY(Constants.FIELD_MANDATORY),
    FOREIGN_KEY(Constants.FIELD_FOREIGN_KEY),
    DEFAULT_VALUE(Constants.FIELD_DEFAULT_VALUE),
    COMMENT(Constants.FIELD_COMMENT);

    private final String fieldTitle;

    public static FieldParameterEnum findByTitle(String title) {
        for (FieldParameterEnum fieldParameterEnum : FieldParameterEnum.values()) {
            if (fieldParameterEnum.getFieldTitle().equalsIgnoreCase(title)) {
                return fieldParameterEnum;
            }
        }
        return null;
    }
}
