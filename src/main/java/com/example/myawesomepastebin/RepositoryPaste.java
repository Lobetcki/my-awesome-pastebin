package com.example.myawesomepastebin;

import com.example.myawesomepastebin.model.Paste;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryPaste extends JpaRepository<Paste, String> {
}
