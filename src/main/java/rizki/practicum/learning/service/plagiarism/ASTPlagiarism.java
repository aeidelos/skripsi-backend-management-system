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

@Component
public class ASTPlagiarism {
    CompilationUnit originFileCompilation;
    CompilationUnit comparatorFileCompilation;
    public void setup(String file1, String file2) throws IOException {
        String originCode = new String(Files.readAllBytes(Paths.get(file1)), Charset.defaultCharset());
        originCode = originCode.replaceAll("(?m)^[ \t]*\r?\n", "");
        originFileCompilation = JavaParser.parse(originCode);

        String comparatorCode = new String(Files.readAllBytes(Paths.get(file2)), Charset.defaultCharset());
        comparatorCode = comparatorCode.replaceAll("(?m)^[ \t]*\r?\n", "");
        comparatorFileCompilation = JavaParser.parse(comparatorCode);

        originFileCompilation.getComments().forEach(r -> r.remove());
        comparatorFileCompilation.getComments().forEach(r -> r.remove());

        originFileCompilation.removePackageDeclaration();
        comparatorFileCompilation.removePackageDeclaration();

        int originImport = originFileCompilation.getImports().size();
        int comparatorImport = comparatorFileCompilation.getImports().size();

        for(int i = 0; i< originImport; i++) {
            originFileCompilation.getImports().removeFirst();
        }
        for(int i = 0; i< comparatorImport; i++) {
            comparatorFileCompilation.getImports().removeFirst();
        }
    }

    public Double getRates() {
        List<Node> plagiarized = getPlagiarism(originFileCompilation.getParentNodeForChildren().getChildNodes(),
                comparatorFileCompilation.getChildNodes());
        List<Node> perLine = plagiarized.stream().
                filter(p -> p.getRange().get().begin.line == p.getRange().get().end.line)
                .collect(Collectors.toList());
        plagiarized.removeAll(perLine);
        Set<Integer> lineDetected = new HashSet<>();
        plagiarized.stream().forEach(line -> {
            for(int i = line.getRange().get().begin.line; i<line.getRange().get().end.line; i++) {
                lineDetected.add(i);
            }
        });
        class NodeCounter {
            int counter;
            Node node;
        }

        HashMap<Integer, NodeCounter> counterLine = new HashMap<>();
        perLine.stream().forEach(node -> {
            int lineTemp = node.getRange().get().begin.line;
            NodeCounter nodeCounter = null;
            if(counterLine.get(lineTemp) == null) {
                nodeCounter = new NodeCounter();
                nodeCounter.node = node;
            } else{
                nodeCounter = counterLine.get(lineTemp);
                nodeCounter.counter++;
            }
            counterLine.put(lineTemp, nodeCounter);
        });

        counterLine.forEach((key, value) -> {
            if(value.counter > value.node.getChildNodes().size()) {
                lineDetected.add(key);
            }
        });

        int originSizeLine = originFileCompilation.getRange().get().end.line -
                originFileCompilation.getRange().get().begin.line;

        int comparatorSizeLine = comparatorFileCompilation.getRange().get().end.line -
                comparatorFileCompilation.getRange().get().begin.line;

        int maxSizeLine = originSizeLine > comparatorSizeLine ? originSizeLine : comparatorSizeLine;

        return (double) lineDetected.size() / maxSizeLine;
    }

    public List<Node> getPlagiarism(List<Node> origin, List<Node> comparator) {
        List<Node> plag = new ArrayList<>();
        List<Node> larger;
        List<Node> smaller;
        if(origin.size() != comparator.size()) {
            larger = origin.size() > comparator.size() ? origin : comparator;
            smaller = origin.size() < comparator.size() ? origin : comparator;
        }else{
            smaller = origin;
            larger = comparator;
        }
        smaller.stream().sorted(Comparator.comparing(Node::hashCode));
        larger.stream().sorted(Comparator.comparing(Node::hashCode));
        for(int i = 0; i<smaller.size(); i++) {
            for(int j = 0; j<larger.size(); j++) {
                if(smaller.hashCode() == larger.hashCode()) {
                    plag.add(smaller.get(i));
                }else{
                    plag.addAll(getPlagiarism(smaller.get(i).getChildNodes(),
                            larger.get(j).getChildNodes()));
                }
            }
        }
        return plag;
    }

}
