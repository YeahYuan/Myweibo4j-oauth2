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
//		String id = "4824411524304379";//1014商务
//		String id = "4824773911642149";//1015盖楼
//		1014高露洁 https://m.weibo.cn/2775934450/4824441718051321
		String id = "4824441718051321";
		//1014高露洁转发 https://m.weibo.cn/2775934450/4824517221026166
//		String id = "4824517221026166";
		long limitCommentId = 0L;
		Comments cm = new Comments(access_token);
		try {
			int page = 1;
			int count = 0;
			CommentWapper commentWapper = cm.getCommentById(id, new Paging(page, pageSize), 0);
			long pages = commentWapper.getTotalNumber() / pageSize + 1;
			System.out.println("共计" + commentWapper.getTotalNumber() + "条");
//			System.out.println("第" + page + "页," + commentWapper.getComments().size() + "条");
			for (Comment com : commentWapper.getComments()) {
				if (com.getId() <= limitCommentId) {
					return;
				}
				System.out.println(++count + "\t" + com.getId() + "\t" + com.getCreatedAt() + "\t" + com.getUser().getScreenName() + "\t" + com.getText());
			}
			for (page++; page <= pages; page ++) {
				Thread.sleep(1000);
				commentWapper = cm.getCommentById(id, new Paging(page, pageSize), 0);
//				System.out.println("第" + page + "页," + commentWapper.getComments().size() + "条");
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
		access_token	true	string	采用OAuth授权方式为必填参数，OAuth授权后获得。
id	true	int64	需要查询的微博ID。
since_id	false	int64	若指定此参数，则返回ID比since_id大的评论（即比since_id时间晚的评论），默认为0。
max_id	false	int64	若指定此参数，则返回ID小于或等于max_id的评论，默认为0。
count	false	int	单页返回的记录条数，默认为50。
page	false	int	返回结果的页码，默认为1。
filter_by_author	false	int	作者筛选类型，0：全部、1：我关注的人、2：陌生人，默认为0。
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
			System.out.println("共计" + commentWapper.getTotalNumber() + "条");
			System.out.println("第" + page++ + "页," + comments.size() + "条");
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
				System.out.println("第" + page++ + "页," + comments.size() + "条");
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
