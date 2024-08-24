package com.tool.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tool.entity.Tool;
import com.tool.exception.ToolNotFound;
import com.tool.repository.ToolRepository;

import jakarta.transaction.Transactional;

@Service
public class ToolService {

	@Autowired
	private ToolRepository toolRepository;

//	public Tool saveTool(Tool tool) {
//		return toolRepository.save(tool);
//	}

	public List<Tool> getAllTools() {
		return toolRepository.findAll();
	}

	public void deleteTool(Long id) {
		toolRepository.deleteById(id);
	}

	public List<Tool> findByIds(List<Long> ids) {
		return toolRepository.findAllById(ids);
	}
	
	public Tool findById(Long id) throws Exception {
		return toolRepository.findById(id).orElseThrow(()-> new ToolNotFound("Record not found for id: "+id));
	}

	public Tool saveTool(Tool tool) {
		// Truncate the find pattern before saving
		tool.setFindPattern(tool.getFindPattern());
		return toolRepository.save(tool);
	}
	
	public Set<String> findDistinctCategory(){
		List<Tool> list = this.getAllTools();
		return list.stream().map(lists -> lists.getCategory()).collect(Collectors.toSet());
	}
	
	public Set<String> findDistinctName(){
		List<Tool> list = this.getAllTools();
		return list.stream().map(lists -> lists.getName()).collect(Collectors.toSet());
	}
	
	public List<String> findDistinctFileNames(){
		return this.toolRepository.findDistinctFileNames();
	}
	
	public List<Tool> findByFileName(String fileName){
		return this.toolRepository.findByFileName(fileName);
	}
	
	@Transactional
	public void deleteByFileName(String fileName) {
		this.toolRepository.deleteByFileName(fileName);
	}
	
	public Tool findToolById(Long id) {
		return this.toolRepository.findById(id).orElseThrow(() -> new ToolNotFound("Record not found with id: "+id));
	}
}
