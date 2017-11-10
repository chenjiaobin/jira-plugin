package com.expdatacloud.ty.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.bc.project.ProjectService;
import com.atlassian.jira.bc.project.ProjectService.DeleteProjectValidationResult;
import com.atlassian.jira.component.ComponentAccessor;

public class ProjectDelete extends HttpServlet {
	private static final Logger log = LoggerFactory.getLogger(ProjectDelete.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String projectKey = req.getParameter("projectKey");
		ProjectService projectService = ComponentAccessor.getComponent(ProjectService.class);
		DeleteProjectValidationResult deleteProjectValidationResult;
		deleteProjectValidationResult = projectService
				.validateDeleteProject(ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser(), projectKey);
		projectService.deleteProject(ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser(),
				deleteProjectValidationResult);
	}

}