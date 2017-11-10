package com.expdatacloud.ty.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.bc.issue.IssueService;
import com.atlassian.jira.bc.issue.IssueService.TransitionValidationResult;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.IssueInputParameters;
import com.atlassian.jira.issue.IssueManager;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.workflow.WorkflowManager;

/**
 * 改变工作流状态
 * @author biao
 * @version 1.0
 * @created 2017.10.12 
 */

public class IssueStatusChange extends HttpServlet{
    private static final Logger log = LoggerFactory.getLogger(IssueStatusChange.class);

    private JiraAuthenticationContext authenticationContext = ComponentAccessor.getJiraAuthenticationContext();
    private IssueService issueService=ComponentAccessor.getIssueService();
    private ApplicationUser user= authenticationContext.getLoggedInUser();
    private IssueManager issueManager=ComponentAccessor.getIssueManager();
    private WorkflowManager workflowManager=ComponentAccessor.getWorkflowManager();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
    	doPost(req, resp);
    }

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//要更改的问题id
		Long issueId=Long.parseLong(req.getParameter("issueId"));
		//要改变的状态
		Integer actionId=Integer.parseInt(req.getParameter("actionId"));
    	MutableIssue issue=issueManager.getIssueObject(issueId);  	
    	IssueInputParameters issueInputParameters = issueService.newIssueInputParameters();		
		TransitionValidationResult validationResult = issueService.validateTransition(user,issue.getId(), actionId , issueInputParameters);
		issueService.transition(user, validationResult);
		
		/*WorkflowTransitionUtil workflowTransitionUtil = ( WorkflowTransitionUtil ) JiraUtils.loadComponent( WorkflowTransitionUtilImpl.class );
		workflowTransitionUtil.setIssue(issue1);
		workflowTransitionUtil.setUsername(remoteuser);
		workflowTransitionUtil.setAction(5);//Id of the status you want to transition to
		workflowTransitionUtil.progress();*/
	
	}

}