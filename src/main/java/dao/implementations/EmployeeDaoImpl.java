package dao.implementations;

import dao.CriteriaUtils;
import dao.entities.Company;
import dao.entities.Employee;
import dao.entities.metamodels.Employee_;
import dao.interfaces.EmployeeDao;
import servlets.providers.helpers.types.OrderParam;
import utils.MathUtils;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
public class EmployeeDaoImpl extends CrudDaoImpl<Employee> implements EmployeeDao {

    @Override
    protected Class<Employee> setClassType() {
        return Employee.class;
    }

    @Override
    public List<Employee> getPageByCompany(Company company, Long start, Long pageSize, OrderParam... orders) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Employee> query = cb.createQuery(Employee.class);
        Root<Employee> root = query.from(Employee.class);
        List<Order> orderList = CriteriaUtils.ordersToList(em, root, orders);

        List<Employee> employees  = em.createQuery(
                query.select(root)
                        .where(cb.equal(root.get(Employee_.COMPANY), company))
                        .orderBy(orderList)) //"company_id"
                .setFirstResult(MathUtils.saturatedCast(start))
                .setMaxResults(MathUtils.saturatedCast(pageSize))
                .getResultList();
        return employees;
    }
}
