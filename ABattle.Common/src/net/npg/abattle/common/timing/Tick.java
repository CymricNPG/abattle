package net.npg.abattle.common.timing;

public interface Tick extends Runnable {

	public void addObserver(final TickObserver observer);

	public void removeObserver(final TickObserver observer);

	public void stop();

}