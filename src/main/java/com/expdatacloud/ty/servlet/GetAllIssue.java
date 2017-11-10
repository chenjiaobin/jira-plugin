package com.expdatacloud.ty.servlet;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.IssueManager;
import com.atlassian.jira.issue.search.SearchException;
import com.atlassian.jira.issue.search.SearchResults;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.util.json.JSONArray;
import com.atlassian.jira.util.json.JSONObject;
import com.atlassian.jira.web.bean.PagerFilter;
import com.atlassian.query.Query;

public class GetAllIssue extends HttpServlet {
	private static final Logger log = LoggerFactory.getLogger(GetAllIssue.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if (ComponentAccessor.getJiraAuthenticationContext().isLoggedInUser()) {
			ApplicationUser loggedInUser = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();// 获取登录用户
			resp.setContentType("text/html");
			String issuename = req.getParameter("issuekey");
			String indexstr = req.getParameter("index");
			int index = 0;// 初始化index
			if (null != indexstr) { // 判断如果传入的参数是否为null
				index = Integer.parseInt(indexstr);// 不为空转化为int
			} else {
				index = 0;// 为空转化为0
			}
			int max = 2;
			JSONArray jsonarray = new JSONArray();
			IssueManager im = ComponentAccessor.getIssueManager();
			int total = Integer.parseInt(String.valueOf(im.getIssueCount()));
			int all = 0;
			if ((total % max) != 0) {
				all = (total / max) + 1;
			} else if ((total % max) == 0) {
				all = total / max;
			}
			try {
				List<Issue> issues = SearchIssue(loggedInUser, issuename, index, max);// 调用方法
				Iterator<Issue> iterator = issues.iterator();
				while (iterator.hasNext()) {
					JSONObject jsonObject = new JSONObject();
					Issue issue = iterator.next();
					jsonObject.put("totalpage", all);
					jsonObject.put("issuekey", issue.getKey());
					jsonObject.put("status", issue.getStatus().getName());
					jsonObject.put("summary", issue.getSummary());
					jsonObject.put("reportor", issue.getReporter().getDisplayName());
					jsonObject.put("creator", issue.getCreator().getDisplayName());
					jsonObject.put("updatetime", issue.getUpdated());
					jsonObject.put("createtime", issue.getCreated());
					jsonObject.put("desscription", issue.getDescription());
					jsonObject.put("duetime", issue.getDueDate());
					jsonObject.put("affectedversion", issue.getAffectedVersions());
					jsonObject.put("fixversion", issue.getFixVersions());
					jsonObject.put("Estimate", issue.getEstimate());
					jsonObject.put("issuetype", issue.getIssueType().getName());
					jsonObject.put("votes", issue.getVotes());
					jsonObject.put("resoulutiondate", issue.getResolutionDate());
					jsonObject.put("spenttime", issue.getTimeSpent());
					jsonObject.put("originEstime", issue.getOriginalEstimate());
					jsonObject.put("priortiy", issue.getPriority().getName());
					jsonObject.put("project", issue.getProjectObject());
					jsonObject.put("environment", issue.getEnvironment());
					jsonObject.put("spenttime", issue.getTimeSpent());
					jsonObject.put("subtask", issue.getSubTaskObjects());
					if (null == issue.getAssignee()) {
						jsonObject.put("assigner", "unassignee");
					} else {
						jsonObject.put("assigner", issue.getAssignee().getDisplayName());
					}
					jsonarray.put(jsonObject);

				}
			} catch (Exception e) {
			}
			resp.getWriter().write(jsonarray.toString());

		} else {
			resp.getWriter().write("请登录！");
		}
	}

	private List<Issue> SearchIssue(ApplicationUser user, String issuekey, int index, int max) throws SearchException {
		SearchService searchService = ComponentAccessor.getComponent(SearchService.class);// 获取searchservice

		String quotes = "\"";
		String issue = "issuekey=";
		String jqlQuery = null;

		PagerFilter pf = ComponentAccessor.getComponent(PagerFilter.class);
		PagerFilter pg = ComponentAccessor.getComponent(PagerFilter.class);
		if (null == issuekey) {// 判断是否有issuekey参数
			jqlQuery = "";// issuekey为空的jql规则
			int pageindex = 0;
			if (1 == index) {
				pageindex = index;

			} else {
				pageindex = max * (index - 1); // 40为每页条数

			}
			pg = pf.newPageAlignedFilter(pageindex, max);
		} else if (0 == index) {
			jqlQuery = issue + quotes + issuekey + quotes;
			pg = pf.getUnlimitedFilter();
		}

		SearchService.ParseResult parseResult = searchService.parseQuery(user, jqlQuery);// 执行jql
		if (parseResult.isValid()) {
			Query query = parseResult.getQuery();
			SearchResults results = searchService.search(user, query, pg);// 获取结果
			return results.getIssues();
		} else {
			System.out.println("Error parsing query:" + jqlQuery);
			return Collections.emptyList();

		}
	}

}