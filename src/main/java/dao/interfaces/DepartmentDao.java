package dao.interfaces;

import dao.entities.Company;
import dao.entities.Department;
import servlets.providers.helpers.types.OrderParam;

import java.util.List;

public interface DepartmentDao extends CrudDao<Department> {
    List<Department> getPageByCompany(Company company, Long start, Long pageSize, OrderParam... orders);
}
