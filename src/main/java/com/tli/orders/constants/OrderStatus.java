package com.tli.orders.constants;

public enum OrderStatus {
    N("New", 1), P("Processing",2), T("In Transit",3), D("Delivered",4), C("Cancelled",5);

    private String value;
    private Integer index;

    OrderStatus(String value, Integer index) {
        this.value = value;
        this.index = index;
    }

    public String getValue() {
        return value;
    }
    public Integer getIndex() {
        return index;
    }

    public static String getValueByIndex(Integer index) {
        switch (index) {
            case 1:
                return OrderStatus.N.getValue();
            case 2:
                return OrderStatus.P.getValue();
            case 3:
                return OrderStatus.T.getValue();
            case 4:
                return OrderStatus.D.getValue();
            case 5:
                return OrderStatus.C.getValue();
            default:
                return "Unknown";
        }
    }
}
