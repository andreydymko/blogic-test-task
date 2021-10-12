package servlets.providers.helpers.types;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

public class OrderParam {
    enum TYPE {
        ASC,
        DESC
    }

    private TYPE orderType;
    private String fieldName;

    public OrderParam(TYPE orderType, String fieldName) {
        this.orderType = orderType;
        this.fieldName = fieldName;
    }

    public TYPE getOrderType() {
        return orderType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public <T>Order toOrder(EntityManager entityManager, Root<T> root) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        Path<T> path = root.get(this.fieldName);
        switch (this.orderType) {
            case ASC:
                return cb.asc(path);
            case DESC:
                return cb.desc(path);
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return String.format("%s%s", orderType == TYPE.ASC ? "+" : "-", fieldName);
    }
}
