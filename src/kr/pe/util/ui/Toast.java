/**
 * Toast.java
 */
package kr.pe.util.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

/**
 * <pre></pre>
 * 
 * gbrai 2026. 10. 18.
 */
public class Toast extends JWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1470690056809058140L;

	/**
	 * <pre></pre>
	 * 
	 * @param owner
	 * @param message
	 * @param durationMs
	 */
	public Toast(JFrame owner, String message, int durationMs) {
		super(owner);

		// 1. 토스트 디자인 구성 (둥근 투명 검은색 배경 + 흰색 글씨)
		JPanel panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g.create();
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setColor(new Color(0, 0, 0, 180)); // 알파값 180으로 반투명 처리
				g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // 둥근 모서리
				g2d.dispose();
			}
		};
		panel.setOpaque(false);
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15)); // 여백 조절

		JLabel label = new JLabel(message);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		panel.add(label, BorderLayout.CENTER);
		add(panel);

		// 2. 글자 길이에 맞춰 토스트 창 크기 자동 조절
		pack();

		// 3. 메인 Swing 창(owner)의 하단 중앙에 위치 지정
		if (owner != null) {
			Point ownerLoc = owner.getLocationOnScreen();
			int x = ownerLoc.x + (owner.getWidth() - getWidth()) / 2;
			int y = ownerLoc.y + owner.getHeight() - getHeight() - 50; // 밑에서 50픽셀 위
			setLocation(x, y);
		}

		// 투명도 레이어 활성화
		setBackground(new Color(0, 0, 0, 0));
	}

	/**
	 * <pre>
	 * 토스트 메시지를 화면에 띄우고 지정된 시간 후 자동으로 닫는 정적 메서드
	 * </pre>
	 * 
	 * @param owner
	 * @param message
	 * @param durationMs
	 */
	public static void show(JFrame owner, String message) {
		show(owner, message, 1500);
	}

	/**
	 * <pre>
	 * 토스트 메시지를 화면에 띄우고 지정된 시간 후 자동으로 닫는 정적 메서드
	 * </pre>
	 * 
	 * @param owner
	 * @param message
	 * @param durationMs
	 */
	public static void show(JFrame owner, String message, int durationMs) {
		SwingUtilities.invokeLater(() -> {
			Toast toast = new Toast(owner, message, durationMs);
			toast.setVisible(true);

			// 지정된 밀리초(ms) 후에 토스트창을 닫고 메모리에서 해제하는 백그라운드 스레드
			new Thread(() -> {
				try {
					Thread.sleep(durationMs);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// Swing UI 제어는 반드시 invokeLater 안에서 안전하게 처리
				SwingUtilities.invokeLater(() -> {
					toast.setVisible(false);
					toast.dispose();
				});
			}).start();
		});
	}
}
