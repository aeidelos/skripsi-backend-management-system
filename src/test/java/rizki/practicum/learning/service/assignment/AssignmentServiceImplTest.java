package rizki.practicum.learning.service.assignment;

import org.apache.commons.io.IOUtils;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.expression.spel.ast.Assign;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import rizki.practicum.learning.entity.Assignment;
import rizki.practicum.learning.entity.Document;
import rizki.practicum.learning.entity.User;
import rizki.practicum.learning.repository.AssignmentRepository;
import rizki.practicum.learning.repository.DocumentRepository;
import rizki.practicum.learning.repository.PlagiarismContentRepository;
import rizki.practicum.learning.repository.UserRepository;
import rizki.practicum.learning.service.storage.DocumentStorageServiceImpl;
import rizki.practicum.learning.service.storage.SourceCodeStorageServiceImpl;
import rizki.practicum.learning.service.storage.StorageService;
import rizki.practicum.learning.service.storage.StorageServiceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AssignmentServiceImplTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private DocumentStorageServiceImpl documentStorageService;

    @Mock
    private SourceCodeStorageServiceImpl sourceCodeStorageService;

    @Mock
    private StorageService storageService;

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private PlagiarismContentRepository plagiarismContentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AssignmentRepository assignmentRepository;

    @Captor
    private ArgumentCaptor<Document> documentCaptor;

    @InjectMocks @Spy
    private AssignmentServiceImpl assignmentService;

    private MultipartFile multipartFile;

    private User user;

    private Assignment assignment;

    private Document document;

    private Document documentOld;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        user = User.builder().id("USERID").active(true).name("USERNAME").email("USEREMAIL").password("USERPASSWORD").identity("USERIDENTITY").build();
        assignment = Assignment.builder().id("ASGID").description("ASGDESC").fileAllowed("document").build();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void fulfillAssignment_TYPE_DOCUMENT_NORMAL_CONDITION() throws Exception {
        multipartFile = new MockMultipartFile("file.docx","file.docx","application/msword", new FileInputStream(
                new File("media/document/test/file.docx")));
        document = Document.builder().id("DOCID").filename(multipartFile.getName()).practican(user).assignment(assignment).grade(0.0).markAsPlagiarized(false).build();
        documentOld = document;
        documentOld.setFilename("DOCOLDNAME");

        ArrayList<String> documentStored = new ArrayList<>();
        documentStored.add(multipartFile.getName());
        ArrayList<Document> documentFileStored = new ArrayList<>();
        documentFileStored.add(document);

        Mockito.doNothing().when(storageService).delete(document.getId());

        Mockito.when(userRepository.findOne(user.getId())).thenReturn(user);

        Mockito.when(documentStorageService.store(new MultipartFile[]{multipartFile}, "file")).
                thenReturn(documentStored);

        Mockito.when(documentRepository.save(document)).thenReturn(document);

        Mockito.doReturn(documentStored).when(assignmentService).documentCreator(documentStored, user.getId(), assignment.getId());

        List<Document> result = assignmentService.fulfillAssignment(assignment.getId(), user.getId(), new MultipartFile[]{multipartFile},
                null);

        verify(documentStorageService).store(new MultipartFile[]{multipartFile}, "file");

        verifyZeroInteractions(sourceCodeStorageService);
        verifyNoMoreInteractions(documentStorageService, documentRepository, plagiarismContentRepository);

        assertEquals(result, documentStored);
    }

    @Test
    public void fulfillAssignment_TYPE_CODE_NORMAL_CONDITION() throws Exception {

        multipartFile = new MockMultipartFile("file.java","file.java","application/java", new FileInputStream(
                new File("media/document/test/file.java")));
        document = Document.builder().id("DOCID").filename(multipartFile.getName()).practican(user).assignment(assignment).grade(0.0).markAsPlagiarized(false).build();
        documentOld = document;
        documentOld.setFilename("DOCOLDNAME");

        ArrayList<String> documentStored = new ArrayList<>();
        documentStored.add(multipartFile.getName());
        ArrayList<Document> documentFileStored = new ArrayList<>();
        documentFileStored.add(document);

        Mockito.doNothing().when(storageService).delete(document.getId());

        Mockito.when(userRepository.findOne(user.getId())).thenReturn(user);

        Mockito.when(sourceCodeStorageService.store(new MultipartFile[]{multipartFile}, "file")).
                thenReturn(documentStored);

        Mockito.when(documentRepository.save(document)).thenReturn(document);

        Mockito.doReturn(documentStored).when(assignmentService).documentCreator(documentStored, user.getId(), assignment.getId());

        List<Document> result = assignmentService.fulfillAssignment(assignment.getId(), user.getId(), new MultipartFile[]{multipartFile},
                null);

        verify(sourceCodeStorageService).store(new MultipartFile[]{multipartFile}, "file");

        verifyZeroInteractions(documentStorageService);
        verifyNoMoreInteractions(sourceCodeStorageService, documentRepository, plagiarismContentRepository);

        assertEquals(result, documentStored);
    }


    @Test(expected = FileFormatException.class)
    public void fulfillAssignment_TYPE_UNDEFINED_NORMAL_CONDITION() throws Exception {

        multipartFile = new MockMultipartFile("file.war","file.war","application/java", new FileInputStream(
                new File("media/document/test/file.war")));
        document = Document.builder().id("DOCID").filename(multipartFile.getName()).practican(user).assignment(assignment).grade(0.0).markAsPlagiarized(false).build();
        documentOld = document;
        documentOld.setFilename("DOCOLDNAME");

        ArrayList<String> documentStored = new ArrayList<>();
        documentStored.add(multipartFile.getName());
        ArrayList<Document> documentFileStored = new ArrayList<>();
        documentFileStored.add(document);

        Mockito.doNothing().when(storageService).delete(document.getId());

        Mockito.when(userRepository.findOne(user.getId())).thenReturn(user);

        Mockito.when(sourceCodeStorageService.store(new MultipartFile[]{multipartFile}, "file")).
                thenReturn(documentStored);

        Mockito.when(documentRepository.save(document)).thenReturn(document);

        Mockito.doReturn(documentStored).when(assignmentService).documentCreator(documentStored, user.getId(), assignment.getId());

        assignmentService.fulfillAssignment(assignment.getId(), user.getId(), new MultipartFile[]{multipartFile},
                null);
    }

}