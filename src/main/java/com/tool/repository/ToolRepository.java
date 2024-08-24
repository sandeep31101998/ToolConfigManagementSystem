package com.tool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tool.entity.Tool;

public interface ToolRepository extends JpaRepository<Tool, Long> {

	@Query("SELECT DISTINCT fileName FROM Tool")
    List<String> findDistinctFileNames();
	
	@Query("SELECT t FROM Tool t WHERE t.fileName = :fileName")
	List<Tool> findByFileName(@Param("fileName") String fileName);

	@Modifying
    @Query("DELETE FROM Tool t WHERE t.fileName = :fileName")
    void deleteByFileName(@Param("fileName") String fileName);
}
