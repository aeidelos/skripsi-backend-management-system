package rizki.practicum.learning.configuration;

public interface FilesLocationConfig {
    String ROOT = "media";
    interface Image {
        String LOCATION = ROOT+"/photo";
        String INITIAL = "image";
        String[] FILE_EXTENSION_ALLOWED = {"image/jpeg","image/jpg","image/png"};
    }
    interface Document {
        String LOCATION = ROOT+"/document";
        String INITIAL = "document";
        String[] FILE_EXTENSION_ALLOWED = {"application/msword","application/pdf"};
    }
    interface SourceCode {
        String LOCATION = ROOT+"/sourcecode";
        String INITIAL = "sourcecode";
        String [] FILE_EXTENSION_ALLOWED = {"text/plain","text/x-java-source"};
    }
}
