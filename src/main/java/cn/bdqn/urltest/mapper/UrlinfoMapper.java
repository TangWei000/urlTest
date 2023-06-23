package cn.bdqn.urltest.mapper;

import cn.bdqn.urltest.domain.Urlinfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;


/**
* @author Tv
* @description 针对表【urlinfo】的数据库操作Mapper
* @createDate 2023-06-16 15:23:18
* @Entity generator.domain.Urlinfo
*/
public interface UrlinfoMapper extends BaseMapper<Urlinfo> {
    @Update("update urlinfo set visits = #{visits} where shirt_url = #{shortUrl}")
    int updateVisits(@Param("shortUrl")String shortUrl, @Param("visits") int visits);
}




