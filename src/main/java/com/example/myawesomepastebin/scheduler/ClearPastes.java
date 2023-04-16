package com.example.myawesomepastebin.scheduler;

import com.example.myawesomepastebin.repozitory.RepositoryPaste;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class ClearPastes {

    private final RepositoryPaste repositoryPaste;

    public ClearPastes(RepositoryPaste repositoryPaste) {
        this.repositoryPaste = repositoryPaste;
    }

    @Transactional
    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.DAYS)
    public void clearPaste() {
        log.info("Clearing pastes");
        repositoryPaste.deleteAll(Instant.now());
    }
}
