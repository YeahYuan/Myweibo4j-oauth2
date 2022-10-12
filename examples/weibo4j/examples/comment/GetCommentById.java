package weibo4j.examples.comment;

import weibo4j.Comments;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.Comment;
import weibo4j.model.CommentWapper;
import weibo4j.model.Paging;
import weibo4j.model.WeiboException;
import weibo4j.util.WeiboConfig;

public class GetCommentById {

	public final static int pageSize = 1000;

	public static void main(String[] args) throws InterruptedException {
		String access_token = WeiboConfig.getValue("access_token");
		String id = "4823844303667720";
		Comments cm = new Comments(access_token);
		try {
			int page = 1;
			int count = 0;
			CommentWapper comment = cm.getCommentById(id, new Paging(page, pageSize), 0);
			long pages = comment.getTotalNumber() / pageSize + 1;
			for (Comment com : comment.getComments()) {
				System.out.println(++count + " " + com.getUser().getScreenName() + ":" + com.getText());
			}
			for (page++; page <= pages; page ++) {
				Thread.sleep(1000);
				comment = cm.getCommentById(id, new Paging(page, pageSize), 0);
				for (Comment com : comment.getComments()) {
					System.out.println(++count + " " + com.getUser().getScreenName() + ":" + com.getText());
				}
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
