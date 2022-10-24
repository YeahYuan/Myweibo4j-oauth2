package yuaner.service;

import weibo4j.Comments;
import weibo4j.Timeline;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.Comment;
import weibo4j.model.Status;
import weibo4j.model.StatusWapper;
import weibo4j.model.WeiboException;
import yuaner.consts.CommentBank;
import yuaner.consts.TokenBank;

import java.util.Objects;

import static yuaner.consts.CommentBank.getOneComment;
import static yuaner.consts.CommentBank.getOtherComment;
import static yuaner.consts.CommonConst.ACCESS_TOKEN;
import static yuaner.consts.CommonConst.TAIL;

public class HuDong {

    private static String previousComment = null;

    public static void commentForFollowWeibo() throws WeiboException, InterruptedException {
        Timeline tm = new Timeline(ACCESS_TOKEN);
        long limitWeiboId = 4827038030233609L;
        int count = 1;
        while (true) {
            long maxWeiboId = limitWeiboId;
            StatusWapper statusWapper = tm.getHomeTimeline();
            System.out.println("TotalNumber:" + statusWapper.getTotalNumber());
            System.out.println("Statuses size:" + statusWapper.getStatuses().size());
            for (Status status : statusWapper.getStatuses()) {
                String weiboIdStr = status.getId();
                long weiboId = Long.parseLong(weiboIdStr);
                maxWeiboId = Math.max(maxWeiboId, weiboId);
                if (weiboId <= limitWeiboId) {
                    limitWeiboId = maxWeiboId;
                    break;
                }
                printWeibo(count, status);
                comment(weiboIdStr);
                if (count % 5 == 0) {
                    Thread.sleep(1000 * 60 * 15);
                } else {
                    Thread.sleep(1000 * 60);
                }
                count++;
                Status retweetedStatus = status.getRetweetedStatus();
                if (Objects.nonNull(retweetedStatus)) {
                    printWeibo(count, retweetedStatus);
                    weiboIdStr = retweetedStatus.getId();
                    comment(weiboIdStr);
                    if (count % 5 == 0) {
                        Thread.sleep(1000 * 60 * 15);
                    } else {
                        Thread.sleep(1000 * 60);
                    }
                    count++;
                }
            }
            Thread.sleep(1000 * 60 * 5);
        }

    }

    public static void commentForUserWeibo(String userId) throws WeiboException, InterruptedException {
        Timeline tm = new Timeline(ACCESS_TOKEN);
        StatusWapper statusWapper = tm.getHomeTimeline();
        System.out.println("TotalNumber:" + statusWapper.getTotalNumber());
        System.out.println("Statuses size:" + statusWapper.getStatuses().size());
        int count = 1;
        for (Status status : statusWapper.getStatuses()) {
            printWeibo(count, status);
            String weiboId = status.getId();
            comment(weiboId);
            if (count % 5 == 0) {
                Thread.sleep(1000 * 60 * 15);
            } else {
                Thread.sleep(1000 * 60);
            }
            count++;
            Status retweetedStatus = status.getRetweetedStatus();
            if (Objects.nonNull(retweetedStatus)) {
                printWeibo(count, retweetedStatus);
                weiboId = retweetedStatus.getId();
                comment(weiboId);
                if (count % 5 == 0) {
                    Thread.sleep(1000 * 60 * 15);
                } else {
                    Thread.sleep(1000 * 60);
                }
                count++;
            }
        }

    }
    public static void comments() throws InterruptedException {
        String weiboId = "4828050502718748";
        String token = null;
        for (int j = 0; j < 10; j++) {
            token = TokenBank.getNextToken(token);
            for (int i = 0; i < 9; i++) {
                comment(weiboId, token, CommentBank.TYPE_CAI_HONG);
                System.out.println("【count】" + (i + 1));
                Thread.sleep(1000 * 65);
            }
            Thread.sleep(1000 * 60 * 5);
        }
    }

    public static void comment(String weiboId) {
        String comments = getOneComment() + TAIL;
        Comments cm = new Comments(ACCESS_TOKEN);
        try {
            cm.createComment(comments, weiboId);
        } catch (WeiboException e) {
            e.printStackTrace();
        }
    }

    public static void comment(String weiboId, String token) {
        String comments = getOneComment() + TAIL;
        Comments cm = new Comments(token);
        try {
            cm.createComment(comments, weiboId);
        } catch (WeiboException e) {
            e.printStackTrace();
        }
    }

    public static void comment(String weiboId, String token, String commentType) {
        previousComment = getOtherComment(commentType, HuDong.previousComment);
        String comments = previousComment + TAIL;
        Comments cm = new Comments(token);
        try {
            cm.createComment(comments, weiboId);
            System.out.println(previousComment);
        } catch (WeiboException e) {
            e.printStackTrace();
        }
    }

    public static void printWeibo(int count, Status status) {
        System.out.println("【" + count + "】[" + status.getId() + "]" + status.getUser().getScreenName() + ":" + status.getText());
    }

    public static void main(String[] args) throws WeiboException, InterruptedException {
//        commentForFollowWeibo();
//        comments();
        comment("4828171289759705", "2.002n_ooBqwBsbD0fe55780b5p_ixJD", CommentBank.TYPE_CAI_HONG);
    }
}
