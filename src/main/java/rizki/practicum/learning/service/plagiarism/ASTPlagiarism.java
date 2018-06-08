package rizki.practicum.learning.service.plagiarism;
/*
    Created by : Rizki Maulana Akbar, On 04 - 2018 ;
*/

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class ASTPlagiarism {
    CompilationUnit originFileCompilation;
    CompilationUnit comparatorFileCompilation;
    Double rates = 0.0;

    public ASTPlagiarism(String file1, String file2) throws IOException {
        // read file, preprocess by replacing tab and blank lines and parse
        String originCode = new String(Files.readAllBytes(Paths.get(file1)), Charset.defaultCharset());
        originCode = originCode.replaceAll("(?m)^[ \t]*\r?\n", "");
        originFileCompilation = JavaParser.parse(originCode);

        String comparatorCode = new String(Files.readAllBytes(Paths.get(file2)), Charset.defaultCharset());
        comparatorCode = comparatorCode.replaceAll("(?m)^[ \t]*\r?\n", "");
        comparatorFileCompilation = JavaParser.parse(comparatorCode);

        // remove comments
        originFileCompilation.getComments().forEach(r -> r.remove());
        comparatorFileCompilation.getComments().forEach(r -> r.remove());

        // remove package declaration
        originFileCompilation.removePackageDeclaration();
        comparatorFileCompilation.removePackageDeclaration();

        // get size import statement line
        int originImport = originFileCompilation.getImports().size();
        int comparatorImport = comparatorFileCompilation.getImports().size();

        // remove each import statement
        for(int i = 0; i< originImport; i++) {
            originFileCompilation.getImports().removeFirst();
        }
        for(int i = 0; i< comparatorImport; i++) {
            comparatorFileCompilation.
                    getImports().removeFirst();
        }
        // get rates similarity
        rates = getRates() * 100.0;
    }


    public List<Node> getPlagiarism(List<Node> origin, List<Node> comparator) {
        List<Node> plag = new ArrayList<>();
        // sorting by hash code
        origin.stream().sorted(Comparator.comparing(Node::hashCode));
        comparator.stream().sorted(Comparator.comparing(Node::hashCode));
        int inc = 0;
        // comparing each node
        for(int i = 0; i<origin.size(); i++) {
            for(int j = inc; j<comparator.size(); j++) {
                if(origin.get(i).hashCode() == comparator.get(j).hashCode()) {
                    // if hash code is same
                    plag.add(origin.get(i)); // add node to detected plag
                    inc++;
                    break;
                }else{
                    // recursive by using child nodes
                    plag.addAll(getPlagiarism(origin.get(i).getChildNodes(),
                            comparator.get(j).getChildNodes()));
                }
            }
        }
        return plag;
    }

    public Double getRates() {
        List<Node> plagiarized = getPlagiarism(originFileCompilation.getParentNodeForChildren().getChildNodes(),
                comparatorFileCompilation.getChildNodes());
        // sum node and divide by original size to get rates
        int sum = plagiarized.size() == 0 ? 0 : plagiarized.stream().mapToInt(plag -> plag.toString().length()).sum() + 1;
        int size = originFileCompilation.toString().length() > comparatorFileCompilation.toString().length() ? originFileCompilation.toString().length() : comparatorFileCompilation.toString().length();
        return (double) sum/size;
    }

}
