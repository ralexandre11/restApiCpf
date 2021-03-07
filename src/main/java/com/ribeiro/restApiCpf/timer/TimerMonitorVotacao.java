package com.ribeiro.restApiCpf.timer;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class TimerMonitorVotacao implements TimerVotacao{
	
	@Override
	@Scheduled(fixedDelay=MINUTO,initialDelay=MINUTO)
	public void execute() {
		System.out.println("TimerMonitorVotacao.execute");
		
	}

}
