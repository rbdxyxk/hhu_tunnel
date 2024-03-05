<template>
  <div>
    <el-row :gutter="10">
      <el-col :span="6" style="border-style: solid;">
        <el-tree ref="treeData" :data="monitorItem" :props="defaultProps" node-key="id" show-checkbox
          :check-on-click-node="true" @node-click="handleNodeClick" :expand-on-click-node="false"
          :highlight-current="true">
        </el-tree>
      </el-col>
      <el-col :span="18">
        <div v-if="isTableVisual" >
          <el-button type="primary" plain @click="addTableData">添加</el-button>
          <el-button type="primary" plain @click="editTableData">编辑</el-button>
          <el-button type="primary" plain @click="deleteTableData">删除</el-button>
          <el-table ref="tab" :data="tableData" border class="tab-table" @selection-change="handleSelectionChange"
            :key="Math.random()" style="margin-top: 1rem;height: 25rem;" :header-cell-style="{ textAlign: 'center' }"
            :cell-style="{ textAlign: 'center' }" height="800">
            <el-table-column type="selection" width="55" :fixed="true">
            </el-table-column>
            <!-- 动态表头 -->
            <div v-for="(item, index) in tableHead" :key="index">
              <el-table-column
                v-if="item.label === '日期' || item.label === '测点位置' || item.label === '安全状态判断' || item.label === '组合权重' || item.label === '其他要求'"
                :label="item.label" :prop="item.prop" width="100">
              </el-table-column>
              <el-table-column v-else-if="item.fuhao == -1" :key="index" :label="item.label" :prop="item.prop"
                width="100">
              </el-table-column>
              <el-table-column v-else-if="item.fuhao === 2 || item.fuhao === '2'" :label="item.label">
                <el-table-column label='实测值1' :prop="'children.' + item.prop + '_real_0'">
                </el-table-column>
                <el-table-column label='阈值1(≤)' :prop="'children.' + item.prop + '_yuzhi_0'">
                </el-table-column>
                <el-table-column label='实测值2' :prop="'children.' + item.prop + '_real_1'">
                </el-table-column>
                <el-table-column label='阈值2(≤)' :prop="'children.' + item.prop + '_yuzhi_1'">
                </el-table-column>
              </el-table-column>
              <el-table-column v-else-if="item.fuhao === 3 || item.fuhao === '3'" :label="item.label">
                <el-table-column label='实测值1' :prop="'children.' + item.prop + '_real_0'">
                </el-table-column>
                <el-table-column label='阈值1(≥)' :prop="'children.' + item.prop + '_yuzhi_0'">
                </el-table-column>
                <el-table-column label='实测值2' :prop="'children.' + item.prop + '_real_1'">
                </el-table-column>
                <el-table-column label='阈值2(≥)' :prop="'children.' + item.prop + '_yuzhi_1'">
                </el-table-column>
              </el-table-column>
              <el-table-column v-else-if="item.fuhao === 4 || item.fuhao === '4'" :label="item.label">
                <el-table-column label='实测值1' :prop="'children.' + item.prop + '_real_0'">
                </el-table-column>
                <el-table-column label='阈值1(≥)' :prop="'children.' + item.prop + '_yuzhi_0'">
                </el-table-column>
                <el-table-column label='实测值2' :prop="'children.' + item.prop + '_real_1'">
                </el-table-column>
                <el-table-column label='阈值2(≤)' :prop="'children.' + item.prop + '_yuzhi_1'">
                </el-table-column>
              </el-table-column>
              <el-table-column v-else-if="item.fuhao === 5 || item.fuhao === '5'" :label="item.label">
                <el-table-column label='实测值1' :prop="'children.' + item.prop + '_real_0'">
                </el-table-column>
                <el-table-column label='阈值1(≤)' :prop="'children.' + item.prop + '_yuzhi_0'">
                </el-table-column>
                <el-table-column label='实测值2' :prop="'children.' + item.prop + '_real_1'">
                </el-table-column>
                <el-table-column label='阈值2(≥)' :prop="'children.' + item.prop + '_yuzhi_1'">
                </el-table-column>
              </el-table-column>
              <el-table-column v-else-if="item.fuhao === 6 || item.fuhao === '6'" :label="item.label">
                <el-table-column label='实测值' :prop="'children.' + item.prop + '_real_0'">
                </el-table-column>
                <el-table-column label='阈值1(≥)' :prop="'children.' + item.prop + '_yuzhi_0'">
                </el-table-column>
                <el-table-column label='阈值2(≤)' :prop="'children.' + item.prop + '_yuzhi_1'">
                </el-table-column>
              </el-table-column>
              <el-table-column v-else :label="item.label">
                <el-table-column label='实测值' :prop="'children.' + item.prop + '_real_0'">
                </el-table-column>
                <el-table-column v-if="item.type === 1" label='阈值' :prop="'children.' + item.prop + '_yuzhi_0'">

                </el-table-column>
                <el-table-column v-else-if="item.fuhao === 0" label='阈值(≥)' :prop="'children.' + item.prop + '_yuzhi_0'">
                </el-table-column>
                <el-table-column v-else-if="item.fuhao === 1" label='阈值(≤)' :prop="'children.' + item.prop + '_yuzhi_0'">
                </el-table-column>
                <el-table-column v-else-if="item.fuhao === 7" label='阈值(=)' :prop="'children.' + item.prop + '_yuzhi_0'">
                </el-table-column>
              </el-table-column>
            </div>
          </el-table>
          <el-pagination background layout="prev, pager, next" :current-page.sync="pages.currentPage"
            :page-size="pages.size" :total="pages.total" @current-change="handleCurrentChange" style="margin-top: 1rem;">
          </el-pagination>
        </div>
        <div v-show="predictVisual" style="margin-top: 1rem;">
          <hr />
          <dynamicPredictVue></dynamicPredictVue>
        </div>
      </el-col>
    </el-row>
    <!-- 动态预测组件 -->

    <el-dialog title="添加" :visible.sync="adddDialogVisual" width="35%" :show-close="false">
      <el-form ref="addform" :model="addData" label-width="13rem">

        <div v-for="(item, index) in tempData" :key="index">
          <el-form-item v-if="item.label === '日期'" :label="item.label" style="margin-bottom: 0.6rem;">
            <el-date-picker v-model="item.data" type="datetime" placeholder="选择日期" value-format="yyyy-MM-dd HH:mm:ss">
            </el-date-picker>
          </el-form-item>
          <el-form-item
            v-if="item.label === '测点位置' || item.label === '安全状态判断' || item.label === '组合权重' || item.label === '其他要求'"
            :label="item.label" style="margin-bottom: 0.6rem;">
            <el-input v-model="item.data"></el-input>
          </el-form-item>
          <div v-else-if="item.fuhao === -1">
            <el-form-item :label="item.label" style="margin-bottom: 0.6rem;">
              <el-input v-model="item.data"></el-input>
            </el-form-item>
          </div>
          <div v-else-if="item.fuhao != -1">
            <div v-for="(item1, index1) in item.children" :key="index1">
              <el-form-item :label="item1.label" style="margin-bottom: 0.6rem;">
                <el-input v-model="item1.data"></el-input>
              </el-form-item>
            </div>
          </div>
        </div>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelAdd">取 消</el-button>
        <el-button @click="getSafe">计算安全状况</el-button>
        <el-button type="primary" @click="submitAdd">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog title="编辑" :visible.sync="editDialogVisual" width="35%" :show-close="false" @close="closeDialog">
      <el-form ref="edditform" :model="editData" label-width="13rem">
        <div v-for="(item, index) in tempData" :key="index">
          <el-form-item v-if="item.label === '日期'" :label="item.label" style="margin-bottom: 0.6rem;">
            <el-date-picker v-model="item.data" type="datetime" placeholder="选择日期" value-format="yyyy-MM-dd HH:mm:ss">
            </el-date-picker>
          </el-form-item>
          <el-form-item
            v-if="item.label === '测点位置' || item.label === '安全状态判断' || item.label === '组合权重' || item.label === '其他要求'"
            :label="item.label" style="margin-bottom: 0.6rem;">
            <el-input v-model="item.data"></el-input>
          </el-form-item>
          <div v-if="item.prop != 'rq' && item.fuhao === -1">
            <el-form-item :label="item.label" style="margin-bottom: 0.6rem;">
              <el-input v-model="item.data"></el-input>
            </el-form-item>
          </div>
          <div v-if="item.prop != 'rq' && item.fuhao != -1">
            <div v-for="(item1, index1) in item.children" :key="index1">
              <el-form-item :label="item1.label" style="margin-bottom: 0.6rem;">
                <el-input v-model="item1.data"></el-input>
              </el-form-item>
            </div>
          </div>
        </div>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelEdit">取 消</el-button>
        <el-button @click="getSafe">计算安全状况</el-button>
        <el-button type="primary" @click="submitEdit">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import dynamicPredictVue from './dynamicPredict.vue';
