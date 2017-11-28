package rizki.practicum.learning.util;

import lombok.Getter;
import lombok.Setter;

public class Confirmation {
    @Setter @Getter
    boolean success = false;
    @Setter @Getter
    String message = "";
    public Confirmation(boolean success, String message){
        this.success = success;
        this.message = message;
    }
    public Confirmation(){}

}
