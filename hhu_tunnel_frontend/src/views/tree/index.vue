<template>
  <div class="app-container">
<el-row :gutter="20">
  <el-col :span="6">
    <!-- 字典拦 -->
    <el-tree ref="tree2" :data="data2" :props="defaultProps" class="filter-tree"
    :check-on-click-node="true"
      @node-click="handleNodeClick" 
      show-checkbox  
      node-key="id"/>
    </el-col>
  <el-col :span="18">
    <div v-if="isTableVisual">
      <el-table ref="tab" :data="tableData" border class="tab-table" 
       >
        <!-- 动态表头 -->
        <el-table-column v-for="(item, index) in tableHead" :key="index" :label="item.label" :prop="item.prop"
          >
        </el-table-column>
      </el-table>
    </div>
    </el-col>
  </el-row>
  </div>
</template>

<script>
import request from '@/utils/request'
import {jianceTable} from '@/api/jiance'
export default {

  data() {
    return {
      filterText: '',
      data2: [{
        id: "1",
        label: "全部",
        children: [],
      }
      ],
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      isTableVisual: false,
      tableData: [],
      tableHead: [],
      dataVo: {
        suidaoId: 0,
        parentid: 0,
        id: 0,
        currentPage: 1,
        limit: 10,
        rq: "",
      },
      nodeCheckDataArr: [],
    }
  },
  watch: {
    filterText(val) {
      this.$refs.tree2.filter(val)
    }
  },

  methods: {
    filterNode(value, data) {
      if (!value) return true
      return data.label.indexOf(value) !== -1
    },

    handleNodeClick(data) {
      if (data.children.length != 0)
        return;
      this.isTableVisual = false
      // this.checkIsOne(data)
      var s = this.$refs.tree2.getCheckedNodes().concat(this.$refs.tree2.getHalfCheckedNodes())
      console.log("选中的节点{}", s)
         //s第一个是当前节点，第二个是上一个
     this.dataVo.suidaoId = s[2].id
     this.dataVo.parentid = s[1].id
     this.dataVo.id = s[0].id
      jianceTable(this.dataVo).then((response) => {
        this.tableHead = response.data.tableHead
        this.tableData = response.data.tableData.records
        this.isTableVisual = true
      })
    },
  
  },
  created() {
    request({
      url: '/jiance/dict',
      method: 'get',
    }).then((response) => {
      console.log(response.data)
      this.data2[0].children = response.data
      console.log(this.data2)
    })

  },
}
</script>

