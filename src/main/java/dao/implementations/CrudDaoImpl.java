package dao.implementations;

import dao.CriteriaUtils;
import dao.interfaces.CrudDao;
import servlets.providers.helpers.types.OrderParam;
import utils.MathUtils;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class CrudDaoImpl<T> implements CrudDao<T> {
    @PersistenceContext
    protected EntityManager em;

    private Class<T> type;

    @PostConstruct
    private void init() {
        type = setClassType();
    }

    protected abstract Class<T> setClassType();

    @Override
    public Long count() {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Long> query = cb.createQuery(Long.class);

        Long count = em.createQuery(query.select(cb.count(query.from(type))))
                .getSingleResult();

        return count;
    }

    @Override
    public List<T> getAll(OrderParam... orders) {
        CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(type);
        Root<T> root = query.from(type);

        List<Order> orderList = CriteriaUtils.ordersToList(em, root, orders);

        List<T> list = em.createQuery(query.select(root).orderBy(orderList)).getResultList();
        return list;
    }

    @Override
    public List<T> getPage(Long start, Long pageSize, OrderParam... orders) {
        CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(type);
        Root<T> root = query.from(type);

        List<Order> orderList = CriteriaUtils.ordersToList(em, root, orders);

        List<T> list = em.createQuery(query.select(root).orderBy(orderList))
                .setFirstResult(MathUtils.saturatedCast(start))
                .setMaxResults(MathUtils.saturatedCast(pageSize))
                .getResultList();
        return list;
    }

    @Override
    public List<T> getPageFiltered(Long start, Long pageSize, List<Predicate> predicates, OrderParam... orders) {
        CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(type);
        Root<T> root = query.from(type);

        List<Order> orderList = CriteriaUtils.ordersToList(em, root, orders);
        Predicate[] predicatesArr = predicates.toArray(new Predicate[0]);

        List<T> list = em.createQuery(query.select(root).where(predicatesArr).orderBy(orderList))
                .setFirstResult(MathUtils.saturatedCast(start))
                .setMaxResults(MathUtils.saturatedCast(pageSize))
                .getResultList();
        return list;
    }

    @Override
    public T getById(Long id) {
        T item = em.find(type, id);
        return item;
    }

    @Override
    public void add(T item) {
        em.persist(item);
    }

    @Override
    public void update(Long oldItemId, T newItem) {
        if (getById(oldItemId) == null) {
            throw new EntityNotFoundException("Item with provided ID has not been found");
        }
        em.merge(newItem);
    }

    @Override
    public void deleteById(Long id) {
        em.remove(getById(id));
    }
}
