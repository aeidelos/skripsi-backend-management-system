package rizki.practicum.learning.service.plagiarism;
/*
    Created by : Rizki Maulana Akbar, On 04 - 2018 ;
*/

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class Levensthein {

    public double getRates(String s1, String s2) {
        int max = Math.max(s1.length(), s2.length());
        return max == 0 ? 0.0D : distance(s1, s2) / (double)max;
    }
    public int distance(String s1, String s2) {
        if (s1 == null) {
            throw new NullPointerException("s1 must not be null");
        } else if (s2 == null) {
            throw new NullPointerException("s2 must not be null");
        } else if (s1.equals(s2)) {
            return 0;
        } else if (s1.length() == 0) {
            return s2.length();
        } else if (s2.length() == 0) {
            return s1.length();
        } else {
            int[] v0 = new int[s2.length() + 1];
            int[] v1 = new int[s2.length() + 1];

            int i;
            for (i = 0; i < v0.length;  i++) {
                v0[i] = i ;
            }
            for (i = 0; i < s1.length(); ++i) {
                v1[0] = i + 1;
                for (int j = 0; j < s2.length(); ++j) {
                    int cost = 1;
                    if (s1.charAt(i) == s2.charAt(j)) {
                        cost = 0;
                    }
                    v1[j + 1] = Math.min(v1[j] + 1, Math.min(v0[j + 1] + 1, v0[j] + cost));
                }
                int[] vtemp = v0;
                v0 = v1;
                v1 = vtemp;
            }
            return v0[s2.length()];
        }
    }
}
