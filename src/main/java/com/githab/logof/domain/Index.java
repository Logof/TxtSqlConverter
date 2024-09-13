package com.githab.logof.domain;

import com.githab.logof.enums.FieldParameterEnum;
import com.githab.logof.enums.IndexParameterEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class Index {
    private String name;
    private boolean isUnique;
    private String indexColumn;
    private boolean isPrimaryKey = false;

    public Index(List<IndexParameterEnum> fieldList, List<String> lineArray) {

        for (IndexParameterEnum field : fieldList) {
            switch (field) {
                case NAME: {
                    setName(lineArray.get(fieldList.indexOf(field)));
                    setPrimaryKey(getName().startsWith("pk_"));
                }
                break;
                case UNIQUE: setUnique(lineArray.get(fieldList.indexOf(field)).equals("X")); break;
                case INDEX_COLUMNS: setIndexColumn(lineArray.get(fieldList.indexOf(field))); break;
            }
        }
    }
}
