package in.b2k.jms.consumer;

import in.b2k.configuration.utils.AuthenticationUtil;
import in.b2k.model.Department;
import in.b2k.repository.DepartmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DepartmentArtemisConsumer {

    private final DepartmentRepository departmentRepository;

    DepartmentArtemisConsumer(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @JmsListener(destination = "${jms.queue.destination.reg_department}")
    public void receive(Department department, @Header(name = AuthenticationUtil.AUTH_CONTEXT, required = false) String authStr) {
        log.debug("Received department: {}", department);
        AuthenticationUtil.setForwardedAuth(authStr);
        Department saved = departmentRepository.save(department);
        log.debug("saved employee: {}", saved);
    }
}
