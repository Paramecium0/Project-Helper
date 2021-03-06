package com.evan.wj.controller;

import com.evan.wj.bean.AllProject;
import com.evan.wj.dao.GroupDAO;
import com.evan.wj.dao.ProjectDAO;
import com.evan.wj.dao.UpPersonInfoDAO;
import com.evan.wj.result.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProjectController {
    @Autowired
    ProjectDAO projectDAO;

    @Autowired
    UpPersonInfoDAO upPersonInfoDAO;

    @CrossOrigin
    @GetMapping(value = "/vue-element-admin/teacher/dashboard/project_list")
    @ResponseBody
    public TempleteResult<AllProject> getAllProject_teacher(@RequestParam("token") String token){

        TempleteResult<AllProject> allProjectResult_t = new TempleteResult<>(20000,projectDAO.getAllProject_teacher(token));

        //AllProjectResult allProjectResult = new AllProjectResult(20000, projectDAO.getAllProject(token));
        return allProjectResult_t;

    }

    @CrossOrigin
    @GetMapping(value = "/vue-element-admin/student/dashboard/project_list")
    @ResponseBody
    public TempleteResult<AllProject> getAllProject_student(@RequestParam("token") String token){

        TempleteResult<AllProject> allProjectResult_t = new TempleteResult<>(20000,projectDAO.getAllProject_student(token));

        //AllProjectResult allProjectResult = new AllProjectResult(20000, projectDAO.getAllProject(token));
        return allProjectResult_t;

    }


    @CrossOrigin
    @GetMapping(value = "/vue-element-admin/student/join/joinable_project")
    @ResponseBody
    public TempleteResult<AllProject> getAllProject_student_canjoin(@RequestParam("token") String token){

        TempleteResult<AllProject> allProjectResult_t = new TempleteResult<>(20000,projectDAO.getAllProject_student_canjoin(token));

        //AllProjectResult allProjectResult = new AllProjectResult(20000, projectDAO.getAllProject(token));
        return allProjectResult_t;

    }


    @CrossOrigin
    @PostMapping(value = "/vue-element-admin/student/join/join_project")
    @ResponseBody
    public Void_return Insert_wantPerson(@RequestParam("token") String token,@RequestParam("project_id") int project_id){

       int person_id = upPersonInfoDAO.getID(token).get(0).getId();
       projectDAO.InsertWantPerson(person_id, project_id);
        //AllProjectResult allProjectResult = new AllProjectResult(20000, projectDAO.getAllProject(token));
        return new Void_return(20000);

    }


    @CrossOrigin
    @PostMapping(value = "/vue-element-admin/teacher/project/delete_project")
    @ResponseBody
    public Message_return Insert_wantPerson(@RequestParam("project_id") int project_id){
        projectDAO.delete_project(project_id);
        projectDAO.delete_person_group_tea(project_id);
        projectDAO.delete_want_person(project_id);
        projectDAO.delete_Project_gro(project_id);
        projectDAO.delete_Project_pretime(project_id);
        projectDAO.delete_Project_tag(project_id);
        //AllProjectResult allProjectResult = new AllProjectResult(20000, projectDAO.getAllProject(token));
        return new Message_return(20000,"Success!");

    }







}
