<template>
  <div>
      <el-row :gutter="10">
          <el-col :span="6" style="border-style: solid;">
              <!-- <el-button @click="addNodeData">添加</el-button>
        <el-button @click="editNodeData">编辑</el-button>
        <el-button @click="deleteNodeData">删除</el-button> -->
              <el-tree ref="treeData" :data="dict" :props="defaultProps" show-checkbox node-key="id"
                  :expand-on-click-node="false" :check-on-click-node="true" @node-click="handleNodeClick"
                  style="margin-top: 0.5rem;" :highlight-current="true">
              </el-tree>
          </el-col>

          <el-col :span="18">
              <div v-if="isTableVisual">
                  <el-button type="primary" plain @click="addTableData">添加</el-button>
                  <el-button type="primary" plain @click="editTableData">编辑</el-button>
                  <el-button type="primary" plain @click="deleteTableData">删除</el-button>
                  <el-table ref="tab" :data="tableData" border class="tab-table" @selection-change="handleSelectionChange"
                      :key="Math.random()" style="margin-top: 1rem;height: 36rem;"
                      :header-cell-style="{ textAlign: 'center' }" :cell-style="{ textAlign: 'center' }" height="575">
                      <el-table-column type="selection" width="55" :fixed="true">
                      </el-table-column>
                      <el-table-column prop="taskname" label="任务名称" width="180" >
                      </el-table-column>
                      <el-table-column prop="type" label="任务类型" width="180" >
                      </el-table-column>
                      <el-table-column prop="source" label="项目来源" width="180" >
                      </el-table-column>
                      <el-table-column prop="begintime" label="开始时间" width="180" >
                      </el-table-column>
                      <el-table-column prop="endtime" label="结束时间" width="180" >
                      </el-table-column>
                      <el-table-column prop="executor" label="执行人" width="180" >
                      </el-table-column>
                      <el-table-column prop="reviewer" label="审核人" width="180" >
                      </el-table-column>
                      <el-table-column prop="status" label="任务状态" width="180" >
                          <template slot-scope="scope">
                              <div v-if="scope.row.status === 0 || scope.row.status === '0'">
                                  <span>已委托</span>
                              </div>
                              <div v-if="scope.row.status === 1 || scope.row.status === '1'">
                                  <span>已执行</span>
                              </div>
                              <div v-if="scope.row.status === 2 || scope.row.status === '2'">
                                  <span>正在执行</span>
                              </div>
                              <div v-if="scope.row.status === 3 || scope.row.status === '3'">
                                  <span>已完成</span>
                              </div>
                              <div v-if="scope.row.status === 4 || scope.row.status === '4'">
                                  <span>已退回</span>
                              </div>
                          </template>
                      </el-table-column>
                      <el-table-column fixed="right" label="操作" width="100">
                          <template slot-scope="scope">
                                  <el-button type="text" size="small" @click="view(scope.row.id)"> 查看 </el-button>
                          </template>
                      </el-table-column>
                  </el-table>
                  <el-pagination background layout="prev, pager, next" :current-page.sync="pages.currentPage"
                      :page-size="pages.size" :total="pages.total" @current-change="handleCurrentChange"
                      style="margin-top: 1rem;">
                  </el-pagination>
              </div>
          </el-col>
      </el-row>
      <el-dialog title="添加" :visible.sync="addTableDataVisual" width="35%" :show-close="false">
          <el-form ref="addform" :model="tempData" label-width="10rem">
              <el-form-item label="任务名称" style="margin-bottom: 0.6rem;">
                  <el-input v-model="tempData['taskname']"></el-input>
              </el-form-item>
              <el-form-item label="任务类型" style="margin-bottom: 0.6rem;">
                  <el-input v-model="tempData['type']"></el-input>
              </el-form-item>
              <el-form-item label="项目来源" style="margin-bottom: 0.6rem;">
                  <el-input v-model="tempData['source']"></el-input>
              </el-form-item>
              <el-form-item label="开始时间" style="margin-bottom: 0.6rem;">
                  <el-date-picker v-model="tempData['begintime']" type="datetime" placeholder="选择日期时间"
                      value-format="yyyy-MM-dd HH:mm:ss">
                  </el-date-picker>
              </el-form-item>
              <el-form-item label="结束时间" style="margin-bottom: 0.6rem;">
                  <el-date-picker v-model="tempData['endtime']" type="datetime" placeholder="选择日期时间"
                      value-format="yyyy-MM-dd HH:mm:ss">
                  </el-date-picker>
              </el-form-item>
              <el-form-item label="执行人" style="margin-bottom: 0.6rem;">
                  <el-input v-model="tempData['executor']"></el-input>
              </el-form-item>
              <el-form-item label="审核人" style="margin-bottom: 0.6rem;">
                  <el-input v-model="tempData['reviewer']"></el-input>
              </el-form-item>
              <el-form-item label="任务状态" style="margin-bottom: 0.6rem;">
                  <el-select v-model="tempData['status']" placeholder="选择状态" style="width: 20rem;">
                      <el-option v-for="item2 in stausList" :key="item2.value" :label="item2.label" :value="item2.value">
                      </el-option>
                  </el-select>
              </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
              <el-button @click="cancelAddTableData">取 消</el-button>
              <el-button type="primary" @click="submitAddTableData">确 定</el-button>
          </span>
      </el-dialog>

      <el-dialog title="编辑" :visible.sync="edditTableDataVisual" width="35%" :show-close="false">
          <el-form ref="edditform" :model="tempData" label-width="10rem">
              <el-form-item label="任务名称" style="margin-bottom: 0.6rem;">
                  <el-input v-model="tempData['taskname']"></el-input>
              </el-form-item>
              <el-form-item label="任务类型" style="margin-bottom: 0.6rem;">
                  <el-input v-model="tempData['type']"></el-input>
              </el-form-item>
              <el-form-item label="项目来源" style="margin-bottom: 0.6rem;">
                  <el-input v-model="tempData['source']"></el-input>
              </el-form-item>
              <el-form-item label="开始时间" style="margin-bottom: 0.6rem;">
                  <el-date-picker v-model="tempData['begintime']" type="datetime" placeholder="选择日期时间"
                      value-format="yyyy-MM-dd HH:mm:ss">
                  </el-date-picker>
              </el-form-item>
              <el-form-item label="结束时间" style="margin-bottom: 0.6rem;">
                  <el-date-picker v-model="tempData['endtime']" type="datetime" placeholder="选择日期时间"
                      value-format="yyyy-MM-dd HH:mm:ss">
                  </el-date-picker>
              </el-form-item>
              <el-form-item label="执行人" style="margin-bottom: 0.6rem;">
                  <el-input v-model="tempData['executor']"></el-input>
              </el-form-item>
              <el-form-item label="审核人" style="margin-bottom: 0.6rem;">
                  <el-input v-model="tempData['reviewer']"></el-input>
              </el-form-item>
              <el-form-item label="任务状态" style="margin-bottom: 0.6rem;">
                  <el-select v-model="tempData['status']" placeholder="选择状态" style="width: 20rem;">
                      <el-option v-for="item2 in stausList" :key="item2.value" :label="item2.label" :value="item2.value">
                      </el-option>
                  </el-select>
              </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
              <el-button @click="cancelEdditTableData">取 消</el-button>
              <el-button type="primary" @click="submitEdditTableData">确 定</el-button>
          </span>
      </el-dialog>
  </div>
