package com.example.myawesomepastebin.repozitory;

import com.example.myawesomepastebin.model.Paste;
import com.example.myawesomepastebin.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface RepositoryPaste extends JpaRepository<Paste, String>, JpaSpecificationExecutor<Paste> {

    @Modifying
    @Query(value="delete from Paste p where p.dataExpired < now()")
    void deleteAll(Instant now);

    List<Paste> findTop10ByAccessAndExpiredDateIsAfterOrderByCreatedDateDesc(Status status, Instant now);

    Optional<Paste> findByUrlAndDataExpiredIsAfter(String url, Instant now);

    List<Paste> findAllByTitleContainsIgnoreCaseOrBodyContainsIgnoreCaseAndStatusAndDataExpiredIsAfter(
            String title, String text, Status status, Instant now);
}
