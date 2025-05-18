package com.really.oh.RAYTT2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateIssueRequest {

    @JsonProperty("summary")
    private String summary;

    @JsonProperty("description")
    private String description;

    @JsonProperty("project")
    private Project project;

    public CreateIssueRequest() {
    }

    public CreateIssueRequest(String summary, String description, Project project) {
        this.summary = summary;
        this.description = description;
        this.project = project;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "CreateIssueRequest{" +
                "summary='" + summary + '\'' +
                ", description='" + description + '\'' +
                ", project=" + project +
                '}';
    }
}
