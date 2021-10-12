package services.interfaces;

import dao.entities.Company;
import dao.entities.Department;
import servlets.providers.helpers.types.OrderParam;

import java.util.List;

public interface DepartmentService extends CrudService<Department> {
    List<Department> getPageByCompanyId(Long companyId, Long start, Long pageSize, OrderParam... orders);
}
