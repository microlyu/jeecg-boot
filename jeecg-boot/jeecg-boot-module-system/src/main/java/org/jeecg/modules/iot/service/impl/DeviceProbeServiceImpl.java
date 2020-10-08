package org.jeecg.modules.iot.service.impl;

import org.jeecg.modules.iot.entity.DeviceProbe;
import org.jeecg.modules.iot.mapper.DeviceProbeMapper;
import org.jeecg.modules.iot.service.IDeviceProbeService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 设备探针
 * @Author: jeecg-boot
 * @Date:   2020-10-06
 * @Version: V1.0
 */
@Service
public class DeviceProbeServiceImpl extends ServiceImpl<DeviceProbeMapper, DeviceProbe> implements IDeviceProbeService {
	
	@Autowired
	private DeviceProbeMapper deviceProbeMapper;
	
	@Override
	public List<DeviceProbe> selectByMainId(String mainId) {
		return deviceProbeMapper.selectByMainId(mainId);
	}

	@Override
	public List<DeviceProbe> selectbyObjectId(String objectId) {
		return deviceProbeMapper.selectByObjectId(objectId);
	}
}
