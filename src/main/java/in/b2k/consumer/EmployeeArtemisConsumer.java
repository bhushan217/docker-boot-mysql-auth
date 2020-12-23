package in.b2k.consumer;

import in.b2k.model.Employee;
import in.b2k.repository.EmployeeRepository;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class EmployeeArtemisConsumer {

    private EmployeeRepository employeeRepository;

    EmployeeArtemisConsumer(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    @JmsListener(destination = "${jms.queue.destination.reg_employee}")
    public void receive(Employee employee) {
        System.out.println("Received employee: " + employee);
        employeeRepository.save(employee);
    }
}
