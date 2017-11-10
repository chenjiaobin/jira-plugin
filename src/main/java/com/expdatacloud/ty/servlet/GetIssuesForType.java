package com.expdatacloud.ty.servlet;

import org.ofbiz.core.entity.GenericEntityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.exception.DataAccessException;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.issue.link.IssueLink;
import com.atlassian.jira.issue.link.IssueLinkManager;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.util.json.JSONArray;
import com.atlassian.jira.util.json.JSONException;
import com.atlassian.jira.util.json.JSONObject;
import com.expdatacloud.ty.util.RestUrlSend;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class GetIssuesForType extends HttpServlet {
	private static final Logger log = LoggerFactory.getLogger(GetIssuesForType.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long projectId = Long.parseLong(req.getParameter("projectId"));
		String type = req.getParameter("type").toLowerCase();
		JSONArray allIssues = new JSONArray();
		RestUrlSend rs = new RestUrlSend();
		IssueLinkManager issueLinkManager = ComponentAccessor.getIssueLinkManager();
		Iterator<Issue> iteratorIssue;
		try {
			Project project = ComponentAccessor.getProjectManager().getProjectObj(projectId);
			//根据项目id获取项目下所有问题
			Collection<Long> longs = ComponentAccessor.getIssueManager().getIssueIdsForProject(project.getId());
			List<Issue> issues = ComponentAccessor.getIssueManager().getIssueObjects(longs);
			if (issues.size() == 0) {

			} else {
				iteratorIssue = issues.iterator();
				//遍历所有问题
				while (iteratorIssue.hasNext()) {
					Issue issue = iteratorIssue.next();
					JSONObject issueList = new JSONObject();
					issueList.put("issueType", issue.getIssueType().getName());
					issueList.put("issueKey", issue.getKey());
					issueList.put("issueId", issue.getId());
					issueList.put("issueName", issue.getSummary());
					issueList.put("priority", issue.getPriority().getName());
					issueList.put("status", issue.getStatus().getName());
					if (issue.getAssigneeId() == null || "".equals(issue.getAssigneeId())) {
						issueList.put("userName", "Unassigned");
					} else {
						issueList.put("userName", issue.getAssigneeId());
					}
					//根据前台接受的问题类型执行相应的代码
					switch (type) {
					case "epic":
						if ("Epic".equals(issue.getIssueType().getName())) {
							JSONArray arrIssuelink = new JSONArray();
							//取epic下的问题链接的集合
							List<IssueLink> list = issueLinkManager.getOutwardLinks(issue.getId());
							if (list.size() == 0) {
							} else {
								Iterator<IssueLink> iteratorIssueLink = list.iterator();
								while (iteratorIssueLink.hasNext()) {
									JSONObject jsonIssue = new JSONObject();
									JSONArray arrSubtask = new JSONArray();
									IssueLink issueLink = iteratorIssueLink.next();
									//获取问题链接外指向的那个问题对象
									Issue mutableIssue = issueLink.getDestinationObject();
									jsonIssue.put("issueType", mutableIssue.getIssueType().getName());
									jsonIssue.put("issueKey", mutableIssue.getKey());
									jsonIssue.put("issueId", mutableIssue.getId());
									jsonIssue.put("issueName", mutableIssue.getSummary());
									jsonIssue.put("priority", mutableIssue.getPriority().getName());
									jsonIssue.put("status", mutableIssue.getStatus().getName());
									if (mutableIssue.getAssigneeId() == null
											|| "".equals(mutableIssue.getAssigneeId())) {
										jsonIssue.put("userName", "Unassigned");
									} else {
										jsonIssue.put("userName", issue.getAssigneeId());
									}
									//获取问题链接的那个问题对象下的子任务
									Collection<Issue> collection = mutableIssue.getSubTaskObjects();
									if (collection.size() == 0) {

									} else {
										Iterator<Issue> iterator2 = collection.iterator();
										while (iterator2.hasNext()) {
											JSONObject subTask2 = new JSONObject();
											Issue issueSubtask = iterator2.next();
											System.out.println(issueSubtask.getKey());
											subTask2.put("issueType", issueSubtask.getIssueType().getName());
											subTask2.put("issueKey", issueSubtask.getKey());
											subTask2.put("issueId", issueSubtask.getId());
											subTask2.put("issueName", issueSubtask.getSummary());
											subTask2.put("priority", issueSubtask.getPriority().getName());
											subTask2.put("status", issueSubtask.getStatus().getName());
											if (issueSubtask.getAssigneeId() == null
													|| "".equals(issueSubtask.getAssigneeId())) {
												subTask2.put("userName", "Unassigned");
											} else {
												subTask2.put("userName", issue.getAssigneeId());
											}
											arrSubtask.put(subTask2);
										}
										jsonIssue.put("values", arrSubtask);
									}
									arrIssuelink.put(jsonIssue);
								}
								issueList.putOpt("values", arrIssuelink);
							}
							allIssues.put(issueList);

						}
						break;
					case "sub-task":
						if ("Sub-task".equals(issue.getIssueType().getName())) {
							allIssues.put(issueList);
						}
						break;
					case "story":
						if ("Story".equals(issue.getIssueType().getName())) {
							JSONArray arrSubtask = new JSONArray();
							Collection<Issue> collection = issue.getSubTaskObjects();
							if (collection.size() == 0) {

							} else {
								Iterator<Issue> iterator2 = collection.iterator();
								while (iterator2.hasNext()) {
									JSONObject subTask2 = new JSONObject();
									Issue issueSubtask = iterator2.next();
									System.out.println(issueSubtask.getKey());
									subTask2.put("issueType", issueSubtask.getIssueType().getName());
									subTask2.put("issueKey", issueSubtask.getKey());
									subTask2.put("issueId", issueSubtask.getId());
									subTask2.put("issueName", issueSubtask.getSummary());
									subTask2.put("priority", issueSubtask.getPriority().getName());
									subTask2.put("status", issueSubtask.getStatus().getName());
									if (issueSubtask.getAssigneeId() == null
											|| "".equals(issueSubtask.getAssigneeId())) {
										subTask2.put("userName", "Unassigned");
									} else {
										subTask2.put("userName", issue.getAssigneeId());
									}
									arrSubtask.put(subTask2);
								}
								issueList.put("values", arrSubtask);
							}
							allIssues.put(issueList);
						}
						break;
					case "task":
						if ("Task".equals(issue.getIssueType().getName())) {
							JSONArray arrSubtask = new JSONArray();
							Collection<Issue> collection = issue.getSubTaskObjects();
							if (collection.size() == 0) {

							} else {
								Iterator<Issue> iterator2 = collection.iterator();
								while (iterator2.hasNext()) {
									JSONObject subTask2 = new JSONObject();
									Issue issueSubtask = iterator2.next();
									System.out.println(issueSubtask.getKey());
									subTask2.put("issueType", issueSubtask.getIssueType().getName());
									subTask2.put("issueKey", issueSubtask.getKey());
									subTask2.put("issueId", issueSubtask.getId());
									subTask2.put("issueName", issueSubtask.getSummary());
									subTask2.put("priority", issueSubtask.getPriority().getName());
									subTask2.put("status", issueSubtask.getStatus().getName());
									if (issueSubtask.getAssigneeId() == null
											|| "".equals(issueSubtask.getAssigneeId())) {
										subTask2.put("userName", "Unassigned");
									} else {
										subTask2.put("userName", issue.getAssigneeId());
									}
									arrSubtask.put(subTask2);
								}
								issueList.put("values", arrSubtask);
							}
							allIssues.put(issueList);
						}
						break;
					case "bug":
						if ("Bug".equals(issue.getIssueType().getName())) {
							allIssues.put(issueList);
						}
						break;

					default:
						allIssues.put(issueList);
						break;
					}
				}
			}
		//	resp.getWriter().write("<script>console.log(" + allIssues.toString() + ")</script>");
			resp.getWriter().write(allIssues.toString());
		} catch (DataAccessException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (GenericEntityException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

}