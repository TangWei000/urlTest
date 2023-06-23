package cn.bdqn.urltest.service;

import cn.bdqn.urltest.domain.Urlinfo;
import cn.bdqn.urltest.mapper.UrlinfoMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;


/**
* @author Tv
* @description 针对表【urlinfo】的数据库操作Service
* @createDate 2023-06-16 15:23:18
*/
public interface UrlinfoService extends IService<Urlinfo> {

    public String saveUrlinfo(String longUrl,String createBy);

    public String getLongUrlByShortUrl(String shortURL);

    public void updateVisits();
}
