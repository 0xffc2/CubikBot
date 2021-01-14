package top.cubik65536.cubikbot.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WeiboToken {
    private String token;
    private String cookie;
}
