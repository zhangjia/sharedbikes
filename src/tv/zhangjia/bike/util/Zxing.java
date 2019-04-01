package tv.zhangjia.bike.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import tv.zhangjia.bike.dao.BikeDao;
import tv.zhangjia.bike.dao.impl.BikeDaoImpl;
import tv.zhangjia.bike.entity.Bike;

public class Zxing {
	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;

	private static BitMatrix encode(String contents) throws WriterException {
		final Map<EncodeHintType, Object> hints = new HashMap<>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		return new QRCodeWriter().encode(contents, BarcodeFormat.QR_CODE, 320, 320, hints);
	}

	

	public static void generateQR(Bike bike) throws WriterException, IOException {
		String bikeInformation = bike.toString().replaceAll("\\t", "   ");
		BitMatrix bt = encode(bikeInformation);
		String bikeName = bike.getId() + "";
		File outputFile = new File("d:" + File.separator + bikeName + ".png");
		String address = outputFile.toString();
		bike.setQr(address);
		writeToFile(bt, "png", outputFile);
	}

	

	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}

	/**
	 * 把二维码保存至本地
	 */
	public static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, file)) {
			throw new IOException("不能写入 " + format + file);
		}
	}

}
