package basic.array.problems;

// Thread producer-consumer problem
class CarFactory {
	int stock;
	
	public CarFactory() {}
	
	public CarFactory(int n) {
		this.stock = n;
	}
	
	synchronized void consume(int n) {
		System.out.println("Need " + n + " cars.");
		while (stock < n) {
			System.out.println("Waiting for stock...");
			try {
				wait();
			}
			catch (Exception e) {}
		}
		stock -= n;
		System.out.println(n + " cars consumed.");
	}
	
	synchronized void produce(int n) {
		stock += n;
		System.out.println("stock is updated to " + stock + " cars.");
		notify();
	}
}

class ProducerThread implements Runnable {
	private CarFactory carFactory;
	private int n;
	
	public ProducerThread(CarFactory carFactory, int n) {
		this.carFactory = carFactory;
		this.n = n;
		new Thread(this).start();
	}

	@Override
	public void run() {
		carFactory.produce(n);	
	}
	
}

class ConsumerThread implements Runnable {
	private CarFactory carFactory;
	private int n;
	
	public ConsumerThread(CarFactory carFactory, int n) {
		this.carFactory = carFactory;
		this.n = n;
		new Thread(this).start();
	}

	@Override
	public void run() {
		carFactory.consume(n);
	}
	
}

public class Test {
	
	public static void main(String[] args) {
		CarFactory carFactory = new CarFactory(10); // initial stock = 10
		try {
			Thread.sleep(500);
		}
		catch (Exception e) {}
		new ConsumerThread(carFactory, 15);
		try {
			Thread.sleep(1500);
		}
		catch (Exception e) {}
		new ProducerThread(carFactory, 10);
	}

}
