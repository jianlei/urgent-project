package com.daren.core.web.wicket.security;

import com.daren.core.web.wicket.PermissionConstant;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

/**
 * @类描述：验证码校验类
 * @创建人：sunlf
 * @创建时间：2014-05-04 下午3:47
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class CaptchaValidator implements IValidator<String> {

    private static final long serialVersionUID = 1L;
    private String INVALID_CODE = "captcha.invalid";

    @Override
    public void validate(IValidatable<String> validatable) {
        String kaptchaReceived = validatable.getValue();

        Session session = SecurityUtils.getSubject().getSession();

        String kaptchaExpected = (String) session.getAttribute(PermissionConstant.VALIDATE_CODE);

        if (kaptchaReceived == null
                || !kaptchaReceived.equalsIgnoreCase(kaptchaExpected)) {
            //throw new CaptchaException("验证码错误!");
            ValidationError error = new ValidationError(this);
            error.addKey(INVALID_CODE);
            validatable.error(error);
        }
    }
}