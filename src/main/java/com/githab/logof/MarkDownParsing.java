package com.githab.logof;

import com.githab.logof.config.Constants;
import com.githab.logof.domain.FieldProperties;
import com.githab.logof.domain.ForeignKey;
import com.githab.logof.domain.Index;
import com.githab.logof.domain.Table;

import com.githab.logof.enums.FieldParameterEnum;
import com.githab.logof.enums.IndexParameterEnum;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MarkDownParsing {

    public Table parsing(String markdown) {
        if (Objects.isNull(markdown) || markdown.isBlank()) {
            return null;
        }

        String[] lineArray = Arrays.stream(markdown.split(System.lineSeparator()))
                                   .map(line -> line.trim().replaceAll("^\\||\\|$", ""))
                                   .toArray((String[]::new));

        Table table = new Table(getValueFromString(lineArray[1]),
                                getValueFromString(lineArray[2]),
                                getValueFromString(lineArray[3]));

        tableEnrich(table, lineArray);

        System.out.println(table.getName());
        return table;
    }

    private void tableEnrich(Table table, String[] lineArray) {
        parsingFieldsTable(table, lineArray);
        parsingIndexesTable(table, lineArray);
        parsingForeignKeyTable(table, lineArray);
    }

    private String getValueFromString(String rawString) {
        String[] tmp = rawString.split("\\|");
        return tmp[1];
    }

    private void parsingFieldsTable(Table table, String[] lines) {
        boolean isBlock = false;

        List<FieldParameterEnum> fieldList = new ArrayList<>();

        for (String line : lines) {
            if (isBlock && line.isEmpty()) {
                break;
            }

            if (line.startsWith(Constants.HEADER_FIELDS_PART)) {
                isBlock = true;
                continue;
            }

            List<String> lineArray = new ArrayList<>();
            if (isBlock) {
                Arrays.stream(line.split("\\|")).forEach(l -> {
                    if (l.trim().startsWith("**")) {
                        fieldList.add(FieldParameterEnum.findByTitle(l.trim()));
                        return;
                    }
                    lineArray.add(l.trim());
                });
            }

            if (!fieldList.isEmpty() && !lineArray.isEmpty()) {
                FieldProperties field = new FieldProperties(fieldList, lineArray);
                table.getFields().add(field);
            }
        }
    }

    private void parsingIndexesTable(Table table, String[] lines) {
        boolean isBlock = false;

        List<IndexParameterEnum> fieldList = new ArrayList<>();

        for (String line : lines) {
            if (isBlock && line.isEmpty()) {
                break;
            }

            if (line.startsWith(Constants.HEADER_INDEXES_PART)) {
                isBlock = true;
                continue;
            }

            List<String> lineArray = new ArrayList<>();
            if (isBlock) {
                Arrays.stream(line.split("\\|")).forEach(l -> {
                    if (l.trim().startsWith("**")) {
                        fieldList.add(IndexParameterEnum.findByTitle(l.trim()));
                        return;
                    }
                    lineArray.add(l.trim());
                });
            }

            if (!fieldList.isEmpty() && !lineArray.isEmpty()) {
                Index index = new Index(fieldList, lineArray);
                table.getIndexList().add(index);
            }
        }
    }

    private void parsingForeignKeyTable(Table table, String[] lines) {
        boolean isBlock = false;

        Map<String, String> fieldList = new HashMap<>();

        for (String line : lines) {
            if (isBlock && line.isEmpty()) {
                table.getForeignKeyList().add(ForeignKey.of(fieldList));
                break;
            }

            if (line.startsWith(Constants.HEADER_FOREIGN_KEY_PART)) {
                isBlock = true;
                continue;
            }

            if (isBlock) {
                String[] data = line.split("\\|");
                fieldList.put(data[0].trim(), data[1].trim());
            }
        }
    }
}
