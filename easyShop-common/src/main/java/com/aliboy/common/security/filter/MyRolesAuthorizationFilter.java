package com.aliboy.common.security.filter;

import com.aliboy.common.constants.ErrorCodes;
import com.aliboy.common.exception.Exceptions;
import com.aliboy.common.security.PrincipalUtils;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 *  MyRolesAuthorizationFilter <br>
 * @author xiquee.com <br>
 * @date 2018-11-09 10:16:00
 */
public class MyRolesAuthorizationFilter extends RolesAuthorizationFilter {

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
//        String[] rolesArray = (String[])((String[])mappedValue);
//        if(rolesArray != null && rolesArray.length != 0) {
//            Set roles = CollectionUtils.asSet(rolesArray);
//            return subject.hasAllRoles(roles);
//        } else {
//            return true;
//        }
    }
}
