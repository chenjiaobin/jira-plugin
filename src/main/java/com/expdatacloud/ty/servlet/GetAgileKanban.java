package com.expdatacloud.ty.servlet;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ofbiz.core.entity.GenericEntityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.CustomFieldManager;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.status.Status;
import com.atlassian.jira.util.json.JSONArray;
import com.atlassian.jira.util.json.JSONException;
import com.atlassian.jira.util.json.JSONObject;
import com.atlassian.jira.workflow.JiraWorkflow;
import com.atlassian.jira.workflow.WorkflowManager;
import com.opensymphony.workflow.loader.ActionDescriptor;
import com.opensymphony.workflow.loader.StepDescriptor;

/**
 * 获取面板的issue数据，根据工作流状态分类
 * 
 * @author biao
 * @version 1.0
 * @created 2017.10.12
 */

public class GetAgileKanban extends HttpServlet{
    private static final Logger log = LoggerFactory.getLogger(GetAgileKanban.class);

    private WorkflowManager workflowManager = ComponentAccessor.getWorkflowManager();
	private CustomFieldManager customFieldManager = ComponentAccessor.getCustomFieldManager();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 获取项目id
		Long projectId = Long.parseLong(req.getParameter("projectId"));
		// 存放工作流状态的map
		Map<String, String> statusMap = new HashMap<String, String>();
		// 存放分好组的问题集
		Map<String, Object> statusArrayMap = new HashMap<String, Object>();
		Collection<Issue> issues = null;
		List list = null;
		try {
			Collection<Long> issueIds = ComponentAccessor.getIssueManager().getIssueIdsForProject(projectId);
			issues = ComponentAccessor.getIssueManager().getIssueObjects(issueIds);
		} catch (GenericEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONArray jsonArray = new JSONArray();
		JSONArray todo = new JSONArray();
		JSONArray doing = new JSONArray();
		JSONArray done = new JSONArray();
		// 获取工作流下的状态类型
		Issue issue1 = issues.iterator().next();
		JiraWorkflow jiraWorkflow = workflowManager.getWorkflow(issue1);
		List<Status> statusList = jiraWorkflow.getLinkedStatusObjects();
		Iterator<Status> iterator = statusList.iterator();
		while (iterator.hasNext()) {
			Status status = iterator.next();
			statusMap.put(status.getName(), status.getId());
		}
		// 循环项目下的问题集，进行分类。
		Iterator<Issue> iterator2 = issues.iterator();
		while (iterator2.hasNext()) {
			Issue issue = iterator2.next();
			System.out.println(issue.getKey());
			JSONObject jsonObject = new JSONObject();
			JSONObject actions = new JSONObject();
			try {
				if (issue.getAssignee().getId() == null) {

				} else {
					jsonObject.put("Assignee", ComponentAccessor.getAvatarService().getAvatarTagged(issue.getAssignee(), issue.getAssignee()).getId());
				}
				JiraWorkflow workflow = workflowManager.getWorkflow(issue);
				StepDescriptor descriptor = workflow.getLinkedStep(issue.getStatus());
				List<ActionDescriptor> actionss = descriptor.getActions();
				for (ActionDescriptor action : actionss) {
					actions.put(action.getName(),action.getId());
				}
				jsonObject.put("Actions", actions);
				jsonObject.put("Id", issue.getId());
				jsonObject.put("Key", issue.getKey());
				jsonObject.put("StatusId", issue.getStatusId());
				jsonObject.put("Summary", issue.getSummary());
				jsonObject.put("IssueTypeAvatarId", issue.getIssueType().getIconUrl());
				jsonObject.put("IssueTypeName", issue.getIssueType().getName());
				jsonObject.put("Priority", issue.getPriority().getIconUrl());
				if (issue.getStatusId().equals(statusMap.get("To Do"))) {
					todo.put(jsonObject);
				}
				if (issue.getStatusId().equals(statusMap.get("In Progress"))) {
					doing.put(jsonObject);
				}
				if (issue.getStatusId().equals(statusMap.get("Done"))) {
					done.put(jsonObject);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		statusArrayMap.put("To Do", todo);
		statusArrayMap.put("In Progress", doing);
		statusArrayMap.put("Done", done);
		jsonArray.put(statusArrayMap);
		resp.getWriter().write("<script>console.log(" + jsonArray.toString() + ")</script>");

	}

}