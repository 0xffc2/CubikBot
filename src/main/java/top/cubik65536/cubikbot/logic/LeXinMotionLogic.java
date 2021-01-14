package top.cubik65536.cubikbot.logic;

import com.IceCreamQAQ.Yu.annotation.AutoBind;
import top.cubik65536.cubikbot.entity.MotionEntity;
import top.cubik65536.cubikbot.entity.QQLoginEntity;
import top.cubik65536.cubikbot.pojo.Result;

import java.io.IOException;

@AutoBind
public interface LeXinMotionLogic {
    byte[] getCaptchaImage(String phone) throws IOException;
    Result<String> getCaptchaCode(String phone, String captchaImageCode) throws IOException;
    Result<MotionEntity> loginByPassword(String phone, String password) throws IOException;
    Result<MotionEntity> loginByQQ(QQLoginEntity qqLoginEntity) throws IOException;
    Result<MotionEntity> loginByPhoneCaptcha(String phone, String captchaPhoneCode) throws IOException;
    String modifyStepCount(int step, MotionEntity motionEntity) throws IOException;
    String bindBand(MotionEntity motionEntity) throws IOException;
}
