<template>
  <div>
    <el-row :gutter="10">
      <el-col :span="6" style="border-style: solid;">
        <el-button @click="addChildren">添加</el-button>
        <el-button @click="editNodeData">编辑</el-button>
        <el-button @click="deleteNodeData">删除</el-button>
        <el-tree ref="dict" :data="dict" :props="defaultProps" show-checkbox node-key="id" :expand-on-click-node="false"
        @node-click="handleNodeClick" style="margin-top: 0.5rem;" :highlight-current="true" check-on-click-node>
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
            <el-table-column type="selection" width="55">
            </el-table-column>
            <el-table-column label="项目号" prop="itemId" >
            </el-table-column>
            <el-table-column label="项目名称" prop="label" >
            </el-table-column>
            <el-table-column label="单价" prop="price" >
            </el-table-column>
            <el-table-column label="单位" prop="unit" >
            </el-table-column>
            <el-table-column label="备注" prop="remarks" >
            </el-table-column>
          </el-table>
          <el-pagination background layout="prev, pager, next" :current-page.sync="pages.currentPage"
            :page-size="pages.size" :total="pages.total" @current-change="handleCurrentChange" style="margin-top: 1rem;">
          </el-pagination>
        </div>
      </el-col>
    </el-row>
    <!-- 添加树节点的弹框 -->
    <div v-if="addTreeNodeVisual">
      <el-dialog title="添加" :visible.sync="addTreeNodeVisual" width="35%" :show-close="false">
        <el-form ref="addform" :model="newChild" label-width="10rem">
          <el-form-item label="父节点" style="margin-bottom: 0.6rem;">
            <el-input v-model="newChild['parent']['label']" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="分类编号" style="margin-bottom: 0.6rem;" :required="true
          ">
            <el-input v-model="newChild.itemId"></el-input>
          </el-form-item>
          <el-form-item label="分类名称" style="margin-bottom: 0.6rem;" :required="true
          ">
            <el-input v-model="newChild.label"></el-input>
          </el-form-item>
          <el-form-item label="分类描述" style="margin-bottom: 0.6rem;">
            <el-input type="textarea" v-model="newChild.describle"></el-input>
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
          <el-form-item label="分类编号" style="margin-bottom: 0.6rem;" :required="true
          ">
            <el-input v-model="editNode.itemId"></el-input>
          </el-form-item>
          <el-form-item label="分类名称" style="margin-bottom: 0.6rem;" :required="true
          ">
            <el-input v-model="editNode.label"></el-input>
          </el-form-item>
          <el-form-item label="分类描述" style="margin-bottom: 0.6rem;">
            <el-input type="textarea" v-model="editNode.describle"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="cancelEditTreeNode">取 消</el-button>
          <el-button type="primary" @click="submitEditTreeNode">确 定</el-button>
        </span>
      </el-dialog>
    </div>
    <!-- 添加表格数据弹框 -->
    <el-dialog title="添加" :visible.sync="addTableDataVisual" width="35%" :show-close="false">
      <el-form ref="addform" :model="tempData" label-width="10rem">
        <el-form-item label="项目号" style="margin-bottom: 0.6rem;" :disabled="true">
          <el-input v-model="tempData['itemId']"></el-input>
        </el-form-item>
        <el-form-item label="项目名称" style="margin-bottom: 0.6rem;" :disabled="true">
          <el-input v-model="tempData['label']"></el-input>
        </el-form-item>
        <el-form-item label="单价" style="margin-bottom: 0.6rem;" :disabled="true">
          <el-input v-model="tempData['price']"></el-input>
        </el-form-item>
        <el-form-item label="单位" style="margin-bottom: 0.6rem;" :disabled="true">
          <el-input v-model="tempData['unit']"></el-input>
        </el-form-item>
        <el-form-item label="备注" style="margin-bottom: 0.6rem;" :disabled="true">
          <el-input v-model="tempData['remarks']"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelAddTableData">取 消</el-button>
        <el-button type="primary" @click="submitAddTableData">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 编辑表格 -->
    <el-dialog title="编辑" :visible.sync="editTableDataVisual" width="35%" :show-close="false">
      <el-form ref="editform" :model="tempData" label-width="10rem">
        <el-form-item label="项目号" style="margin-bottom: 0.6rem;" :disabled="true">
          <el-input v-model="tempData['itemId']"></el-input>
        </el-form-item>
        <el-form-item label="项目名称" style="margin-bottom: 0.6rem;" :disabled="true">
          <el-input v-model="tempData['label']"></el-input>
        </el-form-item>
        <el-form-item label="单价" style="margin-bottom: 0.6rem;" :disabled="true">
          <el-input v-model="tempData['price']"></el-input>
        </el-form-item>
        <el-form-item label="单位" style="margin-bottom: 0.6rem;" :disabled="true">
          <el-input v-model="tempData['unit']"></el-input>
        </el-form-item>
        <el-form-item label="备注" style="margin-bottom: 0.6rem;" :disabled="true">
          <el-input v-model="tempData['remarks']"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelEditTableData">取 消</el-button>
        <el-button type="primary" @click="submitEditTableData">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      dict: [
        {
          id: -1,
          itemId: -1,
          label: '全部',
          children: [],
        }
      ],
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      selectedTree: {},
      newChild: {},
      addTreeNodeVisual: false,
      addTreeData: {},
      isTableVisual: false,
      tableData: [],
      selectedData: [],
      pages: {
        //每页展示条数
        size: 10,
        //总共多少条
        total: 10,
        currentPage: 1
      },
      tempData: {},
      addTableDataVisual: false,
      editTableDataVisual: false,
      oldTempData: {},
      editNode: {},
      oldEditNode: {},
      editNodeVisual: false,
      deleteNodeVisual: false,
      index: 1,
    }
  },
  created() {
    //api获取tableData
    this.tableData = [
      {
        id: 1,
        itemId: '201231311.222',
        label: '测试',
        price: 10000,
        unit: '元',
        remarks: '测试'
      },
      {
        id: 2,
        itemId: '201231311.2223',
        label: '测试2',
        price: 10000,
        unit: '元',
        remarks: '测试'
      }
    ]
  },
  methods: {
    //添加树节点
    addChildren() {
      this.newChild = {
        id: '',
        itemId: '',
        label: '',
        describle: ''
      }
      //如果没有点击就直接添加
      if (this.selectedTree['id'] === undefined) {
        this.selectedTree = this.dict[0]
        this.newChild['parent'] = this.dict[0]
      }
      else {
        this.newChild['parent'] = this.selectedTree
      }
      this.newChild.id = this.index++
      this.addTreeNodeVisual = true
    },
    handleNodeClick(data) {
      this.selectedTree = data
      this.isTableVisual = true
    },
    submitTreeNode() {
      if (this.newChild.itemId === '' || this.newChild.itemId === null || this.newChild.label === '' || this.newChild.label === null) {
        this.$message.error('分类编号和分类名称不能为空');
        return
      }
      if (!this.selectedTree.children) {
        this.$set(this.selectedTree, 'children', []);
      }
      this.selectedTree['children'].push(this.newChild);
      this.addTreeNodeVisual = false
    },
    cancelTreeNode() {
      this.addTreeNodeVisual = false
    },
    addTableData() {
      this.editTableDataVisual = false
      this.tempData = {
        id: '',
        label: '',
        price: '',
        unit: '',
        remarks: '',
      }
      this.addTableDataVisual = true
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
    //表格数据选中
    handleSelectionChange(data) {
      this.selectedData = data
    },
    //分页
    handleCurrentChange(current) {

    },
    //取消添加表格数据
    cancelAddTableData() {
      this.addTableDataVisual = false
    },
    submitAddTableData() {
      this.tableData.push(this.tempData)
      this.addTableDataVisual = false
    },
    //编辑表格数据
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
      this.editTableDataVisual = true
    },
    cancelEditTableData() {
      this.selectedData = []
      for (var i = 0; i < this.tableData.length; i++) {
        if (this.tableData[i].id === this.oldTempData.id) {
          this.tableData[i] = this.oldTempData
          break
        }
      }
      this.editTableDataVisual = false
    },
    submitEditTableData() {
      for (var i = 0; i < this.tableData.length; i++) {
        if (this.tableData[i].id === this.tempData.id) {
          this.tableData[i] = this.tempData
          break
        }
      }
      this.editTableDataVisual = false
    },
    //编辑节点
    editNodeData() {
      this.addTreeNodeVisual = false
      if (this.selectedTree.id === '' || this.selectedTree.id === undefined) {
        this.$message({
          message: '请选择要编辑的节点',
          type: 'warning'
        });
        return
      }
      this.editNode = this.selectedTree
      this.oldEditNode = new Object()
      this.oldEditNode['itemId'] = this.selectedTree.itemId
      this.oldEditNode['label'] = this.selectedTree.label
      this.oldEditNode['describle'] = this.selectedTree.describle
      this.editNodeVisual = true
    },
    //删除节点
    deleteNodeData() {
      if (this.selectedTree['parent'] === undefined) {
        this.$message({
          message: '无法删除根节点',
          type: 'warning'
        });
        return
      }
      if (this.selectedTree.id === undefined || this.selectedTree.id === null || this.selectedTree.id === '') {
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
        //用户点击了确定,要删除信息
        this.$refs.dict.remove(this.selectedTree.id)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });

    },
    submitEditTreeNode() {
      this.editNodeVisual = false
    },
    cancelEditTreeNode() {
      this.selectedTree.itemId = this.oldEditNode.itemId
      this.selectedTree.label = this.oldEditNode.label
      this.selectedTree.describle = this.oldEditNode.describle
      this.editNodeVisual = false
    },

  },
}
</script>

<style></style>