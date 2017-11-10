package com.expdatacloud.ty.servlet;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.bc.issue.IssueService;
import com.atlassian.jira.bc.issue.IssueService.IssueResult;
import com.atlassian.jira.bc.issue.IssueService.UpdateValidationResult;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.IssueInputParameters;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.user.ApplicationUser;

public class IssueUpdate extends HttpServlet{
    private static final Logger log = LoggerFactory.getLogger(IssueUpdate.class);

private IssueService issueService=ComponentAccessor.getIssueService();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        doPost(req, resp);
    }
    
    @Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
    	
    	long issueId = Long.parseLong(req.getParameter("issueId"));				//项目
		String issueTypeId = req.getParameter("issueTypeId");			//问题
		String summary = req.getParameter("summary");								//概括
		String reporterId = req.getParameter("reporterId");			//报告人
		//String dueDate = req.getParameter("dueDate");								
		String description = req.getParameter("description");						//描述
		String priorityId = req.getParameter("priorityId");			//优先级
		String assigneeId = req.getParameter("assigneeId");			//经办人
		
		
		
		MutableIssue issue=ComponentAccessor.getIssueManager().getIssueObject(issueId);
		System.out.println(issue+"222222");
		ApplicationUser user=ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();
		
		IssueInputParameters issueInputParameters = issueService.newIssueInputParameters();
		issueInputParameters.setIssueTypeId(issueTypeId).setSummary(summary).setReporterId(reporterId)
		.setAssigneeId(assigneeId).setDescription(description).setStatusId("10000").setPriorityId(priorityId)
		.setFixVersionIds(10000L);
		
		editIssue(issue,user,issueInputParameters);
	}



	private MutableIssue editIssue(MutableIssue issue, ApplicationUser user,IssueInputParameters issueInputParameters) {
		

		UpdateValidationResult updateValidationResult = issueService.validateUpdate(user, issue.getId(),issueInputParameters);

		if (updateValidationResult.isValid()) {
			IssueResult updateResult = issueService.update(user, updateValidationResult);
			if (updateResult.isValid()) {
				MutableIssue updatedIssue = updateResult.getIssue();
				System.out.println("Updated " + updatedIssue.getKey() + " with new Summary!");
			
				return issue;
			} else {
				Collection<String> errorMessages = updateResult.getErrorCollection().getErrorMessages();
				for (String errorMessage : errorMessages) {
					System.out.println(errorMessage);
				}
				
			}
		} else {
			Collection<String> errorMessages = updateValidationResult.getErrorCollection().getErrorMessages();
			for (String errorMessage : errorMessages) {
				System.out.println(errorMessage);
			}
			Map<String, String> errors = updateValidationResult.getErrorCollection().getErrors();
			Set<String> errorKeys = errors.keySet();
			for (String errorKey : errorKeys) {
				System.out.println(errors.get(errorKey));
			}
			
		}

		return null;
	}

}