package com.ribeiro.restApiCpf.timer;

public interface TimerVotacao {
	public final long SEGUNDO = 1000; 
	public final long MINUTO = SEGUNDO * 60; 
	
	public void execute();

}
