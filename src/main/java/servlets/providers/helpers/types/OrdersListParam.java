package servlets.providers.helpers.types;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrdersListParam {
    private List<OrderParam> orderList;

    public OrdersListParam(List<OrderParam> orderList) {
        this.orderList = orderList;
    }

    public List<OrderParam> getOrderList() {
        return orderList;
    }

    public OrderParam[] getOrderArr() {
        return orderList.toArray(new OrderParam[0]);
    }

    public static OrdersListParam fromValue(String value) {
        if (value == null) {
            return null;
        }

        List<OrderParam> orders = new ArrayList<>();
        String[] ordersStrs = value.replace("(", "").replace(")", "").split(",");

        OrderParam.TYPE type;
        String fieldName;
        for (String orderStr : ordersStrs) {
            switch (orderStr.charAt(0)) {
                case '+':
                    type = OrderParam.TYPE.ASC;
                    break;
                case '-':
                    type = OrderParam.TYPE.DESC;
                    break;
                default:
                    continue;
            }
            if (orderStr.length() < 2) {
                continue;
            }
            fieldName = orderStr.substring(1);
            orders.add(new OrderParam(type, fieldName));
        }

        return new OrdersListParam(orders);
    }

    @Override
    public String toString() {
        return String.format("%s%s%s", "?sort(", orderList.stream().map(OrderParam::toString).collect(Collectors.joining(",")), ")") ;
    }
}
