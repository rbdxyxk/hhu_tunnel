<template>
  <div>
    <el-row :gutter="10">
      <el-col :span="6" style="border-style: solid;">
        <el-tree ref="treeData" :data="dict" :props="defaultProps" show-checkbox node-key="id"
          :expand-on-click-node="false" :check-on-click-node="true" @node-click="handleNodeClick"
          style="margin-top: 0.5rem;" :highlight-current="true">
        </el-tree >
      </el-col>

      <el-col :span="18">
        <div v-if="isTableVisual">
          <h2>单点预测</h2>
          <el-button type="primary" plain @click="addTableData">添加</el-button>
          <el-button type="primary" plain @click="editTableData">编辑</el-button>
          <el-button type="primary" plain @click="deleteTableData">删除</el-button>
          <el-table ref="tab" :data="tableData" border class="tab-table" @selection-change="handleSelectionChange"
            :key="Math.random()" style="margin-top: 1rem;height: 40rem;" :header-cell-style="{ textAlign: 'center' }"
            :cell-style="{ textAlign: 'center' }" height="575">
            <el-table-column type="selection" width="55" :fixed="true">
            </el-table-column>
            <el-table-column label="日期" prop="rq" width="160">
            </el-table-column>
            <el-table-column label="桩号" prop="zhuanghao" >
            </el-table-column>
            <el-table-column label="测点编号" prop="cdbh" >
            </el-table-column>
            <el-table-column label="自然电位变化曲线" prop="zrdwbhqx" >
            </el-table-column>
            <el-table-column label="渗漏水状态" prop="slszt" >
              <template slot-scope="scope">
                  <span v-if="scope.row.slszt === 0">正常</span>
                  <span v-if="scope.row.slszt === 1">黄色预警</span>
                  <span v-if="scope.row.slszt === 2">红色预警</span>
                </template>
            </el-table-column>
            <el-table-column label="养护要求" prop="yhyq" >
            </el-table-column>
            <el-table-column label="备注" prop="beizhu" >
            </el-table-column>
          </el-table>
          <el-pagination background layout="prev, pager, next" :current-page.sync="pages.currentPage"
            :page-size="pages.size" :total="pages.total" @current-change="handleCurrentChange" style="margin-top: 1rem;">
          </el-pagination>
          <hr/>
          <!-- <区域预测 -->
          <h2 style="margin-top: 2rem;">区域预测</h2>
          <el-button type="primary" plain @click="addTableData2">添加</el-button>
          <el-button type="primary" plain @click="editTableData2">编辑</el-button>
          <el-button type="primary" plain @click="deleteTableData2">删除</el-button>
          <el-table ref="tab" :data="tableData2" border class="tab-table" @selection-change="handleSelectionChange"
            :key="Math.random()" style="margin-top: 1rem;" :header-cell-style="{ textAlign: 'center' }"
            :cell-style="{ textAlign: 'center' }" height="575">
            <el-table-column type="selection" width="55" :fixed="true">
            </el-table-column>
            <el-table-column label="日期" prop="rq" width="160">
            </el-table-column>
            <el-table-column label="桩号起点" prop="zhuanghaoqd" >
            </el-table-column>
            <el-table-column label="桩号终点" prop="zhuanghaozd" >
            </el-table-column>
            <el-table-column label="自然电位变化曲线" prop="zrdwbhqx" >
            </el-table-column>
            <el-table-column label="渗漏水状态" prop="slszt" >
              <template slot-scope="scope">
                  <span v-if="scope.row.slszt === 0">正常</span>
                  <span v-if="scope.row.slszt === 1">黄色预警</span>
                  <span v-if="scope.row.slszt === 2">红色预警</span>
                </template>
            </el-table-column>
            <el-table-column label="渗漏水位置" prop="slswz" >
            </el-table-column>
            <el-table-column label="养护要求" prop="yhyq" >
            </el-table-column>
            <el-table-column label="备注" prop="beizhu" >
            </el-table-column>
          </el-table>
          <el-pagination background layout="prev, pager, next" :current-page.sync="pages2.currentPage"
            :page-size="pages2.size" :total="pages2.total" @current-change="handleCurrentChange2" style="margin-top: 1rem;">
          </el-pagination>
        </div>
      </el-col>
    </el-row>
    <!-- 单点预测dialog -->
    <el-dialog title="添加" :visible.sync="addTableDataVisual" width="35%" :show-close="false">
      <el-form ref="addform" :model="tempData" label-width="10rem">
        <el-form-item label="日期" style="margin-bottom: 0.6rem;">
          <el-date-picker v-model="tempData['rq']" type="datetime" placeholder="选择日期时间"
            value-format="yyyy-MM-dd HH:mm:ss">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="桩号" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['zhuanghao']"></el-input>
        </el-form-item>
        <el-form-item label="测点编号" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['cdbh']"></el-input>
        </el-form-item>
        <el-form-item label="自然电位变化曲线" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['zrdwbhqx']"></el-input>
        </el-form-item>
        <el-form-item label="渗漏水状态" style="margin-bottom: 0.6rem;">
          <el-select v-model="tempData['slszt']" placeholder="选择渗漏水" style="width: 20rem;">
              <el-option v-for="item2 in stausList" :key="item2.value" :label="item2.label" :value="item2.value">
              </el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="养护要求" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['yhyq']"></el-input>
        </el-form-item>
        <el-form-item label="备注" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['beizhu']"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelAddTableData">取 消</el-button>
        <el-button type="primary" @click="submitAddTableData">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="修改" :visible.sync="edditTableDataVisual" width="35%" :show-close="false">
      <el-form ref="edditform" :model="tempData" label-width="10rem">
        <el-form-item label="日期" style="margin-bottom: 0.6rem;">
          <el-date-picker v-model="tempData['rq']" type="datetime" placeholder="选择日期时间"
            value-format="yyyy-MM-dd HH:mm:ss">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="桩号" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['zhuanghao']"></el-input>
        </el-form-item>
        <el-form-item label="测点编号" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['cdbh']"></el-input>
        </el-form-item>
        <el-form-item label="自然电位变化曲线" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['zrdwbhqx']"></el-input>
        </el-form-item>
        <el-form-item label="渗漏水状态" style="margin-bottom: 0.6rem;">
          <el-select v-model="tempData['slszt']" placeholder="选择渗漏水" style="width: 20rem;">
              <el-option v-for="item2 in stausList" :key="item2.value" :label="item2.label" :value="item2.value">
              </el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="养护要求" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['yhyq']"></el-input>
        </el-form-item>
        <el-form-item label="备注" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['beizhu']"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelEdditTableData">取 消</el-button>
        <el-button type="primary" @click="submitEdditTableData">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 多点预测dialog -->
    <el-dialog title="添加" :visible.sync="addTableDataVisual2" width="35%" :show-close="false">
      <el-form ref="addform" :model="tempData" label-width="10rem">
        <el-form-item label="日期" style="margin-bottom: 0.6rem;">
          <el-date-picker v-model="tempData['rq']" type="datetime" placeholder="选择日期时间"
            value-format="yyyy-MM-dd HH:mm:ss">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="桩号起点" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['zhuanghaoqd']"></el-input>
        </el-form-item>
        <el-form-item label="桩号终点" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['zhuanghaozd']"></el-input>
        </el-form-item>
        <el-form-item label="自然电位变化曲线" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['zrdwbhqx']"></el-input>
        </el-form-item>
        <el-form-item label="渗漏水状态" style="margin-bottom: 0.6rem;">
          <el-select v-model="tempData['slszt']" placeholder="选择渗漏水" style="width: 20rem;">
              <el-option v-for="item2 in stausList" :key="item2.value" :label="item2.label" :value="item2.value">
              </el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="渗漏水位置" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['slswz']"></el-input>
        </el-form-item>
        <el-form-item label="养护要求" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['yhyq']"></el-input>
        </el-form-item>
        <el-form-item label="备注" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['beizhu']"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelAddTableData2">取 消</el-button>
        <el-button type="primary" @click="submitAddTableData2">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="修改" :visible.sync="edditTableDataVisual2" width="35%" :show-close="false">
      <el-form ref="edditform" :model="tempData" label-width="10rem">
        <el-form-item label="日期" style="margin-bottom: 0.6rem;">
          <el-date-picker v-model="tempData['rq']" type="datetime" placeholder="选择日期时间"
            value-format="yyyy-MM-dd HH:mm:ss">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="桩号起点" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['zhuanghaoqd']"></el-input>
        </el-form-item>
        <el-form-item label="桩号终点" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['zhuanghaozd']"></el-input>
        </el-form-item>
        <el-form-item label="自然电位变化曲线" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['zrdwbhqx']"></el-input>
        </el-form-item>
        <el-form-item label="渗漏水状态" style="margin-bottom: 0.6rem;">
          <el-select v-model="tempData['slszt']" placeholder="选择渗漏水" style="width: 20rem;">
              <el-option v-for="item2 in stausList" :key="item2.value" :label="item2.label" :value="item2.value">
              </el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="渗漏水位置" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['slswz']"></el-input>
        </el-form-item>
        <el-form-item label="养护要求" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['yhyq']"></el-input>
        </el-form-item>
        <el-form-item label="备注" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['beizhu']"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelEdditTableData2">取 消</el-button>
        <el-button type="primary" @click="submitEdditTableData2">确 定</el-button>
      </span>
    </el-dialog>


    
  </div>
