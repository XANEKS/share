package com.example.diplom;

public class ModelTable2 {
    String TableName, TableNum, TableEmail;

    public ModelTable2(String tableName, String tableNum, String tableEmail) {
        TableName = tableName;
        TableNum = tableNum;
        TableEmail = tableEmail;
    }

    public String getTableName() {
        return TableName;
    }

    public void setTableName(String tableName) {
        TableName = tableName;
    }

    public String getTableNum() {
        return TableNum;
    }

    public void setTableNum(String tableNum) {
        TableNum = tableNum;
    }

    public String getTableEmail() {
        return TableEmail;
    }

    public void setTableEmail(String tableEmail) {
        TableEmail = tableEmail;
    }
}
