package dao.interfaces;

import servlets.providers.helpers.types.OrderParam;

import javax.persistence.criteria.Predicate;
import java.util.List;

public interface CrudDao<T> {
    Long count();
    List<T> getAll(OrderParam... orders);
    List<T> getPage(Long start, Long pageSize, OrderParam... orders);
    List<T> getPageFiltered(Long start, Long pageSize, List<Predicate> predicates, OrderParam... orders);
    T getById(Long id);
    void add(T item);
    void update(Long oldItemId, T newItem);
    void deleteById(Long id);
}
