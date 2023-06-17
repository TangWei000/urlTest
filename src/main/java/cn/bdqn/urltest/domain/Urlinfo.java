package cn.bdqn.urltest.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName urlinfo
 */
@TableName(value ="urlinfo")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Urlinfo extends Model implements Serializable {
    /**
     * 
     */
    @TableId(value = "id",type= IdType.AUTO)
    private Integer id;

    /**
     * 短链接
     */
    private String shirtUrl;

    /**
     * 长链接
     */
    private String longUrl;

    /**
     * 访问次数
     */
    private Integer visits;

    /**
     * 创建人
     */
    protected String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    protected Serializable pkVal() {
        return id;
    }
}