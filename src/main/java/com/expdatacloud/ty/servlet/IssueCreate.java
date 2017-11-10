package com.expdatacloud.ty.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.bc.issue.IssueService;
import com.atlassian.jira.bc.issue.IssueService.CreateValidationResult;
import com.atlassian.jira.bc.issue.IssueService.IssueResult;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.exception.CreateException;
import com.atlassian.jira.issue.AttachmentManager;
import com.atlassian.jira.issue.IssueInputParameters;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.issue.attachment.Attachment;
import com.atlassian.jira.issue.attachment.CreateAttachmentParamsBean;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.web.util.AttachmentException;

/**
 * 创建问题
 * 
 * 
 * @version 1.0
 * @author linjunbiao
 * @created 2017.8.31
 */

public class IssueCreate extends HttpServlet{
    private static final Logger log = LoggerFactory.getLogger(IssueCreate.class);

    private IssueService issueService;
	private JiraAuthenticationContext authenticationContext;
	private AttachmentManager attachmentManager;
	

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		issueService = ComponentAccessor.getIssueService();
		authenticationContext = ComponentAccessor.getJiraAuthenticationContext();
		attachmentManager = ComponentAccessor.getAttachmentManager();
		
	}
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
    	
        doPost(req, resp);
        
    }

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		long projectId = Long.parseLong(req.getParameter("projectId"));				//项目
		String issueTypeId = req.getParameter("issueTypeId");			//问题
		String summary = req.getParameter("summary");								//概括
		String reporterId = req.getParameter("reporterId");			//报告人
		String dueDate = req.getParameter("dueDate");
		String resolutionDate=req.getParameter("resolutionDate");
		String description = req.getParameter("description");						//描述
		String priorityId = req.getParameter("priorityId");			//优先级
		String assigneeId = req.getParameter("assigneeId");			//经办人
		long fixVersionIds = Long.parseLong(req.getParameter("fixVersionIds"));
		
		ApplicationUser user = authenticationContext.getLoggedInUser();
		IssueInputParameters issueInputParameters = issueService.newIssueInputParameters();
		issueInputParameters.setProjectId(projectId).setIssueTypeId(issueTypeId).setSummary(summary).setReporterId(reporterId)
				.setAssigneeId(assigneeId).setDescription(description).setStatusId("10000").setPriorityId(priorityId)
				.setFixVersionIds(10000L).setDueDate(dueDate).setResolutionDate(resolutionDate).setFixVersionIds(fixVersionIds);
		MutableIssue issue = createIssue(user, issueInputParameters);
		//attachFileOnIssue(issue, user, "C:/jar/111.txt", "myFile1");			//添加附件
		//attachFileOnIssue(issue, user, "C:/jar/222.txt", "myFile2");
		
		//printAttachments(issue);
		
		try {
			ComponentAccessor.getIssueLinkManager().createIssueLink(10000L, 10001L, 10000L, null, ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser());
		} catch (CreateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private void printAttachments(MutableIssue issue) {
		List<Attachment> attachments = this.attachmentManager.getAttachments(issue);
		for (Attachment attachment : attachments) {
			System.out.println("Attachment: " + attachment.getFilename() + " attached by " + attachment.getAuthorKey());
		}
	}

	private void attachFileOnIssue(MutableIssue issue, ApplicationUser user, String fileName, String newFileName) {
		try {
			CreateAttachmentParamsBean attachmentBean = new CreateAttachmentParamsBean.Builder(new File(fileName),
					newFileName, "text/plain", user, issue).build();
			this.attachmentManager.createAttachment(attachmentBean);
		} catch (AttachmentException e) {
			e.printStackTrace();
		}
	}
    
	private MutableIssue createIssue(ApplicationUser user,IssueInputParameters issueInputParameters) {
	
		CreateValidationResult createValidationResult = issueService.validateCreate(user, issueInputParameters);

		MutableIssue issue = create(user, createValidationResult);

		return issue;
	}
	
	public MutableIssue create(ApplicationUser user, CreateValidationResult createValidationResult) {
		MutableIssue issue = null;

		if (createValidationResult.isValid()) {
			IssueResult createResult = issueService.create(user, createValidationResult);
			if (createResult.isValid()) {
				issue = createResult.getIssue();
				System.out.println("Created " + issue.getKey());
				
			} else {
				Collection<String> errorMessages = createResult.getErrorCollection().getErrorMessages();
				for (String errorMessage : errorMessages) {
					System.out.println(errorMessage);
				}
				
			}
		} else {
			Collection<String> errorMessages = createValidationResult.getErrorCollection().getErrorMessages();
			for (String errorMessage : errorMessages) {
				System.out.println(errorMessage);
			}
			Map<String, String> errors = createValidationResult.getErrorCollection().getErrors();
			Set<String> errorKeys = errors.keySet();
			for (String errorKey : errorKeys) {
				System.out.println(errors.get(errorKey));
			}
			
		}
		return issue;
	}

}