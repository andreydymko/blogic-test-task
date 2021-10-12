package services.implementations;

import dao.entities.Company;
import dao.entities.Department;
import dao.interfaces.CompanyDao;
import dao.interfaces.CrudDao;
import dao.interfaces.DepartmentDao;
import services.interfaces.DepartmentService;
import servlets.providers.helpers.types.OrderParam;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;
import java.util.List;

@Stateless
@Transactional(Transactional.TxType.REQUIRED)
public class DepartmentServiceImpl extends CrudServiceImpl<Department> implements DepartmentService {
    @EJB
    private DepartmentDao departmentDao;

    @EJB
    private CompanyDao companyDao;

    @Override
    protected CrudDao<Department> setDAO() {
        return departmentDao;
    }

    @Override
    public void update(Long oldItemId, Department newItem) {
        newItem.setId(oldItemId);
        super.update(oldItemId, newItem);
    }

    @Override
    public List<Department> getPageByCompanyId(Long companyId, Long start, Long pageSize, OrderParam... orders) {
        Company company = companyDao.getById(companyId);

        List<Department> departments = departmentDao.getPageByCompany(company, start, pageSize, orders);

        return departments;
    }
}
