package top.cubik65536.cubikbot.controller;

import com.IceCreamQAQ.Yu.annotation.Action;
import com.IceCreamQAQ.Yu.annotation.Before;
import com.IceCreamQAQ.Yu.annotation.Config;
import com.IceCreamQAQ.Yu.annotation.Synonym;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.icecreamqaq.yuq.FunKt;
import com.icecreamqaq.yuq.annotation.GroupController;
import com.icecreamqaq.yuq.annotation.PathVar;
import com.icecreamqaq.yuq.annotation.QMsg;
import com.icecreamqaq.yuq.controller.ContextSession;
import com.icecreamqaq.yuq.entity.Group;
import com.icecreamqaq.yuq.entity.Member;
import com.icecreamqaq.yuq.job.RainInfo;
import com.icecreamqaq.yuq.message.Image;
import com.icecreamqaq.yuq.message.Message;
import com.icecreamqaq.yuq.message.MessageItemFactory;
import okhttp3.Response;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import top.cubik65536.cubikbot.entity.ConfigEntity;
import top.cubik65536.cubikbot.entity.GroupEntity;
import top.cubik65536.cubikbot.logic.QQAILogic;
import top.cubik65536.cubikbot.logic.ToolLogic;
import top.cubik65536.cubikbot.pojo.Result;
import top.cubik65536.cubikbot.service.ConfigService;
import top.cubik65536.cubikbot.service.GroupService;
import top.cubik65536.cubikbot.service.MessageService;
import top.cubik65536.cubikbot.utils.BotUtils;
import top.cubik65536.cubikbot.utils.OkHttpUtils;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")
@GroupController
public class ToolController {
    @Inject
    private ToolLogic toolLogic;
    @Inject
    private GroupService groupService;
    @Inject
    private QQAILogic qqAiLogic;
    @Inject
    private ConfigService configService;
    @Inject
    private MessageService messageService;
    @Inject
    private RainInfo rainInfo;
    @Inject
    private MessageItemFactory mif;

    @Before
    public GroupEntity before(long group, Member qq) {
        GroupEntity groupEntity = groupService.findByGroup(group);
        return groupEntity;
    }

    @Config("YuQ.Mirai.protocol")
    private String protocol;

    private final LocalDateTime startTime;

    public ToolController() {
        startTime = LocalDateTime.now();
    }

    @QMsg(at = true)
    @Action("百度 {content}")
    public String teachYouBaidu(String content) throws IOException {
        return toolLogic.teachYou(content, "baidu");
    }

    @QMsg(at = true)
    @Action("谷歌 {content}")
    public String teachYouGoogle(String content) throws IOException {
        return toolLogic.teachYou(content, "google");
    }

    @QMsg(at = true)
    @Action("bing {content}")
    public String teachYouBing(String content) throws IOException {
        return toolLogic.teachYou(content, "bing");
    }

    @QMsg(at = true)
    @Action("搜狗 {content}")
    public String teachYouSouGou(String content) throws IOException {
        return toolLogic.teachYou(content, "sougou");
    }

    @QMsg(at = true, atNewLine = true)
    @Action("舔狗日记")
    public String dogLicking() throws IOException {
        return toolLogic.dogLicking();
    }

    @QMsg(at = true, atNewLine = true)
    @Action("百科 {params}")
    public String baiKe(String params) throws IOException {
        return toolLogic.baiKe(params);
    }

    @QMsg(at = true, atNewLine = true)
    @Action("嘴臭")
    @Synonym({"祖安语录"})
    public String mouthOdor() throws IOException {
        return toolLogic.mouthOdor();
    }

    @QMsg(at = true)
    @Action("毒鸡汤")
    public String poisonousChickenSoup() throws IOException {
        return toolLogic.poisonousChickenSoup();
    }

    @QMsg(at = true)
    @Action("名言")
    public String saying() throws IOException {
        return toolLogic.saying();
    }

    @QMsg(at = true)
    @Action("一言")
    public String hiToKoTo() throws IOException {
        return toolLogic.hiToKoTo().get("text");
    }

