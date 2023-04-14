package com.example.myawesomepastebin.repozitory;

import com.example.myawesomepastebin.model.Paste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;

public interface RepositoryPaste extends JpaRepository<Paste, String>, JpaSpecificationExecutor<Paste> {
    void deleteAllByDataExpiredIsBefore(Instant now);

    @Query(value = "SELECT * FROM skypr.public.paste p WHERE p.status = 'PUBLIC' " +
            "ORDER BY p.data_created DESC LIMIT 10;", nativeQuery = true)
    List<Paste> fundLastTen();


}
