package yuaner.consts;

import java.util.*;

public class TokenBank {

    public static Queue<UserToken> tokenQueue = new LinkedList<>();
    public static Map<String, String> tokenMap = new HashMap<>();
    public static List<String> tokenList = new ArrayList();

    static {
//        tokenList.add("2.00uZfLmDqwBsbD7fd5c6d637BCroOC");//奶茶
        tokenList.add("2.00L4pldCqwBsbD4399cde4cfRhl69B");//解释
        tokenList.add("2.00VaIcVIqwBsbDe84a28d90cfTOMcD");//机器人
        tokenList.add("2.002n_ooBqwBsbD3d55a5eb090sl6Ap");//恋爱

//        tokenQueue.offer(new UserToken("AS_喝不完一杯奶茶", "2.00uZfLmDqwBsbD7fd5c6d637BCroOC", 0));
//        tokenQueue.offer(new UserToken("汽水牌机器人", "2.00VaIcVIqwBsbDe84a28d90cfTOMcD", 0));
        tokenQueue.offer(new UserToken("你一定要听我解释", "2.00L4pldCqwBsbD4399cde4cfRhl69B", 0));
        tokenQueue.offer(new UserToken("檀个小小恋爱", "2.002n_ooBqwBsbD3d55a5eb090sl6Ap", 0));

        tokenMap.put("AS_喝不完一杯奶茶", "2.00uZfLmDqwBsbD7fd5c6d637BCroOC");
        tokenMap.put("汽水牌机器人", "2.00VaIcVIqwBsbDe84a28d90cfTOMcD");
        tokenMap.put("你一定要听我解释", "2.00L4pldCqwBsbD4399cde4cfRhl69B");
        tokenMap.put("檀个小小恋爱", "2.002n_ooBqwBsbD3d55a5eb090sl6Ap");
    }

    public static UserToken getNextToken() {
        UserToken userToken = tokenQueue.poll();
        tokenQueue.offer(userToken);
        return userToken;
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

    public static class UserToken {
        private String username;
        private String token;
        private int commentCount;

        public UserToken(String username, String token, int commentCount) {
            this.username = username;
            this.token = token;
            this.commentCount = commentCount;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }
    }
}
