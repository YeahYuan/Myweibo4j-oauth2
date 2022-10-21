package yuaner.service;

import weibo4j.Comments;
import weibo4j.Timeline;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.Comment;
import weibo4j.model.Status;
import weibo4j.model.StatusWapper;
import weibo4j.model.WeiboException;
import yuaner.consts.CommentBank;

import java.util.Objects;

import static yuaner.consts.CommonConst.ACCESS_TOKEN;
import static yuaner.consts.CommonConst.TAIL;

public class HuDong {

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

    public static void comment(String weiboId) {
        String comments = CommentBank.getOneComment() + TAIL;
        Comments cm = new Comments(ACCESS_TOKEN);
        try {
            cm.createComment(comments, weiboId);
        } catch (WeiboException e) {
            e.printStackTrace();
        }
    }

    public static void printWeibo(int count, Status status) {
        System.out.println("【" + count + "】[" + status.getId() + "]" + status.getUser().getScreenName() + ":" + status.getText());
    }

    public static void main(String[] args) throws WeiboException, InterruptedException {
        commentForFollowWeibo();
    }
}
