package in.b2k.consumer;

import in.b2k.model.Employee;
import in.b2k.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmployeeArtemisConsumer {

    private EmployeeRepository employeeRepository;

    EmployeeArtemisConsumer(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    @JmsListener(destination = "${jms.queue.destination.reg_employee}")
    public void receive(Employee employee) {
        log.debug("Received employee: {}" , employee);
        employeeRepository.save(employee);
    }
}
