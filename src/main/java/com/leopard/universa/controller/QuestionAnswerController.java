package com.leopard.universa.controller;

import com.leopard.universa.config.constant.Response;
import com.leopard.universa.entity.QuestionAnswer;
import com.leopard.universa.service.QuestionAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionAnswerController {

    @Autowired
    private QuestionAnswerService questionAnswerService;

    /**
     * 验证-过去数据库连接数据
     *
     * @return
     */
    @GetMapping("/getData")
    public Response getData() {

        return questionAnswerService.defaultRecommend();
    }

    /**
     * 添加-问答数据
     *
     * @return
     */
    @PostMapping("/addData")
    public Response addData(@RequestBody QuestionAnswer questionAnswer) {

        return questionAnswerService.addAnswer(questionAnswer);
    }

    /**
     * 查找问答信息
     */
    @GetMapping(value = "/findAnswerByContent")
    public Response findAnswerByTitle(String content) {

        return questionAnswerService.findAnswerByTitle2(content);
    }

    /**
     * 更新问答时间，同步已有数据到ES
     */
    @PostMapping(value = "/updateAnswerTime")
    public Response updateAnswerTime() {

        return questionAnswerService.updateAllAnswerForTime();
    }

}
