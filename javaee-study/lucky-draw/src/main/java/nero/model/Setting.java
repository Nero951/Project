package nero.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nero.base.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * 抽奖设置
 */
@Getter
@Setter
@ToString
public class Setting extends BaseEntity {
    
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 每次抽奖人数
     */
    private Integer batchNumber;

    /**
     * 创建时间
     */
    private Date createTime;

    //查询设置接口，需要的三个自定义字段
    /**
     * 用户
     */
    private User user;
    /**
     * 奖项
     */
    private List<Award> awards;
    /**
     * 参加抽奖的成员
     */
    private List<Member> members;
}