package yuaner.consts;

import java.util.*;

public class CommentBank {

    public static final String TYPE_COMMON = "common";
    public static final String TYPE_CAI_HONG = "cauhong";
    public static final Map<String, Queue<String>> commentMap;
    public static final Map<String, String> keyMap;

     static {
         commentMap = new HashMap<>();
         Queue<String> commonQueue = new LinkedList<>();
         commonQueue.offer("好棒！");
         commonQueue.offer("支持支持~");
         commonQueue.offer("厉害厉害！");
         commonQueue.offer("好哦~");
         commonQueue.offer("敬你一杯！");
         commonQueue.offer("祝你事业顺利！");
         commonQueue.offer("好哇~");
         commonQueue.offer("您忙吧 免回 ^ ^");
         commonQueue.offer("喵喵~");
         commonQueue.offer("好赞~");
         commonQueue.offer("咱家汽水棒棒！");
         commonQueue.offer("你说得对");
         commonQueue.offer("万物复苏，你最醒目！");
         commonQueue.offer("猫猫瞪我！");
         commonQueue.offer("宝宝棒棒！");
         commonQueue.offer("妈妈爱你！");
         commentMap.put(TYPE_COMMON, commonQueue);

         Queue<String> caiHongQueue = new LinkedList<>();
         caiHongQueue.offer("同是万物生长的命理，为何苏醒就这么好看");
         caiHongQueue.offer("四海八荒千秋万世我只喜欢苏醒");
         caiHongQueue.offer("我有话想和苏醒说，但是苏醒你看天亮了。");
         caiHongQueue.offer("说不清为什么爱苏醒，但苏醒就是我不爱别人的理由。");
         caiHongQueue.offer("做苏醒最爱甜蜜且乖看着苏醒，我喝白开水都会醉。");
         caiHongQueue.offer("在遇到苏醒之前，我喜欢春天，因为那是树丫新绿，万花重开的日子，直到遇到苏醒，我天天都能看到百花齐放。");
         caiHongQueue.offer("我在船头。失岸于朦胧的扁舟,由苏醒在帆尾,挂了一弯半月。月先坠入水,晕不出涟漪。我才知道,这是苏醒眼中的银河里,我们摘到的梦。");
         caiHongQueue.offer("以前喜欢满天的星星，现在喜欢看月亮。还很喜欢万物都静下来的夜晚，像一个无声的怀抱，更喜欢苏醒");
         caiHongQueue.offer("我想我要因为以危险方法危害公共安全被抓走了。因为一看见苏醒，我就心动到方圆十里都在地震。");
         commentMap.put(TYPE_CAI_HONG, caiHongQueue);

         keyMap = new LinkedHashMap<>();
         keyMap.put("醒年今日", "考古");
         keyMap.put("特刊", "特刊");
         keyMap.put("repo", "repo");
         keyMap.put("录屏", "录屏");
         keyMap.put("cut", "CUT");
         keyMap.put("CUT", "CUT");
         keyMap.put("截修", "截修");
         keyMap.put("饭绘", "产出");
         keyMap.put("混剪", "剪辑");
         keyMap.put("视频", "剪辑");
         keyMap.put("上班", "上班");
         keyMap.put("下班", "下班");
         keyMap.put("接机", "接送");
         keyMap.put("送机", "接送");
         keyMap.put("壁纸", "壁纸");
         keyMap.put("汇总", "整理");
         keyMap.put("整理", "整理");
         keyMap.put("总结", "整理");
         keyMap.put("打卡", "打卡");
         keyMap.put("应援", "应援");
         keyMap.put("饭制", "产出");
         keyMap.put("醒动信号", "日报");
         keyMap.put("考古", "考古");
         keyMap.put("路透", "路透");
     }

     public static String getComment() {
         return getComment(TYPE_COMMON);
     }

     public static String getComment(String type) {
         Queue<String> commonQueue = commentMap.get(type);
         String comment = commonQueue.poll();
         commonQueue.offer(comment);
         return comment;
     }

     public static String getKeyComment(String text) {
         for (String key : keyMap.keySet()) {
             if (text.contains(key)) {
                 return "谢谢你，" + keyMap.get(key) + "侠[送花花]";
             }
         }
         return null;
     }

    public static void main(String[] args) {
        System.out.println(getComment(TYPE_CAI_HONG));
        System.out.println(getKeyComment("20221104【醒动信号】【repo】@苏醒AllenSu"));
    }
}
