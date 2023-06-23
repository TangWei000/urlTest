package cn.bdqn.urltest.controller;

import cn.bdqn.urltest.common.response.Result;
import cn.bdqn.urltest.common.util.UrlUtils;
import cn.bdqn.urltest.domain.Urlinfo;
import cn.bdqn.urltest.service.UrlinfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * @Author: Tv
 * @Description: 长链接转换短链接
 * @DateTime: 2023/6/16 15:32
 **/
@Controller
@RequestMapping("Urlinfo")
public class UrlinfoController {
    @Autowired
    private UrlinfoService urlinfoService;
    private static String host;
    @Value("${server.host}")
    public void setHost(String host) {
        UrlinfoController.host = host;
    }

    /**
     * 获取短链接
     * @param longUrl
     * @param createBy
     * @return
     */
    @PostMapping("/generate")
    @ResponseBody
    public Result longTOShort(@RequestParam String longUrl,@RequestParam String createBy){
        System.out.println("66666");
        if (UrlUtils.checkURL(longUrl)){
            if (!longUrl.startsWith("http")){
                longUrl = "http://" + longUrl;
            }
            String shortUrl = urlinfoService.saveUrlinfo(longUrl,createBy);
            return Result.ok(host+shortUrl);
        }
        return Result.fail("路径错误！");
    }

    /**
     * 重定向
     * @param shortURL
     * @return
     */
    @GetMapping("/{shortURL}")
    public String redirect(@PathVariable String shortURL) {
        String longURL = urlinfoService.getLongUrlByShortUrl(shortURL).trim();
        if (longURL != null) {
//            //修改访问数
//            QueryWrapper<Urlinfo> queryWrapper = new QueryWrapper<>();
//            queryWrapper.lambda().eq(Urlinfo::getShirtUrl,shortURL);
//            Urlinfo one = urlinfoService.getOne(queryWrapper);
//            UpdateWrapper<Urlinfo> updateWrapper = new UpdateWrapper<>();
//            updateWrapper.lambda().set(Urlinfo::getVisits,one.getVisits()+1).eq(Urlinfo::getShirtUrl,shortURL);
//            urlinfoService.update(updateWrapper);
            //查询到对应的原始链接，重定向
            return "redirect:" + longURL;
        }
        //没有对应的原始链接，直接返回首页
        return "redirect:/";
    }
}
