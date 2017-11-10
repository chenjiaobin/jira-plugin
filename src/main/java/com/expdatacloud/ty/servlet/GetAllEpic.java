package com.expdatacloud.ty.servlet;

import org.ofbiz.core.entity.GenericEntityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.issuetype.IssueType;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.util.json.JSONArray;
import com.atlassian.jira.util.json.JSONException;
import com.atlassian.jira.util.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class GetAllEpic extends HttpServlet{
    private static final Logger log = LoggerFactory.getLogger(GetAllEpic.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
    	JSONArray allEpic= new JSONArray();
		
		try {
			//获取所有项目
			List<Project> projects=ComponentAccessor.getProjectManager().getProjects();
			Iterator<Project> iteratorProject=projects.iterator();
			while(iteratorProject.hasNext()){
				Project project=iteratorProject.next();
				//获取每个项目下的问题类型
				Collection<IssueType> issueTypes=project.getIssueTypes();
				Iterator<IssueType> iteratorIssueType=issueTypes.iterator();
				while(iteratorIssueType.hasNext()){
					IssueType issueType=iteratorIssueType.next();
				}
				//获取每个项目下的所有问题
				Collection<Long> longs=ComponentAccessor.getIssueManager().getIssueIdsForProject(project.getId());
				List<Issue> issues=ComponentAccessor.getIssueManager().getIssueObjects(longs);
				Iterator<Issue> iteratorIssue=issues.iterator();
				while(iteratorIssue.hasNext()){
					Issue issue=iteratorIssue.next();
					JSONObject jsonEpic=new JSONObject();
					jsonEpic.put("issueKey", issue.getKey());
					jsonEpic.put("issueId", issue.getId());
					//判断是否epic类型
					if("Epic".equals(issue.getIssueType().getName())){					
						allEpic.put(jsonEpic);
					}
				}
			}
         resp.getWriter().write(allEpic.toString());
        	
//          resp.getWriter().write("<script>console.log("+allEpic.toString()+")</script>");
		} catch (GenericEntityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}