package com.google.tinybang.queue.channel;

/**
 * 
 * @author conan.cao
 *
 */
public class JvmShutdownHook extends Thread {
	
	public JvmShutdownHook() {
		super();
	}

	public void run() {
		
		ServerBootstrap.getInstance().stop();

	}
}
