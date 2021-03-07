package cl.allis.pong.entity;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.util.data.DataUtilities;

import cl.allis.utilities.OpenCVUtilities;
import cl.allis.utilities.SeleniumUtilities;

public class PongIA {

	public void runPong() {
		WebDriver driver = new FirefoxDriver();
		driver.get("https://playpong.net/vertical");

		Mat ball = Imgcodecs.imread("./images/ball.png");

		WebElement ele = driver.findElement(By.xpath("//*[@id=\"canvas\"]"));

		boolean browserIsOpen = true;
		while (browserIsOpen) {

			BufferedImage bufferedScreenshot;
			try {
				bufferedScreenshot = SeleniumUtilities.takeScreenshotAsBufferedImage(driver, ele);
				Mat screenshotMat = OpenCVUtilities.bufferedImageToMat(bufferedScreenshot);
				org.opencv.core.Point location = OpenCVUtilities.findInnerImage(screenshotMat, ball);
				if (location.y > 380) {
					Imgcodecs.imwrite("lostPoint.png", screenshotMat);
				}
				System.out.println(DataUtilities.objectToJson(location));
			} catch (IOException e) {
				browserIsOpen = false;
			}
		}
		driver.close();
	}

}
