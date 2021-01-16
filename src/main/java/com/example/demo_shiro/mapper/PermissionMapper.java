package com.example.demo_shiro.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo_shiro.model.PermissionDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author pangzhihao
 * @since 2021-01-15
 */
public interface PermissionMapper extends BaseMapper<PermissionDO> {

    @Select("select p.id as id, p.name as name, p.url as url from " +
            "role_permission rp left join permission p on rp.permission_id=p.id " +
            "where rp.role_id = #{roleId}")
    List<PermissionDO> findPermissionListByRoldId(@Param("roleId") int roleId);

}
