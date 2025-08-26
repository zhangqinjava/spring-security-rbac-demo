package com.example.securitydemo.bean.vo.system;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public enum LoginEnum {
    ACC("0","账号"),
    Phone("1","手机"),
    WEIXIN("2","微信");
    private String key;
    public String value;
    public static LoginEnum getValue(String key) {
        for (LoginEnum item : LoginEnum.values()) {
            if (item.key.equals(key)) {
                return item;
            }
        }
        return null;
    }

}
