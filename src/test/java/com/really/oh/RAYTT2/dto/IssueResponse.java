package com.really.oh.RAYTT2.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("idReadable")
    private String idReadable;

    @JsonProperty("summary")
    private String summary;

    @JsonProperty("description")
    private String description;

    @JsonProperty("project")
    private Project project;

    public IssueResponse() {
    }

    // Геттеры и сеттеры
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdReadable() {
        return idReadable;
    }

    public void setIdReadable(String idReadable) {
        this.idReadable = idReadable;
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
        return "IssueResponse{" +
                "id='" + id + '\'' +
                ", idReadable='" + idReadable + '\'' +
                ", summary='" + summary + '\'' +
                ", description='" + description + '\'' +
                ", project=" + project +
                '}';
    }
}
