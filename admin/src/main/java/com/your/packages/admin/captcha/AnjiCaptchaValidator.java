package com.your.packages.admin.captcha;

import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import lombok.RequiredArgsConstructor;
import org.ballcat.security.captcha.CaptchaValidateResult;
import org.ballcat.security.captcha.CaptchaValidator;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * anji-plus 的验证码校验器
 *
 * @author hccake
 */
@Component
@RequiredArgsConstructor
public class AnjiCaptchaValidator implements CaptchaValidator {

	private static final String CAPTCHA_VERIFICATION_PARAM = "captchaVerification";

	private final CaptchaService captchaService;

	@Override
	public CaptchaValidateResult validate(HttpServletRequest request) {
		// 获取验证码参数
		String captchaVerification = request.getParameter(CAPTCHA_VERIFICATION_PARAM);

		// anji 的校验处理
		CaptchaVO captchaVO = new CaptchaVO();
		captchaVO.setCaptchaVerification(captchaVerification);
		ResponseModel responseModel = captchaService.verification(captchaVO);

		// 组装校验结果
		return new CaptchaValidateResult(responseModel.isSuccess(), responseModel.getRepMsg());
	}

}
