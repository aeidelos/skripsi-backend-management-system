package rizki.practicum.learning.util.hash;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class MD5 {
    public static String generate(String text){
        return Base64.getEncoder().encodeToString(DigestUtils.md5Digest(text.getBytes()));
    }
}
