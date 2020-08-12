package org.jeecg.modules.iot.service;

import org.jeecg.modules.iot.entity.DeviceSensor;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 设备传感器
 * @Author: jeecg-boot
 * @Date:   2020-07-24
 * @Version: V1.0
 */
public interface IDeviceSensorService extends IService<DeviceSensor> {

	public List<DeviceSensor> selectByMainId(String mainId);
}
