package com.example.myawesomepastebin.repozitory;

import com.example.myawesomepastebin.model.Paste;
import com.example.myawesomepastebin.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface RepositoryPaste extends JpaRepository<Paste, String>, JpaSpecificationExecutor<Paste> {
    void deleteAllByDataExpiredIsBefore(Instant now);

//    @Query(value = "SELECT * FROM skypr.public.paste p WHERE p.status = 'PUBLIC' " +
//            "ORDER BY p.data_created DESC LIMIT 10;", nativeQuery = true)
//    List<Paste> fundLastTen();
    List<Paste> findTop10ByAccessAndExpiredDateIsAfterOrderByCreatedDateDesc(Status status, Instant now);

    Optional<Paste> findByUrlAndDataExpiredIsAfter(String url, Instant now);

    List<Paste> findAllByTitleContainsIgnoreCaseOrBodyContainsIgnoreCaseAndStatusAndDataExpiredIsAfter(
            String title, String text, Status status, Instant now);
}
