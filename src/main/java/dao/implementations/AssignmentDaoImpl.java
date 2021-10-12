package dao.implementations;

import dao.CriteriaUtils;
import dao.entities.Assignment;
import dao.entities.Employee;
import dao.entities.metamodels.Assignment_;
import dao.entities.metamodels.Employee_;
import dao.interfaces.AssignmentDao;
import servlets.providers.helpers.types.OrderParam;
import utils.MathUtils;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
public class AssignmentDaoImpl extends CrudDaoImpl<Assignment> implements AssignmentDao {
    @Override
    protected Class<Assignment> setClassType() {
        return Assignment.class;
    }

    @Override
    public List<Assignment> getPageOfMyAssignments(Employee me, Long start, Long pageSize, OrderParam... orders) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Assignment> query = cb.createQuery(Assignment.class);
        Root<Assignment> root = query.from(Assignment.class);
        List<Order> orderList = CriteriaUtils.ordersToList(em, root, orders);

        query.where(cb.equal(root.get(Assignment_.AUTHOR), me)).select(root).orderBy(orderList);

        List<Assignment> assignments  = em.createQuery(query)
                .setFirstResult(MathUtils.saturatedCast(start))
                .setMaxResults(MathUtils.saturatedCast(pageSize))
                .getResultList();
        return assignments;
    }

    @Override
    public List<Assignment> getPageOfAssignmentsForMe(Employee me, Long start, Long pageSize, OrderParam... orders) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Assignment> query = cb.createQuery(Assignment.class);
        Root<Assignment> root = query.from(Assignment.class);
        Join<Assignment, Employee> join = root.join(Assignment_.EXECUTORS, JoinType.INNER);
        List<Order> orderList = CriteriaUtils.ordersToList(em, root, orders);

        query.where(cb.equal(join.get(Employee_.ID), me.getId())).select(root).orderBy(orderList);

        List<Assignment> assignments  = em.createQuery(query) // "employee_id"
                .setFirstResult(MathUtils.saturatedCast(start))
                .setMaxResults(MathUtils.saturatedCast(pageSize))
                .getResultList();
        return assignments;
    }
}
