package org.jeecg.modules.iot.mapper;

import java.util.List;
import org.jeecg.modules.iot.entity.DeviceSensor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 设备传感器
 * @Author: jeecg-boot
 * @Date:   2020-07-24
 * @Version: V1.0
 */
public interface DeviceSensorMapper extends BaseMapper<DeviceSensor> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<DeviceSensor> selectByMainId(@Param("mainId") String mainId);
}
