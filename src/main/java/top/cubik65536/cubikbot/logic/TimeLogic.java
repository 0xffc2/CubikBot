package top.cubik65536.cubikbot.logic;

import com.IceCreamQAQ.Yu.annotation.AutoBind;

@AutoBind
public interface TimeLogic {
    boolean isInTime(String beginTimeString, String endTimeString);

    boolean isInTimeSec(String beginTimeString, String endTimeString);
}
