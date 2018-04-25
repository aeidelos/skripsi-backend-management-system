package rizki.practicum.learning.configuration;

public interface FilesLocationConfig {
    String ROOT = "media";
    interface Image {
        String LOCATION = ROOT+"/photo";
        String[] FILE_EXTENSION_ALLOWED = {"jpeg","jpg","png"};
    }
    interface Document {
        String LOCATION = ROOT+"/document";
        String [] FILE_EXTENSION_ALLOWED = {"docx","pdf","doc","odt"};
    }
    interface SourceCode {
        String LOCATION = ROOT+"/sourcecode";
        String [] FILE_EXTENSION_ALLOWED = {"java","txt","zip","php","js","html","css","py"};
    }
}
