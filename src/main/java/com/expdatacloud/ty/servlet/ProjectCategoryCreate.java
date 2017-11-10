package com.expdatacloud.ty.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.component.ComponentAccessor;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProjectCategoryCreate extends HttpServlet{
    private static final Logger log = LoggerFactory.getLogger(ProjectCategoryCreate.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
    	doPost(req,resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	String name=req.getParameter("projectCategoryName");
    	String description=req.getParameter("projectCategoryDesc");
    	ComponentAccessor.getProjectManager().createProjectCategory(name, description);
    }

}