import { monitorDict, monitorTable, addmonitorData, monitorGetYuzhi, monitorEditData, monitorDeleteData, monitorPage } from '@/api/monitorAndCheck/monitor'
export default {
  components: {
    dynamicPredictVue
  },
  data() {
    return {
      //监测项目字典
      monitorItem: [
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
      isTableVisual: false,
      tableData: [],
      tableHead: [],
      selectedData: [],
      adddDialogVisual: false,
      editDialogVisual: false,
      addData: {},
      editData: {},
      //用tempData每一项是个对象,label对应中文,prop对应英文属性,data为输入的值
      tempData: [],
      oldEditData: {},
      tempEditData: [],
      queyEntity: {
        suidaoId: -1,
        firstIndexId: -1,
        secondIndexId: -1,
        itemIndexId: -1,
      },
      predictVisual: false,
      pages: {
        //每页展示条数
        size: 10,
        //总共多少条
        total: 10,
        currentPage: 1
      },
      flag: -1,
      yuzhiMap: {},
      predictItemList: [],
    }
  },
  created() {
    //获得数据库中字节点对应信息
    monitorDict().then(response => {
      this.monitorItem[0].children = response.data
    })

  },
  methods: {
    closeDialog(){
      this.selectedData = []
    },
    handleNodeClick(data) {
      this.queyEntity = {}
      if (data.children.length != 0)
        return;
      var s = this.$refs.treeData.getCheckedNodes(false, true)
      var queyEntity;
      //s的长度为5说明选择的施工期
      if (s.length === 5) {
        queyEntity = {
          suidaoId: -1,
          firstIndexId: -1,
          secondIndexId: -1,
          itemIndexId: -1,
           //0 是监测项目，1是检测项目
          type: 0,
        }
        //获得选择的项目id
        queyEntity.itemIndexId = s[4].id
        //获取隧道标题id
        queyEntity.suidaoId = s[1].id
        //获取一级索引id
        queyEntity.firstIndexId = s[2].id
        //获取二级索引id
        queyEntity.secondIndexId = s[3].id
        //发送queryEntity
        this.getTable(queyEntity)

      }
      if (s.length === 4) {
        queyEntity = {
          suidaoId: -1,
          firstIndexId: -1,
          secondIndexId: -1,
          itemIndexId: -1,
           //0 是监测项目，1是检测项目
          type: 0,
        }
        //获得选择的项目id
        queyEntity.itemIndexId = s[3].id
        //获取隧道标题id
        queyEntity.suidaoId = s[1].id
        //获取一级索引id
        queyEntity.firstIndexId = s[2].id
        this.getTable(queyEntity)
      }
    },
    getTable(queyEntity) {
      //调用api获取对应queryEntity的表头和数据
      monitorTable(queyEntity, 1, this.pages.size).then(response => {
        if (response.data.tableHead === null) {
          this.$message.error('暂无数据');
          return
        }
        this.tableHead = response.data.tableHead
        this.tableData = response.data.tableData
        //遍历tableHead,获取除了日期，测点位置，其他要求，安全状态判断，组合权重之外的变量
        this.predictItemList = []
        for(var i = 0; i < this.tableHead.length; i++){
          if (this.tableHead[i]['type'] === 1 ||  this.tableHead[i]['prop'] === 'rq' || this.tableHead[i]['prop'] === 'cdwz' || this.tableHead[i]['prop'] === 'qtyq' || this.tableHead[i]['prop'] === 'auzt' || this.tableHead[i]['prop'] === 'zuheqz')
            continue
          var predictObject = {}
          predictObject['label'] = this.tableHead[i]['label']
          predictObject['prop'] = this.tableHead[i]['prop']
          this.predictItemList.push(predictObject)
        }
        if (this.tableData != undefined && this.tableData != null && this.tableData.length > 0) {
          for (var i = 0; i < this.tableData.length; i++) {
            if (this.tableData[i].rq != undefined && this.tableData[i].rq != undefined && this.tableData[i].rq != "") {
              this.tableData[i].rq = this.$moment(this.tableData[i].rq).format('YYYY-MM-DD HH:mm:ss')
            }
          }
        }

        this.pages.total = parseInt(response.data.total)
        this.pages.current = parseInt(response.data.current)
        // this.pages.total = 2
        // this.pages.current = 1
        // this.tableHead = [
        //   {
        //     id: 1,
        //     label: "日期",
        //     prop: "rq",
        //     fuhao: -1,
        //   },
        //   {
        //     id: 2,
        //     label: "测点编号",
        //     prop: "cdbh",
        //     fuhao: -1,
        //   },
        //   {
        //     id: 3,
        //     label: "据开挖面距离/m",
        //     prop: "jkwmjl",
        //     fuhao: 0,
        //     type: 0,
        //   },
        //   {
        //     id: 4,
        //     label: "单次位移量/mm",
        //     prop: "dcwyl",
        //     fuhao: 0,
        //     type: 0,
        //   },
        //   {
        //     id: 5,
        //     label: "累计位移量/mm",
        //     prop: "ljwyl",
        //     fuhao: 0,
        //     type: 0
        //   },
        //   {
        //     id: 6,
        //     label: "目前位移速率/(mm/d)",
        //     prop: "mqwysl",
        //     fuhao: 0,
        //     type: 0,
        //   },
        //   {
        //     id: 7,
        //     label: "最大位移速率/(mm/d)",
        //     prop: "zdwysl",
        //     fuhao: 0,
        //     type: 0
        //   },
        //   {
        //     id: 8,
        //     label: "判断",
        //     prop: "judge",
        //     fuhao: -1,
        //   },
        //   {
        //     id: 9,
        //     label: "组合权重",
        //     prop: "zhqz",
        //     fuhao: -1,
        //   },
        // ]
        // this.tableData = [
        //   {
        //     id: 1,
        //     rq: "2022-02-03 16:00:00",
        //     cdbh: "测点1",
        //     judge: "安全",
        //     zhqz: "1",
        //     children: [{
        //       itemIndexId: 3,
        //       realData: [9],
        //       yuzhiData: [8],
        //     },
        //     {
        //       itemIndexId: 4,
        //       realData: [12],
        //       yuzhiData: [10],
        //     },
        //     {
        //       itemIndexId: 5,
        //       realData: [20],
        //       yuzhiData: [18],
        //     },
        //     {
        //       itemIndexId: 6,
        //       realData: [18],
        //       yuzhiData: [15],
        //     },
        //     {
        //       itemIndexId: 7,
        //       realData: [15],
        //       yuzhiData: [10],
        //     }
        //     ]
        //   }
        // ]
        if (this.tableData != null && this.tableData != undefined && this.tableData.length > 0) {
          for (var i = 0; i < this.tableData.length; i++) {
            this.transformChildren(this.tableData[i])
          }
        }
        this.queyEntity = queyEntity
        this.predictVisual = true
        this.isTableVisual = true
        this.$bus.$emit('queryEntity', this.queyEntity,this.predictItemList)
        this.getYuzhiMap()
      })
    },
    //获得默认阈值数组
    getYuzhiMap() {
      monitorGetYuzhi(this.queyEntity).then(response => {
        //根据表头获得,因为这里key是id,value是阈值数组
        if (response.code === 201) {
          this.$message.error("获取阈值失败")
          return
        }
        this.yuzhiMap = response.data
      })


    },
    transformChildren(data) {
      //这里前端先自己改造children格式: prop名字_real or yuzhi
      var newChildren = {}
      if (data.children != null && data.children != undefined && data.children.length > 0) {
        for (var i = 0; i < data.children.length; i++) {
          for (var j = 0; j < this.tableHead.length; j++) {
            if (data.children[i].itemIndexId === this.tableHead[j].id) {
              //遍历realData数组
              if (data.children[i].realData != null && data.children[i].realData != undefined && data.children[i].realData.length > 0) {
                for (var k = 0; k < data.children[i].realData.length; k++) {
                  var realLabel = this.tableHead[j].prop + '_real_' + k;
                  newChildren[realLabel] = data.children[i].realData[k]
                }
              }
              //遍历yuzhiData数组
              if (data.children[i].yuzhiData != null && data.children[i].yuzhiData != undefined && data.children[i].yuzhiData.length > 0) {
                for (var k = 0; k < data.children[i].yuzhiData.length; k++) {
                  var yuzhiLabel = this.tableHead[j].prop + '_yuzhi_' + k;
                  newChildren[yuzhiLabel] = data.children[i].yuzhiData[k]
                }
              }
              break
            }
          }
        }
        data.children = newChildren
      }
    },
    addTableData() {
      this.editDialogVisual = false
      this.flag = -1
      var tempresult = {}
      this.tempData = []
      for (var i = 0; i < this.tableHead.length; i++) {
        tempresult = new Object()
        if (this.tableHead[i].label === '日期' || this.tableHead[i].label === '测点位置' || this.tableHead[i].label === '安全状态判断' || this.tableHead[i].label === '组合权重' || this.tableHead[i].label === '其他要求') {
          tempresult['data'] = ''
          tempresult['label'] = this.tableHead[i].label
          tempresult['prop'] = this.tableHead[i].prop
        }
        else if (this.tableHead[i].fuhao === -1) {
          tempresult['data'] = ''
          tempresult['label'] = this.tableHead[i].label
          tempresult['prop'] = this.tableHead[i].prop
        }
        else {
          //这里的都是有实测值和阈值的
          //根据tableHead的符号id进行判断
          tempresult['children'] = []
          if (this.tableHead[i].fuhao === 0) {
            var realData = new Object()
            realData['label'] = this.tableHead[i].label + '实测值'
            realData['data'] = ''
            tempresult['children'].push(realData)
            var yuzhiData = new Object()
            yuzhiData['label'] = this.tableHead[i].label + '-阈值(≥)'
            if (this.yuzhiMap[this.tableHead[i].id] != null && this.yuzhiMap[this.tableHead[i].id] != undefined && (this.yuzhiMap[this.tableHead[i].id]).length >= 1)
              yuzhiData['data'] = this.yuzhiMap[this.tableHead[i].id][0]
            else yuzhiData['data'] = ''
            tempresult['children'].push(yuzhiData)
            //存放id方便存储进数据库
          }
          if (this.tableHead[i].fuhao === 7) {
            var realData = new Object()
            realData['label'] = this.tableHead[i].label + '实测值'
            realData['data'] = ''
            tempresult['children'].push(realData)
            var yuzhiData = new Object()
            yuzhiData['label'] = this.tableHead[i].label + '-阈值(=)'
            if (this.yuzhiMap[this.tableHead[i].id] != null && this.yuzhiMap[this.tableHead[i].id] != undefined && (this.yuzhiMap[this.tableHead[i].id]).length >= 1)
              yuzhiData['data'] = this.yuzhiMap[this.tableHead[i].id][0]
            else yuzhiData['data'] = ''
            tempresult['children'].push(yuzhiData)
            //存放id方便存储进数据库
          }
          if (this.tableHead[i].fuhao === 1) {
            var realData = new Object()
            realData['label'] = this.tableHead[i].label + '实测值'
            realData['data'] = ''
            tempresult['children'].push(realData)
            var yuzhiData = new Object()
            yuzhiData['label'] = this.tableHead[i].label + '-阈值(≤)'
            if (this.yuzhiMap[this.tableHead[i].id] != null && this.yuzhiMap[this.tableHead[i].id] != undefined && (this.yuzhiMap[this.tableHead[i].id]).length >= 1)
              yuzhiData['data'] = this.yuzhiMap[this.tableHead[i].id][0]
            else yuzhiData['data'] = ''
            tempresult['children'].push(yuzhiData)
            //存放id方便存储进数据库
          }
          if (this.tableHead[i].fuhao === 2) {
            var realData0 = new Object()
            realData0['label'] = this.tableHead[i].label + '实测值1'
            realData0['data'] = ''
            tempresult['children'].push(realData0)
            var yuzhiData0 = new Object()
            yuzhiData0['label'] = this.tableHead[i].label + '-阈值1(≤)'
            if (this.yuzhiMap[this.tableHead[i].id] != null && this.yuzhiMap[this.tableHead[i].id] != undefined && (this.yuzhiMap[this.tableHead[i].id]).length >= 1)
              yuzhiData0['data'] = this.yuzhiMap[this.tableHead[i].id][0]
            else yuzhiData0['data'] = ''
            tempresult['children'].push(yuzhiData0)
            var realData1 = new Object()
            realData1['label'] = this.tableHead[i].label + '实测值2'
            realData1['data'] = ''
            tempresult['children'].push(realData1)
            var yuzhiData1 = new Object()
            yuzhiData1['label'] = this.tableHead[i].label + '-阈值2(≤)'
            if (this.yuzhiMap[this.tableHead[i].id] != null && this.yuzhiMap[this.tableHead[i].id] != undefined && (this.yuzhiMap[this.tableHead[i].id]).length >= 2)
              yuzhiData1['data'] = this.yuzhiMap[this.tableHead[i].id][1]
            else yuzhiData1['data'] = ''
            tempresult['children'].push(yuzhiData1)
            //存放id方便存储进数据库
          }
          if (this.tableHead[i].fuhao === 3) {
            var realData0 = new Object()
            realData0['label'] = this.tableHead[i].label + '实测值1'
            realData0['data'] = ''
            tempresult['children'].push(realData0)
            var yuzhiData0 = new Object()
            yuzhiData0['label'] = this.tableHead[i].label + '-阈值1(≥)'
            if (this.yuzhiMap[this.tableHead[i].id] != null && this.yuzhiMap[this.tableHead[i].id] != undefined && (this.yuzhiMap[this.tableHead[i].id]).length >= 1)
              yuzhiData0['data'] = this.yuzhiMap[this.tableHead[i].id][0]
            else yuzhiData0['data'] = ''
            tempresult['children'].push(yuzhiData0)
            var realData1 = new Object()
            realData1['label'] = this.tableHead[i].label + '实测值2'
            realData1['data'] = ''
            tempresult['children'].push(realData1)
            var yuzhiData1 = new Object()
            yuzhiData1['label'] = this.tableHead[i].label + '-阈值2(≥)'
            if (this.yuzhiMap[this.tableHead[i].id] != null && this.yuzhiMap[this.tableHead[i].id] != undefined && (this.yuzhiMap[this.tableHead[i].id]).length >= 2)
              yuzhiData1['data'] = this.yuzhiMap[this.tableHead[i].id][1]
            else yuzhiData1['data'] = ''
            tempresult['children'].push(yuzhiData1)
            //存放id方便存储进数据库
          }
          if (this.tableHead[i].fuhao === 4) {
            var realData0 = new Object()
            realData0['label'] = this.tableHead[i].label + '实测值1'
            realData0['data'] = ''
            tempresult['children'].push(realData0)
            var yuzhiData0 = new Object()
            yuzhiData0['label'] = this.tableHead[i].label + '-阈值1(≥)'
            if (this.yuzhiMap[this.tableHead[i].id] != null && this.yuzhiMap[this.tableHead[i].id] != undefined && (this.yuzhiMap[this.tableHead[i].id]).length >= 1)
              yuzhiData0['data'] = this.yuzhiMap[this.tableHead[i].id][0]
            else yuzhiData0['data'] = ''
            tempresult['children'].push(yuzhiData0)
            var realData1 = new Object()
            realData1['label'] = this.tableHead[i].label + '实测值2'
            realData1['data'] = ''
            tempresult['children'].push(realData1)
            var yuzhiData1 = new Object()
            yuzhiData1['label'] = this.tableHead[i].label + '-阈值2(≤)'
            if (this.yuzhiMap[this.tableHead[i].id] != null && this.yuzhiMap[this.tableHead[i].id] != undefined && (this.yuzhiMap[this.tableHead[i].id]).length >= 2)
              yuzhiData1['data'] = this.yuzhiMap[this.tableHead[i].id][1]
            else yuzhiData1['data'] = ''
            tempresult['children'].push(yuzhiData1)
            //存放id方便存储进数据库
          }
          if (this.tableHead[i].fuhao === 5) {
            var realData0 = new Object()
            realData0['label'] = this.tableHead[i].label + '实测值1'
            realData0['data'] = ''
            tempresult['children'].push(realData0)
            var yuzhiData0 = new Object()
            yuzhiData0['label'] = this.tableHead[i].label + '-阈值1(≤)'
            if (this.yuzhiMap[this.tableHead[i].id] != null && this.yuzhiMap[this.tableHead[i].id] != undefined && (this.yuzhiMap[this.tableHead[i].id]).length >= 1)
              yuzhiData0['data'] = this.yuzhiMap[this.tableHead[i].id][0]
            else yuzhiData0['data'] = ''
            tempresult['children'].push(yuzhiData0)
            var realData1 = new Object()
            realData1['label'] = this.tableHead[i].label + '实测值2'
            realData1['data'] = ''
            tempresult['children'].push(realData1)
            var yuzhiData1 = new Object()
            yuzhiData1['label'] = this.tableHead[i].label + '-阈值2(≥)'
            if (this.yuzhiMap[this.tableHead[i].id] != null && this.yuzhiMap[this.tableHead[i].id] != undefined && (this.yuzhiMap[this.tableHead[i].id]).length >= 2)
              yuzhiData1['data'] = this.yuzhiMap[this.tableHead[i].id][1]
            else yuzhiData1['data'] = ''
            tempresult['children'].push(yuzhiData1)
            //存放id方便存储进数据库
          }
          if (this.tableHead[i].fuhao === 6) {
            var realData0 = new Object()
            realData0['label'] = this.tableHead[i].label + '实测值1'
            realData0['data'] = ''
            tempresult['children'].push(realData0)
            var yuzhiData0 = new Object()
            yuzhiData0['label'] = this.tableHead[i].label + '-阈值1(≥)'
            if (this.yuzhiMap[this.tableHead[i].id] != null && this.yuzhiMap[this.tableHead[i].id] != undefined && (this.yuzhiMap[this.tableHead[i].id]).length >= 1)
              yuzhiData0['data'] = this.yuzhiMap[this.tableHead[i].id][0]
            else yuzhiData0['data'] = ''
            tempresult['children'].push(yuzhiData0)
            var yuzhiData1 = new Object()
            yuzhiData1['label'] = this.tableHead[i].label + '-阈值2(≤)'
            if (this.yuzhiMap[this.tableHead[i].id] != null && this.yuzhiMap[this.tableHead[i].id] != undefined && (this.yuzhiMap[this.tableHead[i].id]).length >= 2)
              yuzhiData1['data'] = this.yuzhiMap[this.tableHead[i].id][1]
            else yuzhiData1['data'] = ''
            tempresult['children'].push(yuzhiData1)
            //存放id方便存储进数据库
          }
          tempresult['type'] = this.tableHead[i].type
        }
        tempresult['itemIndexId'] = this.tableHead[i].id
        tempresult['fuhao'] = this.tableHead[i].fuhao
        this.tempData.push(tempresult)
      }
      this.adddDialogVisual = true
    },
    editTableData() {
      this.adddDialogVisual = false
      if (this.selectedData.length < 1) {
        this.$message({
          message: '请选择要编辑的信息',
          type: 'warning'
        });
        return;
      }
      if (this.selectedData.length > 1) {
        this.$message({
          message: '只能编辑一条',
          type: 'warning'
        });
        return;
      }
      this.tempData = []
      var tempresult = {}
      this.editData = {}
      this.editData = this.selectedData[0]
      this.oldEditData = JSON.parse(JSON.stringify(this.selectedData[0]))
      for (var i = 0; i < this.tableHead.length; i++) {
        tempresult = new Object()
        if (this.tableHead[i].label === '日期' || this.tableHead[i].label === '测点位置' || this.tableHead[i].label === '安全状态判断' || this.tableHead[i].label === '组合权重' || this.tableHead[i].label === '其他要求') {
          tempresult['data'] = this.selectedData[0][this.tableHead[i].prop]
          tempresult['label'] = this.tableHead[i].label
          tempresult['prop'] = this.tableHead[i].prop
        }
        else if (this.tableHead[i].fuhao === -1) {
          if (this.selectedData[0][this.tableHead[i].prop] === undefined || this.selectedData[0][this.tableHead[i].prop] === null) {
            tempresult['data'] = ''
          }
          else {
            tempresult['data'] = this.selectedData[0][this.tableHead[i].prop]
          }
          tempresult['label'] = this.tableHead[i].label
          tempresult['prop'] = this.tableHead[i].prop
        }
        else {
          tempresult['children'] = []
          //这里的都是有实测值和阈值的
          // if (this.tableHead[i].type === 1){
          //   var realData = new Object()
          //   realData['label'] = this.tableHead[i].label + '实测值'
          //   realData['data'] = this.selectedData[0].children[this.tableHead[i].prop + '_real_0'] === null || this.selectedData[0].children[this.tableHead[i].prop + '_real_0'] === undefined ? '' : this.selectedData[0].children[this.tableHead[i].prop + '_real_0']
          //   tempresult['children'].push(realData)
          //   var yuzhiData = new Object()
          //   yuzhiData['label'] = this.tableHead[i].label + '-阈值'
          //   yuzhiData['data'] = this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_0'] === null || this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_0'] === undefined ? '' : this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_0']
          //   tempresult['children'].push(yuzhiData)
          // }
          // else {
          if (this.tableHead[i].fuhao === 0) {
            var realData = new Object()
            realData['label'] = this.tableHead[i].label + '实测值'
            realData['data'] = this.selectedData[0].children[this.tableHead[i].prop + '_real_0'] === null || this.selectedData[0].children[this.tableHead[i].prop + '_real_0'] === undefined ? '' : this.selectedData[0].children[this.tableHead[i].prop + '_real_0']
            tempresult['children'].push(realData)
            var yuzhiData = new Object()
            yuzhiData['label'] = this.tableHead[i].label + '-阈值(≥)'
            yuzhiData['data'] = this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_0'] === null || this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_0'] === undefined ? '' : this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_0']
            tempresult['children'].push(yuzhiData)
            //存放id方便存储进数据库
          }
          if (this.tableHead[i].fuhao === 7) {
            var realData = new Object()
            realData['label'] = this.tableHead[i].label + '实测值'
            realData['data'] = this.selectedData[0].children[this.tableHead[i].prop + '_real_0'] === null || this.selectedData[0].children[this.tableHead[i].prop + '_real_0'] === undefined ? '' : this.selectedData[0].children[this.tableHead[i].prop + '_real_0']
            tempresult['children'].push(realData)
            var yuzhiData = new Object()
            yuzhiData['label'] = this.tableHead[i].label + '-阈值(=)'
            yuzhiData['data'] = this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_0'] === null || this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_0'] === undefined ? '' : this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_0']
            tempresult['children'].push(yuzhiData)
            //存放id方便存储进数据库
          }
          if (this.tableHead[i].fuhao === 1) {
            var realData = new Object()
            realData['label'] = this.tableHead[i].label + '实测值'
            realData['data'] = this.selectedData[0].children[this.tableHead[i].prop + '_real_0'] === null || this.selectedData[0].children[this.tableHead[i].prop + '_real_0'] === undefined ? '' : this.selectedData[0].children[this.tableHead[i].prop + '_real_0']
            tempresult['children'].push(realData)
            var yuzhiData = new Object()
            yuzhiData['label'] = this.tableHead[i].label + '-阈值(≤)'
            yuzhiData['data'] = this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_0'] === null || this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_0'] === undefined ? '' : this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_0']
            tempresult['children'].push(yuzhiData)
            //存放id方便存储进数据库
          }
          if (this.tableHead[i].fuhao === 2) {
            var realData0 = new Object()
            realData0['label'] = this.tableHead[i].label + '实测值1'
            realData0['data'] = this.selectedData[0].children[this.tableHead[i].prop + '_real_0'] === null || this.selectedData[0].children[this.tableHead[i].prop + '_real_0'] === undefined ? '' : this.selectedData[0].children[this.tableHead[i].prop + '_real_0']
            tempresult['children'].push(realData0)
            var yuzhiData0 = new Object()
            yuzhiData0['label'] = this.tableHead[i].label + '-阈值1(≤)'
            yuzhiData0['data'] = this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_0'] === null || this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_0'] === undefined ? '' : this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_0']
            tempresult['children'].push(yuzhiData0)
            var realData1 = new Object()
            realData1['label'] = this.tableHead[i].label + '实测值2'
            realData1['data'] = this.selectedData[0].children[this.tableHead[i].prop + '_real_1'] === null || this.selectedData[0].children[this.tableHead[i].prop + '_real_1'] === undefined ? '' : this.selectedData[0].children[this.tableHead[i].prop + '_real_1']
            tempresult['children'].push(realData1)
            var yuzhiData1 = new Object()
            yuzhiData1['label'] = this.tableHead[i].label + '-阈值2(≤)'
            yuzhiData1['data'] = this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_1'] === null || this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_1'] === undefined ? '' : this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_1']
            tempresult['children'].push(yuzhiData1)
            //存放id方便存储进数据库
          }
          if (this.tableHead[i].fuhao === 3) {
            var realData0 = new Object()
            realData0['label'] = this.tableHead[i].label + '实测值1'
            realData0['data'] = this.selectedData[0].children[this.tableHead[i].prop + '_real_0'] === null || this.selectedData[0].children[this.tableHead[i].prop + '_real_0'] === undefined ? '' : this.selectedData[0].children[this.tableHead[i].prop + '_real_0']
            tempresult['children'].push(realData0)
            var yuzhiData0 = new Object()
            yuzhiData0['label'] = this.tableHead[i].label + '-阈值1(≥)'
            yuzhiData0['data'] = this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_0'] === null || this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_0'] === undefined ? '' : this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_0']
            tempresult['children'].push(yuzhiData0)
            var realData1 = new Object()
            realData1['label'] = this.tableHead[i].label + '实测值2'
            realData1['data'] = this.selectedData[0].children[this.tableHead[i].prop + '_real_1'] === null || this.selectedData[0].children[this.tableHead[i].prop + '_real_1'] === undefined ? '' : this.selectedData[0].children[this.tableHead[i].prop + '_real_1']
            tempresult['children'].push(realData1)
            var yuzhiData1 = new Object()
            yuzhiData1['label'] = this.tableHead[i].label + '-阈值2(≥)'
            yuzhiData1['data'] = this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_1'] === null || this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_1'] === undefined ? '' : this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_1']
            tempresult['children'].push(yuzhiData1)
            //存放id方便存储进数据库
          }
          if (this.tableHead[i].fuhao === 4) {
            var realData0 = new Object()
            realData0['label'] = this.tableHead[i].label + '实测值1'
            realData0['data'] = this.selectedData[0].children[this.tableHead[i].prop + '_real_0'] === null || this.selectedData[0].children[this.tableHead[i].prop + '_real_0'] === undefined ? '' : this.selectedData[0].children[this.tableHead[i].prop + '_real_0']
            tempresult['children'].push(realData0)
            var yuzhiData0 = new Object()
            yuzhiData0['label'] = this.tableHead[i].label + '-阈值1(≥)'
            yuzhiData0['data'] = this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_0'] === null || this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_0'] === undefined ? '' : this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_0']
            tempresult['children'].push(yuzhiData0)
            var realData1 = new Object()
            realData1['label'] = this.tableHead[i].label + '实测值2'
            realData1['data'] = this.selectedData[0].children[this.tableHead[i].prop + '_real_1'] === null || this.selectedData[0].children[this.tableHead[i].prop + '_real_1'] === undefined ? '' : this.selectedData[0].children[this.tableHead[i].prop + '_real_1']
            tempresult['children'].push(realData1)
            var yuzhiData1 = new Object()
            yuzhiData1['label'] = this.tableHead[i].label + '-阈值2(≤)'
            yuzhiData1['data'] = this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_1'] === null || this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_1'] === undefined ? '' : this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_1']
            tempresult['children'].push(yuzhiData1)
            //存放id方便存储进数据库
          }
          if (this.tableHead[i].fuhao === 5) {
            var realData0 = new Object()
            realData0['label'] = this.tableHead[i].label + '实测值1'
            realData0['data'] = this.selectedData[0].children[this.tableHead[i].prop + '_real_0'] === null || this.selectedData[0].children[this.tableHead[i].prop + '_real_0'] === undefined ? '' : this.selectedData[0].children[this.tableHead[i].prop + '_real_0']
            tempresult['children'].push(realData0)
            var yuzhiData0 = new Object()
            yuzhiData0['label'] = this.tableHead[i].label + '-阈值1(≤)'
            yuzhiData0['data'] = this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_0'] === null || this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_0'] === undefined ? '' : this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_0']
            tempresult['children'].push(yuzhiData0)
            var realData1 = new Object()
            realData1['label'] = this.tableHead[i].label + '实测值2'
            realData1['data'] = this.selectedData[0].children[this.tableHead[i].prop + '_real_1'] === null || this.selectedData[0].children[this.tableHead[i].prop + '_real_1'] === undefined ? '' : this.selectedData[0].children[this.tableHead[i].prop + '_real_1']
            tempresult['children'].push(realData1)
            var yuzhiData1 = new Object()
            yuzhiData1['label'] = this.tableHead[i].label + '-阈值2(≥)'
            yuzhiData1['data'] = this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_1'] === null || this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_1'] === undefined ? '' : this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_1']
            tempresult['children'].push(yuzhiData1)
            //存放id方便存储进数据库
          }
          if (this.tableHead[i].fuhao === 6) {
            var realData0 = new Object()
            realData0['label'] = this.tableHead[i].label + '实测值1'
            realData0['data'] = this.selectedData[0].children[this.tableHead[i].prop + '_real_0'] === null || this.selectedData[0].children[this.tableHead[i].prop + '_real_0'] === undefined ? '' : this.selectedData[0].children[this.tableHead[i].prop + '_real_0']
            tempresult['children'].push(realData0)
            var yuzhiData0 = new Object()
            yuzhiData0['label'] = this.tableHead[i].label + '-阈值1(≥)'
            yuzhiData0['data'] = this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_0'] === null || this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_0'] === undefined ? '' : this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_0']
            tempresult['children'].push(yuzhiData0)
            var yuzhiData1 = new Object()
            yuzhiData1['label'] = this.tableHead[i].label + '-阈值2(≤)'
            yuzhiData1['data'] = this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_1'] === null || this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_1'] === undefined ? '' : this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_1']
            tempresult['children'].push(yuzhiData1)
            //存放id方便存储进数据库
          }
          // }

          tempresult['type'] = this.tableHead[i].type

        }
        tempresult['itemIndexId'] = this.tableHead[i].id
        tempresult['fuhao'] = this.tableHead[i].fuhao
        this.tempData.push(tempresult)
      }
      this.editDialogVisual = true
    },
    deleteTableData() {
      if (this.selectedData.length < 1) {
        this.$message({
          message: '请选择要删除的信息',
          type: 'warning'
        });
        return;
      }
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //用户点击了确定,要删除信息
        var entity = new Object()
        entity['list'] = this.selectedData
        entity['firstIndexId'] = this.queyEntity.firstIndexId
        entity['secondIndexId'] = this.queyEntity.secondIndexId
        entity['itemIndexId'] = this.queyEntity.itemIndexId
        //将ids发送给后端，进行删除操作，删除完将tableData重新赋值
        //这里手动实现删除
        monitorDeleteData(entity).then(response => {
          if (response.code === 200){
            var currentPage = (this.pages.currentPage != 1 && this.tableData.length - this.selectedData.length <= 0) ? this.pages.currentPage - 1 : this.pages.currentPage
            monitorTable(this.queyEntity, currentPage, this.pages.size).then(response => {
              this.tableData = response.data.tableData
              if (!(this.tableData === null || this.tableData.size === 0)) {
                //转换tableData
                for (var i = 0; i < this.tableData.length; i++) {
                  this.transformChildren(this.tableData[i])
                }
              }
              this.pages.currentPage = Number.parseInt(response.data.current)
              this.pages.total = Number.parseInt(response.data.total)
              this.selectedData = []
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
            })
          }
          else {
            this.$message.error("删除失败");
          }
          this.selectedData = []
        })

      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
        
      });
    },
    handleSelectionChange(data) {
      this.selectedData = data
    },
    cancelAdd() {
      this.adddDialogVisual = false
    },
    submitAdd() {
      this.addData['children'] = []
      //如果是测点编号等那么就不需要加入children集合中
      for (var i = 0; i < this.tempData.length; i++) {
        if (this.tempData[i].label === '日期' || this.tempData[i].label === '测点位置' || this.tempData[i].label === '安全状态判断' || this.tempData[i].label === '组合权重' || this.tempData[i].label === '其他要求' || this.tempData[i].fuhao === -1) {
          this.addData[this.tempData[i].prop] = this.tempData[i].data
        }
        //剩余的放入到children集合之中,每一个是一个对象，包含有itemIndexId,,yuzhiData
        else {
          //遍历children数组
          var realDataList = []
          var yuzhiDataList = []
          for (var j = 0; j < this.tempData[i].children.length; j++) {
            if (this.tempData[i].children[j].label.indexOf("实测值") != -1)
              realDataList.push(this.tempData[i].children[j].data)
            if (this.tempData[i].children[j].label.indexOf("阈值") != -1)
              yuzhiDataList.push(this.tempData[i].children[j].data)
          }
          var data = new Object()
          data['itemIndexId'] = this.tempData[i].itemIndexId
          data['realData'] = realDataList
          data['yuzhiData'] = yuzhiDataList
          this.addData['children'].push(data)
        }
      }
      this.addData["suidaoId"] = this.queyEntity["suidaoId"]
      this.addData["firstIndexId"] = this.queyEntity["firstIndexId"]
      this.addData["secondIndexId"] = this.queyEntity["secondIndexId"]
      this.addData["itemIndexId"] = this.queyEntity["itemIndexId"]
      //发送给后端addData进行添加,将返回的信息添加到tableData尾部
      addmonitorData(this.addData, this.pages.currentPage, this.pages.size).then(response => {
        if (response.code === 200) {
          this.tableData = response.data.tableData
          //转换tableData
          if (this.tableData != null && this.tableData != undefined && this.tableData.length > 0) {
            for (var i = 0; i < this.tableData.length; i++) {
              this.transformChildren(this.tableData[i])
            }
          }
          // this.transformChildren(this.addData)
          // this.tableData.push(this.addData)
          this.pages.total = Number.parseInt(response.data.total)
          this.pages.currentPage = Number.parseInt(response.data.current)
          this.$message({
            message: '添加成功!',
            type: 'success'
          });
          this.adddDialogVisual = false
        }
        else {
          this.$message({
            message: '添加失败!',
            type: 'warning'
          })
        }
      })

    },
    cancelEdit() {
      for (var i = 0; i < this.tableData.length; i++) {
        //找到修改的数据的id
        if (this.tableData[i].id === this.oldEditData.id) {
          this.tableData[i] = this.oldEditData
          break;
        }
      }
      this.selectedData = []
      this.editDialogVisual = false
    },
    submitEdit() {
      this.editData['children'] = []
      //将tempEditData中的数据加入到eidtData
      for (var i = 0; i < this.tempData.length; i++) {
        if (this.tempData[i].label === '日期' || this.tempData[i].label === '测点位置' || this.tempData[i].label === '安全状态判断' || this.tempData[i].label === '组合权重' || this.tempData[i].label === '其他要求' || this.tempData[i].fuhao === -1) {
          this.editData[this.tempData[i].prop] = this.tempData[i].data
        }
        //剩余的放入到children集合之中,每一个是一个对象，包含有itemIndexId,,yuzhiData
        else {
          //遍历children数组
          var realDataList = []
          var yuzhiDataList = []
          for (var j = 0; j < this.tempData[i].children.length; j++) {
            if (this.tempData[i].children[j].label.indexOf("实测值") != -1)
              realDataList.push(this.tempData[i].children[j].data)
            if (this.tempData[i].children[j].label.indexOf("阈值") != -1)
              yuzhiDataList.push(this.tempData[i].children[j].data)
          }
          var data = new Object()
          data['itemIndexId'] = this.tempData[i].itemIndexId
          data['realData'] = realDataList
          data['yuzhiData'] = yuzhiDataList
          this.editData['children'].push(data)
        }
      }
      //调用api将editData推上去
      monitorEditData(this.editData).then(response => {
        if (response.code === 200) {
          this.transformChildren(this.editData)
          for (var j = 0; j < this.tableData.length; j++) {
            if (this.tableData[j].id === this.editData.id) {
              this.tableData[j] = this.editData
            }
          }
          this.$message({
            message: '修改成功',
            type: 'success'
          });
          this.editDialogVisual = false
        }
        else {
          this.$message.error("修改数据失败")
        }
      })
    },
    //分页操作
    handleCurrentChange(currentPage) {
      monitorPage(this.queyEntity, currentPage, this.pages.size).then(response => {
        this.tableData = response.data.tableData
        if (this.tableData != undefined && this.tableData != null && this.tableData.length > 0) {
          for (var i = 0; i < this.tableData.length; i++) {
            if (this.tableData[i].rq != undefined && this.tableData[i].rq != undefined && this.tableData[i].rq != "") {
              this.tableData[i].rq = this.$moment(this.tableData[i].rq).format('YYYY-MM-DD HH:mm:ss')
            }
          }
        }

        this.pages.total = parseInt(response.data.total)
        this.pages.current = parseInt(response.data.current)
        if (this.tableData != null && this.tableData != undefined && this.tableData.length > 0) {
          for (var i = 0; i < this.tableData.length; i++) {
            this.transformChildren(this.tableData[i])
          }
        }
      })
    },
    getSafe() {
      //flag 0 表示不安全，1表示安全
      this.flag = -1
      var tempFlag = -1

      //循环遍历tempData
      for (var i = 0; i < this.tempData.length; i++) {
        if (this.tempData[i]['children'] === null || this.tempData[i]['children'] === undefined)
          continue
        //符号6也就是测试值数量和阈值数量不同,那么不需要遍历每一个children,直接计算
        if (this.tempData[i].fuhao === 6) {
          //第一个是实测值，后面两个是阈值，并且第一个>= 第二个<=
          if (this.tempData[i].children[0].data === '' || this.tempData[i].children[1].data === '' || this.tempData[i].children[2].data === '') {
            this.$message.error('有实测值或阈值没有输入');
            return;
          }
          var yuzhi1 = parseFloat(this.tempData[i].children[1].data)
          var yuzhi2 = parseFloat(this.tempData[i].children[2].data)
          var real = parseFloat(this.tempData[i].children[0].data)
          if (real < yuzhi1 || real > yuzhi2) {
            this.$message({
              message: this.tempData[i].children[0].label + '输入不符合阈值情况',
              type: 'warning'
            });
            this.flag = 0
            return;
          }
        }
        else {
          for (var j = 1; j < this.tempData[i].children.length; j++) {
            //找到阈值，和上面一个实际值进行比较
            if (this.tempData[i].children[j].label.indexOf("阈值") != -1) {
              //如果是数值比较
              if (this.tempData[i].type === 0) {
                if (this.tempData[i].children[j].data === '' || this.tempData[i].children[j - 1].data === '') {
                  this.$message.error('有实测值或阈值没有输入');
                  return;
                }
                //判断当前阈值是大于等于还是小于等于
                if (this.tempData[i].children[j].label.indexOf('(≤)') != -1) {
                  var yuzhi = parseFloat(this.tempData[i].children[j].data)
                  var real = parseFloat(this.tempData[i].children[j - 1].data)
                  if (real > yuzhi) {
                    this.$message({
                      message: this.tempData[i].children[j - 1].label + '输入不符合阈值情况',
                      type: 'warning'
                    });
                    this.flag = 0
                    return;
                  }
                }
                else if (this.tempData[i].children[j].label.indexOf('(≥)') != -1) {
                  var yuzhi = parseFloat(this.tempData[i].children[j].data)
                  var real = parseFloat(this.tempData[i].children[j - 1].data)
                  if (real < yuzhi) {
                    this.$message({
                      message: this.tempData[i].children[j - 1].label + '输入不符合阈值情况',
                      type: 'warning'
                    });
                    this.flag = 0
                    return;
                  }
                }
                else if (this.tempData[i].children[j].label.indexOf('(=)') != -1) {
                  var yuzhi = parseFloat(this.tempData[i].children[j].data)
                  var real = parseFloat(this.tempData[i].children[j - 1].data)
                  if (real != yuzhi) {
                    this.$message({
                      message: this.tempData[i].children[j - 1].label + '输入不符合阈值情况',
                      type: 'warning'
                    });
                    this.flag = 0
                    return;
                  }
                }
                else {
                  this.$message.error('计算出错!');
                  return;
                }
              }
              else {
                //此时有一个阈值，
                //是否比较
                if (this.tempData[i].children[j].data === '' || this.tempData[i].children[j - 1].data === '') {
                  this.$message.error('有实测值或阈值没有输入');
                  return;
                }
                if (this.tempData[i].children[j].data != this.tempData[i].children[j - 1].data) {
                  this.$message({
                    message: this.tempData[i].children[j - 1].label + '输入不符合阈值情况',
                    type: 'warning'
                  });
                  this.flag = 0
                  return
                }
              }
            }
          }
        }
      }
      tempFlag = 1
      this.flag = tempFlag
    }
  },
  watch: {
    flag: {
      handler(newVal, oldVal) {
        if (newVal === 0) {
          for (var i = 0; i < this.tempData.length; i++) {
            if (this.tempData[i].label === '安全状态判断') {
              this.tempData[i].data = '不安全'
              return
            }
          }

        }
        if (newVal === 1) {
          for (var i = 0; i < this.tempData.length; i++) {
            if (this.tempData[i].label === '安全状态判断') {
              this.tempData[i].data = '安全'
              return
            }
          }

        }
      }
    }
  }
}


</script>

<style></style>