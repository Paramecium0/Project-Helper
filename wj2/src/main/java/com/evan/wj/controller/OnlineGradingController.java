package com.evan.wj.controller;

import com.evan.wj.bean.All_tag_inProject;
import com.evan.wj.bean.OnlineGrade;
import com.evan.wj.dao.OnlineGradingDAO;
import com.evan.wj.dao.TagDAO;
import com.evan.wj.receive.NewProjectReceive;
import com.evan.wj.receive.OnlineGradeUpdateReceive;
import com.evan.wj.receive.OnlineGradeUpdateReceive2;
import com.evan.wj.result.TempleteResult;
import com.evan.wj.result.Void_return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OnlineGradingController {
    @Autowired
    OnlineGradingDAO onlineGradingDAO;

    @CrossOrigin
    @GetMapping(value = "/vue-element-admin/teacher/grade/score_list")
    @ResponseBody
    public TempleteResult<OnlineGrade> getAllGroup(@RequestParam("project_id") int project_id){
        List<OnlineGrade> sub1 = onlineGradingDAO.getAllPerson(project_id);

        TempleteResult<OnlineGrade> allProjectResult_t = new TempleteResult<OnlineGrade>(20000,sub1);
        return allProjectResult_t;
    }

    @CrossOrigin
    @PostMapping(value = "/vue-element-admin/teacher/grade/update_score_list")
    @ResponseBody
    public Void_return create_new_project(@RequestBody OnlineGradeUpdateReceive rec){
        List<Integer> group_ids = rec.getGroup_ids();
        List<Integer> person_ids = rec.getPerson_ids();
        List<String> grades =  rec.getGrades();
        List<String> comments = rec.getComments();

//        System.out.println(group_ids.size());
//        System.out.println(person_ids.size());
//        System.out.println(grades.size());
//        System.out.println(comments.size());
//
//        System.out.println(group_ids.get(0));
//        System.out.println(person_ids.get(0));
//        System.out.println(grades.get(0));
//        System.out.println(comments.get(0));
//        System.out.println(Double.parseDouble(grades.get(0)));

        for(int i=0;i<person_ids.size();i++){
            onlineGradingDAO.update_grade(Double.parseDouble(grades.get(i)),person_ids.get(i),group_ids.get(i));
            onlineGradingDAO.update_comment(comments.get(i),person_ids.get(i),group_ids.get(i));
        }
        return new Void_return(20000);
    }


    @CrossOrigin
    @PostMapping(value = "/vue-element-admin/teacher/grade/update_score_list2")
    @ResponseBody
    public Void_return Ondatelist(@RequestBody OnlineGradeUpdateReceive2 rec){
        List<String> gro_names = rec.getGroup_name();
        List<String> stu_ids = rec.getStu_id();

        List<Integer> group_ids = new ArrayList<>();
        List<Integer> person_ids = new ArrayList<>();
        List<String> grades =  rec.getGrades();
        List<String> comments = rec.getComments();

        for(String g:gro_names){
            group_ids.add(onlineGradingDAO.getGrouID(g).get(0));
        }
        for(String p:stu_ids){
            person_ids.add(onlineGradingDAO.getPerID(p).get(0));
        }

//        System.out.println(group_ids.size());
//        System.out.println(person_ids.size());
//        System.out.println(grades.size());
//        System.out.println(comments.size());
//
//        System.out.println(group_ids.get(0));
//        System.out.println(person_ids.get(0));
//        System.out.println(grades.get(0));
//        System.out.println(comments.get(0));
//        System.out.println(Double.parseDouble(grades.get(0)));

        for(int i=0;i<person_ids.size();i++){
            onlineGradingDAO.update_grade(Double.parseDouble(grades.get(i)),person_ids.get(i),group_ids.get(i));
            onlineGradingDAO.update_comment(comments.get(i),person_ids.get(i),group_ids.get(i));
        }
        return new Void_return(20000);
    }


}
