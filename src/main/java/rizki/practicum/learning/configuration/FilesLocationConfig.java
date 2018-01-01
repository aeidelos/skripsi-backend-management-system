package rizki.practicum.learning.configuration;

public interface FilesLocationConfig {
    String ROOT = "media";
    interface Image {
        final String LOCATION = ROOT+"/photo";
        final String[] FILE_EXTENSION_ALLOWED = {"image/jpeg","image/jpg","image/png"};
    }
    interface Document {
        final String LOCATION = ROOT+"/document";
        final String[] FILE_EXTENSION_ALLOWED = {"application/msword","application/pdf"};
    }
    interface SourceCode {
        String LOCATION = ROOT+"/sourcecode";
        String [] FILE_EXTENSION_ALLOWED = {"text/plain","text/x-java-source"};
    }
}
