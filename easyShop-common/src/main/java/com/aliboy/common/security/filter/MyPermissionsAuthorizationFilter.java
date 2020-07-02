package com.aliboy.common.security.filter;

import com.aliboy.common.constants.ErrorCodes;
import com.aliboy.common.exception.Exceptions;
import com.aliboy.common.security.PrincipalUtils;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 *  MyPermissionsAuthorizationFilter <br>
 * @author xiquee.com <br>
 * @date 2018-11-09 10:16:00
 */
public class MyPermissionsAuthorizationFilter extends PermissionsAuthorizationFilter {

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        if (PrincipalUtils.getPrincipalManager().isRoot()) {
            return true;
        }
        if (!super.isAccessAllowed(request, response, mappedValue)) {
            throw Exceptions.bizException(ErrorCodes.ERROR_403_MSG);
        }
        return true;
//        Subject subject = this.getSubject(request, response);
//        String[] perms = (String[])((String[])mappedValue);
//        boolean isPermitted = true;
//        if(perms != null && perms.length > 0) {
//            if(perms.length == 1) {
//                if(!subject.isPermitted(perms[0])) {
//                    isPermitted = false;
//                }
//            } else if(!subject.isPermittedAll(perms)) {
//                isPermitted = false;
//            }
//        }
//        return isPermitted;
    }
}
