package nero.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nero.base.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * 奖项
 */
@Getter
@Setter
@ToString
public class Award extends BaseEntity {
    
    private Integer id;

    /**
     * 奖项名称
     */
    private String name;

    /**
     * 奖项人数
     */
    private Integer count;

    /**
     * 奖品
     */
    private String award;

    /**
     * 抽奖设置id
     */
    private Integer settingId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 中将人员名单
     */
    private List<Integer> luckyMemberIds;
}