package dao.interfaces;

import dao.entities.Assignment;
import dao.entities.Employee;
import servlets.providers.helpers.types.OrderParam;

import java.util.List;

public interface AssignmentDao extends CrudDao<Assignment> {
    List<Assignment> getPageOfMyAssignments(Employee me, Long start, Long pageSize, OrderParam... orders);
    List<Assignment> getPageOfAssignmentsForMe(Employee me, Long start, Long pageSize, OrderParam... orders);
}
