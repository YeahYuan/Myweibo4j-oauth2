package yuaner.consts;

import java.util.ArrayList;
import java.util.List;

public class TokenBank {
    public static List<String> tokenList = new ArrayList();

    static {
        tokenList.add("2.00L4pldCqwBsbD4399cde4cfRhl69B");
        tokenList.add("2.00VaIcVIqwBsbDe84a28d90cfTOMcD");
    }

    public static String getNextToken(String token) {
        if (null == token) {
            return tokenList.get(0);
        }
        int size = tokenList.size();
        for (int i = 0; i < size; i++) {
            if (token.equals(tokenList.get(i))) {
                return tokenList.get(i == size - 1 ? 0 : i + 1);
            }
        }
        return tokenList.get(0);
    }

    public static void main(String[] args) {
        String token = getNextToken(null);
        System.out.println(token);
        token = getNextToken(token);
        System.out.println(token);
        token = getNextToken(token);
        System.out.println(token);
        token = getNextToken(token);
        System.out.println(token);
        token = getNextToken(token);
        System.out.println(token);
    }
}
