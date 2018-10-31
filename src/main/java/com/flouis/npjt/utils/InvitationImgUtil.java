package com.flouis.npjt.utils;

import com.google.common.collect.Maps;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InvitationImgUtil {

	// String saveUrl, String nickName, String avatarUrl, String qrCodeUrl
	// String goodImgUrl, String goodName, String originPrice, String realPrice
	public static void generateInvitationImg(Map<String, String> paramMap) throws Exception {

		int imageWidth = 300;  //图片的宽度
		int imageHeight = 500; //图片的高度

		// 生成底片：
		BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);

		//设置图片的背景色
		Graphics2D main = image.createGraphics();
		main.setColor(Color.white);
		main.fillRect(0, 0, imageWidth, imageHeight);

		// *********** 绘制头部：由用户头像、用户昵称和提示信息（邀请您购买）构成 ************
		Graphics2D avatarPic = image.createGraphics();
		int avatarX = imageWidth / 2 + 15; // 头像横坐标
		int avatarY = 15; // 头像纵坐标
		int titleHeight = 50; // 标题栏高度
		Ellipse2D.Double shape = new Ellipse2D.Double(avatarX, avatarY, titleHeight - 6, titleHeight - 6);
		avatarPic.setClip(shape);
//		BufferedImage avatarImg = ImageIO.read(new File(paramMap.get("avatarUrl")));
		BufferedImage avatarImg = ImageIO.read(new URL(paramMap.get("avatarUrl")));
		if (avatarImg != null){
			avatarPic.drawImage(avatarImg, avatarX, avatarY, titleHeight - 6, titleHeight - 6, null);
			avatarPic.dispose();
		}
		Graphics userMsg = image.getGraphics();
		userMsg.setColor(new Color(143, 35 ,0));
		userMsg.setFont(new Font("宋体", Font.PLAIN, 12));
		String nickName = paramMap.get("nickName") == null ? "" : paramMap.get("nickName");
		if (nickName != null && nickName.length() > 6){
			nickName = nickName.substring(0, 6) + "..";
		}
		userMsg.drawString(nickName, avatarX + titleHeight, avatarY + titleHeight / 2 - 10);
		userMsg.setColor(new Color(60, 63, 65));
		userMsg.setFont(new Font("宋体", Font.PLAIN, 12));
		userMsg.drawString("邀请您购买", avatarX + titleHeight, avatarY + titleHeight / 2 + 10);

		// *********** 绘制商品主图 ************
		int mainPicHeight = 280;
		Graphics mainPic = image.getGraphics();
		BufferedImage goodImg = ImageIO.read(new File(paramMap.get("goodImgUrl")));
		if(goodImg != null){
			mainPic.drawImage(goodImg, 5, titleHeight + 15, imageWidth-10, mainPicHeight, null);
			mainPic.dispose();
		}

		// *************** 绘制用户二维码 ***********************
		Graphics qrCodePic = image.getGraphics();
		int qrCodePicHeight = 130;
		BufferedImage qrCodeImg = ImageIO.read(new File(paramMap.get("qrCodeUrl")));
		if (qrCodeImg != null){
			qrCodePic.drawImage(qrCodeImg, imageWidth / 2 + 10 , imageHeight - qrCodePicHeight - 10, qrCodePicHeight, qrCodePicHeight, null);
			qrCodePic.dispose();
		}

		// *************** 商品信息 ***********************
		Graphics goodInfo = image.getGraphics();
		int goodInfoX = 10; // 商品信息的横坐标
		int goodNameY = imageHeight - qrCodePicHeight + 10; // 商品名称开始的纵坐标
		// 填充商品名称：
		goodInfo.setColor(new Color(60, 63, 65));
		goodInfo.setFont(new Font("宋体", Font.PLAIN, 12));
		String  goodName = paramMap.get("goodName");
		if (goodName != null){
			List<String> strList = getStrList(goodName, 11);
			if (strList.size() > 2){
				goodInfo.drawString(strList.get(0), goodInfoX, goodNameY);
				goodInfo.drawString(strList.get(1).substring(0, 10) + "..", goodInfoX, goodNameY + 18);
			} else if (strList.size() == 2) {
				goodInfo.drawString(strList.get(0), goodInfoX, goodNameY);
				goodInfo.drawString(strList.get(1), goodInfoX, goodNameY + 17);
			} else {
				goodInfo.drawString(goodName, goodInfoX, goodNameY);
			}
		}

		// 填充原价：
		goodInfo.setColor(new Color(153,153,153));
		int originPriceY = imageHeight - 70;
		goodInfo.drawString("原价：￥ ", goodInfoX, originPriceY);
		String originPrice = paramMap.get("originPrice");
		if (originPrice != null){
			goodInfo.drawString(originPrice, goodInfoX + 55, originPriceY);
		}
		goodInfo.drawLine(goodInfoX + 50, originPriceY - 4, goodInfoX + 80, originPriceY - 4);

		// 填充现价：
		goodInfo.setFont(new Font("宋体", Font.PLAIN, 12));
		int realPriceY = imageHeight - 35;
		goodInfo.drawString("现在只要：￥", goodInfoX, realPriceY);
		goodInfo.setColor(new Color(143, 35 ,0));
		goodInfo.setFont(new Font("微软雅黑", Font.BOLD, 16));
		String realPrice = paramMap.get("realPrice");
		if (realPrice != null){
			goodInfo.drawString(realPrice, goodInfoX + 72, realPriceY + 2);
		}

		// 保存图片：
		BufferedOutputStream bos = null;
		if(image != null){
			String saveUrl = paramMap.get("saveUrl");
			String savePath = saveUrl.substring(0, saveUrl.lastIndexOf("/"));
			// 创建临时文件目录:
			File dir = new File(savePath);
			if (!dir.exists()){
				dir.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(saveUrl);

			bos = new BufferedOutputStream(fos);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
			encoder.encode(image);
			if (bos != null){
				bos.close();
			}
		}
	}

	public static List<String> getStrList(String inputString, int length){
		List<String> list = new ArrayList<>();
		if (StringUtils.isBlank(inputString)){
			return list;
		}
		int size = inputString.length() / length;
		if (inputString.length() % length != 0) {
			size += 1;
		}
		for (int index = 0; index < size; index++) {
			String childStr = substring(inputString, index * length,
					(index + 1) * length);
			list.add(childStr);
		}
		return list;
	}

	public static String substring(String str, int f, int t) {
		if (f > str.length())
			return null;
		if (t > str.length()) {
			return str.substring(f, str.length());
		} else {
			return str.substring(f, t);
		}
	}


	public static void main(String[] args) {

		Map<String, String> paramMap = Maps.newHashMap();
		paramMap.put("saveUrl", "/sadf/temp/123.png");
		paramMap.put("nickName", "你你你你你你你你你你你你你你你你你你");
		paramMap.put("qrCodeUrl","/data/img/qyg/qrcode/1540437724537.jpg");
		paramMap.put("avatarUrl", "http://b.hiphotos.baidu.com/image/h%3D300/sign=593ea84a8b18367ab28979dd1e728b68/0b46f21fbe096b631f4b3b3301338744ebf8ac07.jpg");
		paramMap.put("goodImgUrl", "/data/img/qyg/good/1532437395045.JPG");
		paramMap.put("goodName", "A1充垫宝坚果集【176g盒装/酸奶味】");
//		paramMap.put("goodName", "你你你你你你你你你你你你你你你你你你你你你你你你你你你你你你你你你你你你你你你你你你你你你你你");
		paramMap.put("originPrice", "20.99");
		paramMap.put("realPrice", "1500.99");
		try{
			generateInvitationImg(paramMap);
		} catch (Exception e){
			e.printStackTrace();
		}

	}/**/



}
