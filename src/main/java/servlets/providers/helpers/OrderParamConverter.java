package servlets.providers.helpers;

import servlets.providers.helpers.types.OrdersListParam;

import javax.ws.rs.ext.ParamConverter;

public class OrderParamConverter implements ParamConverter<OrdersListParam> {
    @Override
    public OrdersListParam fromString(String value) {
        return OrdersListParam.fromValue(value);
    }

    @Override
    public String toString(OrdersListParam value) {
        return value.toString();
    }
}
