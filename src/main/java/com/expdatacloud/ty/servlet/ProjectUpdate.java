package com.expdatacloud.ty.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.bc.project.ProjectService;
import com.atlassian.jira.bc.project.ProjectService.UpdateProjectValidationResult;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.user.ApplicationUser;

public class ProjectUpdate extends HttpServlet{
    private static final Logger log = LoggerFactory.getLogger(ProjectUpdate.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
    	doPost(req,resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	String newName = req.getParameter("newName");
		String newKey = req.getParameter("newKey");
		String newDesc = req.getParameter("newDesc");
		//String newLeadKey = req.getParameter("newLeadKey");
		String newUrl = "";
		//String newAssigneeType = req.getParameter("newAssigneeType");
		//String newAvatarId = req.getParameter("newAvatarId");
		String oldKey = req.getParameter("oldKey");
		Project originalProject=ComponentAccessor.getProjectManager().getProjectByCurrentKey(oldKey);
		UpdateProjectValidationResult updateProjectValidationResult;
		ApplicationUser user=ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();
		ApplicationUser newLead = ComponentAccessor.getUserManager().getUserByKey(user.getKey());
		ProjectService projectService=ComponentAccessor.getComponent(ProjectService.class);		
		updateProjectValidationResult=projectService.validateUpdateProject(user, originalProject, newName, newKey, newDesc, user, "",originalProject.getAssigneeType(), originalProject.getAvatar().getId());
		projectService.updateProject(updateProjectValidationResult);
    }

}