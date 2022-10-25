package yuaner.service;

import weibo4j.Comments;
import weibo4j.Timeline;
import weibo4j.model.Paging;
import weibo4j.model.Status;
import weibo4j.model.StatusWapper;
import weibo4j.model.WeiboException;
import yuaner.consts.CommentBank;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static yuaner.consts.CommonConst.ACCESS_TOKEN;
import static yuaner.consts.CommonConst.TAIL;

public class ChanChu {
    private final static Integer PAGE_COUNT = 100;
    private static Pattern datePattern = Pattern.compile("\\d{8}");

    public static void getChanChu() throws WeiboException, InterruptedException {
        Timeline tm = new Timeline(ACCESS_TOKEN);
        int page = 1;
        int count = 1;
        while (true) {
            Paging paging = new Paging(page, PAGE_COUNT);
            StatusWapper statusWapper = tm.getHomeTimeline(0, 0, paging);
            System.out.println("page:" + page);
            System.out.println("Statuses size:" + statusWapper.getStatuses().size());
            for (Status status : statusWapper.getStatuses()) {
                Status retweetedStatus = status.getRetweetedStatus();
                if (status.getUser().getId().equals("6201040390") && Objects.nonNull(retweetedStatus)) {
                    //吸猫现场转发
                    String postText = status.getText();
                    Matcher matcher = datePattern.matcher(postText);
                    String date;
                    if (matcher.find()) {
                        date = matcher.group();
                    } else {
                        continue;
                    }
                    String keyWord = postText.substring(postText.indexOf("【")+1,postText.indexOf("】"));
                    String text = retweetedStatus.getText();
                    String url = "https://m.weibo.cn/" + retweetedStatus.getUser().getId() + "/" + retweetedStatus.getId();
                    int repostsCount = retweetedStatus.getRepostsCount();
                    int commentsCount = retweetedStatus.getCommentsCount();
                    int attitudesCount = retweetedStatus.getAttitudesCount();
                    String userName = retweetedStatus.getUser().getScreenName();
                    System.out.println(count++ + "\t" + date + "\t" + keyWord + "\t" + userName + "\t" + repostsCount + "\t" + commentsCount + "\t" + attitudesCount + "\t" + url + "\t" + postText);
                    count++;

                }
            }
            Thread.sleep(1000 * 60);
            page++;
        }

    }

    public static void comment(String weiboId) {
        String comments = CommentBank.getComment() + TAIL;
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
        getChanChu();
    }
}
