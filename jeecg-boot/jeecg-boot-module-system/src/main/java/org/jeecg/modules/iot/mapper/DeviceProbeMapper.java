package org.jeecg.modules.iot.mapper;

import java.util.List;
import org.jeecg.modules.iot.entity.DeviceProbe;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 设备探针
 * @Author: jeecg-boot
 * @Date:   2020-10-06
 * @Version: V1.0
 */
public interface DeviceProbeMapper extends BaseMapper<DeviceProbe> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<DeviceProbe> selectByMainId(@Param("mainId") String mainId);

	public List<DeviceProbe> selectByObjectId(@Param("objectId") String objectId);
}
