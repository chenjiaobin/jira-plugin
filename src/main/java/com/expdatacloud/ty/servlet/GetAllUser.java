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
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.user.util.UserManager;
import com.atlassian.jira.util.json.JSONArray;
import com.atlassian.jira.util.json.JSONException;
import com.atlassian.jira.util.json.JSONObject;

/**
 * 获取所有的用户
 * @
 * @
 * @version 1.0
 * @author linjunbiao
 * @created 2017.8.31
 */

public class GetAllUser extends HttpServlet{
    private static final Logger log = LoggerFactory.getLogger(GetAllUser.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
    	
    	if(ComponentAccessor.getJiraAuthenticationContext().isLoggedInUser()){
    		
    		JSONArray jsonArray=new JSONArray();
    		UserManager userManager = ComponentAccessor.getUserManager();
    		
    		Collection<ApplicationUser> applicationUsers=userManager.getAllApplicationUsers();
    		Iterator<ApplicationUser> iterator=applicationUsers.iterator();
    		while (iterator.hasNext()) {
    			ApplicationUser applicationUser=iterator.next();	
    			JSONObject jsonObject=new JSONObject();
        		try {
    				jsonObject.put("Id",applicationUser.getId());
    				jsonObject.put("Name",applicationUser.getName());
    				jsonObject.put("EmailAddress",applicationUser.getEmailAddress());
    				jsonObject.put("Key",applicationUser.getKey());
    				jsonObject.put("DisplayName",applicationUser.getDisplayName());
    				jsonObject.put("Username",applicationUser.getUsername());
    				jsonObject.put("Active",applicationUser.isActive());
    				jsonObject.put("DirectoryId",applicationUser.getDirectoryId());
    				jsonObject.put("getClass",applicationUser.getDirectoryUser().getClass());
    				
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