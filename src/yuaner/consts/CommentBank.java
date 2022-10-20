package yuaner.consts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentBank {

    public static final String TYPE_COMMON = "common";
    public static final Map<String, List <String>> commentMap;

     static {
         commentMap = new HashMap<>();
         ArrayList<String> commonComments = new ArrayList<>();
         commonComments.add("好棒！");
         commonComments.add("支持支持~");
         commonComments.add("好哦~");
         commonComments.add("好哇~");
         commonComments.add("好赞~");
         commonComments.add("厉害厉害！");
         commonComments.add("敬你一杯！");
         commonComments.add("祝你事业顺利！");
         commonComments.add("您忙吧 免回 ^ ^");
         commonComments.add("喵喵~");
         commentMap.put(TYPE_COMMON, commonComments);
     }

     public static String getOneComment() {
         List<String> commonComments = commentMap.get(TYPE_COMMON);
         int index = (int) (Math.random() * commonComments.size());
         return commonComments.get(index);
     }
}