</template>

<script>
import { viewFileOnline } from '@/api/File'
export default {
  data() {
    return {
      dict: [{
        id: -1,
        label: '全部',
        children: [],
      }],
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      selectedNode: {},
      isTableVisual: false,
      tableData: [],
      queryEntity: {},
      addTableDataVisual: false,
      tempData: {},
      stausList: [{
        value: 0,
        label: '正常'
      },
      {
        value: 1,
        label: '黄色预警'
      },
      {
        value: 2,
        label: '红色预警'
      }
      ],
      pages: {
        //每页展示条数
        size: 10,
        //总共多少条
        total: 10,
        currentPage: 1
      },
      pages2: {
        //每页展示条数
        size: 10,
        //总共多少条
        total: 10,
        currentPage: 1
      },
      edditTableDataVisual: false,
      oldTempData: {},
      selectedData: [],
      newChild: {},
      index: 3,
      addNodeVisual: false,
      editNodeVisual: false,
      addNodeVisual: false,
      editNodeVisual: false,
      oldEditNode: {},
      editNode: {},
      uploadUrl: process.env.VUE_APP_BASE_API + '/file/upload',
      tableData2: [],
      addTableDataVisual2: false,
      edditTableDataVisual2: false,
    }
  },
  methods: {
    //点击节点事件
    handleNodeClick(data) {
      this.selectedNode = data
      var s = this.$refs.treeData.getCheckedNodes(false, true)
      if (s.length != 6)
        return
      var queyEntity = {
        suidaoId: -1,
        firstIndexId: -1,
        secondIndexId: -1,
        thirdIndexId: -1,
        itemId: -1,
      }
      queyEntity.suidaoId = s[1].id
      queyEntity.firstIndexId = s[2].id
      queyEntity.secondIndexId = s[3].id
      queyEntity.thirdIndexId = s[4].id
      queyEntity.itemId = s[5].id
      this.queryEntity = queyEntity
      //
      this.getTableData(queyEntity)
      this.isTableVisual = true
    },
    addTableData() {
      this.edditTableDataVisual = false
      this.tempData = {
      }
      this.addTableDataVisual = true
    },
    addTableData2() {
      this.edditTableDataVisual2 = false
      this.tempData = {
      }
      this.addTableDataVisual2 = true
    },
    editTableData() {
      this.addTableDataVisual = false
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
      this.tempData = this.selectedData[0]
      this.oldTempData = JSON.parse(JSON.stringify(this.selectedData[0]))
      this.edditTableDataVisual = true
    },
    editTableData2() {
      this.addTableDataVisual2 = false
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
      this.tempData = this.selectedData[0]
      this.oldTempData = JSON.parse(JSON.stringify(this.selectedData[0]))
      this.edditTableDataVisual2 = true
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
        for (var i = 0; i < this.selectedData.length; i++) {
          for (var j = 0; j < this.tableData.length; j++) {
            if (this.selectedData[i].id === this.tableData[j].id) {
              this.tableData.splice(j, 1);
            }
          }
        }
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    deleteTableData2() {
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
        for (var i = 0; i < this.selectedData.length; i++) {
          for (var j = 0; j < this.tableData2.length; j++) {
            if (this.selectedData[i].id === this.tableData2[j].id) {
              this.tableData2.splice(j, 1);
            }
          }
        }
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
    cancelAddTableData() {
      this.tempData = {}
      this.addTableDataVisual = false
    },
    cancelAddTableData2() {
      this.tempData = {}
      this.addTableDataVisual2 = false
    },
    submitAddTableData() {
      //
      this.tableData.push(this.tempData)
      this.addTableDataVisual = false
    },
    submitAddTableData2() {
      //
      this.tableData2.push(this.tempData)
      this.addTableDataVisual2 = false
    },
    handleCurrentChange(current) { },
    handleCurrentChange2(current){},
    cancelEdditTableData() {
      var index = this.tableData.findIndex(item => item.id === this.tempData.id)
      this.tableData[index] = this.oldTempData
      this.edditTableDataVisual = false
    },
    cancelEdditTableData2() {
      var index = this.tableData2.findIndex(item => item.id === this.tempData.id)
      this.tableData2[index] = this.oldTempData
      this.edditTableDataVisual2 = false
    },
    submitEdditTableData() {
      this.edditTableDataVisual = false
    },
    submitEdditTableData2() {
      this.edditTableDataVisual2 = false
    },

    getTableData(queryEntity) {
      this.tableData = [{
        id: 1,
        rq: '2022-02-03 10:15:00',
        zhuanghao: '1号',
        cdbh: '1213141',
        zrdwbhqx: 10,
        slszt: 0,
        yhyq: '养护要求',
        beizhu: '备注'
      },
      {
        id: 2,
        rq: '2022-02-03 10:15:00',
        zhuanghao: '1号',
        cdbh: '1213141',
        zrdwbhqx: 10,
        slszt: 1,
        yhyq: '养护要求',
        beizhu: '备注'
      },
      {
        id: 3,
        rq: '2022-02-03 10:15:00',
        zhuanghao: '1号',
        cdbh: '1213141',
        zrdwbhqx: 10,
        slszt: 2,
        yhyq: '养护要求',
        beizhu: '备注'
      }
      ],
      this.tableData2 = [
      {
        id: 1,
        rq: '2022-02-03 10:15:00',
        zhuanghaoqd: '1号',
        zhuanghaozd: '2号',
        zrdwbhqx: 10,
        slszt: 0,
        slswz: '起点',
        yhyq: '养护要求',
        beizhu: '备注'
      },
      {
        id: 2,
        rq: '2022-02-03 10:15:00',
        zhuanghaoqd: '1号',
        zhuanghaozd: '2号',
        zrdwbhqx: 10,
        slszt: 1,
        slswz: '起点',
        yhyq: '养护要求',
        beizhu: '备注'
      },
      {
        id: 3,
        rq: '2022-02-03 10:15:00',
        zhuanghaoqd: '1号',
        zhuanghaozd: '2号',
        zrdwbhqx: 10,
        slszt: 2,
        slswz: '起点',
        yhyq: '养护要求',
        beizhu: '备注'
      },
      ]
    },

    view(id) {
      // var fileId = 123
      // let path = this.$router.resolve({
      //     path: '/view/file/?fileid=' + fileId
      // })
      // window.open(path.href,'_blank')
      var fileId = 1230123;
      viewFileOnline(fileId).then((res) => {
        // loading.close();
        if (res.size === 0){
            this.$message.warning("文件不存在")
            return
            }
        if (res.status == "500") {
          this.$message({
            message: "下载失败！",
            type: "error",
          });
          return;
        }
        //文件以pdf形式进行预览
        let blob = new Blob([res], {
          type: "application/pdf;chartset=UTF-8",
        });
        console.log(blob)
        let fileURL = URL.createObjectURL(blob);
        window.open(fileURL);


        // let pptx = new pptxgenjs();
        // pptx.load(res.data);
        // let pptxUrl = URL.createObjectURL(pptx.output('blob'));
        // window.open(pptxUrl);
      });

    },
  },
  created() {
    this.dict[0].children = [
      {
        id: 1,
        label: '定淮门隧道',
        children: [
          {
            id: 2,
            label: '施工期',
            children: [
              {
                id: 4,
                label: '结构',
                children: [
                  {
                    id: 6,
                    label: '主体结构',
                    children: [
                      {
                        id: 9,
                        label: '洞身',
                        children: [],
                      },
                      {
                        id: 2,
                        label: '洞门',
                        children: [],
                      }
                    ],
                  },
                  {
                    id: 7,
                    label: '附属结构',
                    children: [],
                  },
                  {
                    id: 8,
                    label: '路面',
                    children: [],
                  }
                ],
              },
              {
                id: 5,
                label: '设施',
                children: [],
              }
            ],
          },
          {
            id: 3,
            label: '运行期',
            children: [],
          }
        ]
      },
    ]
  },
}
</script>

<style></style>