package cl.allis.utilities;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

public class OpenCVUtilities {

	private OpenCVUtilities() {
	}

	public static Mat bufferedImageToMat(BufferedImage sourceImg) {
		Mat ret = new Mat(sourceImg.getHeight(), sourceImg.getWidth(), CvType.CV_8UC3);

		BufferedImage convertedImg = new BufferedImage(sourceImg.getWidth(), sourceImg.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		convertedImg.getGraphics().drawImage(sourceImg, 0, 0, null);

		DataBuffer dataBuffer = convertedImg.getRaster().getDataBuffer();

		int pixelCount = 0;

		for (int i = 0; i < convertedImg.getWidth(); i++) {
			for (int j = 0; j < convertedImg.getHeight(); j++) {
				int r = dataBuffer.getElem(pixelCount * 4 + 3);
				int g = dataBuffer.getElem(pixelCount * 4 + 2);
				int b = dataBuffer.getElem(pixelCount * 4 + 1);

				byte[] pixel = { (byte) b, (byte) g, (byte) r };
				ret.put(i, j, pixel);
				pixelCount++;
			}
		}
		return ret;
	}

	public static Point findInnerImage(Mat image, Mat innerImage) {
		Mat result = new Mat();
		Imgproc.matchTemplate(image, innerImage, result, Imgproc.TM_CCOEFF_NORMED);
		MinMaxLocResult minMaxLoc = Core.minMaxLoc(result);
		return minMaxLoc.maxLoc;
	}

	public static ByteBuffer convertImageData(BufferedImage bi) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ImageIO.write(bi, "png", out);
			return ByteBuffer.wrap(out.toByteArray());
		} catch (IOException ex) {
			// TODO
		}
		return null;
	}
}
