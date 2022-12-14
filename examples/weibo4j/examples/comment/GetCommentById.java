package weibo4j.examples.comment;

import weibo4j.Comments;
import weibo4j.examples.oauth2.Log;
import weibo4j.http.HttpClient;
import weibo4j.http.Response;
import weibo4j.model.*;
import weibo4j.org.json.JSONArray;
import weibo4j.org.json.JSONException;
import weibo4j.org.json.JSONObject;
import weibo4j.util.ArrayUtils;
import weibo4j.util.WeiboConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetCommentById {

	protected static HttpClient client = new HttpClient();
	public final static int pageSize = 200;
	public static int count = 0;

	public static void main(String[] args) throws InterruptedException, WeiboException {
//		queryByOther();
//		queryByUrl();
		query();
	}

	public static void queryByUrl() throws WeiboException {
		String access_token = WeiboConfig.getValue("access_token");
		String id = "4823844303667720";
		String url = "https://m.weibo.cn/api/comments/show";
		int page = 1;
		int count = 1;

		HashMap<String, String> params = new HashMap<>();
		params.put("access_token", access_token);
		params.put("id", id);
		params.put("count", pageSize + "");
		params.put("page", page + "");
		PostParameter[] parList = ArrayUtils.mapToArray(params);
		Response res = client.get(url, parList, access_token);
		JSONObject json = res.asJSONObject(); //asJSONArray();
		try {
			JSONArray comments = json.getJSONObject("data").getJSONArray("data");
			int size = comments.length();
			List<Comment> commentList = new ArrayList<Comment>(size);
			for (int i = 0; i < size; i++) {
				Comment comment = new Comment(comments.getJSONObject(i));
				commentList.add(comment);
				System.out.println(count++ + "\t" + comment);
			}
		} catch (JSONException jsone) {
			throw new WeiboException(jsone);
		}

		for (; page < 6666; page++) {
			params.put("page", page + "");
			res = client.get(url, ArrayUtils.mapToArray(params), access_token);
			json = res.asJSONObject(); //asJSONArray();
			try {
				JSONArray comments = json.getJSONObject("data").getJSONArray("data");
				int size = comments.length();
				List<Comment> commentList = new ArrayList<Comment>(size);
				for (int i = 0; i < size; i++) {
					Comment comment = new Comment(comments.getJSONObject(i));
					commentList.add(comment);
					System.out.println(count++ + "\t" + comment);
				}
			} catch (JSONException jsone) {
				throw new WeiboException(jsone);
			}
		}



	}

	private static void populateResponse(Response res) throws WeiboException {
		JSONObject json = res.asJSONObject(); //asJSONArray();
		try {
			JSONArray comments = json.getJSONObject("data").getJSONArray("data");
			int size = comments.length();
			List<Comment> commentList = new ArrayList<Comment>(size);
			for (int i = 0; i < size; i++) {
				Comment comment = new Comment(comments.getJSONObject(i));
				commentList.add(comment);
				System.out.println(comment);
			}
		} catch (JSONException jsone) {
			throw new WeiboException(jsone);
		}
	}

	public static  void query() {
		String access_token = WeiboConfig.getValue("access_token");
//		String id = "4824411524304379";//1014??????
//		String id = "4824773911642149";//1015??????
//		1014????????? https://m.weibo.cn/2775934450/4824441718051321
		String id = "4824441718051321";
		//1014??????????????? https://m.weibo.cn/2775934450/4824517221026166
//		String id = "4824517221026166";
		long limitCommentId = 0L;
		Comments cm = new Comments(access_token);
		try {
			int page = 1;
			int count = 0;
			CommentWapper commentWapper = cm.getCommentById(id, new Paging(page, pageSize), 0);
			long pages = commentWapper.getTotalNumber() / pageSize + 1;
			System.out.println("??????" + commentWapper.getTotalNumber() + "???");
//			System.out.println("???" + page + "???," + commentWapper.getComments().size() + "???");
			for (Comment com : commentWapper.getComments()) {
				if (com.getId() <= limitCommentId) {
					return;
				}
				System.out.println(++count + "\t" + com.getId() + "\t" + com.getCreatedAt() + "\t" + com.getUser().getScreenName() + "\t" + com.getText());
			}
			for (page++; page <= pages; page ++) {
				Thread.sleep(1000);
				commentWapper = cm.getCommentById(id, new Paging(page, pageSize), 0);
//				System.out.println("???" + page + "???," + commentWapper.getComments().size() + "???");
				for (Comment com : commentWapper.getComments()) {
					if (com.getId() <= limitCommentId) {
						return;
					}
					System.out.println(++count + "\t" + com.getId() + "\t" + com.getCreatedAt() + "\t" + com.getUser().getScreenName() + "\t" + com.getText());
				}
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public static void queryByOther() {
		String access_token = WeiboConfig.getValue("access_token");
		String id = "4823844303667720";
		Comments cm = new Comments(access_token);
		HashMap<String, String> params = new HashMap<>();
		/*
		access_token	true	string	??????OAuth??????????????????????????????OAuth??????????????????
id	true	int64	?????????????????????ID???
since_id	false	int64	??????????????????????????????ID???since_id?????????????????????since_id?????????????????????????????????0???
max_id	false	int64	??????????????????????????????ID???????????????max_id?????????????????????0???
count	false	int	???????????????????????????????????????50???
page	false	int	?????????????????????????????????1???
filter_by_author	false	int	?????????????????????0????????????1?????????????????????2????????????????????????0???
		 */
		params.put("access_token", access_token);
		params.put("id", id);
		params.put("count", pageSize+"");
		String maxId = "0";
		params.put("max_id", maxId);

		try {
			int page = 1;
			System.out.println(params);
			CommentWapper commentWapper = cm.getCommentById(params);
			List<Comment> comments = commentWapper.getComments();
			Comment lastComment = comments.get(comments.size() - 1);
			maxId = lastComment.getMid();
			System.out.println("??????" + commentWapper.getTotalNumber() + "???");
			System.out.println("???" + page++ + "???," + comments.size() + "???");
			System.out.println("id=" + lastComment.getIdstr());
			System.out.println("mid=" + maxId);
			printComments(comments);

			while (true) {
				Thread.sleep(1000);
				params.put("max_id", "" + (Long.parseLong(maxId) - 1));
				System.out.println(params);
				commentWapper = cm.getCommentById(params);
				comments = commentWapper.getComments();
				if (comments.size() == 0) {
					return;
				}
				lastComment = comments.get(comments.size() - 1);
				maxId = lastComment.getMid();
				System.out.println("???" + page++ + "???," + comments.size() + "???");
				System.out.println("id=" + lastComment.getIdstr());
				System.out.println("mid=" + maxId);
				printComments(comments);
			}
		} catch (WeiboException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void printComments(List<Comment> comments) {
		for (Comment comment : comments) {
			printComment(comment);
		}
	}
	private static void printComment(Comment comment) {
		if (null == comment) {
			return;
		}
		System.out.println(++count + "\t" + comment.getId() + "\t" + comment.getUser().getScreenName() + "\t" + comment.getText());
//		printComment(comment.getReplycomment());
	}

}
