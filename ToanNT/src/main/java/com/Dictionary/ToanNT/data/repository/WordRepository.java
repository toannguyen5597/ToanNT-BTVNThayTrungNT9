package com.Dictionary.ToanNT.data.repository;

import com.Dictionary.ToanNT.data.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends JpaRepository<Word, Integer> {
    //List<Word> getWordByKey(List<Word> word, String key);
}
