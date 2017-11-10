package com.expdatacloud.ty.servlet;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.issuetype.IssueType;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.util.json.JSONArray;
import com.atlassian.jira.util.json.JSONException;
import com.atlassian.jira.util.json.JSONObject;

/**
 * 获取项目下 的问题类型
 * @
 * @projectId 项目id 10000
 * @version 1.0
 * @author linjunbiao
 * @created 2017.8.31
 */

public class GetProjectIssuetypes extends HttpServlet{
    private static final Logger log = LoggerFactory.getLogger(GetProjectIssuetypes.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
    	if(ComponentAccessor.getJiraAuthenticationContext().isLoggedInUser()){
    		
    		Long projectId = (long) Integer.parseInt(req.getParameter("projectId"));
    		
    		JSONArray jsonArray=new JSONArray();
    		System.out.println(projectId);
        	Project project = ComponentAccessor.getProjectManager().getProjectObj(projectId);	//获取指定项目
        	System.out.println(project);
        	Collection<IssueType> issueTypes = project.getIssueTypes();
        	Iterator<IssueType> iterator =issueTypes.iterator();
        	while(iterator.hasNext()){
        		JSONObject jsonObject=new JSONObject();
        		IssueType issueType =iterator.next();			//获取项目
        		
        		try {
    				jsonObject.put("Id",issueType.getId());
    				jsonObject.put("Name",issueType.getName());
    				jsonObject.put("Description",issueType.getDescription());
    			} catch (JSONException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			jsonArray.put(jsonObject);
        	}
           
            resp.getWriter().write(jsonArray.toString());
    	}else{
    		resp.getWriter().write("请登录！");
    	}
    }

}