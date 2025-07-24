package com.example.tasks.saver.dto.enums;

import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.example.tasks.saver.global.InstallConstants.*;

@Getter
public enum ItemNames {
    IASKS_LIST(1, ITEM1, TASKS_PAGE_URL),
    CREATION_TASK(2, ITEM2, NEW_TASK_URL),
    EDITING_TASK(3, ITEM3, EDIT_TASK_URL),
    DELETE_TASK(4, ITEM4, TASKS_PAGE_URL),

    OPERATIONS_LIST(5, ITEM5, LIST_OPERATIONS_URL),
    CREATION_OPERATION(6, ITEM6, NEW_OPERATION_URL),
    EDITING_OPERATION(7, ITEM7, EDIT_OPERATION_URL),
    DELETE_OPERATION(8, ITEM8, LIST_OPERATIONS_URL),

    CALCULATE_TASK_COST(9, ITEM9, EDIT_OPERATION_URL);

    public static final List<Item> items = new ArrayList<>();

    ItemNames(int key, String value, String pageURL) {
    }


    public record Item(Integer key, String value, String pageURL) {
        public static Collection<Item> findAll() {
            items.clear();
            items.add(new Item(1, ITEM1, TASKS_PAGE_URL));
            items.add(new Item(2, ITEM2, NEW_TASK_URL));
            items.add(new Item(3, ITEM3, EDIT_TASK_URL));
            items.add(new Item(4, ITEM4, TASKS_PAGE_URL));

            items.add(new Item(5, ITEM5, LIST_OPERATIONS_URL));
            items.add(new Item(6, ITEM6, NEW_OPERATION_URL));
            items.add(new Item(7, ITEM7, EDIT_OPERATION_URL));
            items.add(new Item(8, ITEM8, LIST_OPERATIONS_URL));

            items.add(new Item(9, ITEM9, EDIT_OPERATION_URL));
            return items;
        }

    }

}
