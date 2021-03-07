package cl.allis.pong;

import java.io.IOException;

import cl.allis.pong.entity.PongIA;

public class PongIALauncher {

	public static void main(String args[]) throws IOException {
		System.loadLibrary("./libs/x64/opencv_java451");
		 PongIA pongIA = new PongIA();
		 pongIA.runPong();
	}
}
