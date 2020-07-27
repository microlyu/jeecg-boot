<template>
  <j-modal
    :title="title"
    :width="1200"
    :visible="visible"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    switchFullscreen
    @ok="handleOk"
    @cancel="handleCancel">
    <a-spin :spinning="confirmLoading">
      <!-- 主表单区域 -->
      <a-form :form="form">
        <a-row>

          <a-col :xs="24" :sm="12">
            <a-form-item label="设备类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['deviceType', validatorRules.deviceType]" :trigger-change="true" dictCode="iot_device_type,type_name,id" placeholder="请选择设备类型"/>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="设备名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['deviceName', validatorRules.deviceName]" placeholder="请输入设备名称"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="设备序列号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['deviceSn']" placeholder="请输入设备序列号"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="设备图片" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-image-upload isMultiple v-decorator="['devicePic']"></j-image-upload>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="接入点" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['onboadPoint']" :trigger-change="true" dictCode="iot_onboard_point,onboard_name,id" placeholder="请选择接入点"/>
            </a-form-item>
          </a-col>

        </a-row>
      </a-form>

      <!-- 子表单区域 -->
      <a-tabs v-model="activeKey" @change="handleChangeTabs">
        <a-tab-pane tab="设备传感器" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[0]"
            :loading="deviceSensorTable.loading"
            :columns="deviceSensorTable.columns"
            :dataSource="deviceSensorTable.dataSource"
            :maxHeight="300"
            :rowNumber="true"
            :rowSelection="true"
            :actionButton="true"/>
        </a-tab-pane>
        
      </a-tabs>

    </a-spin>
  </j-modal>
</template>

<script>

  import pick from 'lodash.pick'
  import { FormTypes,getRefPromise } from '@/utils/JEditableTableUtil'
  import { JEditableTableMixin } from '@/mixins/JEditableTableMixin'
  import { validateDuplicateValue } from '@/utils/util'
  import JImageUpload from '@/components/jeecg/JImageUpload'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"

  export default {
    name: 'DeviceModal',
    mixins: [JEditableTableMixin],
    components: {
      JImageUpload,
      JDictSelectTag,
    },
    data() {
      return {
        labelCol: {
          xs: { span: 24 },
          sm: { span: 6 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        labelCol2: {
          xs: { span: 24 },
          sm: { span: 3 },
        },
        wrapperCol2: {
          xs: { span: 24 },
          sm: { span: 20 },
        },
        // 新增时子表默认添加几行空数据
        addDefaultRowNum: 1,
        validatorRules: {
          deviceType: {
            rules: [
              { required: true, message: '请输入设备类型!'},
            ]
          },
          deviceName: {
            rules: [
              { required: true, message: '请输入设备名称!'},
            ]
          },
        },
        refKeys: ['deviceSensor', ],
        tableKeys:['deviceSensor', ],
        activeKey: 'deviceSensor',
        // 设备传感器
        deviceSensorTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '指标编码',
              key: 'sensorCode',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },
            {
              title: '指标名称',
              key: 'sensorName',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },
          ]
        },
        url: {
          add: "/org.jeecg.modules.demo.org.jeecg.modules.iot/device/add",
          edit: "/org.jeecg.modules.demo.org.jeecg.modules.iot/device/edit",
          deviceSensor: {
            list: '/org.jeecg.modules.demo.org.jeecg.modules.iot/device/queryDeviceSensorByMainId'
          },
        }
      }
    },
    methods: {
      getAllTable() {
        let values = this.tableKeys.map(key => getRefPromise(this, key))
        return Promise.all(values)
      },
      /** 调用完edit()方法之后会自动调用此方法 */
      editAfter() {
        let fieldval = pick(this.model,'deviceType','deviceName','deviceSn','devicePic','onboadPoint')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.deviceSensor.list, params, this.deviceSensorTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)
        return {
          ...main, // 展开
          deviceSensorList: allValues.tablesValue[0].values,
        }
      },
      validateError(msg){
        this.$message.error(msg)
      },
     popupCallback(row){
       this.form.setFieldsValue(pick(row,'deviceType','deviceName','deviceSn','devicePic','onboadPoint'))
     },

    }
  }
</script>

<style scoped>
</style>