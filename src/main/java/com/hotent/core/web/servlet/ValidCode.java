package com.hotent.core.web.servlet;

import com.sun.jimi.core.Jimi;
import com.sun.jimi.core.JimiException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ValidCode extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static String SessionName_Randcode = "randcode";

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0L);
		response.setContentType("image/jpeg");
		byte width = 60;
		byte height = 20;
		BufferedImage image = new BufferedImage(width, height, 1);
		Graphics g = image.getGraphics();
		Random random = new Random();
		g.setColor(this.getRandColor(155, 254));
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Times New Roman", 0, 18));
		g.setColor(this.getRandColor(160, 220));

		int os;
		for (int sRand = 0; sRand < 155; ++sRand) {
			os = random.nextInt(width);
			int e = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(os, e, os + xl, e + yl);
		}

		String arg13 = "";

		for (os = 0; os < 4; ++os) {
			String arg15 = String.valueOf(random.nextInt(10));
			arg13 = arg13 + arg15;
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(arg15, 13 * os + 6, 16);
		}

		request.getSession().setAttribute(SessionName_Randcode, arg13);
		g.dispose();
		ServletOutputStream arg14 = response.getOutputStream();

		try {
			Jimi.putImage("image/jpeg", image, arg14);
		} catch (JimiException arg12) {
			arg12.printStackTrace();
		}

		arg14.flush();
		arg14.close();
		arg14 = null;
		response.flushBuffer();
	}

	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255) {
			fc = 255;
		}

		if (bc > 255) {
			bc = 255;
		}

		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	public void init() throws ServletException {
	}
}