package com.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginUtil {
	// 验证码位数.
	public static final int LENGTH = 4;

	// public static final int WIDTH = (int) (12.5 * LENGTH);
	public static final int WIDTH = 50;
	public static final int HEIGHT = 20;
	// 字体大小
	public static final int FONT_SIZE = 20;
	// 干扰线数量.
	public static final int LINE_NUM = 5;
	private static Random r = new Random();

	public static void validationCode(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setHeader("Content-type", "image/jpeg");
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();

		g.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.black);
		g.setFont(new Font("微软雅黑", Font.ITALIC, FONT_SIZE));
		String num = makeNum();
		g.drawString(num, 0, (int) (HEIGHT * 0.9f));
		g.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
		for (int i = 0; i < LINE_NUM; i++) {
			g.drawLine(r.nextInt(WIDTH), r.nextInt(HEIGHT), r.nextInt(WIDTH),
					r.nextInt(HEIGHT));
		}
		ImageIO.write(image, "jpeg", response.getOutputStream());
		request.getSession().setAttribute("verification", num);
	}

	private static String makeNum() {
		String num = r.nextInt((int) Math.pow(10, LENGTH)) + "";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < LENGTH - num.length(); i++) {
			sb.append("0");
		}
		return num + sb.toString();
	}

	public static boolean checkValidation(HttpServletRequest request, HttpServletResponse response) {
		String verification = (String) request.getSession().getAttribute("verification");
		Integer num = null;
		try {
			num = Integer.parseInt(verification);
		} catch (NumberFormatException e) {
			return false;
		}
		if (num == null) {
			return false;
		}
		String numStr = request.getParameter("validation");
		if (num.toString().equals(numStr)) {
			return true;
		}
		return false;
	}

	public static void addRememberCookie(HttpServletRequest request,
			HttpServletResponse response, String field)
			throws UnsupportedEncodingException {
		String loginName = URLEncoder.encode(request.getParameter("loginName"),
				"utf-8");
		String loginPassword = URLEncoder.encode(
				request.getParameter("loginPwd"), "utf-8");
		String remember = request.getParameter("remember");
		Cookie nameCookie = new Cookie(field + "Name", loginName);
		Cookie pwdCookie = new Cookie(field + "Pwd", loginPassword);
		nameCookie.setPath(request.getContextPath() + "/");
		pwdCookie.setPath(request.getContextPath() + "/");
		if (remember != null && "yes".equals(remember)) {
			nameCookie.setMaxAge(7 * 24 * 60 * 60); // 保存一个星期
			pwdCookie.setMaxAge(7 * 24 * 60 * 60); // 保存一个星期
		} else {
			nameCookie.setMaxAge(0); // 销毁Cookie
			pwdCookie.setMaxAge(0); // 销毁Cookie
		}
		response.addCookie(nameCookie);
		response.addCookie(pwdCookie);
	}
}
