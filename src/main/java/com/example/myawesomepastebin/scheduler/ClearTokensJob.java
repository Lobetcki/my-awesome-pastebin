package com.example.myawesomepastebin.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.Instant;

@Slf4j
@Component
public class ClearTokensJob {

//    private final TokenRepository tokenRepository;
//
//    public ClearTokensJob(TokenRepository tokenRepository) {
//        this.tokenRepository = tokenRepository;
//    }
//
//    @Transactional
//    @Scheduled(fixedDelay = 1000)
//    public void clearTokens() {
//        log.info("Clearing tokens");
//        tokenRepository.deleteAllByExpiredDataIsBefore(Instant.now());
//    }
}
