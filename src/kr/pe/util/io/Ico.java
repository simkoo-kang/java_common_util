/**
 * Ico.java
 */
package kr.pe.util.io;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import net.sf.image4j.codec.ico.ICOEncoder;

/**
 * <pre></pre>
 * 
 * gbrai 2026. 10. 17.
 */
public class Ico {

	public static void main(String[] args) {
		String basename = "D:/Workspace/eclipse/Crypto/conf/icon";
		String filename = basename + ".png";
		String savename = basename + ".ico";
		try {
			// 1. 원본 고해상도 이미지 열기
			File inputFile = new File(filename);
			BufferedImage baseImage = ImageIO.read(inputFile);

			if (baseImage == null) {
				System.out.println("이미지를 불러오지 못했습니다. 경로를 확인해 주세요.");
				return;
			}

			// 2. .ico 파일에 포함할 표준 크기들 지정 (파이썬의 icon_sizes 배열 매칭)
			int[] iconSizes = { 16, 32, 48, 256 };
			List<BufferedImage> resizedImages = new ArrayList<>();

			// 3. 루프를 돌며 지정된 크기로 리사이징 이미지 생성
			for (int size : iconSizes) {
				BufferedImage resized = resizeImage(baseImage, size, size);
				resizedImages.add(resized);
			}

			// 4. ico 포맷으로 다중 이미지 세트 저장
			File outputFile = new File(savename);
			try (FileOutputStream fos = new FileOutputStream(outputFile)) {
				// image4j 라이브러리의 ICOEncoder를 사용하여 멀티 레이어 변환 진행
				ICOEncoder.write(resizedImages, fos);
			}

			System.out.println("멀티 사이즈 .ico 아이콘 변환 완료!");

		} catch (IOException e) {
			System.out.println("파일 처리 중 오류가 발생했습니다.");
			e.printStackTrace();
		}
	}

	/**
	 * 이미지를 고화질 옵션으로 리사이징하는 헬퍼 메서드 (파이썬의 Image.resize 매칭)
	 */
	private static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
		BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = resizedImage.createGraphics();

		// 이미지 축소 시 계단 현상을 방지하고 부드럽게 깎아주는 고화질 보정 옵션 (Bicubic 인터폴레이션)
		graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// 그리기 및 자원 해제
		graphics.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
		graphics.dispose();

		return resizedImage;
	}
}
