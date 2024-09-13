package com.githab.logof;

import com.githab.logof.config.Constants;
import com.githab.logof.domain.Table;
import java.util.Objects;
import java.util.stream.Collectors;

public class CreateSqlScript {
    private static final String TAB = "\t";
    private static final String SPACE = " ";
    private static final String TABLE_CREATE = "create table %s (";
    private static final String TABLE_COMMENT = "comment on table %s is '%s';";
    private static final String FIELD_COMMENT = "comment on column %s.%s is '%s';";
    private static final String FOREIGN_KEY = "alter table %s add constraint %s foreign key (%s) " +
            "references %s (%s) on delete restrict on update restrict;";
    private static final String INDEX_CREATE = "create %s index %s on %s using btree (%s);";


    public static String generateSql(Table table) {
        if (Objects.isNull(table) || table.getName().isBlank() || table.getFields().isEmpty()) {
            return Constants.EMPTY_STRING;
        }

        String tableName = table.getName();

        StringBuilder sql = new StringBuilder(String.format(TABLE_CREATE, tableName)).append(System.lineSeparator());

        sql.append(table.getFields().stream()
                        .map(field -> {
                            StringBuilder row = new StringBuilder().append(TAB)
                                                                   .append(field.getName()).append(TAB)
                                                                   .append(field.getDataType());
                            if (field.isMandatory()) {
                                row.append(SPACE).append("not null");
                            }
                            if (field.isPrimary()) {
                                row.append(SPACE)
                                   .append(String.format("constraint %s primary key", table.getPrimaryKeyName()));
                            }

                            if (Objects.nonNull(field.getDefaultValue())) {
                                row.append(SPACE).append(String.format("default %s", field.getDefaultValue()));
                            }
                            return row;
                        }).collect(Collectors.joining("," + System.lineSeparator())));
        sql.append(");").append(System.lineSeparator());

        sql.append(System.lineSeparator());
        // Create comments
        if (!table.getComment().isBlank()) {
            sql.append(String.format(TABLE_COMMENT, tableName, table.getComment())).append(System.lineSeparator());
        }

        sql.append(table.getFields().stream()
                        .filter(field -> !field.getComment().isBlank())
                        .map(field -> String.format(FIELD_COMMENT, tableName, field.getName(), field.getComment()))
                        .collect(Collectors.joining(System.lineSeparator())));

        // Create Indexes
        sql.append(System.lineSeparator());
        sql.append(table.getIndexList().stream()
                        .filter(index -> !index.isPrimaryKey())
                        .map(index -> String.format(INDEX_CREATE, index.isUnique() ? "unique" : Constants.EMPTY_STRING,
                                                    index.getName(), tableName, index.getIndexColumn()))
                        .collect(Collectors.joining(System.lineSeparator())));


        // Create Foreight key
        sql.append(System.lineSeparator());
        sql.append(table.getForeignKeyList().stream().map(key -> String.format(FOREIGN_KEY, key.getName(),
                                                                               key.getChildTableName(),
                                                                               key.getForeignKeyColumns(),
                                                                               key.getParentTableName(),
                                                                               key.getParentKeyColumns()))
                        .collect(Collectors.joining(System.lineSeparator())));


        return sql.toString();
    }
}
