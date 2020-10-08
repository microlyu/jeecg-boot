<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <!-- 主表单区域 -->
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24" >
            <a-form-item label="所属部门" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-select-depart v-decorator="['sysOrgCode']" multi/>
            </a-form-item>
          </a-col>
          <a-col :span="24" >
            <a-form-item label="设备类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['deviceType']" :trigger-change="true" dictCode="iot_device_type,type_name,id" placeholder="请选择设备类型"/>
            </a-form-item>
          </a-col>
          <a-col :span="24" >
            <a-form-item label="设备名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['deviceName']" placeholder="请输入设备名称"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24" >
            <a-form-item label="设备地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['deviceIp']" placeholder="请输入设备地址"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24" >
            <a-form-item label="采样频率" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['collectInterval', validatorRules.collectInterval]" placeholder="请输入采样频率" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24" >
            <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-switch v-decorator="['deviceStatus']" ></j-switch>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </j-form-container>
      <!-- 子表单区域 -->
    <a-tabs v-model="activeKey" @change="handleChangeTabs">
      <a-tab-pane tab="设备探针" :key="refKeys[0]" :forceRender="true">
        <j-editable-table
          :ref="refKeys[0]"
          :loading="deviceProbeTable.loading"
          :columns="deviceProbeTable.columns"
          :dataSource="deviceProbeTable.dataSource"
          :maxHeight="300"
          :disabled="formDisabled"
          :rowNumber="true"
          :rowSelection="true"
          :actionButton="true"/>
      </a-tab-pane>
    </a-tabs>
  </a-spin>
</template>

<script>

  import pick from 'lodash.pick'
  import { getAction } from '@/api/manage'
  import { FormTypes,getRefPromise } from '@/utils/JEditableTableUtil'
  import { JEditableTableMixin } from '@/mixins/JEditableTableMixin'
  import { validateDuplicateValue } from '@/utils/util'
  import JFormContainer from '@/components/jeecg/JFormContainer'
  import JSelectDepart from '@/components/jeecgbiz/JSelectDepart'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JSwitch from '@/components/jeecg/JSwitch'

  export default {
    name: 'DeviceForm',
    mixins: [JEditableTableMixin],
    components: {
      JFormContainer,
      JSelectDepart,
      JDictSelectTag,
      JSwitch,
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
          collectInterval: {
            rules: [
              { required: false},
              { pattern: /^-?\d+$/, message: '请输入整数!'},
            ]
          },
        },
        refKeys: ['deviceProbe', ],
        tableKeys:['deviceProbe', ],
        activeKey: 'deviceProbe',
        // 设备探针
        deviceProbeTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '探针编号',
              key: 'probeNo',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '监控对象',
              key: 'monitorId',
              type: FormTypes.select,
              dictCode:"iot_monitor_object,object_name,id",
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '传感类型',
              key: 'sensorType',
              type: FormTypes.select,
              dictCode:"sensor_type",
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
          ]
        },
        url: {
          add: "/iot/device/add",
          edit: "/iot/device/edit",
          queryById: "/iot/device/queryById",
          deviceProbe: {
            list: '/iot/device/queryDeviceProbeByMainId'
          },
        }
      }
    },
    props: {
      //流程表单data
      formData: {
        type: Object,
        default: ()=>{},
        required: false
      },
      //表单模式：false流程表单 true普通表单
      formBpm: {
        type: Boolean,
        default: false,
        required: false
      },
      //表单禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      }
    },
    computed: {
      formDisabled(){
        if(this.formBpm===true){
          if(this.formData.disabled===false){
            return false
          }
          return true
        }
        return this.disabled
      },
      showFlowSubmitButton(){
        if(this.formBpm===true){
          if(this.formData.disabled===false){
            return true
          }
        }
        return false
      }
    },
    created () {
      //如果是流程中表单，则需要加载流程表单data
      this.showFlowData();
    },
    methods: {
      addBefore(){
        this.form.resetFields()
        this.deviceProbeTable.dataSource=[]
      },
      getAllTable() {
        let values = this.tableKeys.map(key => getRefPromise(this, key))
        return Promise.all(values)
      },
      /** 调用完edit()方法之后会自动调用此方法 */
      editAfter() {
        let fieldval = pick(this.model,'sysOrgCode','deviceType','deviceName','deviceIp','collectInterval','deviceStatus')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.deviceProbe.list, params, this.deviceProbeTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)
        return {
          ...main, // 展开
          deviceProbeList: allValues.tablesValue[0].values,
        }
      },
      //渲染流程表单数据
      showFlowData(){
        if(this.formBpm === true){
          let params = {id:this.formData.dataId};
          getAction(this.url.queryById,params).then((res)=>{
            if(res.success){
              this.edit (res.result);
            }
          })
        }
      },
      validateError(msg){
        this.$message.error(msg)
      },
     popupCallback(row){
       this.form.setFieldsValue(pick(row,'sysOrgCode','deviceType','deviceName','deviceIp','collectInterval','deviceStatus'))
     },

    }
  }
</script>

<style scoped>
</style>