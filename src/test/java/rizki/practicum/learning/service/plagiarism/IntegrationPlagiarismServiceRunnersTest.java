package rizki.practicum.learning.service.plagiarism;
/*
    Created by : Rizki Maulana Akbar, On 03 - 2018 ;
*/

import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import rizki.practicum.learning.entity.Assignment;
import rizki.practicum.learning.entity.Document;
import rizki.practicum.learning.entity.PlagiarismContent;
import rizki.practicum.learning.entity.User;
import rizki.practicum.learning.repository.AssignmentRepository;
import rizki.practicum.learning.repository.DocumentRepository;
import rizki.practicum.learning.repository.PlagiarismContentRepository;
import rizki.practicum.learning.repository.UserRepository;
import rizki.practicum.learning.service.storage.StorageService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class IntegrationPlagiarismServiceRunnersTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private PlagiarismContentRepository plagiarismContentRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired @Qualifier("DocumentStorageService")
    private StorageService documentStorageService;

    @Autowired @Qualifier("SourceCodeStorageService")
    private StorageService sourceCodeStorageService;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Autowired
    private PlagiarismServiceRunners plagiarismServiceRunners;

    private User
            user_document_alone,
            user_document_plagiarized,
            user_document_unique,
            user_source_code_plagiarized,
            user_source_code_unique,
            comparator_for_document_plagiarized,
            comparator_for_document_unique,
            comparator_for_source_code_plagiarized,
            comparator_for_source_code_unique;

    private Assignment
            assignment_alone,
            assignment_document_plagiarized,
            assignment_document_unique,
            assignment_source_code_plagiarized,
            assignment_source_code_unique;

    private Document
            document_alone,
            document_plagiarized,
            document_unique,
            comparator_document_unique,
            comparator_document_plagiarized,
            code_unique,
            code_plagiarized,
            comparator_code_unique,
            comparator_code_plagiarized;

    private List<PlagiarismContent> plagiarismContents = new ArrayList<>();

    @Test
    public void run_DOCUMENT_PLAGIARIZED() throws Exception {

        User u2 = User.builder().name("UDP").password("XP").identity("UDP")
                .email("udp@rizki.com").active(true).photo("").build();
        this.user_document_plagiarized = userRepository.save(u2);

        User u6 = User.builder().name("CDP").password("XP").identity("CDP")
                .email("cdp@rizki.com").active(true).photo("").build();
        this.comparator_for_document_plagiarized = userRepository.save(u6);

        Assignment a2 = Assignment.builder().description("ADP").fileAllowed("document").build();
        this.assignment_document_plagiarized = assignmentRepository.save(a2);

        Document d2 = Document.builder().assignment(this.assignment_document_plagiarized).grade(0.0)
                .practican(this.user_document_plagiarized)
                .markAsPlagiarized(false)
                .filename("media/integration_test/document/user_document_plagiarized.docx")
                .build();

        this.document_plagiarized = documentRepository.save(d2);


        Document d7 = Document.builder().assignment(this.assignment_document_plagiarized).grade(0.0)
                .practican(this.comparator_for_document_plagiarized)
                .markAsPlagiarized(false)
                .filename("media/integration_test/document/comparator_document_plagiarized.docx")
                .build();

        this.comparator_document_plagiarized = documentRepository.save(d7);

        Document document = this.document_plagiarized;

        List<Document> documents = new ArrayList<>();
        documents.add(document);

        plagiarismServiceRunners.setDocument(documents);
        plagiarismServiceRunners.run();

        Assert.assertTrue(this.checkPlagiarismRate(document) != 0.0);
        Assert.assertFalse(this.checkIsEmptyPlagiarismContent(document));
    }

    @Test
    public void run_DOCUMENT_UNIQUE() throws Exception {

        User u3 = User.builder().name("UDU").password("XP").identity("UDU")
                .email("udu@rizki.com").active(true).photo("").build();
        this.user_document_unique = userRepository.save(u3);

        User u7 = User.builder().name("CDU").password("XP").identity("CDU")
                .email("cdu@rizki.com").active(true).photo("").build();
        this.comparator_for_document_unique = userRepository.save(u7);

        Assignment a3 = Assignment.builder().description("ADU").fileAllowed("document").build();
        this.assignment_document_unique = assignmentRepository.save(a3);

        Document d3 = Document.builder().assignment(this.assignment_document_unique).grade(0.0)
                .practican(this.user_document_unique)
                .markAsPlagiarized(false)
                .filename("media/integration_test/document/user_document_unique.docx")
                .build();

        this.document_unique = documentRepository.save(d3);

        Document d6 = Document.builder().assignment(this.assignment_document_unique).grade(0.0)
                .practican(this.comparator_for_document_unique)
                .markAsPlagiarized(false)
                .filename("media/integration_test/document/comparator_document_unique.docx")
                .build();

        this.comparator_document_unique= documentRepository.save(d6);

        Document document = this.document_unique;

        List<Document> documents = new ArrayList<>();
        documents.add(document);

        plagiarismServiceRunners.setDocument(documents);
        plagiarismServiceRunners.run();

        Assert.assertTrue(this.checkPlagiarismRate(document) != 0.0);
        Assert.assertFalse(this.checkIsEmptyPlagiarismContent(document));
    }

    @Test(expected = FileFormatException.class)
    public void run_CODE_UNEXPECTED_FORMAT() throws Exception {

        User u4 = User.builder().name("UCP").password("XP").identity("UCP")
                .email("ucp@rizki.com").active(true).photo("").build();
        this.user_source_code_plagiarized = userRepository.save(u4);

        User u8 = User.builder().name("CSP").password("XP").identity("CSP")
                .email("csp@rizki.com").active(true).photo("").build();
        this.comparator_for_source_code_plagiarized = userRepository.save(u8);

        Assignment a4 = Assignment.builder().description("ACP").fileAllowed("document").build();
        this.assignment_source_code_plagiarized = assignmentRepository.save(a4);


        Document d4 = Document.builder().assignment(this.assignment_source_code_plagiarized).grade(0.0)
                .practican(this.user_source_code_plagiarized)
                .markAsPlagiarized(false)
                .filename("media/integration_test/code/code_unique.war")
                .build();


        Document d9 = Document.builder().assignment(this.assignment_source_code_plagiarized).grade(0.0)
                .practican(this.comparator_for_source_code_plagiarized)
                .markAsPlagiarized(false)
                .filename("media/integration_test/code/comparator_code_plagiarized.java")
                .build();

        this.comparator_code_plagiarized = documentRepository.save(d9);

        this.code_plagiarized = documentRepository.save(d4);

        Document document = this.code_plagiarized;

        List<Document> documents = new ArrayList<>();
        documents.add(document);

        plagiarismServiceRunners.setDocument(documents);
        plagiarismServiceRunners.run();
    }

    @Test
    public void run_CODE_UNIQUE() throws Exception {

        User u5 = User.builder().name("UCU").password("XP").identity("UCU")
                .email("ucu@rizki.com").active(true).photo("").build();
        this.user_source_code_unique = userRepository.save(u5);

        User u9 = User.builder().name("CSU").password("XP").identity("CSU")
                .email("csu@rizki.com").active(true).photo("").build();
        this.comparator_for_source_code_unique = userRepository.save(u9);


        Assignment a5 = Assignment.builder().description("ACU").fileAllowed("document").build();
        this.assignment_source_code_unique = assignmentRepository.save(a5);

        Document d5 = Document.builder().assignment(this.assignment_source_code_unique).grade(0.0)
                .practican(this.user_source_code_unique)
                .markAsPlagiarized(false)
                .filename("media/integration_test/code/code_unique.java")
                .build();

        this.code_unique = documentRepository.save(d5);


        Document d8 = Document.builder().assignment(this.assignment_source_code_unique).grade(0.0)
                .practican(this.comparator_for_source_code_unique)
                .markAsPlagiarized(false)
                .filename("media/integration_test/code/comparator_code_unique.java")
                .build();

        this.comparator_code_unique = documentRepository.save(d8);

        Document document = this.code_unique;

        List<Document> documents = new ArrayList<>();
        documents.add(document);

        plagiarismServiceRunners.setDocument(documents);
        plagiarismServiceRunners.run();

        Assert.assertTrue(this.checkPlagiarismRate(document) != 0.0);
        Assert.assertFalse(this.checkIsEmptyPlagiarismContent(document));
    }


    private boolean checkIsEmptyPlagiarismContent (Document document) {
        PlagiarismContent plagiarismContents = plagiarismContentRepository.findDistinctFirstByDocument1OrDocument2OrderByRate(document, document);
        if (plagiarismContents == null) {
            return true;
        } else {
            this.plagiarismContents.add(plagiarismContents);
            return false;
        }
    }

    private double checkPlagiarismRate (Document document) {
        PlagiarismContent plagiarismContent = plagiarismContentRepository.findDistinctFirstByDocument1OrDocument2OrderByRate(document, document);
        if (plagiarismContent == null) {
            return 0.0;
        } else {
            return plagiarismContent.getRate();
        }
    }

    @After
    public void after() throws Exception {
        this.delete(plagiarismContentRepository, this.plagiarismContents.
                toArray(new PlagiarismContent[this.plagiarismContents.size()]));

        this.delete(documentRepository, new Document[]{
                document_alone,
                document_plagiarized,
                document_unique,
                comparator_document_unique,
                comparator_document_plagiarized,
                code_unique,
                code_plagiarized,
                comparator_code_unique,
                comparator_code_plagiarized
        });

        this.delete(assignmentRepository, new Assignment[]{
                assignment_alone,
                assignment_document_plagiarized,
                assignment_document_unique,
                assignment_source_code_plagiarized,
                assignment_source_code_unique
        });

        this.delete(userRepository, new User[]{
                user_document_alone,
                user_document_plagiarized,
                user_document_unique,
                user_source_code_plagiarized,
                user_source_code_unique,
                comparator_for_document_plagiarized,
                comparator_for_document_unique,
                comparator_for_source_code_plagiarized,
                comparator_for_source_code_unique
        });
    }

    private <T extends CrudRepository, V> void delete(T repository, V objects[]){
        if (objects.length > 0) {
            Arrays.stream(objects).forEach(object -> {
                if ( object != null) {
                    repository.delete(object);
                }});
        }
    }
}
