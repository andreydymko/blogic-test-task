package dao.interfaces;

import dao.entities.Company;
import dao.entities.Employee;
import servlets.providers.helpers.types.OrderParam;

import java.util.List;

public interface EmployeeDao extends CrudDao<Employee> {
    List<Employee> getPageByCompany(Company company, Long start, Long pageSize, OrderParam... orders);
}
