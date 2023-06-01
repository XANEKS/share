package com.example.diplom;

public class ModelTable {
    String TableName, TableCon, TablePrice;

    public ModelTable(String tableName, String tableCon, String tablePrice) {
        TableName = tableName;
        TableCon = tableCon;
        TablePrice = tablePrice;
    }

    public String getTableName() {
        return TableName;
    }

    public void setTableName(String tableName) {
        TableName = tableName;
    }

    public String getTableCon() {
        return TableCon;
    }

    public void setTableCon(String tableCon) {
        TableCon = tableCon;
    }

    public String getTablePrice() {
        return TablePrice;
    }

    public void setTablePrice(String tablePrice) {
        TablePrice = tablePrice;
    }
}
