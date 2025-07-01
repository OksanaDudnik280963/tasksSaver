package com.example.tasks.saver.dto.enums;

import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Getter
public enum ItemNames {
    IASKS_LIST(1, "Tasks list with status.", "/tasks/tasksList"),
    CREATION_TASK(2, "Creation of task.", "/tasks/new_task"),
    OPERATIONS_LIST(3, "Operation list with status.", "/operations/operationsList"),
    CREATION_OPERATION(4, "Creation of operation.", "/operations/new_operation"),
    CALCULATE_TASK_COST(5, "Calculate tasks cost.", "/operations/edit_operation");


    ItemNames(int key, String title, String pageURL) {
        Item item = new Item(key, title, pageURL);
        items.add(item);
    }

    public final List<Item> items = new ArrayList<>();

    @Getter
    @Setter
    public static class Item {

        private Integer key;
        private final String value;
        private final String pageURL;

        public Item(Integer key, String value, String pageURL) {
            this.key = key;
            this.value = value;
            this.pageURL = pageURL;
        }

    }

}
