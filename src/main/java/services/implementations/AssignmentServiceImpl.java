package services.implementations;

import dao.entities.Assignment;
import dao.entities.Employee;
import dao.interfaces.AssignmentDao;
import dao.interfaces.CrudDao;
import dao.interfaces.EmployeeDao;
import services.interfaces.AssignmentService;
import servlets.providers.helpers.types.OrderParam;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;
import java.util.List;

@Stateless
@Transactional(Transactional.TxType.REQUIRED)
public class AssignmentServiceImpl extends CrudServiceImpl<Assignment> implements AssignmentService {
    @EJB
    private AssignmentDao assignmentDao;

    @EJB
    private EmployeeDao employeeDao;

    @Override
    protected CrudDao<Assignment> setDAO() {
        return assignmentDao;
    }

    @Override
    public void update(Long oldItemId, Assignment newItem) {
        newItem.setId(oldItemId);
        super.update(oldItemId, newItem);
    }

    @Override
    public List<Assignment> getPageOfMyAssignments(Long myEmployeeId, Long start, Long pageSize, OrderParam... orders) {
        Employee employee = employeeDao.getById(myEmployeeId);
        List<Assignment> assignments = assignmentDao.getPageOfMyAssignments(employee, start, pageSize, orders);
        return assignments;
    }

    @Override
    public List<Assignment> getPageOfAssignmentsForMe(Long myEmployeeId, Long start, Long pageSize, OrderParam... orders) {
        Employee employee = employeeDao.getById(myEmployeeId);

        return assignmentDao.getPageOfAssignmentsForMe(employee, start, pageSize, orders);
    }

    @Override
    public Assignment sendOnExecution(Long assignmentId) {
        Assignment assignment = assignmentDao.getById(assignmentId);
        assignment.sendOnExecution();
        return assignment;
    }

    @Override
    public Assignment sendOnControl(Long assignmentId) {
        Assignment assignment = assignmentDao.getById(assignmentId);
        assignment.sendOnControl();
        return assignment;
    }

    @Override
    public Assignment sendOnRevision(Long assignmentId) {
        Assignment assignment = assignmentDao.getById(assignmentId);
        assignment.sendOnRevision();
        return assignment;
    }

    @Override
    public Assignment acceptAssignment(Long assignmentId) {
        Assignment assignment = assignmentDao.getById(assignmentId);
        assignment.sendOnAcceptation();
        return assignment;
    }
}
