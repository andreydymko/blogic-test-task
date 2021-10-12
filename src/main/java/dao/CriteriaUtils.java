package dao;

import servlets.providers.helpers.types.OrderParam;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CriteriaUtils {
    public static <T> List<Order> ordersToList(EntityManager em, Root<T> root, OrderParam... orders) {
        if (orders == null) {
            return new ArrayList<>();
        }
        return Arrays.stream(orders)
                .map(orderParam -> orderParam.toOrder(em, root))
                .collect(Collectors.toList());
    }

}
