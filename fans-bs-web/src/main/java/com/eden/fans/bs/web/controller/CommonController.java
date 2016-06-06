package com.eden.fans.bs.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.eden.fans.bs.dao.IFootBallScoreDao;
import com.eden.fans.bs.domain.request.FootBallScoreAddReq;
import com.eden.fans.bs.domain.response.ServiceResponse;
import com.eden.fans.bs.domain.user.FootBallScoreVo;
import com.eden.fans.bs.service.ICommonService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


@Controller
@RequestMapping(value = "/common")
public class CommonController {
	private static Logger logger = LoggerFactory.getLogger(CommonController.class);

	@Autowired
	private ICommonService commonService;
	@Autowired
	private IFootBallScoreDao footBallScoreDao;

	private static Gson gson = new Gson();

	// 验证码图片的宽度。
	private int width = 88;
	// 验证码图片的高度。
	private int height = 36;
	// 验证码字符个数
	private int codeCount = 4;
	private int x = 0;
	// 字体高度
	private int fontHeight;
	private int codeY;
	char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
			'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
			'X', 'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	/**
	 * 初始化验证图片属性
	 */
	public void initxuan() throws ServletException {
		// 从web.xml中获取初始信息
		// 宽度
		String strWidth = "88";
		// 高度
		String strHeight = "36";
		// 字符个数
		String strCodeCount = "4";
		// 将配置的信息转换成数值
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

	@RequestMapping(value = "/getValidCode", method = {RequestMethod.GET, RequestMethod.POST})
	public void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, java.io.IOException {
		String timestamp = req.getParameter("timestamp");
		initxuan();
		// 定义图像buffer
		BufferedImage buffImg = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = buffImg.createGraphics();
		// 创建一个随机数生成器类
		Random random = new Random();
		// 将图像填充为白色
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);
		// 创建字体，字体的大小应该根据图片的高度来定。
		Font font = new Font("Fixedsys", Font.PLAIN, fontHeight);
		// 设置字体。
		g.setFont(font);
		// 画边框。
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, width - 1, height - 1);
		// 随机产生160条干扰线，使图象中的认证码不易被其它程序探测到。
		g.setColor(Color.BLACK);
		for (int i = 0; i < 10; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}
		//randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
		StringBuffer randomCode = new StringBuffer();
		// 随机产生codeCount数字的验证码。
		for (int i = 0; i < codeCount; i++) {
			// 得到随机产生的验证码数字。
			String strRand = String.valueOf(codeSequence[random.nextInt(34)]);
			// 用随机产生的颜色将验证码绘制到图像中。
			g.setColor(getRandColor(0, 100));
			g.drawString(strRand, (i + 0) * x, codeY);
			// 将产生的四个随机数组合在一起。
			randomCode.append(strRand);
		}
		// 将四位数字的验证码保存到mongo中。
		commonService.saveValidCode(timestamp,randomCode.toString());
		// 禁止图像缓存。
		resp.setHeader("Pragma", "no-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0);
		resp.setContentType("image/jpeg");
		// 将图像输出到Servlet输出流中。
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

	/**获取指定目录图片*/
	@RequestMapping(value = "/getImage2", method = RequestMethod.GET)
	public void getImage(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, java.io.IOException {
		String imageName = req.getParameter("imageName");
		BufferedImage bufferImage= ImageIO.read(new File("d://test/"+imageName));//linux下替换路径格式
		// 禁止图像缓存。
		resp.setHeader("Pragma", "no-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0);
		resp.setContentType("image/jpeg");
		// 将图像输出到Servlet输出流中。
		ServletOutputStream sos = resp.getOutputStream();
		ImageIO.write(bufferImage, "jpeg", sos);
		sos.flush();
		sos.close();
	}

	/**批量获取用户信息*/
	@RequestMapping(value = "/getUsers", method = RequestMethod.GET)
	@ResponseBody
	public String getUsers(HttpServletRequest req, HttpServletResponse resp){
		Long[] arr = new Long[5];
		arr[0]=100020l;
		arr[1]=100021l;
		arr[2]=100022l;
		arr[3]=100023l;
		arr[4]=100024l;
		return gson.toJson(commonService.qryUserVosBatch(arr));
	}

	/**批量获取用户信息*/
	@RequestMapping(value = "/addFootBallScore", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public void addFootBallScore(HttpServletRequest req, HttpServletResponse resp,FootBallScoreAddReq footBallScoreAddReq){
		FootBallScoreVo footBallScoreVo = new FootBallScoreVo();
		footBallScoreVo.setName(footBallScoreAddReq.getName());
		footBallScoreVo.setPhone(footBallScoreAddReq.getPhone());
		footBallScoreVo.setScore(footBallScoreAddReq.getScore());
		try{
			boolean flag =footBallScoreDao.addFootBallScore(footBallScoreVo);
			ServiceResponse<Boolean> response = new ServiceResponse<Boolean>(flag);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("code","0");
			jsonObject.put("msg","成功");
			jsonObject.put("result",flag);
			resp.getWriter().print("receiveAdd("+jsonObject.toString()+")");
		}catch(Exception e){
			e.printStackTrace();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("code","-1");
			jsonObject.put("msg","保存得分排行失败");
			try {
				resp.getWriter().print("receiveAdd("+jsonObject.toString()+")") ;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**批量获取用户信息*/
	@RequestMapping(value = "/qryFootBallScores", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public void qryFootBallScores(HttpServletRequest req, HttpServletResponse resp){
		try {
			java.util.List<FootBallScoreVo> list = footBallScoreDao.qryFootBallScores();
			ServiceResponse<java.util.List<FootBallScoreVo>> response = new ServiceResponse<java.util.List<FootBallScoreVo>>(list);
			resp.setCharacterEncoding("UTF-8");
			resp.setHeader("Content-Type", "text/html;charset=UTF-8");
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("code","0");
			jsonObject.put("msg","成功");
			jsonObject.put("result",list);
			resp.getWriter().print("receive("+jsonObject.toString()+")") ;
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("code","-1");
			jsonObject.put("msg","查询得分排行失败");
			try {
				resp.getWriter().print("receive("+jsonObject.toString()+")") ;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}


}
