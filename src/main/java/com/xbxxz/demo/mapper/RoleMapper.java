package com.xbxxz.demo.mapper;

import cn.hutool.core.io.checksum.CRC16;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xbxxz.demo.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.mapper.Mapper;
import org.apache.ibatis.annotations.Select;

public interface RoleMapper extends BaseMapper<Role> {

}
