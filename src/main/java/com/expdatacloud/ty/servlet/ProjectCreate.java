package com.expdatacloud.ty.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.bc.project.ProjectCreationData;
import com.atlassian.jira.bc.project.ProjectService;
import com.atlassian.jira.bc.project.ProjectService.CreateProjectValidationResult;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.project.ProjectCategory;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.util.json.JSONArray;
import com.atlassian.jira.util.json.JSONException;
import com.atlassian.jira.util.json.JSONObject;
import com.expdatacloud.ty.util.RestUrlSend;

public class ProjectCreate extends HttpServlet {
	private static final Logger log = LoggerFactory.getLogger(ProjectCreate.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String projectName = req.getParameter("projectName");
		String projectKey = req.getParameter("projectKey");
		String projectDesc = req.getParameter("projectDesc");
		String userKey = req.getParameter("userKey");
		Long categoryId= Long.parseLong(req.getParameter("categoryId"));
		
		ApplicationUser user=ComponentAccessor.getUserManager().getUserByKey(userKey);
		ProjectCategory category=ComponentAccessor.getProjectManager().getProjectCategory(categoryId);
		ProjectService projectService=ComponentAccessor.getComponent(ProjectService.class);
		ProjectCreationData projectCreationData;
		ProjectCreationData.Builder builder = new ProjectCreationData.Builder();
		
		builder.withKey(projectKey).withName(projectName).withDescription(projectDesc).withType("business").withLead(user);
		projectCreationData=builder.build();
		
		CreateProjectValidationResult createProjectValidationResult=projectService.validateCreateProject(ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser(), projectCreationData);
		Project project=projectService.createProject(createProjectValidationResult);
		ComponentAccessor.getProjectManager().setProjectCategory(project, category);
		
		RestUrlSend rs=new RestUrlSend();
		
		JSONObject jsonBack=null;
		JSONObject jsonFilter=new JSONObject();
		JSONObject jsonBoard=new JSONObject();
    	try {
    		jsonFilter.put("name","Filter for "+projectKey);
    		jsonFilter.put("description","");
    		jsonFilter.put("jql","project = "+projectName+" ORDER BY Rank ASC");
    		jsonFilter.put("favourite","false");
    		
    		jsonBack=rs.postSend("/rest/api/2/filter", jsonFilter, req, resp);
    		jsonBoard.put("name",projectKey+" board");
    		jsonBoard.put("type","scrum");
    		jsonBoard.put("filterId",jsonBack.get("id"));
    		rs.postSend("/rest/agile/1.0/board", jsonBoard, req, resp);
    				
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//ComponentAccessor.getProjectManager().createProject(ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser(), projectCreationData);
		// 添加工作流方案到项目
		/*
		 * try { GenericValue
		 * genericValue=ComponentAccessor.getWorkflowSchemeManager().getScheme(
		 * "GGGGGG");
		 * ComponentAccessor.getWorkflowSchemeManager().addSchemeToProject(
		 * ComponentAccessor.getProjectManager().getProjectObjByName("Demo2").
		 * getGenericValue(), genericValue); } catch (GenericEntityException e1)
		 * { // TODO Auto-generated catch block e1.printStackTrace(); }
		 */
	}

}