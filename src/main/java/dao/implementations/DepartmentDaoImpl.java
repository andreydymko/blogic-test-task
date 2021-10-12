package dao.implementations;

import dao.CriteriaUtils;
import dao.entities.Company;
import dao.entities.Department;
import dao.entities.metamodels.Department_;
import dao.interfaces.DepartmentDao;
import servlets.providers.helpers.types.OrderParam;
import utils.MathUtils;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
public class DepartmentDaoImpl extends CrudDaoImpl<Department> implements DepartmentDao {
    @Override
    protected Class<Department> setClassType() {
        return Department.class;
    }

    @Override
    public List<Department> getPageByCompany(Company company, Long start, Long pageSize, OrderParam... orders) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Department> query = cb.createQuery(Department.class);
        Root<Department> root = query.from(Department.class);
        List<Order> orderList = CriteriaUtils.ordersToList(em, root, orders);

        List<Department> departments  = em.createQuery(
                query.select(root)
                        .where(cb.equal(root.get(Department_.COMPANY), company))
                        .orderBy(orderList)) //"company_id"
                .setFirstResult(MathUtils.saturatedCast(start))
                .setMaxResults(MathUtils.saturatedCast(pageSize))
                .getResultList();
        return departments;
    }
}
