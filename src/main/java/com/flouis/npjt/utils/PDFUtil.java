package com.flouis.npjt.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PDFUtil {

	public static void main(String[] args) {
		System.out.println( pdf2Image("C:/Users/Administrator/Desktop/Temporary/测试.pdf", "", 300, "测试") );
	}

	/***
	 * PDF文件转PNG图片，全部页数
	 *
	 * @param PdfFilePath pdf完整路径
	 * @param imgFilePath 图片存放的文件夹
	 * @param dpi dpi越大转换后越清晰，相对转换速度越慢
	 * @return
	 */
	public static Map<String, Object> pdf2Image(String PdfFilePath, String dstImgFolder, int dpi, String filename) {
		Map<String, Object> resMap = Maps.newHashMap();

		File file = new File(PdfFilePath);
		PDDocument pdDocument;
		int num = 0;
		List<String> imgNameList = Lists.newArrayList();
		try {
			String imgPDFPath = file.getParent();
			String imgFolderPath = null;
			if (dstImgFolder.equals("")) {
				imgFolderPath = imgPDFPath;// 获取图片存放的文件夹路径
			} else {
				imgFolderPath = dstImgFolder;
			}
			if (createDirectory(imgFolderPath)) {
				pdDocument = PDDocument.load(file);
				PDFRenderer renderer = new PDFRenderer(pdDocument);
				// dpi越大转换后越清晰，相对转换速度越慢
				num = pdDocument.getNumberOfPages();
				StringBuffer imgFilePath = null;
				for (int i = 0; i < num; i++) {
					String imgFilePathPrefix = imgFolderPath + File.separator + filename;
					imgFilePath = new StringBuffer();
					imgFilePath.append(imgFilePathPrefix);
					imgFilePath.append("_");
					imgFilePath.append(String.valueOf(i + 1));
					imgFilePath.append(".png");
					File dstFile = new File(imgFilePath.toString());

					imgNameList.add(filename + "_" + (i + 1) + ".png" );

					BufferedImage image = renderer.renderImageWithDPI(i, dpi);
					ImageIO.write(image, "png", dstFile);
				}
				System.out.println("PDF文档转PNG图片成功！");
			} else {
				System.out.println("PDF文档转PNG图片失败：" + "创建" + imgFolderPath + "失败");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		resMap.put("totalNum", num);
		resMap.put("imgNameList", imgNameList);
		return resMap;
	}

	private static boolean createDirectory(String folder) {
		File dir = new File(folder);
		if (dir.exists()) {
			return true;
		} else {
			return dir.mkdirs();
		}
	}

}
