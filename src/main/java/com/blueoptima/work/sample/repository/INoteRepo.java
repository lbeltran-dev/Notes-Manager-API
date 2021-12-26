package com.blueoptima.work.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blueoptima.work.sample.entity.Note;

@Repository
public interface INoteRepo extends JpaRepository<Note, Long> {

}
