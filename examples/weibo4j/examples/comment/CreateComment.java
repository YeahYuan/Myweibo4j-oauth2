package weibo4j.examples.comment;

import weibo4j.Comments;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.Comment;
import weibo4j.model.WeiboException;
import weibo4j.util.URLEncodeUtils;
import weibo4j.util.WeiboConfig;

public class CreateComment {
	public final static String TAIL = "——来自笨蛋机器人的小尾巴";

	public static void main(String[] args) {
		String access_token = WeiboConfig.getValue("access_token");
		String comments = "瓶瓶棒棒！" + TAIL;
		String id = "4826557410185394";
		Comments cm = new Comments(access_token);
		try {
			Comment comment = cm.createComment(comments, id);
			Log.logInfo(comment.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}
}
