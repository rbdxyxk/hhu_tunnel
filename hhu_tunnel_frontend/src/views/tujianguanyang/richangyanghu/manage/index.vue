<template>
    <div>
      <el-row :gutter="10">
        <el-col :span="6" style="border-style: solid;">
          <el-button @click="addNodeData">添加</el-button>
          <el-button @click="editNodeData">编辑</el-button>
          <el-button @click="deleteNodeData">删除</el-button>
          <el-tree ref="treeData" :data="dict" :props="defaultProps" show-checkbox node-key="id"
            :expand-on-click-node="false"  @node-click="handleNodeClick" check-on-click-node
            style="margin-top: 0.5rem;" :highlight-current="true" >
          </el-tree>
        </el-col>
  
        <el-col :span="18">
          <div v-if="isTableVisual">
            <el-button type="primary" plain @click="addTableData">添加</el-button>
            <el-button type="primary" plain @click="editTableData">编辑</el-button>
            <el-button type="primary" plain @click="deleteTableData">删除</el-button>
            <el-table ref="tab" :data="tableData" border class="tab-table" @selection-change="handleSelectionChange"
              :key="Math.random()" style="margin-top: 1rem;height: 36rem;" :header-cell-style="{ textAlign: 'center' }"
              :cell-style="{ textAlign: 'center' }" height="575">
              <el-table-column type="selection" width="55" :fixed="true">
              </el-table-column>
              <el-table-column prop="createtime" label="创建日期" width="180" >
              </el-table-column>
              <el-table-column prop="name" label="名称/概要" width="180" >
              </el-table-column>
              <el-table-column prop="sponsor" label="发起人" width="180" >
              </el-table-column>
              <el-table-column prop="company" label="发起单位" width="180" >
              </el-table-column>
              <el-table-column prop="endtime" label="结束时间" width="180"  >
              </el-table-column>
              <el-table-column prop="status" label="当前状态" width="180" >
                <template slot-scope="scope">
                  <div v-if="scope.row.status === 0 || scope.row.status === '0'">
                    <span>健康</span>
                  </div>
                  <div v-if="scope.row.status === 1 || scope.row.status === '1'">
                    <span>维修</span>
                  </div>
                </template>
              </el-table-column>
              <el-table-column prop="node" label="当前节点" width="180" >
              </el-table-column>
              <el-table-column fixed="right" label="操作" width="100">
                <template slot-scope="scope">
  
                  <el-button type="text" size="small" @click="view(scope.row.id)"> 查看 </el-button>
                </template>
              </el-table-column>
            </el-table>
            <el-pagination background layout="prev, pager, next" :current-page.sync="pages.currentPage"
              :page-size="pages.size" :total="pages.total" @current-change="handleCurrentChange" style="margin-top: 1rem;">
            </el-pagination>
          </div>
        </el-col>
      </el-row>
      <el-dialog title="添加" :visible.sync="addTableDataVisual" width="35%" :show-close="false">
        <el-form ref="addform" :model="tempData" label-width="10rem">
          <el-form-item label="创建日期" style="margin-bottom: 0.6rem;">
            <el-date-picker v-model="tempData['createtime']" type="datetime" placeholder="选择日期时间"
              value-format="yyyy-MM-dd HH:mm:ss">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="名称/概要" style="margin-bottom: 0.6rem;">
            <el-input v-model="tempData['name']"></el-input>
          </el-form-item>
          <el-form-item label="发起人" style="margin-bottom: 0.6rem;">
            <el-input v-model="tempData['sponsor']"></el-input>
          </el-form-item>
          <el-form-item label="发起单位" style="margin-bottom: 0.6rem;">
            <el-input v-model="tempData['company']"></el-input>
          </el-form-item>
          <el-form-item label="结束时间" style="margin-bottom: 0.6rem;">
            <el-date-picker v-model="tempData['endtime']" type="datetime" placeholder="选择日期时间"
              value-format="yyyy-MM-dd HH:mm:ss">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="状态" style="margin-bottom: 0.6rem;">
            <el-select v-model="tempData['status']" placeholder="选择状态" style="width: 20rem;">
              <el-option v-for="item2 in stausList" :key="item2.value" :label="item2.label" :value="item2.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="当前节点" style="margin-bottom: 0.6rem;">
            <el-input v-model="tempData['node']"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="cancelAddTableData">取 消</el-button>
          <el-button type="primary" @click="submitAddTableData">确 定</el-button>
        </span>
      </el-dialog>
  
      <el-dialog title="修改" :visible.sync="edditTableDataVisual" width="35%" :show-close="false">
        <el-form ref="edditform" :model="tempData" label-width="10rem">
          <el-form-item label="创建日期" style="margin-bottom: 0.6rem;">
            <el-date-picker v-model="tempData['createtime']" type="datetime" placeholder="选择日期时间"
              value-format="yyyy-MM-dd HH:mm:ss">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="名称/概要" style="margin-bottom: 0.6rem;">
            <el-input v-model="tempData['name']"></el-input>
          </el-form-item>
          <el-form-item label="发起人" style="margin-bottom: 0.6rem;">
            <el-input v-model="tempData['sponsor']"></el-input>
          </el-form-item>
          <el-form-item label="发起单位" style="margin-bottom: 0.6rem;">
            <el-input v-model="tempData['company']"></el-input>
          </el-form-item>
          <el-form-item label="结束时间" style="margin-bottom: 0.6rem;">
            <el-date-picker v-model="tempData['endtime']" type="datetime" placeholder="选择日期时间"
              value-format="yyyy-MM-dd HH:mm:ss">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="结束时间" style="margin-bottom: 0.6rem;">
            <el-select v-model="tempData['status']" placeholder="选择状态" style="width: 20rem;">
              <el-option v-for="item2 in stausList" :key="item2.value" :label="item2.label" :value="item2.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="当前节点" style="margin-bottom: 0.6rem;">
            <el-input v-model="tempData['node']"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="cancelEdditTableData">取 消</el-button>
          <el-button type="primary" @click="submitEdditTableData">确 定</el-button>
        </span>
      </el-dialog>
  
      <!-- 添加树节点的弹框 -->
      <div v-if="addTreeNodeVisual">
        <el-dialog title="添加" :visible.sync="addTreeNodeVisual" width="35%" :show-close="false">
          <el-form ref="addform" :model="newChild" label-width="10rem">
            <el-form-item label="父节点" style="margin-bottom: 0.6rem;">
              <el-input v-model="newChild['parent']['label']" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="分类名称" style="margin-bottom: 0.6rem;" :required="true
            ">
              <el-input v-model="newChild.label"></el-input>
            </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
            <el-button @click="cancelTreeNode">取 消</el-button>
            <el-button type="primary" @click="submitTreeNode">确 定</el-button>
          </span>
        </el-dialog>
      </div>
      <div v-if="editNodeVisual">
        <!-- 编辑节点 -->
        <el-dialog title="编辑" :visible.sync="editNodeVisual" width="35%" :show-close="false">
          <el-form ref="addform" :model="editNode" label-width="10rem">
            <!-- <el-form-item label="父节点" style="margin-bottom: 0.6rem;" :disabled="true">
              <el-input v-model="editNode.parent.label"></el-input>
            </el-form-item> -->
            <el-form-item label="分类名称" style="margin-bottom: 0.6rem;" :required="true
            ">
              <el-input v-model="editNode.label"></el-input>
            </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
            <el-button @click="cancelEditTreeNode">取 消</el-button>
            <el-button type="primary" @click="submitEditTreeNode">确 定</el-button>
          </span>
        </el-dialog>
      </div>
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
        stausList: [{
          value: 0,
          label: '健康'
        },
        {
          value: 1,
          label: '维修'
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
        index: 100,
        newChild: {},
        editNode: {},
        oldEditNode: {},
        addTreeNodeVisual: false,
        editNodeVisual: false,
      }
    },
    methods: {
      addNodeData() {
        this.newChild = {
          id: '',
          itemId: '',
          label: '',
          describle: ''
        }
        //如果没有点击就直接添加
        if (this.selectedNode['id'] === undefined) {
          this.selectedNode = this.dict[0]
          this.newChild['parent'] = this.dict[0]
        }
        else {
          this.newChild['parent'] = this.selectedNode
        }
        this.newChild.id = this.index++
        this.addTreeNodeVisual = true
      },
      submitTreeNode(){
        if ( this.newChild.label === '' || this.newChild.label === null) {
          this.$message.error('分类名称不能为空');
          return
        }
          //发送newChild发送给后端,然后更新返回的id
          this.newChild['id'] = this.index++
        if (!this.selectedNode.children) {
          this.$set(this.selectedNode, 'children', []);
        }
        this.selectedNode['children'].push(this.newChild);
      
        this.addTreeNodeVisual = false
      },
      cancelTreeNode(){
        this.addTreeNodeVisual = false
      },
      editNodeData() {
        this.addTreeNodeVisual = false
        if (this.selectedNode.id === '' || this.selectedNode.id === undefined) {
          this.$message({
            message: '请选择要编辑的节点',
            type: 'warning'
          });
          return
        }
        this.editNode = this.selectedNode
        this.oldEditNode = new Object()
        this.oldEditNode['label'] = this.selectedNode.label
        this.editNodeVisual = true
       },
      submitEditTreeNode(){
        this.editNodeVisual = false
      },
      cancelEditTreeNode(){
        this.selectedNode.label = this.oldEditNode.label
        this.editNodeVisual = false
      },
      deleteNodeData() {
        if (this.selectedNode['label'] === '全部') {
          this.$message({
            message: '无法删除根节点',
            type: 'warning'
          });
          return
        }
        if (this.selectedNode.id === undefined || this.selectedNode.id === null || this.selectedNode.id === '') {
          this.$message({
            message: '请选择删除节点',
            type: 'warning'
          });
          return
        }
        this.$confirm('此操作将永久删除, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          console.log(this.selectedNode)
          //用户点击了确定,要删除信息
          this.$refs.treeData.remove(this.selectedNode.id)
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
  
       },
      //点击节点事件
      handleNodeClick(data) {
        this.selectedNode = data
        var s = this.$refs.treeData.getCheckedNodes(false, true)
        if (s.length != 4)
          return
        var queyEntity = {
          suidaoId: -1,
          firstIndexId: -1,
          secondIndexId: -1,
        }
        queyEntity.suidaoId = s[1].id
        queyEntity.firstIndexId = s[2].id
        queyEntity.secondIndexId = s[3].id
        this.queryEntity = queyEntity
        //
        this.getTableData(queyEntity)
        this.isTableVisual = true
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
      getTableData(queryEntity) {
        this.tableData = [{
          id: 1,
          createtime: '2023-03-09 13:56:30',
          name: '扬子江隧道-隧道土建',
          sponsor: '张三',
          company: '管养中心',
          endtime: '2023-03-09 13:56:30',
          status: 0,
          node: '土建日常'
        },
        {
          id: 2,
          createtime: '2023-03-09 13:56:30',
          name: '扬子江隧道-隧道土建',
          sponsor: '张三',
          company: '管养中心',
          endtime: '2023-03-09 13:56:30',
          status: 1,
          node: '土建日常'
        },
        {
          id: 3,
          createtime: '2023-03-09 13:56:30',
          name: '扬子江隧道-隧道土建',
          sponsor: '张三',
          company: '管养中心',
          endtime: '2023-03-09 13:56:30',
          status: 1,
          node: '土建日常'
        }]
      },
      view(id) {
        let path = this.$router.resolve({
          path: '/view/yanghu/' + id
        })
        window.open(path.href, '_blank')
      },
    },
    created() {
      this.dict[0].children = [
        {
          id: 1001,
          label: '定淮门隧道',
          children: [
            {
              id: 1,
              label: '2023年',
              children: [{
                id: 2,
                label: '3月',
                children: [],
              },
              ]
            }]
        },
        {
          id: 1002,
          label: '扬子江隧道',
          children: [],
        }]
    },
  }
  </script>
  
  <style></style>