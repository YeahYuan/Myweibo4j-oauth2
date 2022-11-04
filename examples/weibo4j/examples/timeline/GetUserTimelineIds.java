package weibo4j.examples.timeline;

import weibo4j.Timeline;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.UserTimelineIds;
import weibo4j.model.WeiboException;
import weibo4j.util.WeiboConfig;

public class GetUserTimelineIds {
	
	public static void main(String[] args) {
		String access_token = WeiboConfig.getValue("access_token");
		String uid = "2419941665";//解释
		Timeline tm = new Timeline(access_token);
		try {
			UserTimelineIds ids = tm.getUserTimelineIdsByUid(uid);
			Log.logInfo(ids.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}
}
