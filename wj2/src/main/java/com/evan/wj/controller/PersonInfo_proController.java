package com.evan.wj.controller;

import com.evan.wj.bean.*;
import com.evan.wj.dao.PersonInfoDAO;
import com.evan.wj.dao.ProjectDAO;
import com.evan.wj.result.TempleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PersonInfo_proController {

    @Autowired
    PersonInfoDAO personInfoDAO;

    @CrossOrigin
    @GetMapping(value = "/vue-element-admin/teacher/personal/all_students")
    @ResponseBody
    public TempleteResult<PersonalInfo_pro_tea> getAllGroup_teacher(@RequestParam("project_id") int project_id){
        List<PersonalInfo_pro_tea> sub1 = personInfoDAO.getperson_teacher(project_id);
        List<ID_tag> sub2 = personInfoDAO.get_idTag2(project_id);
        int p2_id_t = 0;
        int p1 = 0;
        for(ID_tag s2: sub2){
            p2_id_t = s2.getId();
            for (PersonalInfo_pro_tea s1: sub1){
                p1 = s1.getId();
                if (p2_id_t == p1){
                    s1.addSkill(s2.getTag());
                    break;
                }
            }
        }
        TempleteResult<PersonalInfo_pro_tea> allProjectResult_t = new TempleteResult<PersonalInfo_pro_tea>(20000,sub1);
        //AllProjectResult allProjectResult = new AllProjectResult(20000, projectDAO.getAllProject(token));
        return allProjectResult_t;
    }


    @CrossOrigin
    @GetMapping(value = "/vue-element-admin/teacher/personal/all_ungrouped_students")
    @ResponseBody
    public TempleteResult<PersonalInfo_pro_tea> getAllGroup_teacher_ungrouped(@RequestParam("project_id") int project_id){
        List<PersonalInfo_pro_tea> sub1 = personInfoDAO.getperson_teacher2_weizudui(project_id);
        List<ID_tag> sub2 = personInfoDAO.get_idTag2(project_id);
        int p2_id_t = 0;
        int p1 = 0;
        for(ID_tag s2: sub2){
            p2_id_t = s2.getId();
            for (PersonalInfo_pro_tea s1: sub1){
                p1 = s1.getId();
                if (p2_id_t == p1){
                    s1.addSkill(s2.getTag());
                    break;
                }
            }
        }
        TempleteResult<PersonalInfo_pro_tea> allProjectResult_t = new TempleteResult<PersonalInfo_pro_tea>(20000,sub1);
        //AllProjectResult allProjectResult = new AllProjectResult(20000, projectDAO.getAllProject(token));
        return allProjectResult_t;
    }



    @CrossOrigin
    @GetMapping(value = "/vue-element-admin/student/personal/all_students")
    @ResponseBody
    public TempleteResult<PersonalInfo_pro> getAllGroup_student(@RequestParam("token") String token, @RequestParam("project_id") int project_id){

        String cross = personInfoDAO.get_CrossClass(project_id).get(0);
        List<PersonalInfo_pro> sub1 = null;
        if(cross.equals("true")){
            sub1 = personInfoDAO.getperson(project_id);

            List<ID_tag> sub2 = personInfoDAO.get_idTag2(project_id);

            int p2_id_t = 0;
            int p1 = 0;
            for(ID_tag s2: sub2){
                p2_id_t = s2.getId();
                for (PersonalInfo_pro s1: sub1){
                    p1 = s1.getId();
                    if (p2_id_t == p1){
                        s1.addSkill(s2.getTag());
                        break;
                    }
                }
            }
        }else {
            int classID = personInfoDAO.get_classID(project_id,token).get(0);
            sub1 = personInfoDAO.getperson_classCross(project_id,classID);
            List<ID_tag> sub2 = personInfoDAO.get_idTag2_classCross(project_id,classID);

            int p2_id_t = 0;
            int p1 = 0;
            for(ID_tag s2: sub2){
                p2_id_t = s2.getId();
                for (PersonalInfo_pro s1: sub1){
                    p1 = s1.getId();
                    if (p2_id_t == p1){
                        s1.addSkill(s2.getTag());
                        break;
                    }
                }
            }
        }

        TempleteResult<PersonalInfo_pro> allProjectResult_t = new TempleteResult<PersonalInfo_pro>(20000,sub1);
        //AllProjectResult allProjectResult = new AllProjectResult(20000, projectDAO.getAllProject(token));
        return allProjectResult_t;

    }
}
