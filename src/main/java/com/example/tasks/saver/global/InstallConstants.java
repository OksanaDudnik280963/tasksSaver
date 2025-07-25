package com.example.tasks.saver.global;

public class InstallConstants {
    InstallConstants() {
    }
    public static final String DATABASE_NAME = "tasks";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/" + DATABASE_NAME + "/?createDatabaseIfNotExist=true";

    public static final String ERR_MSG = "errMsg";
    public static final String ERR_PAGE = "/errors/error";
    public static final String ERR_PAGE_TITLE = "Errors page.";

    public static final String TASKS_PAGE = "/tasks/tasksList";
    public static final String TASKS_PAGE_URL = "/rest/api/tasks/list";
    public static final String EDIT_TASK = "/tasks/edit_task";
    public static final String EDIT_TASK_URL = "/rest/api/tasks/edit/1";
    public static final String NEW_TASK = "/tasks/new_task";
    public static final String NEW_TASK_URL = "/rest/api/tasks/new";
    public static final String TASK_OPERATIONS_PAGE = "/tasks/task_operations";
    public static final String TASK_OPERATIONS_URL ="/rest/api/tasks/operations/list/";//+${task.id};

    public static final String NEW_OPERATION = "/operations/new_operation";
    public static final String NEW_OPERATION_URL = "/rest/api/tasks/operations/new";
    public static final String EDIT_OPERATION = "/operations/edit_operation";
    public static final String EDIT_OPERATION_URL = "/rest/api/tasks/operations/edit";
    public static final String LIST_OPERATIONS = "/operations/operationsList";
    public static final String LIST_OPERATIONS_URL = "/rest/api/tasks/operations/list";
    public static final String HOME_PAGE = "home";

    public static final String START_TASK_NAME = "New task";
    public static final String START_OPERATION_NAME = "New operation";

    public static final String ITEM1 = "Tasks list with status.";
    public static final String ITEM2 = "Creation of task.";
    public static final String ITEM3 = "Edit of task.";
    public static final String ITEM4 = "Delete task";
    public static final String ITEM5 = "Operations list with status.";
    public static final String ITEM6 = "Creation of operation.";
    public static final String ITEM7 = "Edit of operation.";
    public static final String ITEM8 = "Delete operation";
    public static final String ITEM9 = "Calculate tasks cost.";

}
