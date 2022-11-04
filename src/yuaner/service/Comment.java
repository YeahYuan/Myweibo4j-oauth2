package yuaner.service;

import weibo4j.Comments;
import weibo4j.Timeline;
import weibo4j.model.Status;
import weibo4j.model.StatusWapper;
import weibo4j.model.WeiboException;
import yuaner.consts.CommentBank;
import yuaner.consts.TokenBank;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

import static yuaner.consts.CommentBank.getComment;
import static yuaner.consts.CommonConst.ACCESS_TOKEN;
import static yuaner.consts.CommonConst.TAIL;

public class Comment {
    private static Queue<String> weiboQueue = new LinkedList<>();

    static {
        weiboQueue.offer("4831742311013096");
    }

    public static void comments() throws InterruptedException {
        int count = 1;
        for (int j = 0; j < 30; j++) {
            TokenBank.UserToken userToken = TokenBank.getNextToken();
            String token = userToken.getToken();
            for (int i = 0; i < 5; i++) {
                String weiboId = weiboQueue.poll();
                weiboQueue.offer(weiboId);
                commentWithoutTail(weiboId, token, CommentBank.TYPE_CAI_HONG);
                userToken.setCommentCount(userToken.getCommentCount() + 1);
                System.out.println("【total_count】" + count++ + ", "  + userToken.getUsername() + "【count】" + userToken.getCommentCount());
                Thread.sleep(1000 * 70);
            }
            Thread.sleep(1000 * 60 * 10);
        }
    }

    public static void comment(String weiboId) {
        String comments = getComment() + TAIL;
        Comments cm = new Comments(ACCESS_TOKEN);
        try {
            cm.createComment(comments, weiboId);
        } catch (WeiboException e) {
            e.printStackTrace();
        }
    }

    public static void comment(String weiboId, String token) {
        String comments = getComment() + TAIL;
        Comments cm = new Comments(token);
        try {
            cm.createComment(comments, weiboId);
        } catch (WeiboException e) {
            e.printStackTrace();
        }
    }

    public static void comment(String weiboId, String token, String commentType) {
        String comments = getComment(commentType) + TAIL;
        Comments cm = new Comments(token);
        try {
            cm.createComment(comments, weiboId);
            System.out.println(comments);
        } catch (WeiboException e) {
            e.printStackTrace();
        }
    }
    public static void commentWithoutTail(String weiboId, String token, String commentType) {
        String comments = getComment(commentType);
        Comments cm = new Comments(token);
        try {
            cm.createComment(comments, weiboId);
            System.out.println(comments);
        } catch (WeiboException e) {
            e.printStackTrace();
        }
    }

    public static void printWeibo(int count, Status status) {
        System.out.println("【" + count + "】[" + status.getId() + "]" + status.getUser().getScreenName() + ":" + status.getText());
    }

    public static void main(String[] args) throws WeiboException, InterruptedException {
//        commentForFollowWeibo();
        comments();
//        tokenList.add("2.00L4pldCqwBsbD4399cde4cfRhl69B");//解释
//        tokenList.add("2.00VaIcVIqwBsbDe84a28d90cfTOMcD");//机器人
//        tokenList.add("2.00uZfLmDqwBsbD7fd5c6d637BCroOC");//奶茶
//        tokenList.add("2.002n_ooBqwBsbD0fe55780b5p_ixJD");//恋爱
//        commentWithoutTail("4828110900170443", "2.00L4pldCqwBsbD4399cde4cfRhl69B", CommentBank.TYPE_CAI_HONG);
//        commentWithoutTail("4828110900170443", "2.00uZfLmDqwBsbD7fd5c6d637BCroOC", CommentBank.TYPE_CAI_HONG);
//        commentWithoutTail("4828110900170443", "2.00VaIcVIqwBsbDe84a28d90cfTOMcD", CommentBank.TYPE_CAI_HONG);
//        commentWithoutTail("4828110900170443", "2.002n_ooBqwBsbD0fe55780b5p_ixJD", CommentBank.TYPE_CAI_HONG);
    }
}
