package weibo4j.examples.comment;

import weibo4j.Comments;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.CommentWapper;
import weibo4j.model.WeiboException;
import weibo4j.util.WeiboConfig;

public class GetCommentTimeline {

	public static void main(String[] args) {
		String access_token = WeiboConfig.getValue("access_token");
		Comments cm = new Comments(access_token);
		try {
			CommentWapper comment = cm.getCommentTimeline();
			Log.logInfo(comment.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
