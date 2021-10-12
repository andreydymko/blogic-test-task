package services.implementations;

import dao.entities.Company;
import dao.entities.Employee;
import dao.interfaces.CompanyDao;
import dao.interfaces.CrudDao;
import dao.interfaces.EmployeeDao;
import services.interfaces.EmployeeService;
import servlets.providers.helpers.types.OrderParam;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;
import java.util.List;

@Stateless
@Transactional(Transactional.TxType.REQUIRED)
public class EmployeeServiceImpl extends CrudServiceImpl<Employee> implements EmployeeService {
    @EJB
    private EmployeeDao employeeDao;

    @EJB
    private CompanyDao companyDao;

    @Override
    protected CrudDao<Employee> setDAO() {
        return employeeDao;
    }

    @Override
    public void update(Long oldItemId, Employee newItem) {
        newItem.setId(oldItemId);
        super.update(oldItemId, newItem);
    }

    @Override
    public List<Employee> getPageByCompanyId(Long companyId, Long start, Long pageSize, OrderParam... orders) {
        Company company = companyDao.getById(companyId);

        List<Employee> employees = employeeDao.getPageByCompany(company, start, pageSize, orders);
        return employees;
    }
}
