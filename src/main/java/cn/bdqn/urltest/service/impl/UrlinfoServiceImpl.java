package cn.bdqn.urltest.service.impl;

import cn.bdqn.urltest.common.util.HashUtils;
import cn.bdqn.urltest.common.util.RedisUtil;
import cn.bdqn.urltest.domain.Urlinfo;
import cn.bdqn.urltest.mapper.UrlinfoMapper;
import cn.bdqn.urltest.service.UrlinfoService;
import cn.hutool.bloomfilter.BitMapBloomFilter;
import cn.hutool.bloomfilter.BloomFilterUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
* @author Tv
* @description 针对表【urlinfo】的数据库操作Service实现
* @createDate 2023-06-16 15:23:18
*/
@Service
public class UrlinfoServiceImpl extends ServiceImpl<UrlinfoMapper, Urlinfo>
    implements UrlinfoService {

    @Resource
    UrlinfoMapper urlinfoMapper;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private RedisUtil redisUtil;
    /**
     * 最近使用的短链接缓存过期时间(秒)
     */
    private static final long TIMEOUT = 10 * 60;

    @Override
    public String saveUrlinfo(String longUrl, String createBy) {
        //长网址及创建人联合生成短网址
        String shortUrl = HashUtils.hashToBase62(longUrl);
        //数据库是否存在
        Urlinfo dbUrlinfo = urlinfoMapper.selectOne(Wrappers.<Urlinfo>lambdaQuery()
                .select(Urlinfo::getShirtUrl,Urlinfo::getLongUrl,Urlinfo::getCreateBy)
                .eq(Urlinfo::getShirtUrl,shortUrl));
        // 数据库中没有该短链接,直接生成
        if (dbUrlinfo == null){
            Urlinfo urlinfo = new Urlinfo();
            urlinfo.setCreateBy(createBy);
            urlinfo.setShirtUrl(shortUrl);
            urlinfo.setLongUrl(longUrl);
            urlinfo.setVisits(0);
            urlinfo.setCreateTime(new Date());
            urlinfoMapper.insert(urlinfo);
            return shortUrl;
        }
        // 短链接相同,长链接不同,说明不同长链接Hash到了一样的值,需要重新Hash
        if (Objects.equals(dbUrlinfo.getShirtUrl(), shortUrl) && !Objects.equals(dbUrlinfo.getLongUrl(), longUrl)){
            saveUrlinfo(longUrl,createBy);
        }
        // 短链接相同,长链接相同,不同创建人,需要重新Hash
        if(Objects.equals(dbUrlinfo.getShirtUrl(), shortUrl) && Objects.equals(dbUrlinfo.getLongUrl(), longUrl) && !Objects.equals(dbUrlinfo.getCreateBy(), createBy)) {
            saveUrlinfo(longUrl,createBy);
        }

        return dbUrlinfo.getShirtUrl();
    }

    @Override
    public String getLongUrlByShortUrl(String shortURL) {

        String longUrl = (String)redisUtil.get(shortURL);
        if (longUrl != null){
            //有缓存
            redisUtil.expire(shortURL,TIMEOUT);

            return longUrl;
        }
        //没有缓存
        if (!redisTemplate.hasKey(shortURL)){
            System.out.println("存储新值");
            Urlinfo urlinfo = urlinfoMapper.selectOne(Wrappers.<Urlinfo>lambdaQuery()
                    .select(Urlinfo::getShirtUrl, Urlinfo::getLongUrl, Urlinfo::getCreateBy)
                    .eq(Urlinfo::getShirtUrl, shortURL));
            if (urlinfo != null){
                longUrl = urlinfo.getLongUrl();
                redisTemplate.opsForValue().set(shortURL,longUrl,TIMEOUT);
            }
        }
        return longUrl;
    }
}




