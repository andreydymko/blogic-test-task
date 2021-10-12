package services.interfaces;

import servlets.providers.helpers.types.OrderParam;

import java.util.List;

public interface CrudService<T> {
    Long count();
    List<T> getAll(OrderParam... orders);
    List<T> getPage(Long start, Long pageSize, OrderParam... orders);
    T getById(Long id);
    void add(T item);
    void update(Long oldItemId, T newItem);
    void deleteById(Long id);
}
