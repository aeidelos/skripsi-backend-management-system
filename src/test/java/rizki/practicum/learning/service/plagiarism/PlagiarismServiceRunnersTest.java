package rizki.practicum.learning.service.plagiarism;

import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;
import rizki.practicum.learning.entity.Assignment;
import rizki.practicum.learning.entity.Document;
import rizki.practicum.learning.entity.PlagiarismContent;
import rizki.practicum.learning.repository.DocumentRepository;
import rizki.practicum.learning.repository.PlagiarismContentRepository;
import rizki.practicum.learning.service.storage.DocumentStorageServiceImpl;


import rizki.practicum.learning.service.storage.SourceCodeStorageServiceImpl;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class PlagiarismServiceRunnersTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private DocumentStorageServiceImpl documentStorageService;

    @Mock
    private SourceCodeStorageServiceImpl sourceCodeStorageService;

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private ASTPlagiarism astPlagiarism;

    @Mock
    private PlagiarismContentRepository plagiarismContentRepository;

    @Mock
    private Levensthein levensthein;

    @Captor
    private ArgumentCaptor<PlagiarismContent> plagiarismContentArgumentCaptor;

    @Captor
    private ArgumentCaptor<Document> documentArgumentCaptor;

    @InjectMocks
    private PlagiarismServiceRunners plagiarismServiceRunners;

    private Document resource;

    private Document comparator;

    private Assignment assignment;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        assignment = Assignment.builder().id("ASGID").fileAllowed("ASGFA").description("ASGDESC").build();
    }

    @Test
    public void documentCheckPlagiarism_DOC_TYPE_DOCUMENTS_PLAGIARIZED_EXEC() throws Exception {
        resource = Document.builder().id("RESID").assignment(assignment).filename("RES.docx").markAsPlagiarized(false).grade(0.0).build();
        comparator = Document.builder().id("COMID").assignment(assignment).filename("COM.docx").markAsPlagiarized(false).grade(0.0).build();

        String resourceContent = "this is an identical content";
        String comparatorContent = "this is an identical content";

        Mockito.when(documentStorageService.load(resource.getFilename())).thenReturn(resourceContent);
        Mockito.when(documentStorageService.load(comparator.getFilename())).thenReturn(comparatorContent);

        Mockito.when(documentRepository.save(resource)).thenReturn(resource);
        Mockito.when(documentRepository.save(comparator)).thenReturn(comparator);

        PlagiarismContent plagiarismContent = PlagiarismContent.builder().id("PLAGCONTENT").assignment(assignment).
                document1(resource).document2(resource).build();

        Mockito.when(plagiarismContentRepository.save(plagiarismContent)).thenReturn(plagiarismContent);

        plagiarismServiceRunners.documentCheckPlagiarism(resource,comparator);

        Mockito.verify(documentStorageService).load(resource.getFilename());
        Mockito.verify(documentStorageService).load(comparator.getFilename());

        Mockito.verify(documentRepository, Mockito.times(2)).
                save(documentArgumentCaptor.capture());

        List<Document> documentsCaptured = documentArgumentCaptor.getAllValues();

        Document resourceResult = documentsCaptured.get(0);
        Document comparatorResult = documentsCaptured.get(1);

        Mockito.verify(plagiarismContentRepository).save(plagiarismContentArgumentCaptor.capture());

        PlagiarismContent plagiarismContentResult = plagiarismContentArgumentCaptor.getValue();

        Mockito.verifyZeroInteractions(sourceCodeStorageService);

        Mockito.verifyNoMoreInteractions(documentRepository, documentStorageService, plagiarismContentRepository);

        Assert.assertEquals(resourceResult.getId(), resource.getId());
        Assert.assertEquals(comparatorResult.getId(), comparator.getId());

        Assert.assertTrue(plagiarismContentResult.getRate() > 99);
    }

    @Test
    public void documentCheckPlagiarism_DOC_TYPE_CODE_PLAGIARIZED_EXEC() throws Exception {
        resource = Document.builder().id("RESID").assignment(assignment).filename("RES.java").markAsPlagiarized(false).grade(0.0).build();
        comparator = Document.builder().id("COMID").assignment(assignment).filename("COM.java").markAsPlagiarized(false).grade(0.0).build();

        String resourceContent = "this is an identical content";
        String comparatorContent = "this is an identical content";

        Mockito.when(sourceCodeStorageService.load(resource.getFilename())).thenReturn(resourceContent);
        Mockito.when(sourceCodeStorageService.load(comparator.getFilename())).thenReturn(comparatorContent);
        // Mockito.doNothing().when(astPlagiarism).setup(resource.getFilename(), comparator.getFilename());
        // Mockito.when(astPlagiarism.getRates()).thenReturn(100.0);
        Mockito.when(documentRepository.save(resource)).thenReturn(resource);
        Mockito.when(documentRepository.save(comparator)).thenReturn(comparator);

        PlagiarismContent plagiarismContent = PlagiarismContent.builder().id("PLAGCONTENT").assignment(assignment).
                document1(resource).document2(resource).build();

        Mockito.when(plagiarismContentRepository.save(plagiarismContent)).thenReturn(plagiarismContent);

        plagiarismServiceRunners.documentCheckPlagiarism(resource,comparator);

        Mockito.verify(documentRepository, Mockito.times(2)).
                save(documentArgumentCaptor.capture());

        List<Document> documentsCaptured = documentArgumentCaptor.getAllValues();

        Document resourceResult = documentsCaptured.get(0);
        Document comparatorResult = documentsCaptured.get(1);

        Mockito.verify(plagiarismContentRepository).save(plagiarismContentArgumentCaptor.capture());

        PlagiarismContent plagiarismContentResult = plagiarismContentArgumentCaptor.getValue();

        Mockito.verifyZeroInteractions(documentStorageService);

        Mockito.verifyNoMoreInteractions(documentRepository, sourceCodeStorageService, plagiarismContentRepository);

        Assert.assertEquals(resourceResult.getId(), resource.getId());
        Assert.assertEquals(comparatorResult.getId(), comparator.getId());

        Assert.assertTrue(plagiarismContentResult.getRate() > 99);
    }

    @Test
    public void documentCheckPlagiarism_DOC_TYPE_DOCUMENTS_NOT_PLAGIARIZED_EXEC() throws Exception {
        resource = Document.builder().id("RESID").assignment(assignment).filename("RES.docx").markAsPlagiarized(false).grade(0.0).build();
        comparator = Document.builder().id("COMID").assignment(assignment).filename("COM.docx").markAsPlagiarized(false).grade(0.0).build();

        String resourceContent = "this is my document in docx";
        String comparatorContent = "why ya always copying me dude";

        Mockito.when(documentStorageService.load(resource.getFilename())).thenReturn(resourceContent);
        Mockito.when(documentStorageService.load(comparator.getFilename())).thenReturn(comparatorContent);

        Mockito.when(documentRepository.save(resource)).thenReturn(resource);
        Mockito.when(documentRepository.save(comparator)).thenReturn(comparator);
        Mockito.when(levensthein.rates(resourceContent, comparatorContent)).thenReturn(100.0);

        PlagiarismContent plagiarismContent = PlagiarismContent.builder().id("PLAGCONTENT").assignment(assignment).
                document1(resource).document2(resource).build();

        Mockito.when(plagiarismContentRepository.save(plagiarismContent)).thenReturn(plagiarismContent);

        plagiarismServiceRunners.documentCheckPlagiarism(resource,comparator);

        Mockito.verify(documentStorageService).load(resource.getFilename());
        Mockito.verify(documentStorageService).load(comparator.getFilename());

        Mockito.verify(plagiarismContentRepository).save(plagiarismContentArgumentCaptor.capture());

        PlagiarismContent plagiarismContentResult = plagiarismContentArgumentCaptor.getValue();

        Mockito.verifyZeroInteractions(sourceCodeStorageService);

        Mockito.verifyNoMoreInteractions(documentStorageService, plagiarismContentRepository);

        Assert.assertTrue(plagiarismContentResult.getRate() < 99);
    }

    @Test(expected = FileFormatException.class)
    public void documentCheckPlagiarism_UNSUPPORTED_FORMAT() throws Exception {
        resource = Document.builder().id("RESID").assignment(assignment).filename("RES.war").markAsPlagiarized(false).grade(0.0).build();
        comparator = Document.builder().id("COMID").assignment(assignment).filename("COM.war").markAsPlagiarized(false).grade(0.0).build();

        String resourceContent = "this is my document in war";
        String comparatorContent = "why ya always copying me dude";

        Mockito.when(documentStorageService.load(resource.getFilename())).thenReturn(resourceContent);
        Mockito.when(documentStorageService.load(comparator.getFilename())).thenReturn(comparatorContent);

        Mockito.when(documentRepository.save(resource)).thenReturn(resource);
        Mockito.when(documentRepository.save(comparator)).thenReturn(comparator);

        PlagiarismContent plagiarismContent = PlagiarismContent.builder().id("PLAGCONTENT").assignment(assignment).
                document1(resource).document2(resource).build();

        Mockito.when(plagiarismContentRepository.save(plagiarismContent)).thenReturn(plagiarismContent);

        plagiarismServiceRunners.documentCheckPlagiarism(resource,comparator);

        Mockito.verifyZeroInteractions(documentStorageService, plagiarismContentRepository, sourceCodeStorageService, documentRepository);

    }

    @After
    public void tearDown() throws Exception {
    }
}