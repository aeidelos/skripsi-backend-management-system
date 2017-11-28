package rizki.practicum.learning.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rizki.practicum.learning.entity.Role;
import rizki.practicum.learning.repository.RoleRepository;

@Service
public class RoleGeneratorService {

    @Autowired
    private RoleRepository roleRepository;

    public void populateRole(){

        Role headLaboratory = new Role();
        headLaboratory.setId("1");
        headLaboratory.setDescription("Kepala Laboratorium");

        Role coordinatorAssistant = new Role();
        coordinatorAssistant.setId("2");
        coordinatorAssistant.setDescription("Koordinator Asisten");

        Role assistant = new Role();
        assistant.setId("3");
        assistant.setDescription("Asisten Praktikum");

        Role practican = new Role();
        practican.setId("4");
        practican.setDescription("Mahasiswa");

        roleRepository.save(headLaboratory);
        roleRepository.save(coordinatorAssistant);
        roleRepository.save(assistant);
        roleRepository.save(practican);

    }

}
