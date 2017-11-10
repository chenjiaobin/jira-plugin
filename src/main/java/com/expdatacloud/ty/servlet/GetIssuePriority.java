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
import com.atlassian.jira.config.ConstantsManager;
import com.atlassian.jira.issue.priority.Priority;
import com.atlassian.jira.util.json.JSONArray;
import com.atlassian.jira.util.json.JSONException;
import com.atlassian.jira.util.json.JSONObject;

/**
 * 获取优先级
 * @
 * @
 * @version 1.0
 * @author linjunbiao
 * @created 2017.8.31
 */

public class GetIssuePriority extends HttpServlet{
    private static final Logger log = LoggerFactory.getLogger(GetIssuePriority.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
    	if(ComponentAccessor.getJiraAuthenticationContext().isLoggedInUser()){
    		
    		JSONArray jsonArray=new JSONArray();
    		
    		ConstantsManager constantsManager = ComponentAccessor.getConstantsManager();
    		Collection<Priority> priorities=constantsManager.getPriorities();
    		Iterator<Priority> Iterator = priorities.iterator();
    		while (Iterator.hasNext()) {
    			Priority priority=Iterator.next();
    			
    			JSONObject jsonObject=new JSONObject();
        					//获取项目
        		try {
    				jsonObject.put("Id",priority.getId());
    				jsonObject.put("Name",priority.getName());
    				jsonObject.put("Description",priority.getDescription());
    			} catch (JSONException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
        		jsonArray.put(jsonObject);
    		}
           
    		//resp.getWriter().write("<script>console.log("+jsonArray.toString()+")</script>");
            resp.getWriter().write(jsonArray.toString());
    	}else{
    		resp.getWriter().write("请登录！");
    	}
    }

}