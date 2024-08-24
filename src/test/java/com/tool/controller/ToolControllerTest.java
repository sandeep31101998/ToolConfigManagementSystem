package com.tool.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tool.entity.Tool;
import com.tool.service.ToolService;

public class ToolControllerTest {

    @InjectMocks
    private ToolController toolController;

    @Mock
    private ToolService toolService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveTool() {
        // Create a sample tool object
        Tool tool = new Tool();
        tool.setId(1L);
        tool.setName("Sample Tool");
        tool.setFileName("sample-file.xml");
        tool.setInstruction1("Instruction 1");
        tool.setInstruction2("Instruction 2");
        tool.setFindPattern("Pattern");
        tool.setSkipPausePlay("Yes");
        tool.setCompleteEnable("No");
        tool.setCategory("Category");
        tool.setFindNotPattern("NotPattern");
        tool.setInclude("Include");
        tool.setValidation_UI("ValidationUI");

        // Mock the saveTool method of ToolService
        when(toolService.saveTool(any(Tool.class))).thenReturn(tool);

        // Call the saveTool method in ToolController
        ResponseEntity<Tool> response = toolController.saveTool(tool);

        // Assert that the response status is CREATED
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Assert that the returned Tool object matches the expected tool
        assertEquals(tool, response.getBody());
    }
    
    @Test
    void testGetAllTools() {
        // Create a list of sample tools
        List<Tool> tools = new ArrayList<>();
        Tool tool1 = new Tool();
        tool1.setId(1L);
        tool1.setName("Tool 1");
        Tool tool2 = new Tool();
        tool2.setId(2L);
        tool2.setName("Tool 2");
        tools.add(tool1);
        tools.add(tool2);

        // Mock the getAllTools method of ToolService
        when(toolService.getAllTools()).thenReturn(tools);

        // Call the getAllTools method in ToolController
        ResponseEntity<List<Tool>> response = toolController.getAllTools();

        // Assert that the response status is OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Assert that the returned list of tools matches the expected list
        assertEquals(tools, response.getBody());
    }
    
    @Test
    void testDeleteTool_Success() throws Exception {
        Long toolId = 1L;
        Tool tool = new Tool();
        tool.setId(toolId);

        // Mock the findById method to return a tool
        when(toolService.findById(toolId)).thenReturn(tool);

        // Mock the deleteTool method to do nothing
        doNothing().when(toolService).deleteTool(toolId);

        // Call the deleteTool method in ToolController
        ResponseEntity<String> response = toolController.deleteTool(toolId);

        // Assert that the response status is OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Assert that the response body contains the correct message
        assertEquals("The Tool config with id: " + toolId + " is deleted.", response.getBody());
    }

    @Test
    void testDeleteTool_NotFound() throws Exception {
        Long toolId = 1L;

        // Mock the findById method to return null (tool not found)
        when(toolService.findById(toolId)).thenReturn(null);

        // Call the deleteTool method in ToolController
        ResponseEntity<String> response = toolController.deleteTool(toolId);

        // Assert that the response status is NOT_FOUND
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        // Assert that the response body contains the correct message
        assertEquals("Tool with id: " + toolId + " not found", response.getBody());
    }
    
    @Test
    void testUpdateToolConfig_Success() throws Exception {
        Tool existingTool = new Tool();
        existingTool.setId(1L);
        existingTool.setName("Old Name");

        Tool updatedTool = new Tool();
        updatedTool.setId(1L);
        updatedTool.setName("New Name");

        // Mock the findById method to return the existing tool
        when(toolService.findById(existingTool.getId())).thenReturn(existingTool);

        // Mock the saveTool method to return the updated tool
        when(toolService.saveTool(existingTool)).thenReturn(updatedTool);

        // Call the updateToolConfig method in ToolController
        ResponseEntity<Tool> response = toolController.updateToolConfig(updatedTool);

        // Assert that the response status is OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Assert that the response body contains the updated tool
        assertEquals(updatedTool, response.getBody());
    }

    @Test
    void testGetAllDistinctCategoryNames() {
        Set<String> categories = Set.of("Category1", "Category2");

        // Mock the findDistinctCategory method to return the categories
        when(toolService.findDistinctCategory()).thenReturn(categories);

        // Call the getAllDistinctCategoryNames method in ToolController
        ResponseEntity<Set<String>> response = toolController.getAllDistinctCategoryNames();

        // Assert that the response status is OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Assert that the response body contains the expected categories
        assertEquals(categories, response.getBody());
    }

    @Test
    void testGetAllDistinctToolNames() {
        Set<String> toolNames = Set.of("ToolName1", "ToolName2");

        // Mock the findDistinctName method to return the tool names
        when(toolService.findDistinctName()).thenReturn(toolNames);

        // Call the getAllDistinctToolNames method in ToolController
        ResponseEntity<Set<String>> response = toolController.getAllDistinctToolNames();

        // Assert that the response status is OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Assert that the response body contains the expected tool names
        assertEquals(toolNames, response.getBody());
    }

    @Test
    void testFindDistinctFileNames() {
        List<String> fileNames = List.of("FileName1", "FileName2");

        // Mock the findDistinctFileNames method to return the file names
        when(toolService.findDistinctFileNames()).thenReturn(fileNames);

        // Call the findDistinctFileNames method in ToolController
        ResponseEntity<List<String>> response = toolController.findDistinctFileNames();

        // Assert that the response status is OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Assert that the response body contains the expected file names
        assertEquals(fileNames, response.getBody());
    }
    
    @Test
    void testDeleteFileByFileName() {
        String fileName = "FileName1";

        // Mock the deleteByFileName method to do nothing
        doNothing().when(toolService).deleteByFileName(fileName);

        // Call the deleteFileByFileName method in ToolController
        ResponseEntity<String> response = toolController.deleteFileByFileName(fileName);

        // Assert that the response status is OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Assert that the response body contains the correct message
        assertEquals("File deleted Successfully", response.getBody());
    }

   
}
