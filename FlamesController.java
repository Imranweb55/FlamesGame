package flames.game.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlamesController {

    @GetMapping("/flames")
    public String getFlames(@RequestParam String name1, @RequestParam String name2) {
        int balance = computeBalance(name1, name2);
        String result = mapResult(balance);
        return "" + balance + "  Result = " + result;
    }

    private int computeBalance(String fn, String sn) {
        if (fn == null) fn = "";
        if (sn == null) sn = "";

        fn = fn.toLowerCase();
        sn = sn.toLowerCase();

        int[] freq1 = new int[26];
        int[] freq2 = new int[26];

        for (char c : fn.toCharArray()) if (Character.isLetter(c)) freq1[c - 'a']++;
        for (char c : sn.toCharArray()) if (Character.isLetter(c)) freq2[c - 'a']++;

        for (int i = 0; i < 26; i++) {
            int cancel = Math.min(freq1[i], freq2[i]);
            freq1[i] -= cancel;
            freq2[i] -= cancel;
        }

        int balance = 0;
        for (int i = 0; i < 26; i++) balance += freq1[i] + freq2[i];
        return balance;
    }

    private String mapResult(int balance) {
        // Normalize so we always map into 1..8 range (if you used 1..8 mapping)
        int k = balance % 8;
        if (k == 0) k = 8;
        switch (k) {
            case 1: return "Sister";
            case 2: return "Enemy";
            case 3: return "Friend";
            case 4: return "Sister";
            case 5: return "Friend";
            case 6: return "Marriage";
            case 7: return "Enemy";
            case 8: return "Affection";
            case 9: return "Enemy";
            case 10: return "Love";
            case 11: return "Marriage";
            case 12: return "Affection";
            case 13: return "Love";
            case 14: return "Friend";
            case 15: return "Marriage";
            default: return "UNKNOWN";
        }
    }
}