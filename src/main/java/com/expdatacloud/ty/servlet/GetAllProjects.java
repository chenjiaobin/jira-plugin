package com.expdatacloud.ty.servlet;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.exception.DataAccessException;
import com.atlassian.jira.permission.PermissionContext;
import com.atlassian.jira.permission.ProjectPermission;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.project.ProjectCategory;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.util.json.JSONArray;
import com.atlassian.jira.util.json.JSONException;
import com.atlassian.jira.util.json.JSONObject;

/**
 * 获取所有项目
 * 
 * 
 * @version 1.0
 * @author linjunbiao
 * @created 2017.8.31
 */

public class GetAllProjects extends HttpServlet{
    private static final Logger log = LoggerFactory.getLogger(GetAllProjects.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
    	
    	if(ComponentAccessor.getJiraAuthenticationContext().isLoggedInUser()){
    		JSONArray jsonArray=new JSONArray();
    		JSONArray noCategoryProject=new JSONArray();
    		JSONArray byMe=new JSONArray();
    		JSONArray meIn=new JSONArray();
    		JSONObject noCategory=new JSONObject();
    		JSONObject leadByMe=new JSONObject();
    		JSONObject iIn=new JSONObject();
    		//获取我负责的项目
    		List<Project> list=ComponentAccessor.getProjectManager().getProjectsLeadBy(ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser());
    		if(list.size()==0){
    			
    		}else{
    			Iterator<Project> iterator=list.iterator();
    			while(iterator.hasNext()){
    				JSONObject jsonProject=new JSONObject();
    				Project project=iterator.next();
    				try {
            			jsonProject.put("projectKey",project.getKey());
            			jsonProject.put("projectId",project.getId());
            			jsonProject.put("projectName",project.getName());
            			jsonProject.put("projectLead",project.getLeadUserName());
            			
        			} catch (JSONException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}
    				byMe.put(jsonProject);
    				
    			}
    			try {
					leadByMe.put("leadByMe", byMe);
					jsonArray.put(leadByMe);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    		
    		List<Project> listProject=ComponentAccessor.getProjectManager().getProjectObjects();
    		Iterator<Project> iteratorProject1=listProject.iterator();
    		while(iteratorProject1.hasNext()){
    			JSONObject jsonProject=new JSONObject();
    			Project project=iteratorProject1.next();
    			try {
        			jsonProject.put("projectKey",project.getKey());
        			jsonProject.put("projectId",project.getId());
        			jsonProject.put("projectName",project.getName());
        			jsonProject.put("projectLead",project.getLeadUserName());
        			
    			} catch (JSONException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			PermissionContext permissionContext=ComponentAccessor.getPermissionContextFactory().getPermissionContext(ComponentAccessor.getProjectManager().getProjectByCurrentKey(project.getKey()));
    			Iterator<ProjectPermission> iteratorProjectPermission=ComponentAccessor.getPermissionManager().getAllProjectPermissions().iterator();
    			while(iteratorProjectPermission.hasNext()){
    				ProjectPermission permission=iteratorProjectPermission.next();
    				if(permission.getKey().equals("BROWSE_PROJECTS")){
    					Collection<ApplicationUser> collection=ComponentAccessor.getPermissionSchemeManager().getUsers(permission.getProjectPermissionKey(), permissionContext);
    					Iterator<ApplicationUser> iteratorApplicationUser=collection.iterator();
    					while(iteratorApplicationUser.hasNext()){
    						ApplicationUser user=iteratorApplicationUser.next();
    						if(ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser().getKey().equals(user.getKey())){
    							meIn.put(jsonProject);
    						}
    					}
    				}
    			}
    		}
    		try {
    			iIn.put("meIn", meIn);
				jsonArray.put(iIn);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
        	Collection<Project> projects = ComponentAccessor.getProjectManager().getProjectObjectsWithNoCategory();	//获取没有类型的项目集合
        	System.out.println(projects.size());
        	Iterator<Project> iterator = projects.iterator();
        	while(iterator.hasNext()){
        		JSONObject jsonProject=new JSONObject();
        		Project project =iterator.next();			//获取项目 
        		try {
        			jsonProject.put("projectKey",project.getKey());
        			jsonProject.put("projectId",project.getId());
        			jsonProject.put("projectName",project.getName());
        			jsonProject.put("projectLead",project.getLeadUserName());
        			
    			} catch (JSONException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
        		noCategoryProject.put(jsonProject);
        	}
        	
        	try {
				noCategory.put("noCategory", noCategoryProject);
				jsonArray.put(noCategory);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	//获取有项目类型的项目集合
        	try {
				Collection<ProjectCategory> categories = ComponentAccessor.getProjectManager().getAllProjectCategories();
				System.out.println(categories.size());
				Iterator<ProjectCategory> iteratorProjectCategory=categories.iterator();
				while(iteratorProjectCategory.hasNext()){
					JSONObject jsonObject=new JSONObject();
					ProjectCategory category=iteratorProjectCategory.next();
					jsonObject.put("categoryId", category.getId());
					jsonObject.put("categoryName", category.getName());
					jsonObject.put("categoryDesc", category.getDescription());
					Collection<Project> collection=ComponentAccessor.getProjectManager().getProjectObjectsFromProjectCategory(category.getId());
					Iterator<Project> iteratorProject=collection.iterator();
					JSONArray arrProject=new JSONArray();
					while(iteratorProject.hasNext()){
						JSONObject jsonProject=new JSONObject();
						Project project=iteratorProject.next();
						jsonProject.put("projectKey",project.getKey());
						jsonProject.put("projectId", project.getId());
						jsonProject.put("projectName", project.getName());
						jsonProject.put("projectLead", project.getLeadUserName());
						jsonProject.put("projectCategory", project.getProjectCategory().getName());
						arrProject.put(jsonProject);
					}
					jsonObject.put("Projects", arrProject);
					jsonArray.put(jsonObject);
				}
				
			} catch (DataAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	
           
//            resp.getWriter().write(jsonArray.toString());
        	
            resp.getWriter().write("<script>console.log("+jsonArray.toString()+")</script>");
    	}else{
    		resp.getWriter().write("请登录！");
    	}
    	
    }

}