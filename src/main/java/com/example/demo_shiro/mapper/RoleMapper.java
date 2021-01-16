package com.example.demo_shiro.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo_shiro.model.RoleDO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import javax.management.relation.Role;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author pangzhihao
 * @since 2021-01-15
 */
public interface RoleMapper extends BaseMapper<RoleDO> {
    @Select("select ur.role_id as id, r.name as name, r.description as description from " +
            "user_role ur left join role r on ur.role_id = r.id " +
            "where ur.user_id = #{userId}")
    @Results(
            value = {
                    @Result(id = true,property = "id",column = "id"),
                    @Result(property = "name",column = "name"),
                    @Result(property = "description",column = "description"),
                    @Result(property = "permissionDOList",column = "id",
                    many = @Many(select = "com.example.demo_shiro.mapper.PermissionMapper.findPermissionListByRoldId",fetchType = FetchType.DEFAULT)
                    )
            }
    )
    List<RoleDO> findRoleListByUserId(@Param("userId")int userId);

}
