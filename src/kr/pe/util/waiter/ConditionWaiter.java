/**
 * ConditionWaiter.java
 */
package kr.pe.util.waiter;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * 
 * PC  2026. 8. 30. 오후 8:04:04
 * </pre>
 */
public class ConditionWaiter {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private final Lock lock = new ReentrantLock();
	private final Condition condition = this.lock.newCondition();

	private boolean isReady = false; // 기다릴 특정 조건

	/**
	 * <pre></pre>
	 * 
	 */
	public ConditionWaiter() {
		super();
	}

	/**
	 * <pre>
	 * 1. 조건을 기다리는 메서드
	 * 
	 * PC 2026. 8. 30. 오후 8:11:36
	 * </pre>
	 *
	 * @throws InterruptedException
	 */
	public void waitUntilReady() {
		this.lock.lock();
		try {
			this.logger.debug("{} : 조건을 기다리기 시작합니다...", Thread.currentThread().getName());
			while (!this.isReady) {
				this.condition.await(); // wait()와 동일한 역할
			}
			this.logger.debug("{} : [성공] 조건이 만족되어 실행합니다!", Thread.currentThread().getName());
		} catch (InterruptedException e) {
			this.logger.debug(e.getMessage());
			Thread.currentThread().interrupt();
		} finally {
			this.lock.unlock();
		}
	}

	/**
	 * <pre>
	 * 2. 조건을 만족시키고 알림을 보내는 메서드
	 * 
	 * PC 2026. 8. 30. 오후 8:11:48
	 * </pre>
	 *
	 */
	public void makeReady() {
		this.lock.lock();
		try {
			this.logger.debug("{} : 3초 뒤 조건을 충족시킵니다.", Thread.currentThread().getName());
			Thread.sleep(3000); // 3초 대기 유도

			this.isReady = true;
			this.logger.debug("{} : 조건 충족 완료! 대기 중인 스레드를 깨웁니다.", Thread.currentThread().getName());

			this.condition.signalAll(); // notifyAll()과 동일한 역할
		} catch (InterruptedException e) {
			this.logger.debug(e.getMessage());
			Thread.currentThread().interrupt();
		} finally {
			this.lock.unlock();
		}
	}

	/**
	 * <pre>
	 * 강제 종료!
	 * 
	 * PC 2026. 8. 30. 오후 8:21:29
	 * </pre>
	 *
	 */
	public void intterrup() {
		Thread.currentThread().interrupt();
	}

	public static void main(String[] args) {
		ConditionWaiter waiter = new ConditionWaiter();

		// 1. 기다리는 스레드 생성 및 시작
		Thread waiterThread = new Thread(() -> {
			waiter.waitUntilReady();
		}, "Waiter-Thread");

		// 2. 조건을 충족시켜줄 스레드 생성 및 시작
		Thread signalerThread = new Thread(() -> {
			waiter.makeReady();
		}, "Signaler-Thread");

		// 실행 순서: waiter가 먼저 기다리기 시작함
		waiterThread.start();

		// 잠시 후 signaler 시작
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		signalerThread.start();
	}
}
