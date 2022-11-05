package com.your.packages.admin.captcha;

import cloud.tianai.captcha.spring.application.ImageCaptchaApplication;
import cloud.tianai.captcha.spring.plugins.secondary.SecondaryVerificationApplication;
import cn.hutool.core.text.CharSequenceUtil;
import lombok.RequiredArgsConstructor;
import org.ballcat.security.captcha.CaptchaValidateResult;
import org.ballcat.security.captcha.CaptchaValidator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * tianai 验证码的校验器
 *
 * @author whace
 */
@Component
@Primary
@RequiredArgsConstructor
public class TianaiCaptchaValidator implements CaptchaValidator {

	private final ImageCaptchaApplication sca;

	@Override
	public CaptchaValidateResult validate(HttpServletRequest request) {
		String captchaId = request.getParameter("captchaId");
		if (CharSequenceUtil.isBlank(captchaId)) {
			return CaptchaValidateResult.failure("captcha id can not be null");
		}

		if (!(sca instanceof SecondaryVerificationApplication)) {
			return CaptchaValidateResult.failure("captcha must enable secondary verification");
		}

		boolean match = ((SecondaryVerificationApplication) sca).secondaryVerification(captchaId);
		return match ? CaptchaValidateResult.success() : CaptchaValidateResult.failure("captcha validate failure");
	}

}