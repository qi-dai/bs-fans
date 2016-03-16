package com.eden.fans.bs.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;


@Controller
@RequestMapping(value = "/image")
public class VerifyCodeController {
	// ��֤��ͼƬ�Ŀ�ȡ�
	private int width = 88;
	// ��֤��ͼƬ�ĸ߶ȡ�
	private int height = 36;
	// ��֤���ַ�����
	private int codeCount = 4;
	private int x = 0;
	// ����߶�
	private int fontHeight;
	private int codeY;
	char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
			'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
			'X', 'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	/**
	 * ��ʼ����֤ͼƬ����
	 */
	public void initxuan() throws ServletException {
		// ��web.xml�л�ȡ��ʼ��Ϣ
		// ���
		String strWidth = "88";
		// �߶�
		String strHeight = "36";
		// �ַ�����
		String strCodeCount = "4";
		// �����õ���Ϣת������ֵ
		try {
			if (strWidth != null && strWidth.length() != 0) {
				width = Integer.parseInt(strWidth);
			}
			if (strHeight != null && strHeight.length() != 0) {
				height = Integer.parseInt(strHeight);
			}
			if (strCodeCount != null && strCodeCount.length() != 0) {
				codeCount = Integer.parseInt(strCodeCount);
			}
		} catch (NumberFormatException e) {
		}
		x = width / (codeCount + 1);
		fontHeight = height;
		codeY = height - 2;
	}

	@RequestMapping(value = "/getValidCode", method = RequestMethod.GET)
	public void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, java.io.IOException {
		String timestamp = req.getParameter("timestamp");
		initxuan();
		// ����ͼ��buffer
		BufferedImage buffImg = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = buffImg.createGraphics();
		// ����һ���������������
		Random random = new Random();
		// ��ͼ�����Ϊ��ɫ
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);
		// �������壬����Ĵ�СӦ�ø���ͼƬ�ĸ߶�������
		Font font = new Font("Fixedsys", Font.PLAIN, fontHeight);
		// �������塣
		g.setFont(font);
		// ���߿�
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, width - 1, height - 1);
		// �������160�������ߣ�ʹͼ���е���֤�벻�ױ���������̽�⵽��
		g.setColor(Color.BLACK);
		for (int i = 0; i < 10; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}
		//randomCode���ڱ��������������֤�룬�Ա��û���¼�������֤��
		StringBuffer randomCode = new StringBuffer();
		// �������codeCount���ֵ���֤�롣
		for (int i = 0; i < codeCount; i++) {
			// �õ������������֤�����֡�
			String strRand = String.valueOf(codeSequence[random.nextInt(34)]);
			// �������������ɫ����֤����Ƶ�ͼ���С�
			g.setColor(getRandColor(0, 100));
			g.drawString(strRand, (i + 0) * x, codeY);
			// ���������ĸ�����������һ��
			randomCode.append(strRand);
		}
		// ����λ���ֵ���֤�뱣�浽Session�С�
		HttpSession session = req.getSession();
		session.setAttribute("validateCode_" + timestamp, randomCode.toString());
		// ��ֹͼ�񻺴档
		resp.setHeader("Pragma", "no-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0);
		resp.setContentType("image/jpeg");
		// ��ͼ�������Servlet������С�
		ServletOutputStream sos = resp.getOutputStream();
		ImageIO.write(buffImg, "jpeg", sos);
		sos.flush();
		sos.close();
	}

	public Color getRandColor(int lower, int upper) {
		Random random = new Random();
		if (upper > 255)
			upper = 255;
		if (upper < 1)
			upper = 1;
		if (lower < 1)
			lower = 1;
		if (lower > 255)
			lower = 255;
		int r = lower + random.nextInt(upper - lower);
		int g = lower + random.nextInt(upper - lower);
		int b = lower + random.nextInt(upper - lower);
		return new Color(r, g, b);
	}

	@RequestMapping(value = "/validateCode", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String validateCode(String veryCode, String timestamp, HttpServletRequest request, HttpSession session) throws Exception {
		String validateC = (String) session.getAttribute(
				"validateCode_" + timestamp);
		if (validateC.equalsIgnoreCase(veryCode)){
			return "true";
		}else{
			return "false";
		}
	}
	/**��ȡָ��Ŀ¼ͼƬ*/
	@RequestMapping(value = "/getImage2", method = RequestMethod.GET)
	public void getImage(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, java.io.IOException {
		String imageName = req.getParameter("imageName");
		BufferedImage bufferImage= ImageIO.read(new File("d://test/"+imageName));//linux���滻·����ʽ
		// ��ֹͼ�񻺴档
		resp.setHeader("Pragma", "no-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0);
		resp.setContentType("image/jpeg");
		// ��ͼ�������Servlet������С�
		ServletOutputStream sos = resp.getOutputStream();
		ImageIO.write(bufferImage, "jpeg", sos);
		sos.flush();
		sos.close();
	}
}
