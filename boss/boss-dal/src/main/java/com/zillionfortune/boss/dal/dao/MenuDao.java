package com.zillionfortune.boss.dal.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zillionfortune.boss.dal.entity.Menu;
import com.zillionfortune.boss.dal.entity.Power;
import com.zillionfortune.boss.support.mybatis.annotation.MyBatisRepository;

@MyBatisRepository
@Component
public interface MenuDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);
    
    /**
     * 根据用户ID查询有效的菜单列表
     * @param userId
     * @return
     */
    List<Menu> selectMenuListByUserId(Integer userId);
    
    /**
     * selectBySelective:选择查询. <br/>
     *
     * @param recode
     * @return
     */
    List<Menu> selectBySelective(Menu recode);
    
    /**
     * queryByPage:分页查询菜单. <br/>
     *
     * @param recode
     * @return
     */
    List<Menu> queryByPage(Map paramMap);
    
}