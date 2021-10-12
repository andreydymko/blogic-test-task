package services.interfaces;

import dao.entities.Assignment;
import servlets.providers.helpers.types.OrderParam;

import java.util.List;

public interface AssignmentService extends CrudService<Assignment> {
    List<Assignment> getPageOfMyAssignments(Long myEmployeeId, Long start, Long pageSize, OrderParam... orders);
    List<Assignment> getPageOfAssignmentsForMe(Long myEmployeeId, Long start, Long pageSize,  OrderParam... orders);
    Assignment sendOnExecution(Long assignmentId);
    Assignment sendOnControl(Long assignmentId);
    Assignment sendOnRevision(Long assignmentId);
    Assignment acceptAssignment(Long assignmentId);
}