    @Action("缩短/{params}")
    @QMsg(at = true)
    public String shortUrl(String params){
        return BotUtils.shortUrl(params);
    }

    @Action("ip/{params}")
    @QMsg(at = true)
    public String queryIp(String params) throws IOException {
        return toolLogic.queryIp(params);
    }

    @Action("whois/{params}")
    @QMsg(at = true, atNewLine = true)
    public String queryWhois(String params) throws IOException {
        return toolLogic.queryWhois(params);
    }

    @Action("icp/{params}")
    @QMsg(at = true, atNewLine = true)
    public String queryIcp(String params) throws IOException {
        return toolLogic.queryIcp(params);
    }

    @Action("知乎日报")
    @QMsg(at = true, atNewLine = true)
    public String zhiHuDaily() throws IOException {
        return toolLogic.zhiHuDaily();
    }

    @QMsg(at = true, atNewLine = true)
    @Action("测吉凶")
    public String qqGodLock(long qq) throws IOException {
        return toolLogic.qqGodLock(qq);
    }

    @QMsg(at = true, atNewLine = true)
    @Action("拼音/{params}")
    public String convertPinYin(String params) throws IOException {
        return toolLogic.convertPinYin(params);
    }

    @QMsg(at = true, atNewLine = true)
    @Action("笑话")
    public String jokes() throws IOException {
        return toolLogic.jokes();
    }

    @QMsg(at = true, atNewLine = true)
    @Action("垃圾/{params}")
    public String rubbish(String params) throws IOException {
        return toolLogic.rubbish(params);
    }

    @Action("解析/{url}")
    @QMsg(at = true, atNewLine = true)
    public String parseVideo(String url) throws IOException {
        return toolLogic.parseVideo(url);
    }

    @Action("还原/{url}")
    @QMsg(at = true)
    public String restoreShortUrl(String url) throws IOException {
        return toolLogic.restoreShortUrl(url);
    }

    @Action("ping/{domain}")
    @QMsg(at = true, atNewLine = true)
    public String ping(String domain) throws IOException {
        return toolLogic.ping(domain);
    }

    @Action("搜 {question}")
    @QMsg(at = true)
    public String search(String question) throws IOException {
        return toolLogic.searchQuestion(question);
    }

    @Action("色图")
    public Message colorPic(long group, long qq) throws IOException {
        GroupEntity groupEntity = groupService.findByGroup(group);
        if (groupEntity == null || groupEntity.getColorPic() == null || !groupEntity.getColorPic())
            return FunKt.getMif().at(qq).plus("该功能已关闭！！");
        String type = groupEntity.getColorPicType();
        if ("lolicon".equals(type) || "loliconR18".equals(type)){
            ConfigEntity configEntity = configService.findByType("loLiCon");
            if (configEntity == null) return FunKt.getMif().at(qq).plus("您还没有配置lolicon的apiKey，无法获取色图！！");
            String apiKey = configEntity.getContent();
            Result<Map<String, String>> result = toolLogic.colorPicByLoLiCon(apiKey, type.equals("loliconR18"));
            Map<String, String> map = result.getData();
            if (map == null) return FunKt.getMif().at(qq).plus(result.getMessage());
            byte[] by = toolLogic.piXivPicProxy(map.get("url"));
            return FunKt.getMif().imageByInputStream(new ByteArrayInputStream(by)).toMessage();
        }else if (type.contains("danbooru")){
            String[] arr = type.split("-");
            String danType = null;
            if (arr.length > 1) danType = arr[1];
            String url;
            if (danType == null) url = "https://api.kuku.me/danbooru";
            else url = "https://api.kuku.me/danbooru?type=" + danType;
            Response response = OkHttpUtils.get(url);
            if (response.header("content-type") != null){
                return FunKt.getMif().at(qq).plus("danbooru的tags类型不匹配，请重新设置tags类型，具体tag类型可前往https://danbooru.donmai.us/" +
                        "查看，如果tag中带空格，请用_替换");
            }else {
                byte[] bytes = OkHttpUtils.getBytes(response);
                return FunKt.getMif().imageByInputStream(new ByteArrayInputStream(bytes)).toMessage();
            }
        }else return Message.Companion.toMessage("色图类型不匹配！！");
    }

