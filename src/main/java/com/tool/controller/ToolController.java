package com.tool.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tool.entity.SopProcess;
import com.tool.entity.Tool;
import com.tool.entity.ToolConfiguration;
import com.tool.service.ToolService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

@RestController
@RequestMapping("/api/tools")
@CrossOrigin
public class ToolController {

	@Autowired
	private ToolService toolService;

	@Operation(tags = "saveTool")
	@PostMapping
	public ResponseEntity<Tool> saveTool(@RequestBody Tool tool) {
		return new ResponseEntity<Tool>(toolService.saveTool(tool), HttpStatus.CREATED);
	}

	@Operation(tags = "getAllTools")
	@GetMapping
	public ResponseEntity<List<Tool>> getAllTools() {
		return new ResponseEntity<List<Tool>>(toolService.getAllTools(), HttpStatus.OK);
	}

	@Operation(tags = "deleteTool")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteTool(@PathVariable Long id) throws Exception { 
		Tool tool = this.toolService.findById(id);
		if(tool!=null) {
		toolService.deleteTool(id);
		return new ResponseEntity<String>("The Tool config with id: "+id+" is deleted.",HttpStatus.OK);
		}
		return new ResponseEntity<String>("Tool with id: "+id+" not found",HttpStatus.NOT_FOUND);
	}
	
	@Operation(tags = "findToolById")
	@GetMapping("/{id}")
	public ResponseEntity<Tool> findToolById(@PathVariable Long id) throws Exception{
		Tool tool = this.toolService.findById(id);
		if(tool!=null) {
			return new ResponseEntity<Tool>(tool,HttpStatus.FOUND);
		}
		return new ResponseEntity<Tool>(tool, HttpStatus.NOT_FOUND);
	}
	
	@Operation(tags = "updateToolConfig")
	@PutMapping("/update")
    public ResponseEntity<Tool> updateToolConfig(@RequestBody Tool newConfig) throws Exception {
        Tool tool = toolService.findById(newConfig.getId());
        if(tool!=null) {
        	tool.setName(newConfig.getName());
        	tool.setFileName(newConfig.getFileName());
        	tool.setInstruction1(newConfig.getInstruction1());
        	tool.setInstruction2(newConfig.getInstruction2());
        	tool.setFindPattern(newConfig.getFindPattern());
        	tool.setSkipPausePlay(newConfig.getSkipPausePlay());
        	tool.setCategory(newConfig.getCategory());
        	tool.setFindNotPattern(newConfig.getFindNotPattern());
        	tool.setCompleteEnable(newConfig.getCompleteEnable());
        	tool.setInclude(newConfig.getInclude());
        	tool.setValidation_UI(newConfig.getValidation_UI());
        	
        	return new ResponseEntity<Tool>(this.toolService.saveTool(tool),HttpStatus.OK);
        }
        return null;
    }
	
	@Operation(tags = "getAllDistinctCategoryNames")
	@GetMapping("/allCategories")
	public ResponseEntity<Set<String>> getAllDistinctCategoryNames(){
		return new ResponseEntity<Set<String>>(toolService.findDistinctCategory(),HttpStatus.OK);
	}
	
	@Operation(tags = "getAllDistinctToolNames")
	@GetMapping("/allToolNames")
	public ResponseEntity<Set<String>> getAllDistinctToolNames(){
		return new ResponseEntity<Set<String>>(toolService.findDistinctName(),HttpStatus.OK);
	}
	
	@Operation(tags = "findDistinctFileNames")
	@GetMapping("/fileNames")
	public ResponseEntity<List<String>> findDistinctFileNames(){
		return new ResponseEntity<List<String>>(toolService.findDistinctFileNames(),HttpStatus.OK);
	}
	
	@Operation(tags = "deleteFileByFileName")
	@DeleteMapping("/delete/{fileName}")
	public ResponseEntity<String> deleteFileByFileName(@PathVariable String fileName){
		this.toolService.deleteByFileName(fileName);
		return new ResponseEntity<String>("File deleted Successfully",HttpStatus.OK);
	}

	@Operation(tags = "importTools")
	@PostMapping("/import")
	public ResponseEntity<List<Tool>> importTools(@RequestParam("file") MultipartFile file) throws Exception {
		String fileName = file.getOriginalFilename();
		JAXBContext context = JAXBContext.newInstance(ToolConfiguration.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		ToolConfiguration configuration = (ToolConfiguration) unmarshaller.unmarshal(file.getInputStream());

		for (SopProcess sopProcess : configuration.getSopProcesses()) {
			for (Tool tool : sopProcess.getTools()) {
				tool.setFileName(fileName);
				tool.setCategory(sopProcess.getCategory());
				toolService.saveTool(tool);
			}
		}
//		return toolService.getAllTools();
		return new ResponseEntity(toolService.getAllTools(),HttpStatus.OK);
	}

	@Operation(tags = "exportTools")
	@PostMapping("/export/{fileName}")
	public void exportTools(@PathVariable String fileName, HttpServletResponse response) throws IOException {
		List<Tool> selectedTools = toolService.findByFileName(fileName);
		
		String xml = generateXmlForTools(selectedTools);

		response.setContentType("application/xml");
		response.setHeader("Content-Disposition", "attachment; filename=tools_export.xml");

		try (PrintWriter out = response.getWriter()) {
			out.print(xml);
		}
	}
	
	@Operation(tags = "fetchToolDetailsByFileName")
	@GetMapping("/filter/{fileName}")
	public ResponseEntity<List<Tool>> fetchToolDetailsByFileName(@PathVariable String fileName){
		return new ResponseEntity<List<Tool>>(this.toolService.findByFileName(fileName),HttpStatus.OK);
	}

	
	private String generateXmlForTools(List<Tool> tools) {
	    StringBuilder xml = new StringBuilder();
	    xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
	    xml.append("<sop-configuration>\n");

	    // Group tools by category dynamically
	    Map<String, List<Tool>> groupedTools = tools.stream()
	                                                .collect(Collectors.groupingBy(Tool::getCategory));

	    for (Map.Entry<String, List<Tool>> entry : groupedTools.entrySet()) {
	        String category = entry.getKey();
	        List<Tool> categoryTools = entry.getValue();
	        
	        xml.append("<sop-process category=\"").append(category).append("\">\n");
	        for (Tool tool : categoryTools) {
	            xml.append("  <tool name=\"").append(tool.getName()).append("\"")
	               .append(" Instruction1=\"").append(tool.getInstruction1()).append("\"")
	               .append(" Instruction2=\"").append(tool.getInstruction2()).append("\"")
	               .append(" FindPattern=\"").append(tool.getFindPattern()).append("\"")
	               .append(" FindNotPattern=\"").append(tool.getFindNotPattern()).append("\"")
	               .append(" complete-enable=\"").append(tool.getCompleteEnable()).append("\"")
	               .append(" Include=\"").append(tool.getInclude()).append("\"")
	               .append(" Validation_UI=\"").append(tool.getValidation_UI()).append("\"")
	               .append(" skip-pause-play=\"").append(tool.getSkipPausePlay()).append("\"")
	               .append(" />\n");
	        }
	        xml.append("</sop-process>\n");
	    }

	    xml.append("</sop-configuration>");
	    return xml.toString();
	}

}
