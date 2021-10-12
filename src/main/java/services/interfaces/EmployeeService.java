package services.interfaces;

import dao.entities.Employee;
import servlets.providers.helpers.types.OrderParam;

import java.util.List;

public interface EmployeeService extends CrudService<Employee> {
    List<Employee> getPageByCompanyId(Long companyId, Long start, Long pageSize, OrderParam... orders);
}
