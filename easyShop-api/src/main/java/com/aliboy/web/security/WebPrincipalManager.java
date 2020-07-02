package com.aliboy.web.security;

import com.aliboy.common.security.SystemPrincipalManager;
import org.springframework.stereotype.Service;

@Service
public class WebPrincipalManager extends SystemPrincipalManager {

    @Override
    protected String loadPreferredLanguage() {
        return null;
    }
}
