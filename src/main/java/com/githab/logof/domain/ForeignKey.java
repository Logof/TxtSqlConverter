package com.githab.logof.domain;

import com.githab.logof.config.Constants;
import lombok.Data;
import java.util.Map;

@Data
public class ForeignKey {
    private String name;
    private String childTableName;
    private String parentTableName;
    private String foreignKeyColumns;
    private String parentKeyColumns;

    public static ForeignKey of(Map<String, String> map) {
        if (map.isEmpty()) {
            return null;
        }

        ForeignKey foreignKey = new ForeignKey();
        map.forEach((key, value) -> {
            switch (key) {
                case Constants.FOREIGN_KEY_NAME:
                    foreignKey.setName(value);
                    break;
                case Constants.FOREIGN_KEY_CHILD_TABLE:
                    foreignKey.setChildTableName(value);
                    break;
                case Constants.FOREIGN_KEY_PARENT_TABLE:
                    foreignKey.setParentTableName(value);
                    break;
                case Constants.FOREIGN_KEY_FOREIGN_KEY_COLUMNS:
                    foreignKey.setForeignKeyColumns(value);
                    break;
                case Constants.FOREIGN_KEY_PARENT_KEY_COLUMNS:
                    foreignKey.setParentKeyColumns(value);
                    break;
            }
        });

        return foreignKey;
    }
}
