package top.cubik65536.cubikbot.controller;

import com.IceCreamQAQ.Yu.annotation.Action;
import com.IceCreamQAQ.Yu.annotation.Before;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.icecreamqaq.yuq.FunKt;
import com.icecreamqaq.yuq.annotation.GroupController;
import com.icecreamqaq.yuq.entity.Member;
import com.icecreamqaq.yuq.message.Image;
import com.icecreamqaq.yuq.message.Message;
import top.cubik65536.cubikbot.entity.ConfigEntity;
import top.cubik65536.cubikbot.entity.GroupEntity;
import top.cubik65536.cubikbot.logic.TimeLogic;
import top.cubik65536.cubikbot.logic.ToolLogic;
import top.cubik65536.cubikbot.pojo.Result;
import top.cubik65536.cubikbot.service.ConfigService;
import top.cubik65536.cubikbot.service.GroupService;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

@GroupController
@SuppressWarnings("unused")
public class PictureController {
    @Inject
    private ToolLogic toolLogic;
    @Inject
    private TimeLogic timeLogic;
    @Inject
    private GroupService groupService;
    @Inject
    private ConfigService configService;

    private boolean enabled;

    @Before
    public void before(long group, Member qq) {
        GroupEntity groupEntity = groupService.findByGroup(group);
        enabled = true;
        JSONArray jsonArray = groupEntity.getPicTimeJsonArray();
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        String beginTimeString = jsonObject.getString("start");
        String endTimeString = jsonObject.getString("end");
        System.out.println("getPicTimeJsonArray = \n" + jsonArray);
        if (groupEntity == null || groupEntity.getColorPic() == null || !groupEntity.getColorPic()) {
            enabled = false;
            throw FunKt.getMif().at(qq).plus("该功能已关闭！").toThrowable();
        } else if (!timeLogic.isInTime(beginTimeString, endTimeString)) {
            enabled = false;
            throw FunKt.getMif().at(qq).plus("没有到达开启时间！每日开启时间为：" + beginTimeString + "，结束时间为：" + endTimeString).toThrowable();
        } else {
            enabled = true;
        }
    }

    @Action("色图")
    public Message colorPic(long group, long qq) throws IOException {
        if (!enabled) return null;
        GroupEntity groupEntity = groupService.findByGroup(group);
        String type = groupEntity.getColorPicType();
        if ("lolicon".equals(type) || "loliconR18".equals(type)) {
            ConfigEntity configEntity = configService.findByType("loLiCon");
            if (configEntity == null) return FunKt.getMif().at(qq).plus("您还没有配置lolicon的apiKey，无法获取色图！！");
            String apiKey = configEntity.getContent();
            Result<Map<String, String>> result = toolLogic.colorPicByLoLiCon(apiKey, type.equals("loliconR18"));
            Map<String, String> map = result.getData();
            if (map == null) return FunKt.getMif().at(qq).plus(result.getMessage());
            byte[] by = toolLogic.piXivPicProxy(map.get("url"));
            return FunKt.getMif().imageByInputStream(new ByteArrayInputStream(by)).toMessage();
        } else return Message.Companion.toMessage("色图类型不匹配！！");
    }

    @Action("看美女")
    public Image girl() throws IOException {
        if (!enabled) return null;
        return FunKt.getMif().imageByUrl(toolLogic.girlImage());
    }

    @Action("cosplay")
    public Message cosplay() throws IOException {
        if (!enabled) return null;
        return FunKt.getMif().imageByByteArray(toolLogic.cosplay()).toMessage();
    }

    @Action("写真")
    public Image photo() throws IOException {
        if (!enabled) return null;
        return FunKt.getMif().imageByByteArray(toolLogic.photo());
    }

}
