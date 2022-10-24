package yuaner.consts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentBank {

    public static final String TYPE_COMMON = "common";
    public static final String TYPE_CAI_HONG = "cauhong";
    public static final Map<String, List <String>> commentMap;

     static {
         commentMap = new HashMap<>();
         ArrayList<String> commonComments = new ArrayList<>();
         commonComments.add("好棒！");
         commonComments.add("支持支持~");
         commonComments.add("厉害厉害！");
         commonComments.add("好哦~");
         commonComments.add("敬你一杯！");
         commonComments.add("祝你事业顺利！");
         commonComments.add("好哇~");
         commonComments.add("您忙吧 免回 ^ ^");
         commonComments.add("喵喵~");
         commonComments.add("好赞~");
         commonComments.add("咱家汽水棒棒！");
         commonComments.add("你说得对");
         commonComments.add("万物复苏，你最醒目！");
         commonComments.add("猫猫瞪我！");
         commonComments.add("宝宝棒棒！");
         commonComments.add("妈妈爱你！");
         commentMap.put(TYPE_COMMON, commonComments);

         ArrayList<String> caiHongComments = new ArrayList<>();
         caiHongComments.add("同是万物生长的命理，为何苏醒就这么好看");
         caiHongComments.add("四海八荒千秋万世我只喜欢苏醒");
         caiHongComments.add("我有话想和苏醒说，但是苏醒你看天亮了。");
         caiHongComments.add("说不清为什么爱苏醒，但苏醒就是我不爱别人的理由。");
         caiHongComments.add("做苏醒最爱甜蜜且乖看着苏醒，我喝白开水都会醉。");
         caiHongComments.add("在遇到苏醒之前，我喜欢春天，因为那是树丫新绿，万花重开的日子，直到遇到苏醒，我天天都能看到百花齐放。");
         caiHongComments.add("我在船头。失岸于朦胧的扁舟,由苏醒在帆尾,挂了一弯半月。月先坠入水,晕不出涟漪。我才知道,这是苏醒眼中的银河里,我们摘到的梦。");
         caiHongComments.add("以前喜欢满天的星星，现在喜欢看月亮。还很喜欢万物都静下来的夜晚，像一个无声的怀抱，更喜欢苏醒");
         caiHongComments.add("我想我要因为以危险方法危害公共安全被抓走了。因为一看见苏醒，我就心动到方圆十里都在地震。");
         commentMap.put(TYPE_CAI_HONG, caiHongComments);
     }

     public static String getOneComment() {
         List<String> commonComments = commentMap.get(TYPE_COMMON);
         int index = (int) (Math.random() * commonComments.size());
         return commonComments.get(index) + (int)(Math.random() * 100);
     }

     public static String getOtherComment(String comment) {
         return getOtherComment(TYPE_COMMON, comment);
     }

     public static String getOtherComment(String type, String comment) {
         List<String> commonComments = commentMap.get(type);
         int index = (int) (Math.random() * commonComments.size());
         String otherComment = commonComments.get(index);
         if (otherComment.equals(comment)) {
             return getOtherComment(type, comment);
         }
//         System.out.println(otherComment);
         return otherComment;
     }

    public static void main(String[] args) {
        String otherComment = getOtherComment(TYPE_CAI_HONG, null);
        otherComment = getOtherComment(TYPE_CAI_HONG, otherComment);
        otherComment = getOtherComment(TYPE_CAI_HONG, otherComment);
        otherComment = getOtherComment(TYPE_CAI_HONG, otherComment);
        otherComment = getOtherComment(TYPE_CAI_HONG, otherComment);
        otherComment = getOtherComment(TYPE_CAI_HONG, otherComment);
        otherComment = getOtherComment(TYPE_CAI_HONG, otherComment);
        otherComment = getOtherComment(TYPE_CAI_HONG, otherComment);
        otherComment = getOtherComment(TYPE_CAI_HONG, otherComment);
        otherComment = getOtherComment(TYPE_CAI_HONG, otherComment);
        otherComment = getOtherComment(TYPE_CAI_HONG, otherComment);
        otherComment = getOtherComment(TYPE_CAI_HONG, otherComment);
        otherComment = getOtherComment(TYPE_CAI_HONG, otherComment);
        otherComment = getOtherComment(TYPE_CAI_HONG, otherComment);
    }
}
