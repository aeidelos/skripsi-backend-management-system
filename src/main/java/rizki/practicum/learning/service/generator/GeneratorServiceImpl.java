package rizki.practicum.learning.service.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rizki.practicum.learning.entity.*;
import rizki.practicum.learning.repository.RoleRepository;
import rizki.practicum.learning.service.assignment.AssignmentService;
import rizki.practicum.learning.service.classroom.ClassroomService;
import rizki.practicum.learning.service.course.CourseService;
import rizki.practicum.learning.service.practicum.PracticumService;
import rizki.practicum.learning.service.role.RoleDefinition;
import rizki.practicum.learning.service.role.RoleService;
import rizki.practicum.learning.service.storage.StorageService;
import rizki.practicum.learning.service.task.TaskService;
import rizki.practicum.learning.service.user.UserService;

import java.sql.Date;
import java.util.ArrayList;

@Service
public class GeneratorServiceImpl implements GeneratorService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private PracticumService practicumService;

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private AssignmentService assignmentService;


    @Override
    public void populate() {
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

        headLaboratory = roleRepository.save(headLaboratory);
        coordinatorAssistant = roleRepository.save(coordinatorAssistant);
        assistant = roleRepository.save(assistant);
        practican = roleRepository.save(practican);


        // ========================

        User defaultUser = new User();
        defaultUser.setName("Rizki Maulana Akbar");
        defaultUser.setPassword("test");
        defaultUser.setEmail("test");
        defaultUser.setIdentity("12345");
        defaultUser.setActive(true);
        defaultUser.setRole(roleService.getRole());
        try {
            userService.createUser(defaultUser);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<Role> mhs = new ArrayList<>();
        ArrayList<Role> assistants = new ArrayList<>();
        ArrayList<Role> coasssistant = new ArrayList<>();

        try {
            mhs.add(roleService.getRole(RoleDefinition.Practican.initial));
            assistants.addAll(mhs);
            assistants.add(roleService.getRole(RoleDefinition.Assistance.initial));
            coasssistant.addAll(assistants);
            coasssistant.add(roleService.getRole(RoleDefinition.CoordinatorAssistance.initial));
        } catch (Exception e) {
            e.printStackTrace();
        }

        User assistant1 = new User();
        assistant1.setName("Asisten 1");
        assistant1.setPassword("asisten1");
        assistant1.setEmail("asisten1");
        assistant1.setIdentity("123456");
        assistant1.setActive(true);
        assistant1.setRole(assistants);
        try {
            userService.createUser(assistant1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        User assistant2 = new User();
        assistant2.setName("Asisten 2");
        assistant2.setPassword("asisten2");
        assistant2.setEmail("asisten2");
        assistant2.setIdentity("1234567");
        assistant2.setActive(true);
        assistant2.setRole(assistants);
        try {
            userService.createUser(assistant2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        User practican1 = new User();
        practican1.setName("Praktikan 1");
        practican1.setPassword("praktikan1");
        practican1.setEmail("praktikan1");
        practican1.setIdentity("12345678");
        practican1.setActive(true);
        practican1.setRole(mhs);
        try {
            userService.createUser(practican1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        User practican2 = new User();
        practican2.setName("Praktikan 2");
        practican2.setPassword("praktikan2");
        practican2.setEmail("praktikan2");
        practican2.setIdentity("123456789");
        practican2.setActive(true);
        practican2.setRole(mhs);
        try {
            userService.createUser(practican2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        User coas1 = new User();
        coas1.setName("Koordinator Asisten 1");
        coas1.setPassword("koas1");
        coas1.setEmail("koas1");
        coas1.setIdentity("1234567890");
        coas1.setActive(true);
        coas1.setRole(coasssistant);
        try {
            userService.createUser(coas1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        User coas2 = new User();
        coas2.setName("Koordinator Asisten 2");
        coas2.setPassword("koas2");
        coas2.setEmail("koas2");
        coas2.setIdentity("12345678901");
        coas2.setActive(true);
        coas2.setRole(coasssistant);
        try {
            userService.createUser(coas2);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // =====================

        Course course = new Course();
        course.setCourseName("Pemrograman Dasar");
        course.setCourseCode("PD1000");

        Course course2 = new Course();
        course2.setCourseCode("PL1000");
        course2.setCourseName("Pemrograman Lanjut");

        Course course3 = new Course();
        course3.setCourseCode("ASD100");
        course3.setCourseName("Algoritma dan Struktur Data");
        try {
            course = courseService.addCourse(course);
            course2 = courseService.addCourse(course2);
            course3 = courseService.addCourse(course3);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ========================

        Practicum practicum = new Practicum();
        practicum.setName("Praktikum Pemrograman Dasar 2017/2018");
        practicum.setCourse(course);
        try {
            practicum.setCoordinatorAssistance(userService.getUserByEmail("koas1"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        practicum.setActive(true);
        try {
            practicum = practicumService.addPracticum(practicum);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // ==========================

        Classroom classroom = new Classroom();
        classroom.setName("TIF A");
        ArrayList<User> assistance = new ArrayList<>();
        try {
            assistance.add(userService.getUserByEmail("asisten1"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        classroom.setAssistance(assistance);
        classroom.setLocation("Gedung A");
        classroom.setEnrollmentKey("JAVA00");
        classroom.setPracticum(practicum);
        ArrayList<User> praktikan = new ArrayList<>();
        try {
            praktikan.add(userService.getUserByEmail("praktikan1"));
            praktikan.add(userService.getUserByEmail("praktikan2"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        classroom.setPractican(praktikan);
        try {
            classroom = classroomService.addClassroom(classroom);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Task task = new Task();
        task.setPracticum(practicum);
        task.setCreatedDate(new java.util.Date());
        task.setDueDate(new java.util.Date());
        task.setDescription("Pengumpulan Laporan 1 Bab Seleksi dan Kondisi");
        task.setAllowLate(true);
        try {
            task.setCreatedBy(userService.getUserByEmail("koas1"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        task.setTitle("Seleksi dan Kondisi");

        try {
            task = taskService.addTask(task);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assignment assignment = new Assignment();
        assignment.setFileAllowed("document");
        assignment.setDescription("Dokumen Laporan");
        ArrayList<Assignment> assignmentList = new ArrayList<>();
        try {
            assignment = assignmentService.addAssignment(task.getId(),assignment);

        } catch (Exception e) {
            e.printStackTrace();
        }

        assignmentList.add(assignment);
        task.setAssignments(assignmentList);
        try {
            taskService.updateTask(task);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void destroy() {

    }
}