    @Action("qr/{content}")
    @QMsg(at = true, atNewLine = true)
    public Message creatQrCode(String content) throws IOException {
        byte[] bytes = toolLogic.creatQr(content);
        return FunKt.getMif().imageByInputStream(new ByteArrayInputStream(bytes)).toMessage();
    }

    @Action("看美女")
    public Image girl() throws IOException {
        return FunKt.getMif().imageByUrl(toolLogic.girlImage());
    }

    @QMsg(at = true)
    @Action("蓝奏 {url}")
    public String lanZou(String url, @PathVar(2) String pwd) throws UnsupportedEncodingException {
        String resultUrl;
        if (pwd == null)
            resultUrl = "https://v1.alapi.cn/api/lanzou?url=" + URLEncoder.encode(url, "utf-8");
        else resultUrl = "https://v1.alapi.cn/api/lanzou?url=" + URLEncoder.encode(url, "utf-8") + "&pwd=$pwd";
        return BotUtils.shortUrl(resultUrl);
    }

    @Action("lol周免")
    @QMsg(at = true, atNewLine = true)
    public String lolFree() throws IOException {
        return toolLogic.lolFree();
    }

    @Action("缩写/{content}")
    @QMsg(at = true, atNewLine = true)
    public String abbreviation(String content) throws IOException {
        return toolLogic.abbreviation(content);
    }

    @Action("几点了")
    public Image time() throws IOException {
        return FunKt.getMif().imageByInputStream(new ByteArrayInputStream(toolLogic.queryTime()));
    }

    @Action("\\^BV.*\\")
    @Synonym({"\\^bv.*\\"})
    @QMsg(at = true)
    public Message bvToAv(Message message) throws IOException {
        String bv = message.getBody().get(0).toPath();
        Result<Map<String, String>> result = toolLogic.bvToAv(bv);
        if (result.getCode() == 200){
            Map<String, String> map = result.getData();
            MessageItemFactory mif = FunKt.getMif();
            return mif.imageByUrl(map.get("pic")).plus(
                    "标题：" + map.get("title") + "\n" +
                            "描述：" + map.get("desc") +
                            "链接：" + map.get("url")
            );
        }else return Message.Companion.toMessage(result.getMessage());
    }

