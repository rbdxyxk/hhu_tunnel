<template>
  <div>
    <el-row :gutter="10">
      <el-col :span="6" style="border-style: solid;">
        <el-button @click="addNodeData">添加</el-button>
        <el-button @click="editNodeData">编辑</el-button>
        <el-button @click="deleteNodeData">删除</el-button>
        <el-tree ref="treeData" :data="dict" :props="defaultProps" show-checkbox node-key="id"
          :expand-on-click-node="false" @node-click="handleNodeClick" :check-on-click-node="true"
          style="margin-top: 0.5rem;" :highlight-current="true">
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
            <el-table-column label="报告分类" prop="bgfl">
            </el-table-column>
            <el-table-column label="报告名称" prop="bgmc">
            </el-table-column>
            <el-table-column label="编制单位" prop="bzdw">
            </el-table-column>
            <el-table-column label="生成时间" prop="scsj" width="160">
            </el-table-column>
            <el-table-column fixed="right" label="操作" width="100">
              <template slot-scope="scope">
                <el-button type="text" size="small" @click="view(scope.row.id)"> 查看</el-button>
                <!-- <el-button type="text" size="small" @click="view(scope.row.id)">更新</el-button> -->
              </template>
            </el-table-column>
          </el-table>
          <el-pagination background layout="prev, pager, next" :current-page.sync="pages.currentPage"
            :page-size="pages.size" :total="pages.total" @current-change="handleCurrentChange" style="margin-top: 1rem;">
          </el-pagination>
        </div>
      </el-col>
    </el-row>
    <el-dialog title="添加" :visible.sync="addTableDataVisual" width="35%" :show-close="false" @close="colseDialog">
      <el-form ref="addform" :model="tempData" label-width="10rem">
        <el-form-item label="报告分类" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['bgfl']"></el-input>
        </el-form-item>
        <el-form-item label="报告名称" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['bgmc']"></el-input>
        </el-form-item>
        <el-form-item label="编制单位" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['bzdw']"></el-input>
        </el-form-item>
        <el-form-item label="生成时间" style="margin-bottom: 0.6rem;">
          <el-date-picker v-model="tempData['scsj']" type="datetime" placeholder="选择日期时间"
            value-format="yyyy-MM-dd HH:mm:ss">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="上传报表" style="margin-bottom: 0.6rem;">
          <el-upload class="upload-demo" :action="uploadUrl" :on-success="addfileId" ref="uploadSingle" :limit="1"
            :on-exceed="handleExceed" :on-remove="removeFile" :file-list="fileList">
            <el-button type="info" plain size="small">上传<i class="el-icon-upload el-icon--right"></i></el-button>
          </el-upload>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelAddTableData">取 消</el-button>
        <el-button type="primary" @click="submitAddTableData">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="修改" :visible.sync="edditTableDataVisual" width="35%" :show-close="false" @close="colseDialog">
      <el-form ref="edditform" :model="tempData" label-width="10rem">
        <el-form-item label="报告分类" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['bgfl']"></el-input>
        </el-form-item>
        <el-form-item label="报告名称" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['bgmc']"></el-input>
        </el-form-item>
        <el-form-item label="编制单位" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['bzdw']"></el-input>
        </el-form-item>
        <el-form-item label="生成时间" style="margin-bottom: 0.6rem;">
          <el-date-picker v-model="tempData['scsj']" type="datetime" placeholder="选择日期时间"
            value-format="yyyy-MM-dd HH:mm:ss">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="上传报表" style="margin-bottom: 0.6rem;">
          <el-upload class="upload-demo" :action="uploadUrl" :on-success="addfileId" ref="uploadSingle" :limit="1"
            :on-exceed="handleExceed" :on-remove="removeFile" :file-list="fileList">
            <el-button type="info" plain size="small">上传<i class="el-icon-upload el-icon--right"></i></el-button>
          </el-upload>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelEdditTableData">取 消</el-button>
        <el-button type="primary" @click="submitEdditTableData">确 定</el-button>
      </span>
    </el-dialog>
    <div v-if="addNodeVisual">
      <el-dialog title="添加" :visible.sync="addNodeVisual" width="35%" :show-close="false">
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
import { viewFileOnline,removeFile} from '@/api/File'

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
      newChild: {},
      index: 3,
      addNodeVisual: false,
      editNodeVisual: false,
      oldEditNode: {},
      editNode: {},
      uploadUrl: process.env.VUE_APP_BASE_API + '/file/upload',
      fileList: [],
    }
  },
  methods: {
    colseDialog() {
      // this.$refs.uploadSingle.clearFiles()
      this.fileList = []
      // document.body.style.overflow = '';
      // window.scrollTo(0, this.top);
    },
    removeFile(files, fileList) {
      var id = files.fileId
      //通过id将file删除
      removeFile(id).then(response => {
        if (response.data === false)
          this.$message.warning('删除失败')
        for (var i = 0; i < this.fileList.length; i++) {
          if (this.fileList[i].fileId === id) {
            this.fileList.splice(i, 1)
            break
          }
        }
      })
    },
    addfileId(response, file, fileList) {
      var temp = new Object()
      temp['name'] = response.data.orginalName
      temp['url'] = response.data.fileUrl
      temp['fileId'] = response.data.fileId
      this.fileList.push(temp)
    },
    handleExceed(files, fileList) {
      this.$message.warning('最多只能上传一份报表')
    },
    addNodeData() {
      this.newChild = {
        id: '',
        itemId: '',
        label: '',
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
      this.addNodeVisual = true
    },
    cancelTreeNode() {
      this.newChild = {}
      this.addNodeVisual = false
    },
    submitTreeNode() {
      if (this.newChild.itemId === '' || this.newChild.itemId === null || this.newChild.label === '' || this.newChild.label === null) {
        this.$message.error('分类编号和分类名称不能为空');
        return
      }
      if (!this.selectedNode.children) {
        this.$set(this.selectedNode, 'children', []);
      }
      this.selectedNode['children'].push(this.newChild);
      this.addNodeVisual = false
    },
    editNodeData() {
      if (this.selectedNode.id === '' || this.selectedNode.id === undefined) {
        this.$message({
          message: '请选择要编辑的节点',
          type: 'warning'
        });
        return
      }
      this.editNode = this.selectedNode
      this.oldEditNode = new Object()
      this.oldEditNode['itemId'] = this.selectedNode.itemId
      this.oldEditNode['label'] = this.selectedNode.label
      this.editNodeVisual = true
    },
    cancelEditTreeNode() {
      this.selectedNode.itemId = this.oldEditNode.itemId
      this.selectedNode.label = this.oldEditNode.label
      this.editNodeVisual = false

    },
    submitEditTreeNode() {
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
      if (s.length != 2)
        return
      var queyEntity = {
        itemId: -1,
      }
      queyEntity.itemId = s[1].id
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
      var temp = new Object()
      if (this.selectedData[0].fileId != undefined && this.selectedData[0].fileId != null && this.selectedData[0].fileId != '') {
        temp['name'] = this.selectedData[0].fileName
        temp['url'] = this.selectedData[0].fileUrl
        temp['fileId'] = this.selectedData[0].fileId
        this.fileList.push(temp)
      }
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
      if (this.fileList.length > 0) {
        this.tempData['fileId'] = this.fileList[0].fileId
        this.tempData['fileUrl'] = this.fileList[0].url
        this.tempData['fileName'] = this.fileList[0].name
      }
      else {
        this.tempData['fileId'] = null
        this.tempData['fileUrl'] = null
        this.tempData['fileName'] = null
      } 
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
        bgfl: '机电定期检测报告',
        bgmc: '2020年外线配电检测报告',
        bzdw: '中交基础设施养护集团有限公司',
        scsj: '2020-09-13 15:26:07',
        fileId: '1648881188808445953',
        fileName: '测试.pdf',
        fileUrl: 'http://localhost:9000/file/1681958884767.pdf'
      },
      {
        id: 2,
        bgfl: '机电定期检测报告',
        bgmc: '2020年外线配电检测报告',
        bzdw: '中交基础设施养护集团有限公司',
        scsj: '2020-09-13 15:26:07',
        fileId: '1648881188808445953',
        fileName: '测试.pdf',
        fileUrl: 'http://localhost:9000/file/1681958884767.pdf'
      },
      {
        id: 3,
        bgfl: '机电定期检测报告',
        bgmc: '2020年外线配电检测报告',
        bzdw: '中交基础设施养护集团有限公司',
        scsj: '2020-09-13 15:26:07',
        fileId: '1648881188808445953',
        fileName: '测试.pdf',
        fileUrl: 'http://localhost:9000/file/1681958884767.pdf'
      }
      ]
    },
    view(id) {
      var fileId = "1648881188808445953";
      viewFileOnline(fileId).then((res) => {
        // loading.close();
        if (res.size === 0) {
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
      })
    },
  },
  created() {
    this.dict[0].children = [
      {
        id: 1,
        itemId: '',
        label: '监测报告1',
        children: []
      },
      {
        id: 2,
        itemId: '',
        label: '监测报告2',
        children: [],
      }]
  },
}
</script>

<style></style>