package com.githab.logof.domain;

import com.githab.logof.config.Constants;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class Table {
    private String name;
    private String comment;
    private String options;
    private List<FieldProperties> fields = new ArrayList<>();
    private List<Index> indexList = new ArrayList<>();
    private List<ForeignKey> foreignKeyList = new ArrayList<>();

    public Table(String name, String comment, String options) {
        this.name = name.trim();
        this.comment = comment.trim();
        this.options = options.trim();
    }

    public String getPrimaryKeyName() {
        return indexList.stream().filter(Index::isPrimaryKey)
                        .findFirst().map(Index::getName)
                        .orElse(Constants.EMPTY_STRING);
    }
}
