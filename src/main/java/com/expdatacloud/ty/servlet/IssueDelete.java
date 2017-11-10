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
import com.atlassian.jira.bc.issue.IssueService.DeleteValidationResult;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.util.ErrorCollection;

public class IssueDelete extends HttpServlet{
    private static final Logger log = LoggerFactory.getLogger(IssueDelete.class);

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
    	
    	ApplicationUser user=ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();
    	MutableIssue issue=ComponentAccessor.getIssueManager().getIssueObject(issueId);
    	if(issue==null){
    		System.out.println("问题不存在");
    	}
    	deleteIssue(issue,user);
	}


	private void deleteIssue(MutableIssue issue, ApplicationUser user) {
		DeleteValidationResult deleteValidationResult = issueService.validateDelete(user, issue.getId());

		if (deleteValidationResult.isValid()) {
			ErrorCollection deleteErrors = issueService.delete(user, deleteValidationResult);
			if (deleteErrors.hasAnyErrors()) {
				Collection<String> errorMessages = deleteErrors.getErrorMessages();
				for (String errorMessage : errorMessages) {
					System.out.println(errorMessage);
				}
				
			} else {
				System.out.println("Deleted!");
			}
		} else {
			Collection<String> errorMessages = deleteValidationResult.getErrorCollection().getErrorMessages();
			for (String errorMessage : errorMessages) {
				System.out.println(errorMessage);
			}
			Map<String, String> errors = deleteValidationResult.getErrorCollection().getErrors();
			Set<String> errorKeys = errors.keySet();
			for (String errorKey : errorKeys) {
				System.out.println(errors.get(errorKey));
			}
			
		}
	}

}