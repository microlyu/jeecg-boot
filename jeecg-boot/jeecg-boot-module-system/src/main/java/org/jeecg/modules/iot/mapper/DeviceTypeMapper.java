package org.jeecg.modules.iot.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.demo.org.jeecg.modules.demo.org.jeecg.modules.iot.entity.DeviceType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 设备类型
 * @Author: jeecg-boot
 * @Date:   2020-07-24
 * @Version: V1.0
 */
public interface DeviceTypeMapper extends BaseMapper<DeviceType> {

	/**
	 * 编辑节点状态
	 * @param id
	 * @param status
	 */
	void updateTreeNodeStatus(@Param("id") String id,@Param("status") String status);

}
