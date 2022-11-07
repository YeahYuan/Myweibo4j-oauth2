package yuaner.service;

import weibo4j.Comments;
import weibo4j.Timeline;
import weibo4j.http.HttpClient;
import weibo4j.http.Response;
import weibo4j.model.PostParameter;
import weibo4j.model.Status;
import weibo4j.model.StatusWapper;
import weibo4j.model.WeiboException;
import yuaner.consts.CommentBank;
import yuaner.consts.TokenBank;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

import static yuaner.consts.CommentBank.getComment;
import static yuaner.consts.CommentBank.getKeyComment;
import static yuaner.consts.CommonConst.ACCESS_TOKEN;
import static yuaner.consts.CommonConst.TAIL;

public class HuDong {

    private static String previousComment = null;
    private static Queue<String> weiboQueue = new LinkedList<>();

    static {
        weiboQueue.offer("4831742311013096");
    }

    public static void commentForFollowWeibo() throws WeiboException, InterruptedException {
        Timeline tm = new Timeline(ACCESS_TOKEN);
        long limitWeiboId = 4827796285430562L;
        int count = 1;
        while (count <= 50) {
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
                if (status.getUser().getId().equals("2461615292")) {
                    //发电站
                    continue;
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
                if (Objects.nonNull(retweetedStatus) && status.getUser().getId().equals("6201040390")) {
                    //吸猫现场转发
                    printWeibo(count, retweetedStatus);
                    weiboIdStr = retweetedStatus.getId();
                    commentForXiMaoZhuan(status.getText(), weiboIdStr);
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
        String comments = getComment() + TAIL;
        Comments cm = new Comments(ACCESS_TOKEN);
        try {
            cm.createComment(comments, weiboId);
        } catch (WeiboException e) {
            e.printStackTrace();
        }
    }
    public static void commentForXiMaoZhuan(String text, String weiboId) {
        String keyComment = getKeyComment(text);
        String comments;
        if (null == keyComment) {
            comments = getComment() + TAIL;
        } else {
            comments = keyComment + TAIL;
        }
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

    public static void printWeibo(int count, Status status) {
        System.out.println("【" + count + "】[" + status.getId() + "]" + status.getUser().getScreenName() + ":" + status.getText());
    }

    public static void main(String[] args) throws WeiboException, InterruptedException {
        HttpClient httpClient = new HttpClient();
        Response response = httpClient.post("https://api.weibo.com/2/attitudes/create.json",
            new PostParameter[] {
                    new PostParameter("source", "3308000132"),
                    new PostParameter("attitude", "smile"),
                    new PostParameter("id", "4833164541300052"),
                    new PostParameter("access_token", ACCESS_TOKEN)
            }, ACCESS_TOKEN);
        System.out.println(response.asJSONObject());

//        commentForFollowWeibo();

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
