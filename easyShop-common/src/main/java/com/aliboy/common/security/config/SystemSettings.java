package com.aliboy.common.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *  SystemSettings <br>
 * @author xiquee.com <br>
 * @date 2018-11-09 10:16:00
 */
@Component
@ConfigurationProperties(prefix = "easyshop")
@Data
public class SystemSettings {
    private int pageSize;
    private boolean vcodeForTest;

    private int loginAttemptTimes;
    private int loginAttemptSpan;
    private int leftTimesForErrorPwd;
}
