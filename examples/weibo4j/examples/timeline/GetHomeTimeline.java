package weibo4j.examples.timeline;

import weibo4j.Timeline;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.StatusWapper;
import weibo4j.model.WeiboException;
import weibo4j.util.WeiboConfig;

public class GetHomeTimeline {

	public static void main(String[] args) {
		String access_token = WeiboConfig.getValue("access_token");
		Timeline tm = new Timeline(access_token);
		try {
			StatusWapper status = tm.getHomeTimeline();
			Log.logInfo(status.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
