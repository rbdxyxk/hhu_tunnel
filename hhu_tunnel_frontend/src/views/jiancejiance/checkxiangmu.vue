<template>
  <div>
    <el-row :gutter="10">
      <el-col :span="6" style="border-style: solid;" >
        <el-scrollbar style="height: 100%;">
        <el-tree ref="treeData" :data="checkItem" :props="defaultProps" node-key="id" show-checkbox
          :check-on-click-node="true" @node-click="handleNodeClick" :expand-on-click-node="false"
          :highlight-current="true" >
        </el-tree>
        </el-scrollbar>
      </el-col>
      <el-col :span="18">
        <div v-if="isTableVisual">
          <el-button type="primary" plain @click="addTableData">添加</el-button>
          <el-button type="primary" plain @click="editTableData">编辑</el-button>
          <el-button type="primary" plain @click="deleteTableData">删除</el-button>
          <el-table ref="tab" :data="tableData" border class="tab-table" @selection-change="handleSelectionChange"
            :key="Math.random()" style="margin-top: 1rem;height: 36rem;" :header-cell-style="{ textAlign: 'center' }"
            :cell-style="{ textAlign: 'center' }" height="800">
            <el-table-column type="selection" width="55" :fixed="true">
            </el-table-column>
            <!-- 动态表头 -->
            <div v-for="(item, index) in tableHead" :key="index">
              <el-table-column
                v-if="item.label === '日期' || item.label === '测点位置' || item.label === '安全状态判断' || item.label === '组合权重' || item.label === '其他要求'"
                :label="item.label" :prop="item.prop" width="100">

              </el-table-column>

              <el-table-column v-else-if="item.label === '防水卷材类型'" :label="item.label" :prop="item.prop" width="283">
                <template slot-scope="scope">
                  <div v-if="scope.row.fsjclx === 0 || scope.row.fsjclx === '0'">
                    <span>弹性体改性沥青防水卷材</span>
                  </div>
                  <div v-if="scope.row.fsjclx === 1 || scope.row.fsjclx === '1'">
                    <span>改性沥青聚乙烯胎防水卷材</span>
                  </div>
                  <div v-if="scope.row.fsjclx === 2 || scope.row.fsjclx === '2'">
                    <span>自黏聚合物改性沥青防水卷材(聚酯毡胎体)</span>
                  </div>
                  <div v-if="scope.row.fsjclx === 3 || scope.row.fsjclx === '3'">
                    <span>自黏聚合物改性沥青防水卷材(无胎体)</span>
                  </div>
                  <div v-if="scope.row.fsjclx === 4 || scope.row.fsjclx === '4'">
                    <span>三元乙丙橡胶防水卷材(胶黏剂)</span>
                  </div>
                  <div v-if="scope.row.fsjclx === 5 || scope.row.fsjclx === '5'">
                    <span>三元乙丙橡胶防水卷材(胶黏带)</span>
                  </div>
                  <div v-if="scope.row.fsjclx === 6 || scope.row.fsjclx === '6'">
                    <span>聚氯乙烯防水卷材(单焊缝)</span>
                  </div>
                  <div v-if="scope.row.fsjclx === 7 || scope.row.fsjclx === '7'">
                    <span>聚氯乙烯防水卷材(双焊缝)</span>
                  </div>
                  <div v-if="scope.row.fsjclx === 8 || scope.row.fsjclx === '8'">
                    <span>聚氯乙烯防水卷材(胶粘剂)</span>
                  </div>
                  <div v-if="scope.row.fsjclx === 9 || scope.row.fsjclx === '9'">
                    <span>聚乙烯丙纶复合防水卷材-卷材</span>
                  </div>
                  <div v-if="scope.row.fsjclx === 10 || scope.row.fsjclx === '10'">
                    <span>聚乙烯丙纶复合防水卷材-黏结料</span>
                  </div>
                  <div v-if="scope.row.fsjclx === 11 || scope.row.fsjclx === '11'">
                    <span>聚乙烯丙纶复合防水卷材-芯材厚度</span>
                  </div>
                  <div v-if="scope.row.fsjclx === 12 || scope.row.fsjclx === '12'">
                    <span>高分子自粘胶膜防水卷材(自黏胶)</span>
                  </div>
                  <div v-if="scope.row.fsjclx === 13 || scope.row.fsjclx === '13'">
                    <span>高分子自粘胶膜防水卷材(黏胶带)</span>
                  </div>
                </template>
              </el-table-column>
              <!-- children[0].item -->
              <!-- item当前表头项 -->
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

    <!-- 动态预测组件
    <el-row v-show="predictVisual">
      <dynamicPredictVue></dynamicPredictVue>
    </el-row> -->
    <el-dialog title="添加" :visible.sync="adddDialogVisual" width="35%" :show-close="false">
      <el-form ref="addform" :model="addData" label-width="10rem">

        <div v-for="(item, index) in tempData" :key="index">
          <el-form-item v-if="item.label === '日期'" label="日期" style="margin-bottom: 0.6rem;">
            <el-date-picker v-model="item.data" type="datetime" placeholder="选择日期" value-format="yyyy-MM-dd HH:mm:ss">
            </el-date-picker>
          </el-form-item>
          <el-form-item
            v-if="item.label === '测点位置' || item.label === '安全状态判断' || item.label === '组合权重' || item.label === '其他要求'"
            :label="item.label" style="margin-bottom: 0.6rem;">
            <el-input v-model="item.data"></el-input>
          </el-form-item>
          <div v-else-if="item.type != 1 && item.fuhao === -1">
          <el-form-item  :label="item.label" style="margin-bottom: 0.6rem;">
            <el-input v-model="item.data"></el-input>
          </el-form-item>
        </div>  
          <!-- 防水卷材，他接宽度 -->
          <el-form-item
            v-else-if="item.label === '防水卷材类型' && (queyEntity.itemIndexId === '4' || queyEntity.itemIndexId === '25' || queyEntity.itemIndexId === '36' || queyEntity.itemIndexId === 4 || queyEntity.itemIndexId === 25 || queyEntity.itemIndexId === 36)"
            :label="item.label" style="margin-bottom: 0.6rem;">
            <el-select v-model="item.data" placeholder="选择防水卷材类型" style="width: 20rem;" @change="listenFsjc">
              <el-option v-for="item2 in fangshuijuancai" :key="item2.value" :label="item2.label" :value="item2.value">
              </el-option>
            </el-select>
          </el-form-item>
          <!-- 防水卷材粘接质量 -->
          <el-form-item
            v-else-if="item.label === '防水卷材类型' && (queyEntity.itemIndexId === '5' || queyEntity.itemIndexId === '26' || queyEntity.itemIndexId === '37' || queyEntity.itemIndexId === 5 || queyEntity.itemIndexId === 26 || queyEntity.itemIndexId === 37)"
            :label="item.label" style="margin-bottom: 0.6rem;">
            <el-select v-model="item.data" placeholder="选择防水卷材类型" style="width: 20rem;" @change="listenFsjc">
              <el-option v-for="item2 in fangshuijuancai_2" :key="item2.value" :label="item2.label" :value="item2.value">
              </el-option>
            </el-select>
          </el-form-item>
          <div v-else-if="item.label != '日期'" v-for="(item1, index1) in item.children" :key="index1">
            <el-form-item v-if="item.type === 1 || item.type === '1'" :label="item1.label" style="margin-bottom: 0.6rem;">
              <el-select v-model="item1.data" placeholder="选择是或者否" style="width: 20rem;">
                <el-option v-for="item3 in yesOrNo" :key="item3.valx1ue" :label="item3.label" :value="item3.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item v-else :label="item1.label" style="margin-bottom: 0.6rem;">
              <el-input v-model="item1.data"></el-input>
            </el-form-item>
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
      <el-form ref="edditform" :model="editData" label-width="10rem">
        <div v-for="(item, index) in tempData" :key="index">
          <el-form-item v-if="item.label === '日期'" label="日期" style="margin-bottom: 0.6rem;">
            <el-date-picker v-model="item.data" type="datetime" placeholder="选择日期" value-format="yyyy-MM-dd HH:mm:ss">
            </el-date-picker>
          </el-form-item>
          <el-form-item
            v-if="item.label === '测点位置' || item.label === '安全状态判断' || item.label === '组合权重' || item.label === '其他要求'"
            :label="item.label" style="margin-bottom: 0.6rem;">
            <el-input v-model="item.data"></el-input>
          </el-form-item>
          <!-- 防水卷材，他接宽度 -->
          <el-form-item
            v-else-if="item.label === '防水卷材类型' && (queyEntity.itemIndexId === '4' || queyEntity.itemIndexId === '25' || queyEntity.itemIndexId === '36' || queyEntity.itemIndexId === 4 || queyEntity.itemIndexId === 25 || queyEntity.itemIndexId === 36)"
            :label="item.label" style="margin-bottom: 0.6rem;">
            <el-select v-model="item.data" placeholder="选择防水卷材类型" style="width: 20rem;" @change="listenFsjc">
              <el-option v-for="item2 in fangshuijuancai" :key="item2.value" :label="item2.label" :value="item2.value">
              </el-option>
            </el-select>
          </el-form-item>
          <!-- 防水卷材粘接质量 -->
          <el-form-item
            v-else-if="item.label === '防水卷材类型' && (queyEntity.itemIndexId === '5' || queyEntity.itemIndexId === '26' || queyEntity.itemIndexId === '37' || queyEntity.itemIndexId === 5 || queyEntity.itemIndexId === 26 || queyEntity.itemIndexId === 37)"
            :label="item.label" style="margin-bottom: 0.6rem;">
            <el-select v-model="item.data" placeholder="选择防水卷材类型" style="width: 20rem;" @change="listenFsjc">
              <el-option v-for="item2 in fangshuijuancai_2" :key="item2.value" :label="item2.label" :value="item2.value">
              </el-option>
            </el-select>
          </el-form-item>
          <div v-else-if="item.label != '日期'" v-for="(item1, index1) in item.children" :key="index1">
            <el-form-item v-if="item.type === 1 || item.type === '1'" :label="item1.label" style="margin-bottom: 0.6rem;">
              <el-select v-model="item1.data" placeholder="选择是或者否" style="width: 20rem;">
                <el-option v-for="item3 in yesOrNo" :key="item3.value" :label="item3.label" :value="item3.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item v-else :label="item1.label" style="margin-bottom: 0.6rem;">
              <el-input v-model="item1.data"></el-input>
            </el-form-item>
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
import preventBack from 'vue-prevent-browser-back';
import dynamicPredictVue from './dynamicPredict.vue';
import { jianceDict, jianceTable, addJianceData, jiancePage, jianceEditData, jianceDeleteData, jianceGetYuzhi } from '@/api/monitorAndCheck/check'
export default {
  components: {
    dynamicPredictVue
  },
  data() {
    return {
      childrenIndex: 0,
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
      queyEntity: {},
      isTableVisual: false,
      tableData: [],
      tableHead: [],
      selectedData: [],
      adddDialogVisual: false,
      editDialogVisual: false,
      addData: {},
      editData: {},
      //用tempData每一项是个对象,label对应中文,prop对应英文属性,data为输入的值,children保存的是实测值和阈值
      tempData: [],
      oldEditData: {},
      tempEditData: [],
      //保存实测值和阈值对应表头
      realAndyuzhi: [],
      pages: {
        //每页展示条数
        size: 10,
        //总共多少条
        total: 10,
        currentPage: 1
      },
      adddDialogVisual: false,
      editDialogVisual: false,
      fangshuijuancai: [
        {
          value: 0,
          label: '弹性体改性沥青防水卷材',
        },
        {
          value: 1,
          label: '改性沥青聚乙烯胎防水卷材',
        },
        {
          value: 2,
          label: '自黏聚合物改性沥青防水卷材(聚酯毡胎体)',
        },
        {
          value: 3,
          label: '自黏聚合物改性沥青防水卷材(无胎体)',
        },
        {
          value: 4,
          label: '三元乙丙橡胶防水卷材(胶黏剂)',
        },
        {
          value: 5,
          label: '三元乙丙橡胶防水卷材(胶黏带)',
        },
        {
          value: 6,
          label: '聚氯乙烯防水卷材(单焊缝)',
        },
        {
          value: 7,
          label: '聚氯乙烯防水卷材(双焊缝)',
        },
        {
          value: 8,
          label: '聚氯乙烯防水卷材(胶粘剂)',
        },
        {
          value: 9,
          label: '聚乙烯丙纶复合防水卷材-卷材',
        },
        {
          value: 10,
          label: '聚乙烯丙纶复合防水卷材-黏结料',
        },
        {
          value: 11,
          label: '聚乙烯丙纶复合防水卷材-芯材厚度',
        },
        {
          value: 12,
          label: '高分子自粘胶膜防水卷材(自黏胶)',
        },
        {
          value: 13,
          label: '高分子自粘胶膜防水卷材(黏胶带)',
        },

      ],
      fangshuijuancai_2: [
        {
          value: 2,
          label: '自黏聚合物改性沥青防水卷材(聚酯毡胎体)',
        },
        {
          value: 3,
          label: '自黏聚合物改性沥青防水卷材(无胎体)',
        },
        {
          value: 4,
          label: '三元乙丙橡胶和聚氯乙烯防水卷材黏合剂',
        },
        {
          value: 5,
          label: '合成橡胶黏结带',
        },
        {
          value: 6,
          label: '高分子自粘胶膜防水卷材粘合面',
        },
        // {
        //   value: 7,
        //   label: '聚氯乙烯防水卷材(双焊缝)',
        // },
        // {
        //   value: 8,
        //   label: '聚氯乙烯防水卷材(胶粘剂)',
        // },
        // {
        //   value: 9,
        //   label: '聚乙烯丙纶复合防水卷材-卷材',
        // },
        // {
        //   value: 10,
        //   label: '聚乙烯丙纶复合防水卷材-黏结料',
        // },
        // {
        //   value: 11,
        //   label: '聚乙烯丙纶复合防水卷材-芯材厚度',
        // },
        // {
        //   value: 12,
        //   label: '高分子自粘胶膜防水卷材(自黏胶)',
        // },
        // {
        //   value: 13,
        //   label: '高分子自粘胶膜防水卷材(黏胶带)',
        // },

      ],
      predictVisual: false,
      fuhaoList: [],
      yuzhiMap: {},
      yesOrNo: [{
        value: '是',
        label: '是'
      },
      {
        value: '否',
        label: '否'
      },
      ],
      //用来判断是否安全，-1表示初始，0表示不安全，1表示安全
      flag: -1,
      newYuzhiMap: {},
      predictItemList: [],
    }
  },
  mixins: [preventBack],//注入  阻止返回上一页
  methods: {
    closeDialog(){
      this.selectedData = []
    },
    handleNodeClick(data) {
      this.queyEntity = {}
      this.childrenIndex = 0
      if (data.children.length != 0)
        return;
      var s = this.$refs.treeData.getCheckedNodes(false, true)
      var queyEntity = this.queyEntity
      //s的长度为5说明选择的施工期
      if (s.length === 4) {
        queyEntity = {
          suidaoId: -1,
          firstIndexId: -1,
          secondIndexId: -1,
          itemIndexId: -1,
          type: 1,
        }
        //获得选择的项目id
        queyEntity.itemIndexId = s[3].id
        //获取隧道标题id
        queyEntity.suidaoId = s[1].id
        //获取一级索引id
        queyEntity.firstIndexId = s[2].id
        //发送queryEntity
        this.getTable(queyEntity)
      }
    },
    //获得默认阈值数组
    getYuzhiMap() {
      //获取queryEntity的firstIndexId,判断是哪一种隧道类型
      jianceGetYuzhi(this.queyEntity).then(response => {
        if (response.code === 201) {
          if (response.data != undefined || response.data != null) {
            this.$message.error("获取阈值失败");
            return;
          }
        }
        else {
          this.yuzhiMap = response.data
        }
      })

    },
    getTable(queyEntity) {
    
      jianceTable(queyEntity, 1, this.pages.size).then(response => {
        if (response.data.tableHead === null) {
          this.$message.error('暂无数据');
          return
        }
        this.tableHead = response.data.tableHead
        this.tableData = response.data.tableData
        this.pages.total = Number.parseInt(response.data.total)
        this.pages.currentPage = Number.parseInt(response.data.current)
        this.predictItemList = []
        for(var i = 0; i < this.tableHead.length; i++){
          if (this.tableHead[i]['type'] === 1 ||  this.tableHead[i]['prop'] === 'rq' || this.tableHead[i]['prop'] === 'cdwz' || this.tableHead[i]['prop'] === 'qtyq' || this.tableHead[i]['prop'] === 'auzt' || this.tableHead[i]['prop'] === 'zuheqz' || this.tableHead[i]['prop'] == 'fsjclx')
            continue
          var predictObject = {}
          predictObject['label'] = this.tableHead[i]['label']
          predictObject['prop'] = this.tableHead[i]['prop']
          this.predictItemList.push(predictObject)
        }
        if (this.tableData != null && this.tableData != null && this.tableData.length != 0) {
          //转换tableData
          for (var i = 0; i < this.tableData.length; i++) {
            this.transformChildren(this.tableData[i])
          }
        }
        //   }
        this.queyEntity = queyEntity
        this.isTableVisual = true
        this.predictVisual = true
        this.$bus.$emit('queryEntity', this.queyEntity,this.predictItemList)
        this.getYuzhiMap()
      })
    },
    addTableData() {
      this.editDialogVisual = false
      this.flag = -1
      this.addData = {}
      this.tempData = []
      var tempresult = {}

      for (var i = 0; i < this.tableHead.length; i++) {
        tempresult = new Object()
        if (this.tableHead[i].label === '日期' || this.tableHead[i].label === '测点位置' || this.tableHead[i].label === '安全状态判断' || this.tableHead[i].label === '组合权重' || this.tableHead[i].label === '其他要求' || this.tableHead[i].label === '防水卷材类型') {
          tempresult['data'] = ''
          tempresult['label'] = this.tableHead[i].label
          tempresult['prop'] = this.tableHead[i].prop
        }
        else {

          //这里的都是有实测值和阈值的
          //根据tableHead的符号id进行判断
          tempresult['children'] = []
          if (this.tableHead[i].type == 1){
            realData = new Object()
            realData['label'] = this.tableHead[i].label + '实测值'
            realData['data'] = ''
            tempresult['children'].push(realData)
            var yuzhiData = new Object()
            yuzhiData['label'] = this.tableHead[i].label + '-阈值'
            if (this.yuzhiMap[this.tableHead[i].id] != null && this.yuzhiMap[this.tableHead[i].id] != undefined && (this.yuzhiMap[this.tableHead[i].id]).length >= 1)
              yuzhiData['data'] = this.yuzhiMap[this.tableHead[i].id][0]
            else yuzhiData['data'] = ''
            tempresult['children'].push(yuzhiData)
            //存放id方便存储进数据库
          }
          else {
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
            if (this.yuzhiMap[this.tableHead[i].id] != null && this.yuzhiMap[this.tableHead[i].id] != undefined &&(this.yuzhiMap[this.tableHead[i].id]).length >= 1)
              yuzhiData0['data'] = this.yuzhiMap[this.tableHead[i].id][0]
            else yuzhiData0['data'] = ''
            tempresult['children'].push(yuzhiData0)
            var realData1 = new Object()
            realData1['label'] = this.tableHead[i].label + '实测值2'
            realData1['data'] = ''
            tempresult['children'].push(realData1)
            var yuzhiData1 = new Object()
            yuzhiData1['label'] = this.tableHead[i].label + '-阈值2(≤)'
            if (this.yuzhiMap[this.tableHead[i].id] != null && this.yuzhiMap[this.tableHead[i].id] != undefined &&(this.yuzhiMap[this.tableHead[i].id]).length >= 2)
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
            if (this.yuzhiMap[this.tableHead[i].id] != null && this.yuzhiMap[this.tableHead[i].id] != undefined && (this.yuzhiMap[this.tableHead[i].id]).length >= 1)
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
            if (this.yuzhiMap[this.tableHead[i].id] != null && this.yuzhiMap[this.tableHead[i].id] != undefined &&(this.yuzhiMap[this.tableHead[i].id]).length >= 1)
              yuzhiData0['data'] = this.yuzhiMap[this.tableHead[i].id][0]
            else yuzhiData0['data'] = ''
            tempresult['children'].push(yuzhiData0)
            var realData1 = new Object()
            realData1['label'] = this.tableHead[i].label + '实测值2'
            realData1['data'] = ''
            tempresult['children'].push(realData1)
            var yuzhiData1 = new Object()
            yuzhiData1['label'] = this.tableHead[i].label + '-阈值2(≤)'
            if (this.yuzhiMap[this.tableHead[i].id] != null && this.yuzhiMap[this.tableHead[i].id] != undefined &&(this.yuzhiMap[this.tableHead[i].id]).length >= 2)
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
            if (this.yuzhiMap[this.tableHead[i].id] != null && this.yuzhiMap[this.tableHead[i].id] != undefined &&(this.yuzhiMap[this.tableHead[i].id]).length >= 1)
              yuzhiData0['data'] = this.yuzhiMap[this.tableHead[i].id][0]
            else yuzhiData0['data'] = ''
            tempresult['children'].push(yuzhiData0)
            var realData1 = new Object()
            realData1['label'] = this.tableHead[i].label + '实测值2'
            realData1['data'] = ''
            tempresult['children'].push(realData1)
            var yuzhiData1 = new Object()
            yuzhiData1['label'] = this.tableHead[i].label + '-阈值2(≥)'
            if (this.yuzhiMap[this.tableHead[i].id] != null && this.yuzhiMap[this.tableHead[i].id] != undefined &&(this.yuzhiMap[this.tableHead[i].id]).length >= 2)
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
            if (this.yuzhiMap[this.tableHead[i].id] != null && this.yuzhiMap[this.tableHead[i].id] != undefined &&(this.yuzhiMap[this.tableHead[i].id]).length >= 1)
              yuzhiData0['data'] = this.yuzhiMap[this.tableHead[i].id][0]
            else yuzhiData0['data'] = ''
            tempresult['children'].push(yuzhiData0)
            var yuzhiData1 = new Object()
            yuzhiData1['label'] = this.tableHead[i].label + '-阈值2(≤)'
            if (this.yuzhiMap[this.tableHead[i].id] != null && this.yuzhiMap[this.tableHead[i].id] != undefined &&(this.yuzhiMap[this.tableHead[i].id]).length >= 2)
              yuzhiData1['data'] = this.yuzhiMap[this.tableHead[i].id][1]
            else yuzhiData1['data'] = ''
            tempresult['children'].push(yuzhiData1)
            //存放id方便存储进数据库
          }
        }

          tempresult['itemIndexId'] = this.tableHead[i].id
          tempresult['type'] = this.tableHead[i].type
          tempresult['fuhao'] = this.tableHead[i].fuhao
        }
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
        if (this.tableHead[i].label === '日期' || this.tableHead[i].label === '测点位置' || this.tableHead[i].label === '安全状态判断' || this.tableHead[i].label === '组合权重' || this.tableHead[i].label === '其他要求' || this.tableHead[i].label === '防水卷材类型') {
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
          if (this.tableHead[i].type === 1){
            var realData = new Object()
            realData['label'] = this.tableHead[i].label + '实测值'
            realData['data'] = this.selectedData[0].children[this.tableHead[i].prop + '_real_0'] === null || this.selectedData[0].children[this.tableHead[i].prop + '_real_0'] === undefined ? '' : this.selectedData[0].children[this.tableHead[i].prop + '_real_0']
            tempresult['children'].push(realData)
            var yuzhiData = new Object()
            yuzhiData['label'] = this.tableHead[i].label + '-阈值'
            yuzhiData['data'] = this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_0'] === null || this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_0'] === undefined ? '' : this.selectedData[0].children[this.tableHead[i].prop + '_yuzhi_0']
            tempresult['children'].push(yuzhiData)
            //存放id方便存储进数据库
          }
          else {
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
        jianceDeleteData(entity).then(response => {
          if (response.code === 200) {
            //判断删除后是否需要返回上一页
            var currentPage = (this.pages.currentPage != 1 && this.tableData.length - this.selectedData.length <= 0) ? this.pages.currentPage - 1 : this.pages.currentPage
            jiancePage(this.queyEntity, currentPage, this.pages.size).then(response => {
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
          else if (response.code === 201) {
            this.$message({
              type: 'info',
              message: '删除失败'
            });
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
    //分页操作
    handleCurrentChange(currentPage) {
      jiancePage(this.queyEntity, currentPage, this.pages.size).then(response => {
        this.tableData = response.data.tableData
        //转换tableData
        if (this.tableData != null && this.tableData != undefined && this.tableData.length > 0) {
          for (var i = 0; i < this.tableData.length; i++) {
            this.transformChildren(this.tableData[i])
          }
        }
        this.pages.currentPage = Number.parseInt(response.data.current)
        this.pages.total = Number.parseInt(response.data.total)
      })
    },

    cancelAdd() {
      this.addData = {}
      this.tempData = []
      this.adddDialogVisual = false
    },
    submitAdd() {
      this.addData = {}
      this.addData['children'] = []
      //如果是测点位置等那么就不需要加入children集合中
      for (var i = 0; i < this.tempData.length; i++) {
        if (this.tempData[i].label === '日期' || this.tempData[i].label === '测点位置' || this.tempData[i].label === '安全状态判断' || this.tempData[i].label === '组合权重' || this.tempData[i].label === '其他要求' || this.tempData[i].label === '防水卷材类型') {
          if (this.tempData[i].label === '防水卷材类型' && this.tempData[i].data === '') {
            this.$message.error('请选择防水材料');
            return
          }
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
      this.addData['suidaoId'] = this.queyEntity.suidaoId
      this.addData['firstIndexId'] = this.queyEntity.firstIndexId
      this.addData['secondIndexId'] = this.queyEntity.secondIndexId
      this.addData['itemIndexId'] = this.queyEntity.itemIndexId
      //TODO提交addData
      // //转化后端传过来的数据
      addJianceData(this.addData, this.pages.currentPage, this.pages.size).then(response => {
        if(response.code != 201){
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
        // }).catch((error) => {
        //   this.$message({
        //     message: '添加失败!',
        //     type: 'warning'
        //   })
        // })

      })
    },
    cancelEdit() {
      for (var i = 0; i < this.tableData.length; i++) {
        if (this.tableData[i].id === this.oldEditData.id) {
          this.tableData[i] = this.oldEditData;
          break;
        }
      }
      this.selectedData = []
      this.editDialogVisual = false
    },
    submitEdit() {
      //editData的chilren是个对象,dhcn_real这种
      this.editData['children'] = []
      //将tempEditData中的数据加入到eidtData
      for (var i = 0; i < this.tempData.length; i++) {
        if (this.tempData[i].label === '日期' || this.tempData[i].label === '测点位置' || this.tempData[i].label === '安全状态判断' || this.tempData[i].label === '组合权重' || this.tempData[i].label === '其他要求' || this.tempData[i].label === '防水卷材类型') {
          if (this.tempData[i].label === '防水卷材类型' && this.tempData[i].data === '') {
            this.$message.error('请选择防水材料');
            return
          }
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
          data['id'] = 
          this.editData['children'].push(data)
        }
      }
      //调用api将editData推上去
      jianceEditData(this.editData).then(response => {
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

        }
        else if (response.code === 201) {
          //
          this.$message.error('修改失败');
          this.cancelEdit()
        }
        this.editDialogVisual = false
      }
      )

    },

    transformChildren(data) {
      //这里前端先自己改造children格式: prop名字_real or yuzhi
      var newChildren = {}
      for (var i = 0; i < data.children.length; i++) {
        for (var j = 0; j < this.tableHead.length; j++) {
          if (data.children[i].itemIndexId === this.tableHead[j].id) {
            //遍历realData数组
            if (data.children[i].realData != null && data.children[i].realData != undefined && data.children[i].realData.length > 0){
            for (var k = 0; k < data.children[i].realData.length; k++) {
              var realLabel = this.tableHead[j].prop + '_real_' + k;
              newChildren[realLabel] = data.children[i].realData[k]
            }
          }
          if (data.children[i].yuzhiData != null && data.children[i].yuzhiData != undefined && data.children[i].yuzhiData.length > 0){
            //遍历yuzhiData数组
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
    },
    //选择防水卷材触发
    listenFsjc(data) {
      this.flag = -1
      //data是选中的id,从后端获取点中的防水卷材
      //如果是钻爆法就是-1,盾构法-2,堰就是-3(防水卷材厚度、搭接宽度),如果是防水卷材粘结质量那么就-4,-5,-6
      let query = {}
      if (this.queyEntity.itemIndexId === '4' || this.queyEntity.itemIndexId === '25' || this.queyEntity.itemIndexId === '36'){
      if (this.queyEntity.firstIndexId === '1' )
        query['firstIndexId'] = -1
      else if (this.queyEntity.firstIndexId === '2')
        query['firstIndexId'] = -2
      else
        query['firstIndexId'] = -3
      }
      else if (this.queyEntity.itemIndexId === '5' || this.queyEntity.itemIndexId === '26' || this.queyEntity.itemIndexId === '37' ){
        if (this.queyEntity.firstIndexId === '1' )
        query['firstIndexId'] = -4
      else if (this.queyEntity.firstIndexId === '2')
        query['firstIndexId'] = -5
      else
        query['firstIndexId'] = -6
      }
      query['itemIndexId'] = data
      //根据query来查询
      jianceGetYuzhi(query).then(response => {
        if (response.code === 201) {
          if (response.data != undefined || response.data != null) {
            this.$message.error("获取阈值失败");
            return
          }
          for (var i = 0; i < this.tempData.length; i++) {
            //有children说明有阈值
            if (this.tempData[i]['children'] === null || this.tempData[i]['children'] === undefined)
              continue
            //赋值
            for (var j = 0; j < this.tempData[i].children.length; j++) {
              //是阈值 
              if (this.tempData[i].children[j].label.indexOf("阈值") != -1) {
                //找到阈值1
                if (this.tempData[i].children[j].label.indexOf("1") != -1)
                  this.tempData[i].children[j].data = ""
                //阈值2
                else if (this.tempData[i].children[j].label.indexOf("2") != -1)
                  this.tempData[i].children[j].data = ""
                //一个阈值
                else
                  this.tempData[i].children[j].data = ""
              }
            }
          }
          return;
        }
        this.newYuzhiMap = response.data
        //修改tempData的对应选项的阈值
        for (var i = 0; i < this.tempData.length; i++) {
          //有children说明有阈值
          if (this.tempData[i]['children'] === null || this.tempData[i]['children'] === undefined)
            continue
          //赋值
          for (var j = 0; j < this.tempData[i].children.length; j++) {
            //是阈值 
            if (this.tempData[i].children[j].label.indexOf("阈值") != -1) {
              //找到阈值1
              if (this.tempData[i].children[j].label.indexOf("1") != -1){
                if (this.newYuzhiMap[this.tempData[i].itemIndexId][0] === '-'){
                  this.tempData[i].children[j - 1].data = '-'
                }
                //如果当前阈值不是-但是实际值此时是-,那么就清空
                else if (this.tempData[i].children[j - 1].data === '-')
                this.tempData[i].children[j - 1].data = ''
                this.tempData[i].children[j].data = this.newYuzhiMap[this.tempData[i].itemIndexId][0]
              }
              //阈值2
              else if (this.tempData[i].children[j].label.indexOf("2") != -1){
              if (this.newYuzhiMap[this.tempData[i].itemIndexId][1] === '-'){
                  this.tempData[i].children[j - 1].data = '-'
                }
                else if (this.tempData[i].children[j - 1].data === '-')
                  this.tempData[i].children[j - 1].data = ''
                this.tempData[i].children[j].data = this.newYuzhiMap[this.tempData[i].itemIndexId][1]
              }
              //一个阈值
              else{
                if (this.newYuzhiMap[this.tempData[i].itemIndexId][0] === '-'){
                  this.tempData[i].children[j - 1].data = '-'
                }
                else if (this.tempData[i].children[j - 1].data === '-')
                  this.tempData[i].children[j - 1].data = ''
                this.tempData[i].children[j].data = this.newYuzhiMap[this.tempData[i].itemIndexId][0]
              }
            }
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
          if (this.tempData[i].children[1].data === '-' && this.tempData[i].children[2].data === '-')
            continue
          //第一个是实测值，后面两个是阈值，并且第一个>= 第二个<=
          if (this.tempData[i].children[0].data === '' || this.tempData[i].children[1].data === '' || this.tempData[i].children[2].data === '') {
            this.$message.error('有实测值或阈值没有输入');
            return;
          }
          var yuzhi1 = parseFloat(this.tempData[i].children[1].data)
          var yuzhi2 = parseFloat(this.tempData[i].children[2].data)
          if (yuzhi1 === '-' && yuzhi2 === '-')
            continue
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
              if (this.tempData[i].children[j].data === '-')
                continue
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
                  if (yuzhi === '-')
                    continue
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
                  if (yuzhi === '-')
                    continue
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
  created() {
    //通过api获取treeData
    // this.checkItem[0].children = [{
    //   id: 1001,
    //   label: '定淮门隧道',
    //   children: [{
    //     id: 1,
    //     label: '钻爆法隧道',
    //     children: [{
    //       id: 2,
    //       label: '防水卷材厚度、搭接宽度',
    //       children: [],
    //     },
    //     {
    //       id: 3,
    //       label: '防水卷材粘结质量',
    //       children: [],
    //     },
    //     {
    //       id: 4,
    //       label: '总体质量',
    //       children: [],
    //     },
    //     {
    //       id: 5,
    //       label: '总体质量',
    //       children: [],
    //     },]
    //   },
    //   {
    //     id: 2,
    //     label: '盾构法隧道',
    //     children: [{
    //       id: 6,
    //       label: '防水卷材厚度、搭接宽度',
    //       children: [],
    //     },
    //     {
    //       id: 7,
    //       label: '防水卷材粘结质量',
    //       children: [],
    //     },
    //     {
    //       id: 8,
    //       label: '总体质量',
    //       children: [],
    //     },
    //     {
    //       id: 9,
    //       label: '混凝土管片质量',
    //       children: [],
    //     },]
    //   },
    //   {
    //     id: 3,
    //     label: '堰筑法隧道',
    //     children: [{
    //       id: 2,
    //       label: '防水卷材厚度、搭接宽度',
    //       children: [],
    //     },
    //     {
    //       id: 3,
    //       label: '防水卷材粘结质量',
    //       children: [],
    //     },
    //     {
    //       id: 4,
    //       label: '总体质量',
    //       children: [],
    //     },
    //     {
    //       id: 5,
    //       label: '钢板桩围堰质量',
    //       children: [],
    //     },]
    //   },
    //   ]
    // }]
    jianceDict().then(response => {
      this.checkItem[0].children = response.data
    })
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

<style scoped>
 .el-tree {
    min-width: 100%;
    display: inline-block !important;
  }
 
  </style>