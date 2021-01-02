package in.b2k.jms.consumer;

import in.b2k.configuration.utils.AuthenticationUtil;
import in.b2k.model.Employee;
import in.b2k.repository.DepartmentRepository;
import in.b2k.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmployeeArtemisConsumer {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    EmployeeArtemisConsumer(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    @JmsListener(destination = "${jms.queue.destination.reg_employee}")
    public void receive(Employee employee, @Header(name = AuthenticationUtil.AUTH_CONTEXT, required = false) String authStr) {
        log.debug("Received employee: {}", employee);
        AuthenticationUtil.setForwardedAuth(authStr);
        final var department = employee.getDepartment();
        if(department!=null && department.getId()!=null){
            employee.setDepartment(departmentRepository.getOne(department.getId()));
        }
        final var saved = employeeRepository.save(employee);
        log.debug("saved employee: {}", saved);
    }
}
