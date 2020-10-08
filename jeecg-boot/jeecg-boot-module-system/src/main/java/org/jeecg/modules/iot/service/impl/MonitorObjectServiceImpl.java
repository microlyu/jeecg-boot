package org.jeecg.modules.iot.service.impl;

import org.jeecg.modules.iot.entity.MonitorObject;
import org.jeecg.modules.iot.mapper.MonitorObjectMapper;
import org.jeecg.modules.iot.service.IMonitorObjectService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 监控对象
 * @Author: jeecg-boot
 * @Date:   2020-10-06
 * @Version: V1.0
 */
@Service
public class MonitorObjectServiceImpl extends ServiceImpl<MonitorObjectMapper, MonitorObject> implements IMonitorObjectService {

}