</template>

<script>
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
          stausList: [
              {
              value: 0,
              label: '已委托'
          },
          {
              value: 1,
              label: '已执行'
          },
          {
              value: 2,
              label: '正在执行'
          },
          {
              value: 3,
              label: '已完成'
          },
          {
              value: 4,
              label: '已退回'
          }
          ],
          pages: {
              //每页展示条数
              size: 10,
              //总共多少条
              total: 10,
              currentPage: 1
          },
          edditTableDataVisual: false,
          oldTempData: {},
          selectedData: [],
      }
  },
  methods: {
      getTableData(queryEntity) {
          this.tableData = [
              {
                  id: 1,
                  begintime: '2023-03-09 13:56:30',
                  taskname: '保护层厚度',
                  type: '钢筋保护层厚度',
                  source: '扬子江隧道渗漏专项',
                  endtime: '2023-03-09 13:56:30',
                  executor: '安全员1',
                  reviewer: '安全员2',
                  status: 0,
              },
              {
                  id: 2,
                  begintime: '2023-03-09 13:56:30',
                  taskname: '保护层厚度',
                  type: '钢筋保护层厚度',
                  source: '扬子江隧道渗漏专项',
                  endtime: '2023-03-09 13:56:30',
                  executor: '安全员1',
                  reviewer: '安全员2',
                  status: 1,
              },
              {
                  id: 3,
                  begintime: '2023-03-09 13:56:30',
                  taskname: '保护层厚度',
                  type: '钢筋保护层厚度',
                  source: '扬子江隧道渗漏专项',
                  endtime: '2023-03-09 13:56:30',
                  executor: '安全员1',
                  reviewer: '安全员2',
                  status: 2,
              },
              {
                  id: 4,
                  begintime: '2023-03-09 13:56:30',
                  taskname: '保护层厚度',
                  type: '钢筋保护层厚度',
                  source: '扬子江隧道渗漏专项',
                  endtime: '2023-03-09 13:56:30',
                  executor: '安全员1',
                  reviewer: '安全员2',
                  status: 3,
              },
              {
                  id: 5,
                  begintime: '2023-03-09 13:56:30',
                  taskname: '保护层厚度',
                  type: '钢筋保护层厚度',
                  source: '扬子江隧道渗漏专项',
                  endtime: '2023-03-09 13:56:30',
                  executor: '安全员1',
                  reviewer: '安全员2',
                  status: 4,
              },
          ]
      },
      addNodeData() {

      },
      editNodeData() { },
      deleteNodeData() { },
      //点击节点事件
      handleNodeClick(data) {
          this.selectedNode = data
          var s = this.$refs.treeData.getCheckedNodes(false, true)
          if (s.length == 2) {
              var queyEntity = {
                  firstIndexId: -1,
                  secondIndexId: -1,
              }
              queyEntity.firstIndexId = s[1].id
              this.queryEntity = queyEntity
              console.log(queyEntity)
              //queyEntity获得表格数据
              this.getTableData(this.queryEntity)
              this.isTableVisual = true
          }
          if (s.length == 3) {
              var queyEntity = {
                  firstIndexId: -1,
                  secondIndexId: -1,
              }
              queyEntity.firstIndexId = s[1].id
              queyEntity.secondIndexId = s[2].id
              this.queryEntity = queyEntity
              console.log(queyEntity)
              //queyEntity获得表格数据
              this.getTableData(this.queryEntity)
              this.isTableVisual = true
          }
      },
      addTableData() {
          this.edditTableDataVisual = false
          this.tempData = {}
          this.addTableDataVisual = true
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
      handleSelectionChange(data) {
          this.selectedData = data
      },
      cancelAddTableData() {
          this.tempData = {}
          this.addTableDataVisual = false
      },
      submitAddTableData() {
          //
          this.tableData.push(this.tempData)
          this.tempData = {}
          this.addTableDataVisual = false
      },
      handleCurrentChange(current) { },
      cancelEdditTableData() {
          var index = this.tableData.findIndex(item => item.id === this.tempData.id)
          this.tableData[index] = this.oldTempData
          this.edditTableDataVisual = false
      },
      submitEdditTableData() {
          this.edditTableDataVisual = false
      },
      view(id){
            let path = this.$router.resolve({
                path: '/view/yanghu/' + id 
            })
            window.open(path.href,'_blank')
        },

  },
  created() {
      this.dict[0].children = [
          {
              id: 2,
              label: '2023',
              children: [{
                id: 3,
                label: '渗漏项目',
                children: [],
              }],
          },
        ]


  },
}
</script>

<style></style>