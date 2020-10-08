<template>
  <a-card :bordered="false">
    <!-- table区域-begin -->
    <div class="app-list">
      <a-list
        :grid="{ gutter: 24, lg: 3, md: 2, sm: 1, xs: 1 }"
        :dataSource="dataSource">

        <a-list-item slot="renderItem" slot-scope="item, index">
          <a-card :hoverable="true">
            <a-card-meta>
              <div style="margin-bottom: 3px" slot="title">{{ item.objectName }}</div>
              <a-avatar class="card-avatar" slot="avatar" :src="getImgView(item.objectLogo)" size="300"/>
              <div class="meta-cardInfo" slot="description">
                <div>
                  <p>当前温度</p>
                  <p>
                    <span>{{ item.currentTemp }}</span>
                  </p>
                </div>
                <div>
                  <p>当前湿度</p>
                  <p>{{ item.currentHumidity | NumberFormat }}</p>
                </div>
              </div>
            </a-card-meta>
            <template class="ant-card-actions" slot="actions">
              <a>
                <a-icon type="download"/>
              </a>
              <a>
                <a-icon @click="handleDetail(item)" type="edit"/>
              </a>
            </template>
          </a-card>
        </a-list-item>
      </a-list>

    </div>

    <monitor-object-modal ref="modalForm" @ok="modalFormOk"></monitor-object-modal>
  </a-card>
</template>

<script>

import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import MonitorObjectModal from './modules/MonitorObjectModal'
import { getAction } from '@api/manage'

export default {
  name: 'MonitorObjectList',
  mixins: [JeecgListMixin, mixinDevice],
  components: {
    MonitorObjectModal
  },
  data() {
    return {
      description: '监控对象管理页面',
      dataSource: [],
      url: {
        list: '/iot/monitorObject/monitorAll'
      },
      dictOptions: {}
    }
  },
  created() {
    this.$set(this.dictOptions, 'objectStatus', [{ text: '是', value: 'Y' }, { text: '否', value: 'N' }])
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
  methods: {
    loadData(arg) {
      // this.loading = true;
      let params = {};
      getAction(this.url.list, params).then((res) => {
        if (res.success) {
          this.dataSource = res.result;
          // this.ipagination.total = res.result.total;
          // console.log(this.dataSource);
        }
        if(res.code===510){
          this.$message.warning(res.message)
        }
        // this.loading = false;
      })

      // for (let i = 0; i < 5; i++) {
      //   this.dataSource.push({
      //     title: '海尔冰箱',
      //     avatar: 'https://gw.alipayobjects.com/zos/rmsportal/WdGqmHpayyMjiEhcKoVE.png',
      //     activeUser: 30,
      //     newUser: 56
      //   })
      // }
    },
    initDictConfig() {
    }
  }
}
</script>
<style lang="less" scoped>
@import '~@assets/less/common.less';

.app-list {

  .meta-cardInfo {
    zoom: 1;
    margin-top: 16px;

    > div {
      position: relative;
      text-align: left;
      float: left;
      width: 50%;

      p {
        line-height: 32px;
        font-size: 24px;
        margin: 0;

        &:first-child {
          color: rgba(0, 0, 0, .45);
          font-size: 12px;
          line-height: 20px;
          margin-bottom: 4px;
        }
      }

    }
  }
}

</style>