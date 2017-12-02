package rizki.practicum.learning.service.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rizki.practicum.learning.entity.Role;
import rizki.practicum.learning.repository.RoleRepository;
import rizki.practicum.learning.service.role.RoleDefinition;

@Service
public class RoleGeneratorService implements GeneratorService {

    @Autowired
    private RoleRepository roleRepository;

    public void populate(){

        Role headLaboratory = new Role();
        headLaboratory.setId("1");
        headLaboratory.setDescription(RoleDefinition.HeadLaboratory.description);
        headLaboratory.setInitial(RoleDefinition.HeadLaboratory.initial);

        Role coordinatorAssistant = new Role();
        coordinatorAssistant.setId("2");
        coordinatorAssistant.setDescription(RoleDefinition.CoordinatorAssistance.description);
        coordinatorAssistant.setInitial(RoleDefinition.CoordinatorAssistance.initial);

        Role assistant = new Role();
        assistant.setId("3");
        assistant.setDescription(RoleDefinition.Assistance.description);
        assistant.setInitial(RoleDefinition.Assistance.initial);

        Role practican = new Role();
        practican.setId("4");
        practican.setDescription(RoleDefinition.Practican.description);
        practican.setInitial(RoleDefinition.Practican.initial);

        roleRepository.save(headLaboratory);
        roleRepository.save(coordinatorAssistant);
        roleRepository.save(assistant);
        roleRepository.save(practican);

    }

    @Override
    public void destroy() {

    }

}
