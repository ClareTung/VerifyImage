package cn.iponkan.controller;

import cn.iponkan.util.VerificationImageUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@RequestMapping("/captchaImage")
public class CaptchaImageController {

	/**
	 * 生成验证码
	 *
	 * @param request
	 * @param response
	 * @param model
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/kaptcha", method = RequestMethod.GET)
	public void ImageCaptcha(HttpServletRequest request, HttpServletResponse response, Model model)
			throws ServletException, IOException {

		try {
			// get the session id that will identify the generated captcha.
			// the same id must be used to validate the response, the session id
			// is a good candidate!
			response.setHeader("Cache-Control", "no-store");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("image/jpeg");

			String verifyCode = VerificationImageUtils.generateVerifyCode(4);
			//存入会话session
			HttpSession session = request.getSession(true);
			session.setAttribute("verificationImage", verifyCode.toLowerCase());
			//生成图片
			int w = 200, h = 80;
			VerificationImageUtils.outputImage(w, h, response.getOutputStream(), verifyCode);

		} catch (IllegalArgumentException e) {
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}