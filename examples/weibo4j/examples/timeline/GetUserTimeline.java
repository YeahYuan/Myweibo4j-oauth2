package weibo4j.examples.timeline;

import weibo4j.Timeline;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.StatusWapper;
import weibo4j.model.WeiboException;
import weibo4j.util.WeiboConfig;

public class GetUserTimeline {

	public static void main(String[] args) {
		String access_token = WeiboConfig.getValue("access_token");
		String uid = "1748405443";//素素
		Timeline tm = new Timeline(access_token);
		try {
			StatusWapper status = tm.getUserTimelineByUid(uid);
			Log.logInfo(status.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
