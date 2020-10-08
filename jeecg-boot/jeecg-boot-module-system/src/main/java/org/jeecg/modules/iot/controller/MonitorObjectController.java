package org.jeecg.modules.iot.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.iot.IOTConstants;
import org.jeecg.modules.iot.entity.DeviceProbe;
import org.jeecg.modules.iot.entity.MonitorObject;
import org.jeecg.modules.iot.service.IDeviceProbeService;
import org.jeecg.modules.iot.service.IMonitorObjectService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.iot.vo.MonitorVO;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 监控对象
 * @Author: jeecg-boot
 * @Date:   2020-10-06
 * @Version: V1.0
 */
@Api(tags="监控对象")
@RestController
@RequestMapping("/iot/monitorObject")
@Slf4j
public class MonitorObjectController extends JeecgController<MonitorObject, IMonitorObjectService> {
	@Autowired
	private IMonitorObjectService monitorObjectService;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private IDeviceProbeService deviceProbeService;
	
	/**
	 * 分页列表查询
	 *
	 * @param monitorObject
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "监控对象-分页列表查询")
	@ApiOperation(value="监控对象-分页列表查询", notes="监控对象-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(MonitorObject monitorObject,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<MonitorObject> queryWrapper = QueryGenerator.initQueryWrapper(monitorObject, req.getParameterMap());
		Page<MonitorObject> page = new Page<MonitorObject>(pageNo, pageSize);
		IPage<MonitorObject> pageList = monitorObjectService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param monitorObject
	 * @return
	 */
	@AutoLog(value = "监控对象-添加")
	@ApiOperation(value="监控对象-添加", notes="监控对象-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody MonitorObject monitorObject) {
		monitorObjectService.save(monitorObject);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param monitorObject
	 * @return
	 */
	@AutoLog(value = "监控对象-编辑")
	@ApiOperation(value="监控对象-编辑", notes="监控对象-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody MonitorObject monitorObject) {
		monitorObjectService.updateById(monitorObject);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "监控对象-通过id删除")
	@ApiOperation(value="监控对象-通过id删除", notes="监控对象-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		monitorObjectService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "监控对象-批量删除")
	@ApiOperation(value="监控对象-批量删除", notes="监控对象-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.monitorObjectService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "监控对象-通过id查询")
	@ApiOperation(value="监控对象-通过id查询", notes="监控对象-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		MonitorObject monitorObject = monitorObjectService.getById(id);
		if(monitorObject==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(monitorObject);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param monitorObject
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, MonitorObject monitorObject) {
        return super.exportXls(request, monitorObject, MonitorObject.class, "监控对象");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, MonitorObject.class);
    }

	 /**
	  * 获取所有设备的信息
	  *
	  * @return
	  */
	 @RequestMapping(value = "/monitorAll", method = RequestMethod.GET)
	 public Result<?> monitorAll() {
		 List<MonitorVO> voList = new ArrayList<MonitorVO>();
		 List<MonitorObject> moList = this.monitorObjectService.list();
		 for (MonitorObject item : moList) {
			 MonitorVO vo = new MonitorVO();
			 vo.setId(item.getId());
			 vo.setSysOrgCode(item.getSysOrgCode());
			 vo.setObjectLogo(item.getObjectLogo());
			 vo.setObjectName(item.getObjectName());
			 vo.setObjectMemo(item.getObjectMemo());
			 vo.setObjectStatus(item.getObjectStatus());

			 List<DeviceProbe> probeList = this.deviceProbeService.selectbyObjectId(item.getId());
			 for (DeviceProbe probeItem : probeList) {
				 if (probeItem.getSensorType().equalsIgnoreCase(IOTConstants.SENSOR_TEMP)) {
					 String redisKey = "monitor:" + probeItem.getMonitorId() + ":" + probeItem.getSensorType();
					 Object tempValue = redisUtil.get(redisKey);
					 if (null != tempValue) vo.setCurrentTemp(Double.parseDouble(tempValue.toString()));
				 } else if (probeItem.getSensorType().equalsIgnoreCase(IOTConstants.SENSOR_HUMIDITY)) {
					 String redisKey = "monitor:" + probeItem.getMonitorId() + ":" + probeItem.getSensorType();
					 Object humiValue = redisUtil.get(redisKey);
					 if (null != humiValue)  vo.setCurrentHumidity(Double.parseDouble(humiValue.toString()));
				 }
			 }

			 voList.add(vo);
		 }
		 System.out.println("result size = " + voList.size());
		 return Result.ok(voList);
	 }

	 /**
	  * 获取指定设备的信息
	  *
	  * @return
	  */
	 @RequestMapping(value = "/monitorOne", method = RequestMethod.GET)
	 public Result<?> monitorOne(@RequestParam(name = "id", required = true) String id) {
		 MonitorObject item = this.monitorObjectService.getById(id);
		 MonitorVO vo = new MonitorVO();
		 vo.setId(item.getId());
		 vo.setSysOrgCode(item.getSysOrgCode());
		 vo.setObjectLogo(item.getObjectLogo());
		 vo.setObjectName(item.getObjectName());
		 vo.setObjectMemo(item.getObjectMemo());
		 vo.setObjectStatus(item.getObjectStatus());

		 List<DeviceProbe> probeList = this.deviceProbeService.selectbyObjectId(item.getId());
		 for (DeviceProbe probeItem : probeList) {
			 if (probeItem.getSensorType().equalsIgnoreCase(IOTConstants.SENSOR_TEMP)) {
				 String redisKey = "monitor:" + probeItem.getMonitorId() + ":" + probeItem.getSensorType();
				 vo.setCurrentTemp(Double.parseDouble(redisUtil.get(redisKey).toString()));
			 } else if (probeItem.getSensorType().equalsIgnoreCase(IOTConstants.SENSOR_HUMIDITY)) {
				 String redisKey = "monitor:" + probeItem.getMonitorId() + ":" + probeItem.getSensorType();
				 vo.setCurrentHumidity(Double.parseDouble(redisUtil.get(redisKey).toString()));
			 }
		 }

		 return Result.ok(vo);
	 }

 }
