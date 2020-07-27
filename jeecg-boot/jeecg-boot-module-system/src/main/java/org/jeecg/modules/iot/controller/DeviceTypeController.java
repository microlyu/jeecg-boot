package org.jeecg.modules.iot.controller;

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
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.org.jeecg.modules.demo.org.jeecg.modules.iot.entity.DeviceType;
import org.jeecg.modules.demo.org.jeecg.modules.demo.org.jeecg.modules.iot.service.IDeviceTypeService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

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
 * @Description: 设备类型
 * @Author: jeecg-boot
 * @Date:   2020-07-24
 * @Version: V1.0
 */
@Api(tags="设备类型")
@RestController
@RequestMapping("/org.jeecg.modules.demo.org.jeecg.modules.iot/deviceType")
@Slf4j
public class DeviceTypeController extends JeecgController<DeviceType, IDeviceTypeService>{
	@Autowired
	private IDeviceTypeService deviceTypeService;
	
	/**
	 * 分页列表查询
	 *
	 * @param deviceType
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "设备类型-分页列表查询")
	@ApiOperation(value="设备类型-分页列表查询", notes="设备类型-分页列表查询")
	@GetMapping(value = "/rootList")
	public Result<?> queryPageList(DeviceType deviceType,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		String parentId = deviceType.getPid();
		if (oConvertUtils.isEmpty(parentId)) {
			parentId = "0";
		}
		deviceType.setPid(null);
		QueryWrapper<DeviceType> queryWrapper = QueryGenerator.initQueryWrapper(deviceType, req.getParameterMap());
		// 使用 eq 防止模糊查询
		queryWrapper.eq("pid", parentId);
		Page<DeviceType> page = new Page<DeviceType>(pageNo, pageSize);
		IPage<DeviceType> pageList = deviceTypeService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	 /**
      * 获取子数据
      * @param testTree
      * @param req
      * @return
      */
	@AutoLog(value = "设备类型-获取子数据")
	@ApiOperation(value="设备类型-获取子数据", notes="设备类型-获取子数据")
	@GetMapping(value = "/childList")
	public Result<?> queryPageList(DeviceType deviceType,HttpServletRequest req) {
		QueryWrapper<DeviceType> queryWrapper = QueryGenerator.initQueryWrapper(deviceType, req.getParameterMap());
		List<DeviceType> list = deviceTypeService.list(queryWrapper);
		return Result.ok(list);
	}
	
	
	/**
	 *   添加
	 *
	 * @param deviceType
	 * @return
	 */
	@AutoLog(value = "设备类型-添加")
	@ApiOperation(value="设备类型-添加", notes="设备类型-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody DeviceType deviceType) {
		deviceTypeService.addDeviceType(deviceType);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param deviceType
	 * @return
	 */
	@AutoLog(value = "设备类型-编辑")
	@ApiOperation(value="设备类型-编辑", notes="设备类型-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody DeviceType deviceType) {
		deviceTypeService.updateDeviceType(deviceType);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "设备类型-通过id删除")
	@ApiOperation(value="设备类型-通过id删除", notes="设备类型-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		deviceTypeService.deleteDeviceType(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "设备类型-批量删除")
	@ApiOperation(value="设备类型-批量删除", notes="设备类型-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.deviceTypeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "设备类型-通过id查询")
	@ApiOperation(value="设备类型-通过id查询", notes="设备类型-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		DeviceType deviceType = deviceTypeService.getById(id);
		if(deviceType==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(deviceType);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param deviceType
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DeviceType deviceType) {
		return super.exportXls(request, deviceType, DeviceType.class, "设备类型");
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
		return super.importExcel(request, response, DeviceType.class);
    }

}