    @Action("知乎热榜")
    @QMsg(at = true, atNewLine = true)
    public String zhiHuHot() throws IOException {
        List<Map<String, String>> list = toolLogic.zhiHuHot();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++){
            Map<String, String> map = list.get(i);
            sb.append(i + 1).append("、").append(map.get("title")).append("\n");
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    @Action("分词")
    @QMsg(at = true, atNewLine = true)
    public String wordSegmentation(long qq, ContextSession session, Group group) throws IOException {
        group.sendMessage(FunKt.getMif().at(qq).plus("请输入需要中文分词的内容！！"));
        Message nextMessage = session.waitNextMessage();
        return toolLogic.wordSegmentation(Message.Companion.firstString(nextMessage));
    }

    @Action("acg")
    public Image acgPic() throws IOException {
        return FunKt.getMif().imageByUrl(toolLogic.acgPic());
    }

    @Action("搜图 {img}")
    @QMsg(at = true)
    public Message searchImage(Image img) throws IOException {
        String url = toolLogic.identifyPic(img.getUrl());
        if (url != null) return FunKt.getMif().imageByUrl(img.getUrl()).plus(url);
        else return Message.Companion.toMessage("没有找到这张图片！！！");
    }

    @Action("OCR {img}")
    @Synonym({"ocr {img}"})
    @QMsg(at = true, atNewLine = true)
    public String ocr(Image img) throws IOException {
        return qqAiLogic.generalOCR(img.getUrl());
    }

    @Action("github加速 {url}")
    @QMsg(at = true)
    public String githubQuicken(ContextSession session, long qq, String url){
        return BotUtils.shortUrl(toolLogic.githubQuicken(url));
    }

    @Action("traceroute {domain}")
    @Synonym({"路由追踪 {domain}"})
    public String traceRoute(String domain) throws IOException {
        return toolLogic.traceRoute(domain);
    }

    @Action("查发言数")
    public String queryMessage(Group group){
        Map<Long, Long> map = messageService.findCountQQByGroupAndToday(group.getId());
        StringBuilder sb = new StringBuilder().append("本群今日发言数统计如下：").append("\n");
        for (Map.Entry<Long, Long> entry: map.entrySet()){
            sb.append("@").append(group.get(entry.getKey()).nameCardOrName())
                    .append("（").append(entry.getKey()).append("）").append("：")
                    .append(entry.getValue()).append("条").append("\n");
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    @Action("语音合成 {text}")
    public Message voice(String text, Group group, long qq) throws IOException {
        Result<byte[]> result = qqAiLogic.voiceSynthesis(text);
        if (result.getCode() == 200){
            return FunKt.getMif().voiceByByteArray(result.getData()).toMessage();
        }else return FunKt.getMif().at(qq).plus(result.getMessage());
    }

    @QMsg(at = true)
    @Action("防红 {url}")
    public String preventRed(String url) throws IOException {
        return toolLogic.preventQQRed(url);
    }

    @Action("点歌 {name}")
    public Object musicFromQQ(String name) throws IOException {
        String xmlStr = toolLogic.songByQQ(name);
        return mif.xmlEx(2, xmlStr);
    }

    @Action("统计")
    @Synonym({"运行状态"})
    public String status(){
        SystemInfo systemInfo = new SystemInfo();
        CentralProcessor processor = systemInfo.getHardware().getProcessor();
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        // 睡眠1s
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long cSys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;
        GlobalMemory memory = systemInfo.getHardware().getMemory();
        //总内存
        long totalByte = memory.getTotal();
        //剩余
        long acaliableByte = memory.getAvailable();
        Properties props = System.getProperties();
        //系统名称
        String osName = props.getProperty("os.name");
        //架构名称
        String osArch = props.getProperty("os.arch");
        Runtime runtime = Runtime.getRuntime();
        //jvm总内存
        long jvmTotalMemoryByte = runtime.totalMemory();
        //jvm最大可申请
        long jvmMaxMoryByte = runtime.maxMemory();
        //空闲空间
        long freeMemoryByte = runtime.freeMemory();
        //jdk版本
        String jdkVersion = props.getProperty("java.version");
        //jdk路径
        String jdkHome = props.getProperty("java.home");
        LocalDateTime nowTime = LocalDateTime.now();
        Duration duration = Duration.between(startTime, nowTime);
        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;
        String ss = days + "天" + hours + "小时" + minutes + "分钟";
        return  "程序运行时长：" + ss + "\n" +
                "cpu核数：" + processor.getLogicalProcessorCount() + "\n" +
                "cpu当前使用率：" + new DecimalFormat("#.##%").format(1.0-(idle * 1.0 / totalCpu)) + "\n" +
                "总内存：" + formatByte(totalByte) + "\n" +
                "已使用内存：" + formatByte(totalByte-acaliableByte) + "\n" +
                "操作系统：" + osName + "\n" +
                "系统架构：" + osArch + "\n" +
                "jvm内存总量：" + formatByte(jvmTotalMemoryByte) + "\n" +
                "jvm已使用内存：" + formatByte(jvmTotalMemoryByte-freeMemoryByte) + "\n" +
                "java版本：" + jdkVersion;
    }

    @Action("消息统计")
    public String message(){
        return "当前收发消息状态：\n" +
                "收：" + rainInfo.getCountRm() + " / 分钟\n" +
                "发：" + rainInfo.getCountSm() + " / 分钟\n" +
                "总计：\n" +
                "收：" + rainInfo.getCountRa() + " 条，\n" +
                "发：" + rainInfo.getCountSa() + " 条。";
    }

    private String formatByte(long byteNumber){
        //换算单位
        double FORMAT = 1024.0;
        double kbNumber = byteNumber/FORMAT;
        if(kbNumber<FORMAT){
            return new DecimalFormat("#.##KB").format(kbNumber);
        }
        double mbNumber = kbNumber/FORMAT;
        if(mbNumber<FORMAT){
            return new DecimalFormat("#.##MB").format(mbNumber);
        }
        double gbNumber = mbNumber/FORMAT;
        if(gbNumber<FORMAT){
            return new DecimalFormat("#.##GB").format(gbNumber);
        }
        double tbNumber = gbNumber/FORMAT;
        return new DecimalFormat("#.##TB").format(tbNumber);
    }

    @Action("genshin {id}")
    public String queryGenShinUserInfo(long id) throws IOException {
        return toolLogic.genShinUserInfo(id);
    }

    @Action("cosplay")
    public Message cosplay() throws IOException {
        return FunKt.getMif().imageByByteArray(toolLogic.cosplay()).toMessage();
    }

    @Action("写真")
    public Image photo() throws IOException {
        return FunKt.getMif().imageByByteArray(toolLogic.photo());
    }

    @Action("kuku上传 {image}")
    @QMsg(at = true)
    public String uploadImage(Image image){
        try {
            return "您上传的图片链接如下：" + toolLogic.uploadImage(OkHttpUtils.getBytes(image.getUrl()));
        } catch (IOException e) {
            e.printStackTrace();
            return "图片上传失败，请稍后再试！！";
        }
    }

    @Action("抽象话 {word}")
    @QMsg(at = true)
    public String abstractWords(String word) {
        return "抽象话如下：\n" + toolLogic.abstractWords(word);
    }

    @Action("学习 {learn} {say}")
    @QMsg(at = true)
    public String learn(ContextSession session, long qq, GroupEntity groupEntity, Group group, @PathVar(2) String type, String learn, String say) {
        if (learn.equals("草")) {
            return "无法学习该词汇！";
        }
        MessageItemFactory mif = FunKt.getMif();
        JSONObject jsonObject = new JSONObject();
        JSONArray aJsonArray = BotUtils.messageToJsonArray(mif.text(say).toMessage());
        jsonObject.put("q", learn);
        jsonObject.put("a", aJsonArray);
        if (type == null) type = "PARTIAL";
        if (!"ALL".equalsIgnoreCase(type)) type = "PARTIAL";
        else type = "ALL";
        jsonObject.put("type", type);
        JSONArray jsonArray = groupEntity.getLearnJsonArray();
        jsonArray.add(jsonObject);
        groupEntity.setLearnJsonArray(jsonArray);
        groupService.save(groupEntity);
        return "学习成功！快对我说 " + learn + " 试试吧！";
    }

    @Action("复读 {word}")
    @QMsg(at = true)
    public String learn(ContextSession session, long qq, GroupEntity groupEntity, Group group, @PathVar(2) String type, String word) {
        String message = learn(session, qq, groupEntity, group, type, word, word);
        return message;
    }

    @Action("忘记 {word}")
    @QMsg(at = true)
    public String delLearn(GroupEntity groupEntity, String word) {
        JSONArray learnJsonArray = groupEntity.getLearnJsonArray();
        List<JSONObject> delList = new ArrayList<>();
        for (int i = 0; i < learnJsonArray.size(); i++) {
            JSONObject jsonObject = learnJsonArray.getJSONObject(i);
            if (word.equals(jsonObject.getString("q"))) {
                delList.add(jsonObject);
            }
        }
        delList.forEach(learnJsonArray::remove);
        groupEntity.setLearnJsonArray(learnJsonArray);
        groupService.save(groupEntity);
        return "我已经忘记啦！";
    }

}
