<template>
  <div>
    <el-row :gutter="10">
      <el-col :span="6" style="border-style: solid;">
        <el-tree ref="treeData" :data="checkItem" :props="defaultProps" node-key="id" show-checkbox
          :check-on-click-node="true" @node-click="handleNodeClick" :expand-on-click-node="false"
          :highlight-current="true">
        </el-tree>
      </el-col>
      <el-col :span="18">
        <div v-show="predictVisual">
          <dynamicPredict></dynamicPredict>
        </div>
        </el-col>
    </el-row>
  </div>
</template>

<script>
import dynamicPredict from '@/views/jiancejiance/dynamicPredict.vue'
import { jianceDict} from '@/api/monitorAndCheck/check'
export default {
  components: {
    dynamicPredict
  },
data() {
  return {
    checkItem: [
        {
          id: -1,
          label: '全部',
          children: []
        }
      ],
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      predictVisual: false,
      queyEntity: {},
  }
},
methods: {
  handleNodeClick(data) {
    this.queyEntity = {}
      this.childrenIndex = 0
      if (data.children.length != 0)
        return;
        var s = this.$refs.treeData.getCheckedNodes(false,true)
      var queyEntity = this.queyEntity
      //s的长度为5说明选择的施工期
      if (s.length === 4) {
        queyEntity = {
          suidaoId: -1,
          firstIndexId: -1,
          secondIndexId: -1,
          itemIndexId: -1,
        }
        //获得选择的项目id
        queyEntity.itemIndexId = s[3].id
        //获取隧道标题id
        queyEntity.suidaoId = s[1].id
        //获取一级索引id
        queyEntity.firstIndexId = s[2].id
        //发送queryEntity
        this.queyEntity = queyEntity
        this.$bus.$emit('queryEntity', this.queyEntity)
        this.predictVisual = true
      }
      if (s.length === 4) {
        queyEntity = {
          suidaoId: -1,
          firstIndexId: -1,
          secondIndexId: -1,
          itemIndexId: -1,
        }
        //获得选择的项目id
        queyEntity.itemIndexId = s[3].id
        //获取隧道标题id
        queyEntity.suidaoId = s[1].id
        //获取一级索引id
        queyEntity.firstIndexId = s[2].id
        this.queyEntity = queyEntity
        this.$bus.$emit('queryEntity', this.queyEntity)
        this.predictVisual = true
      }
    },
},
created() {
    //获得数据库中字节点对应信息
    jianceDict().then(response => {
      this.checkItem[0].children = response.data
    })

  },
}
</script>

<style></style>