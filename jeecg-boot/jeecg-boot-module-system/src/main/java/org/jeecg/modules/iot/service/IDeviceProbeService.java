package org.jeecg.modules.iot.service;

import org.jeecg.modules.iot.entity.DeviceProbe;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 设备探针
 * @Author: jeecg-boot
 * @Date:   2020-10-06
 * @Version: V1.0
 */
public interface IDeviceProbeService extends IService<DeviceProbe> {

	public List<DeviceProbe> selectByMainId(String mainId);

	public List<DeviceProbe> selectbyObjectId(String objectId);
}
