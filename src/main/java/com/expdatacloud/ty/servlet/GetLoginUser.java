package com.expdatacloud.ty.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.util.json.JSONArray;
import com.atlassian.jira.util.json.JSONException;
import com.atlassian.jira.util.json.JSONObject;

/**
 * 获取登陆的用户的信息
 * @
 * @
 * @version 1.0
 * @author linjunbiao
 * @created 2017.8.31
 */

public class GetLoginUser extends HttpServlet{
    private static final Logger log = LoggerFactory.getLogger(GetLoginUser.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
    	if(ComponentAccessor.getJiraAuthenticationContext().isLoggedInUser()){
    		
    		ApplicationUser applicationUser = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();
    		
    		JSONArray jsonArray=new JSONArray();	
    		JSONObject jsonObject=new JSONObject();
    		
    			//获取项目
    		try {
				jsonObject.put("Id",applicationUser.getId());
				jsonObject.put("Name",applicationUser.getName());
				jsonObject.put("Key",applicationUser.getKey());
				jsonObject.put("EmailAddress",applicationUser.getEmailAddress());
				jsonObject.put("DisplayName",applicationUser.getDisplayName());
				jsonObject.put("Username",applicationUser.getUsername());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jsonArray.put(jsonObject);
        
           
            //resp.getWriter().write("<script>console.log("+jsonArray.toString()+")</script>");
            resp.getWriter().write(jsonArray.toString());
    	}else{
    		resp.getWriter().write("请登录！");
    	}
    }

}