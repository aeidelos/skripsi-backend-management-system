package rizki.practicum.learning.service.plagiarism;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class ASTPlagiarismTest {

    CompilationUnit originFileCompilation;
    CompilationUnit comparatorFileCompilation;

    ASTPlagiarism astPlagiarism;


    @Before
    public void before() throws IOException {
        astPlagiarism = new ASTPlagiarism("media/RES.java", "media/COM.java");
        originFileCompilation = astPlagiarism.originFileCompilation;
        comparatorFileCompilation = astPlagiarism.comparatorFileCompilation;
    }

    @Test
    public void getPlagiarism_SAMEPARENT() {
        List<Node> result = astPlagiarism.getPlagiarism(originFileCompilation.getChildNodes(), originFileCompilation.getChildNodes());
        Assert.assertEquals(result.size(), 1);
    }

    @Test
    public void getPlagiarism_DifferentParentWithoutExplicitLoops() {
        List<Node> result = astPlagiarism.getPlagiarism(originFileCompilation.getChildNodes(), comparatorFileCompilation.getChildNodes());
        Assert.assertTrue(result.size()>1);
    }

    @Test
    public void getPlagiarism_LoopsInNode3() {
        List<Node> result = astPlagiarism.getPlagiarism(originFileCompilation.getChildNodes().get(0).getChildNodes(), comparatorFileCompilation.getChildNodes().get(0).getChildNodes());
        Assert.assertTrue(result.size() > 1);
    }

    @Test
    public void getPlagiarism_LoopsInNode2() {
        List<Node> result = astPlagiarism.getPlagiarism(comparatorFileCompilation.getChildNodes().get(0).getChildNodes(), originFileCompilation.getChildNodes().get(0).getChildNodes());
        Assert.assertTrue(result.size() > 1);
    }
}