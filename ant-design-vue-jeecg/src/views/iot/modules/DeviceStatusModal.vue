<template>
  <a-modal
    :width="modalWidth"
    :visible="visible"
    :footer="null"
    @cancel="handleCancel"
    cancelText="关闭">

     <iframe
          :id="id"
          :src="url"
          frameborder="0"
          width="100%"
          height="250px"
          scrolling="auto">
     </iframe>

  </a-modal>
</template>

<script>
  import Vue from 'vue'
  import { ACCESS_TOKEN } from "@/store/mutation-types"

  export default {
    name: "DeviceStatusModal",
    data () {
      return {
        url: '',
        id:"deviceStatusIframe",
        title:"",
        confirmLoading:true,
        visible: false,
        headers:{}
      }
    },
    created () {
      const token = Vue.ls.get(ACCESS_TOKEN);
      this.headers = {"X-Access-Token":token}
    },
    computed:{
    },
    mounted(){
      window.addEventListener('message', this.handleScanFileMessage);
    },
    methods: {
      handleScanFileMessage (event) {
        // 根据上面制定的结构来解析iframe内部发回来的数据
        const data = event.data;
         console.log(data);
      },

      handleCancel() {
        this.visible=false;
        this.close()
      },

      showstatus (deviceSn) {
        this.url = "http://47.100.64.204:3000/d-solo/XrSc9DDGk/device_status?orgId=1&theme=light&panelId=2&var-device_sn="+deviceSn;
        this.visible = true;
      },

    }
  }
</script>

<style scoped>

</style>