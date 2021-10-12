package services.implementations;

import dao.interfaces.CrudDao;
import services.interfaces.CrudService;
import servlets.providers.helpers.types.OrderParam;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

public abstract class CrudServiceImpl<T> implements CrudService<T> {
    private CrudDao<T> dao = null;

    @PostConstruct
    private void init() {
        this.dao = setDAO();
    }

    protected abstract CrudDao<T> setDAO();

    @Override
    public Long count() {
        return dao.count();
    }

    @Override
    public List<T> getAll(OrderParam... orders) {
        return dao.getAll(orders);
    }

    @Override
    public List<T> getPage(Long start, Long pageSize, OrderParam... orders) {
        return dao.getPage(start, pageSize, orders);
    }

    @Override
    public T getById(Long id) {
        return dao.getById(id);
    }

    @Override
    public void add(T item) {
        dao.add(item);
    }

    @Override
    public void update(Long oldItemId, T newItem) {
        dao.update(oldItemId, newItem);
    }

    @Override
    public void deleteById(Long id) {
        dao.deleteById(id);
    }
}
