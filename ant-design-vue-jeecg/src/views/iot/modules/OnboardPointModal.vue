<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    switchFullscreen
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="接入点名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['onboardName', validatorRules.onboardName]" placeholder="请输入接入点名称"></a-input>
        </a-form-item>
        <a-form-item label="接入点协议" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['protocol', validatorRules.protocol]" :trigger-change="true" dictCode="protocol_type" placeholder="请选择接入点协议"/>
        </a-form-item>
        <a-form-item label="访问地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['accessUrl']" placeholder="请输入访问地址"></a-input>
        </a-form-item>
        <a-form-item label="主动拉取的采集间隔（秒）" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="['observeInterval', validatorRules.observeInterval]" placeholder="请输入主动拉取的采集间隔（秒）" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="认证账户" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['authAccount']" placeholder="请输入认证账户"></a-input>
        </a-form-item>
        <a-form-item label="认证密码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['authPassword']" placeholder="请输入认证密码"></a-input>
        </a-form-item>
        <a-form-item label="参数一" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['param1']" placeholder="请输入参数一"></a-input>
        </a-form-item>
        <a-form-item label="参数2" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['param2']" placeholder="请输入参数2"></a-input>
        </a-form-item>
        <a-form-item label="参数3" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['param3']" placeholder="请输入参数3"></a-input>
        </a-form-item>

      </a-form>
    </a-spin>
  </j-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"


  export default {
    name: "OnboardPointModal",
    components: { 
      JDictSelectTag,
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
          onboardName: {
            rules: [
              { required: true, message: '请输入接入点名称!'},
              { validator: (rule, value, callback) => validateDuplicateValue('iot_onboard_point', 'onboard_name', value, this.model.id, callback)},
            ]
          },
          protocol: {
            rules: [
              { required: true, message: '请输入接入点协议!'},
            ]
          },
          observeInterval: {
            rules: [
              { pattern: /^-?\d+$/, message: '请输入整数!'},
            ]
          },
        },
        url: {
          add: "/org.jeecg.modules.demo.org.jeecg.modules.iot/onboardPoint/add",
          edit: "/org.jeecg.modules.demo.org.jeecg.modules.iot/onboardPoint/edit",
        }
      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'onboardName','protocol','accessUrl','observeInterval','authAccount','authPassword','param1','param2','param3'))
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }
         
        })
      },
      handleCancel () {
        this.close()
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'onboardName','protocol','accessUrl','observeInterval','authAccount','authPassword','param1','param2','param3'))
      },

      
    }
  }
</script>