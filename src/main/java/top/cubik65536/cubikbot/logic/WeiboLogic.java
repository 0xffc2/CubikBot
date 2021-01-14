package top.cubik65536.cubikbot.logic;

import com.IceCreamQAQ.Yu.annotation.AutoBind;
import top.cubik65536.cubikbot.entity.QQLoginEntity;
import top.cubik65536.cubikbot.entity.WeiboEntity;
import top.cubik65536.cubikbot.pojo.Result;
import top.cubik65536.cubikbot.pojo.WeiboPojo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@AutoBind
public interface WeiboLogic {
    List<String> hotSearch() throws IOException;
    Result<List<WeiboPojo>> getIdByName(String name) throws IOException;
    String convertStr(WeiboPojo weiboPojo);
    Result<List<WeiboPojo>> getWeiboById(String id) throws IOException;
    String getCaptchaUrl(String pcId);
    Result<Map<String, String>> login(Map<String, String> map, String door) throws IOException;
    WeiboEntity loginSuccess(String cookie, String referer, String url) throws IOException;
    Result<Map<String, String>> preparedLogin(String username, String password) throws IOException;
    Result<Map<String, String>> loginBySms1(String token) throws IOException;
    Result<WeiboEntity> loginBySms2(String token, String phone, String code) throws IOException;
    Result<WeiboEntity> loginByPrivateMsg(String token) throws IOException;
    Result<WeiboEntity> loginByQQ(QQLoginEntity qqLoginEntity) throws IOException;
    Map<String, String> loginByQr1() throws IOException;
    Result<WeiboEntity> loginByQr2(String id) throws IOException;
    Result<List<WeiboPojo>> getFriendWeibo(WeiboEntity weiboEntity) throws IOException;
    Result<List<WeiboPojo>> getMyWeibo(WeiboEntity weiboEntity) throws IOException;
    Result<List<WeiboPojo>> weiboTopic(String keyword) throws IOException;
    String like(WeiboEntity weiboEntity, String id) throws IOException;
    String comment(WeiboEntity weiboEntity, String id, String commentContent) throws IOException;
    String forward(WeiboEntity weiboEntity, String id, String content, String picUrl) throws IOException;
    String getUserInfo(String id) throws IOException;
    String publishWeibo(WeiboEntity weiboEntity, String content, List<String> url) throws IOException;
    String removeWeibo(WeiboEntity weiboEntity, String id) throws IOException;
    String favoritesWeibo(WeiboEntity weiboEntity, String id) throws IOException;
    String shortUrl(WeiboEntity weiboEntity, String url) throws IOException;
}
