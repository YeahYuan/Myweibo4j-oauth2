package weibo4j.examples.timeline;

import weibo4j.Timeline;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.Status;
import weibo4j.model.WeiboException;
import weibo4j.util.URLEncodeUtils;
import weibo4j.util.WeiboConfig;

import static weibo4j.examples.comment.CreateComment.TAIL;

public class UpdateStatus {

	public static void main(String[] args) {
		String access_token = WeiboConfig.getValue("access_token");
		String statuses = URLEncodeUtils.encodeURL("好诶，我学会发微博啦！" + TAIL);
		Timeline tm = new Timeline(access_token);
		try {
			Status status = tm.updateStatusNew(statuses);
			Log.logInfo(status.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}	
	}

}
