package com.eden.fans.bs.web.controller;

import com.eden.fans.bs.common.util.UserGsonUtil;
import com.eden.fans.bs.domain.request.ScoreRecordRequest;
import com.eden.fans.bs.service.IUserScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2016/5/9.
 */
@Controller
@RequestMapping(value = "/score")
public class UserScoreController {
    @Autowired
    private IUserScoreService userScoreService;//积分服务类
    @RequestMapping(value = "/addScoreRecord", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String addScoreRecord(ScoreRecordRequest scoreRecordRequest) throws Exception {
        return UserGsonUtil.getGson().toJson(userScoreService.addScoreRecord(scoreRecordRequest));
    }

    /**
     * 查询积分排行榜
     * */
    @RequestMapping(value = "/scoreBoard", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String scoreBoard(int pageNumber, int currentPage) throws Exception {
        return UserGsonUtil.getGson().toJson(userScoreService.qryScoreBoard(pageNumber,currentPage));
    }
}
