package com.githab.logof.domain;

import com.githab.logof.enums.FieldParameterEnum;
import lombok.Data;
import java.util.List;

@Data
public class FieldProperties {
    private String name;
    private String dataType;
    private boolean isPrimary;
    private boolean isMandatory;
    private boolean isForeignKey;
    private String defaultValue;
    private String comment;

    public FieldProperties(List<FieldParameterEnum> fieldList, List<String> lineArray) {

        for (FieldParameterEnum field : fieldList) {
            switch (field) {
                case NAME: setName(lineArray.get(fieldList.indexOf(field))); break;
                case DATA_TYPE: setDataType(lineArray.get(fieldList.indexOf(field))); break;
                case PRIMARY: setPrimary(lineArray.get(fieldList.indexOf(field)).equals("X")); break;
                case MANDATORY: setMandatory(lineArray.get(fieldList.indexOf(field)).equals("X")); break;
                case FOREIGN_KEY: setForeignKey(lineArray.get(fieldList.indexOf(field)).equals("X")); break;
                case DEFAULT_VALUE: setDefaultValue(lineArray.get(fieldList.indexOf(field))); break;
                case COMMENT: setComment(lineArray.get(fieldList.indexOf(field))); break;
            }
        }
    }
}
