package com.tool.entity;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "sop-configuration")
public class ToolConfiguration {

    private List<SopProcess> sopProcesses;

    @XmlElement(name = "sop-process")
    public List<SopProcess> getSopProcesses() {
        return sopProcesses;
    }

    public void setSopProcesses(List<SopProcess> sopProcesses) {
        this.sopProcesses = sopProcesses;
    }
}


