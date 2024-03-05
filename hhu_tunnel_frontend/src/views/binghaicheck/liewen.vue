<template>
  <div style="height: 100rem;">
    <el-row :gutter="10">
      <el-col :span="6" style="border-style: solid;">
        <el-tree ref="treeData" :data="dict" :props="defaultProps" show-checkbox node-key="id"
          :expand-on-click-node="false" :check-on-click-node="true" @node-click="handleNodeClick"
          style="margin-top: 0.5rem;" :highlight-current="true">
        </el-tree>
      </el-col>

      <el-col :span="18">
       
        <div v-if="isTableVisual">
          <el-button type="primary" plain @click="usePython" style="margin-top: 1rem;">裂缝智能化识别</el-button>
          <h2>单张裂缝病害分析</h2>
          <el-button type="primary" plain @click="addTableData">添加</el-button>
          <el-button type="primary" plain @click="editTableData">编辑</el-button>
          <el-button type="primary" plain @click="deleteTableData">删除</el-button>
          <el-table ref="tab" :data="tableData" border class="tab-table" @selection-change="handleSelectionChange"
            :key="Math.random()" style="margin-top: 1rem;" :header-cell-style="{ textAlign: 'center' }"
            :cell-style="{ textAlign: 'center' }" height="700">
            <el-table-column type="selection" width="55" :fixed="true">
            </el-table-column>
            <el-table-column label="日期" prop="rq" width="160">
            </el-table-column>
            <el-table-column label="桩号" prop="zhuanghao">
            </el-table-column>
            <el-table-column label="图片输入" prop="fileUrl">
              <template slot-scope="scope">
                <el-image v-if="scope.row.fileUrl != undefined && scope.row.fileUrl != null && scope.row.fileUrl != ''"
                  :src="scope.row.fileUrl" style="width: 100px; height: 100px;" fit="fill" />
                <span v-else>未上传图片</span>
              </template>
            </el-table-column>
            <el-table-column label="识别结果" prop="sbjg">
            </el-table-column>
            <el-table-column label="裂缝宽度/mm" prop="lfkd">
            </el-table-column>
            <el-table-column label="裂缝长度/mm" prop="lfcd">
            </el-table-column>
            <el-table-column label="裂缝性质" prop="lfxz">
            </el-table-column>
            <el-table-column label="备注" prop="beizhu">
            </el-table-column>
          </el-table>
          <el-pagination background layout="prev, pager, next" :current-page.sync="pages.currentPage"
            :page-size="pages.size" :total="pages.total" @current-change="handleCurrentChange" style="margin-top: 1rem;">
          </el-pagination>
          <hr />


          <h2 style="margin-top: 2rem;">多张裂缝病害分析</h2>
          <el-button type="primary" plain @click="addTableData2">添加</el-button>
          <el-button type="primary" plain @click="editTableData2">编辑</el-button>
          <el-button type="primary" plain @click="deleteTableData2">删除</el-button>
          <el-table ref="tab" :data="tableData2" border class="tab-table" @selection-change="handleSelectionChange"
            :key="Math.random()" style="margin-top: 1rem;" :header-cell-style="{ textAlign: 'center' }"
            :cell-style="{ textAlign: 'center' }" height="1000">
            <el-table-column type="selection" width="55" :fixed="true">
            </el-table-column>
            <el-table-column label="日期" prop="rq" width="160">
            </el-table-column>
            <el-table-column label="桩号" prop="zhuanghao">
            </el-table-column>
            <el-table-column label="图片输入" prop="fileUrl">
              <template slot-scope="scope">
                <span
                  v-if="scope.row.fileUrl === null || scope.row.fileUrl === undefined || scope.row.fileUrl === '' || scope.row.fileUrl.length === 0">未上传图片</span>
                <el-image v-for="(item, index) in scope.row.fileUrl " :key="index" :src="item"
                  style="width: 100px; height: 100px;" fit="fill" />
              </template>
            </el-table-column>
            <el-table-column label="识别结果" prop="sbjg">
            </el-table-column>
            <el-table-column label="裂缝尺寸统计" prop="lfcctj">
            </el-table-column>
            <el-table-column label="裂缝病害状态" prop="lfbhzt">
            </el-table-column>
            <el-table-column label="备注" prop="beizhu">
            </el-table-column>
          </el-table>
          <el-pagination background layout="prev, pager, next" :current-page.sync="pages2.currentPage"
            :page-size="pages2.size" :total="pages2.total" @current-change="handleCurrentChange2" style="margin-top: 1rem;">
          </el-pagination>
        </div>
      </el-col>
    </el-row>
    <!-- 单张裂缝病害分析 -->
    <div v-if="addTableDataVisual">
    <el-dialog title="添加" :visible.sync="addTableDataVisual" width="35%" :show-close="false" @close="colseDialog">
      <el-form ref="addform" :model="tempData" label-width="10rem">
        <el-form-item label="日期" style="margin-bottom: 0.6rem;">
          <el-date-picker v-model="tempData['rq']" type="datetime" placeholder="选择日期时间"
            value-format="yyyy-MM-dd HH:mm:ss">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="桩号" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['zhuanghao']"></el-input>
        </el-form-item>
        <el-form-item label="上传图片" style="margin-bottom: 0.6rem;">
          <el-upload class="upload-demo" :action="uploadUrl" :on-success="addfileId" ref="uploadSingle" :limit="1"
            :on-exceed="handleExceed" :on-remove="removeFile" :file-list="fileList" list-type="picture">
            <el-button type="info" plain size="small">上传<i class="el-icon-upload el-icon--right"></i></el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="识别结果" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['sbjg']"></el-input>
        </el-form-item>
        <el-form-item label="裂缝宽度/mm" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['lfkd']"></el-input>
        </el-form-item>
        <el-form-item label="裂缝长度/mm" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['lfcd']"></el-input>
        </el-form-item>
        <el-form-item label="裂缝性质" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['lfxz']"></el-input>
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
  </div>

  <div v-if="edditTableDataVisual">
    <el-dialog title="修改" :visible.sync="edditTableDataVisual" width="35%" :show-close="false" @close="colseDialog">
      <el-form ref="edditform" :model="tempData" label-width="10rem">
        <el-form-item label="日期" style="margin-bottom: 0.6rem;">
          <el-date-picker v-model="tempData['rq']" type="datetime" placeholder="选择日期时间"
            value-format="yyyy-MM-dd HH:mm:ss">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="桩号" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['zhuanghao']"></el-input>
        </el-form-item>
        <el-form-item label="上传图片" style="margin-bottom: 0.6rem;">
          <el-upload class="upload-demo" :action="uploadUrl" :on-success="addfileId" ref="uploadSingle" :limit="1"
            :on-exceed="handleExceed" :on-remove="removeFile" :file-list="fileList" list-type="picture">
            <el-button type="info" plain size="small">上传<i class="el-icon-upload el-icon--right"></i></el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="识别结果" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['sbjg']"></el-input>
        </el-form-item>
        <el-form-item label="裂缝宽度/mm" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['lfkd']"></el-input>
        </el-form-item>
        <el-form-item label="裂缝长度/mm" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['lfcd']"></el-input>
        </el-form-item>
        <el-form-item label="裂缝性质" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['lfxz']"></el-input>
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
</div>

  <div v-if="addTableDataVisual2">
    <!-- 多张  -->
    <el-dialog title="添加" :visible.sync="addTableDataVisual2" width="35%" :show-close="false" @close="colseDialog2">
      <el-form ref="addform" :model="tempData" label-width="10rem">
        <el-form-item label="日期" style="margin-bottom: 0.6rem;">
          <el-date-picker v-model="tempData['rq']" type="datetime" placeholder="选择日期时间"
            value-format="yyyy-MM-dd HH:mm:ss">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="桩号" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['zhuanghao']"></el-input>
        </el-form-item>
        <el-form-item label="上传图片" style="margin-bottom: 0.6rem;">
          <el-upload class="upload-demo" :action="uploadUrl" :on-success="addfileId2" ref="uploadMany"
            :on-remove="removeFile" :file-list="fileList" list-type="picture">
            <el-button type="info" plain size="small">上传<i class="el-icon-upload el-icon--right"></i></el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="识别结果" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['sbjg']"></el-input>
        </el-form-item>
        <el-form-item label="裂缝尺寸统计" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['lfcctj']"></el-input>
        </el-form-item>
        <el-form-item label="裂缝病害状态" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['lfbhzt']"></el-input>
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
  </div>

  <div v-if="edditTableDataVisual2">
    <el-dialog title="修改" :visible.sync="edditTableDataVisual2" width="35%" :show-close="false" @close="colseDialog">
      <el-form ref="edditform" :model="tempData" label-width="10rem">
        <el-form-item label="日期" style="margin-bottom: 0.6rem;">
          <el-date-picker v-model="tempData['rq']" type="datetime" placeholder="选择日期时间"
            value-format="yyyy-MM-dd HH:mm:ss">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="桩号" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['zhuanghao']"></el-input>
        </el-form-item>
        <el-form-item label="上传图片" style="margin-bottom: 0.6rem;">
          <el-upload class="upload-demo" :action="uploadUrl" :on-success="addfileId2" ref="uploadMany"
            :on-remove="removeFile" :file-list="fileList" list-type="picture">
            <el-button type="info" plain size="small">上传<i class="el-icon-upload el-icon--right"></i></el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="识别结果" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['sbjg']"></el-input>
        </el-form-item>
        <el-form-item label="裂缝尺寸统计" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['lfcctj']"></el-input>
        </el-form-item>
        <el-form-item label="裂缝病害状态" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['lfbhzt']"></el-input>
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
  </div>
</template>

<script>
import { viewFileOnline, removeFile } from '@/api/File'
import request from '@/utils/request'
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
      oldEditNode: {},
      editNode: {},
      uploadUrl: process.env.VUE_APP_BASE_API + '/file/upload',
      tableData2: [],
      addTableDataVisual2: false,
      edditTableDataVisual2: false,
      fileList: [],
      top: 0,
      left: 0,
      dialogTop: "0",
      usePythonLoading: false
    }
  },
  methods: {
    usePython(){
      if (this.usePythonLoading)
        return
      this.usePythonLoading = true
      request({
        url: "/python/use",
        method: 'get'
      }).then((response) => {
        this.usePythonLoading = false
      }).catch((e) => {
        this.usePythonLoading = false
      })
    },
    handleDialogBeforeOpen() {
      // 防止页面滚动
      // console.log("进入到打开前的方法")
      // document.body.style.overflow = 'hidden';
    },
    handlerDialogOpen() {
      // window.pageXOffset = document.documentElement.scrollLeft = document.body.scrollLeft = this.left
      // window.pageYOffset = document.documentElement.scrollTop = document.body.scrollTop = this.top
      // let dom = document.getElementById("main");
      // dom.style.position = "fixed";
      // dom.style.top = "-" + this.top + "px";
      // dom.style.width = "100%"
    },   //获得页面向左、向上卷动的距离
    getScroll() {
      return {
        left: window.pageXOffset || document.documentElement.scrollLeft || document.body.scrollLeft || 0,
        top: window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0
      };
    },
    colseDialog() {
      // this.$refs.uploadSingle.clearFiles()
      this.fileList = []
      // document.body.style.overflow = '';
      // window.scrollTo(0, this.top);
    },

    colseDialog2() {
      // this.$refs.uploadSingle.clearFiles()
      // this.$refs.uploadMany.clearFiles()
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
    handleExceed(files, fileList) {
      this.$message.warning('最多只能上传一张图片')
    },
    addfileId(response, file, fileList) {
      var temp = new Object()
      temp['name'] = response.data.orginalName
      temp['url'] = response.data.fileUrl
      temp['fileId'] = response.data.fileId
      this.fileList.push(temp)
    },
    addfileId2(response, file, fileList) {
      var temp = new Object()
      temp['name'] = response.data.orginalName
      temp['url'] = response.data.fileUrl
      temp['fileId'] = response.data.fileId
      this.fileList.push(temp)
    },
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
      this.fileList = []
      this.tempData = {
      }
      this.addTableDataVisual = true
    },
    addTableData2() {
      this.fileList = []
      this.edditTableDataVisual2 = false
      this.tempData = {
        fileName: [],
        fileUrl: [],
        fileId: [],
      }
      // this.top = this.getScroll().top
      // this.left = this.getScroll().left
      // console.log(this.top)
      // console.log(this.left)
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
      if (this.selectedData[0].fileId != null && this.selectedData[0].fileId != undefined && this.selectedData[0].fileId != null != '') {
        for (var i = 0; i < this.selectedData[0].fileId.length; i++) {
          var temp = new Object()
          temp['name'] = this.selectedData[0].fileName[i]
          temp['url'] = this.selectedData[0].fileUrl[i]
          temp['fileId'] = this.selectedData[0].fileId[i]
          this.fileList.push(temp)
        }
      }
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
        //删除文件，同时删除信息
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
      this.addTableDataVisual = false
    },
    submitAddTableData2() {
      //
      if (this.fileList.length > 0) {
        for (var i = 0; i < this.fileList.length; i++) {
          this.tempData['fileId'].push(this.fileList[i].fileId)
          this.tempData['fileUrl'].push(this.fileList[i].url)
          this.tempData['fileName'].push(this.fileList[i].name)
        }
      }
      else {
        this.tempData['fileId'] = null
        this.tempData['fileUrl'] = null
        this.tempData['fileName'] = null
      }
      console.log(this.tempData)
      this.tableData2.push(this.tempData)
      this.addTableDataVisual2 = false
    },
    handleCurrentChange(current) { },
    handleCurrentChange2(current) { },
    cancelEdditTableData() {
      if (this.fileList.length > 0) {
        this.oldTempData['fileId'] = this.fileList[0].fileId
        this.oldTempData['fileUrl'] = this.fileList[0].url
        this.oldTempData['fileName'] = this.fileList[0].name
      }
      else {
        this.oldTempData['fileId'] = null
        this.oldTempData['fileUrl'] = null
        this.oldTempData['fileName'] = null
      }
      var index = this.tableData.findIndex(item => item.id === this.tempData.id)
      this.tableData[index] = this.oldTempData
      this.edditTableDataVisual = false
    },
    cancelEdditTableData2() {
      if (this.fileList.length > 0) {
        for (var i = 0; i < this.fileList.length; i++) {
          this.oldTempData['fileId'].push(this.fileList[i].fileId)
          this.oldTempData['fileUrl'].push(this.fileList[i].url)
          this.oldTempData['fileName'].push(this.fileList[i].name)
        }
      }
      else {
        this.oldTempData['fileId'] = null
        this.oldTempData['fileUrl'] = null
        this.oldTempData['fileName'] = null
      }
      var index = this.tableData2.findIndex(item => item.id === this.tempData.id)
      this.tableData2[index] = this.oldTempData
    },
    submitEdditTableData() {
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
      this.edditTableDataVisual = false
    },
    submitEdditTableData2() {
      this.tempData['fileId'] = []
      this.tempData['fileUrl'] = []
      this.tempData['fileName'] = []
      if (this.fileList.length > 0) {
        for (var i = 0; i < this.fileList.length; i++) {
          this.tempData['fileId'].push(this.fileList[i].fileId)
          this.tempData['fileUrl'].push(this.fileList[i].url)
          this.tempData['fileName'].push(this.fileList[i].name)
        }
      }
      else {
        this.tempData['fileId'] = null
        this.tempData['fileUrl'] = null
        this.tempData['fileName'] = null
      }
      console.log(this.tempData)
      this.edditTableDataVisual2 = false
    },
    getTableData(queryEntity) {
      this.tableData = [{
        id: 1,
        rq: '2022-02-03 10:15:00',
        fileUrl: 'http://localhost:9000/file/1681374628735.JPG',
        fileId: 1646430641317355521,
        fileName: 'IMG_7130.JPG',
        zhuanghao: '1号',
        sbjg: '正常',
        lfkd: 10,
        lfcd: 20,
        lfxz: '是裂缝',
        beizhu: '完蛋'
      },
      {
        id: 2,
        rq: '2022-02-03 10:15:00',
        fileUrl: 'http://localhost:9000/file/1681375711203.JPG',
        fileId: 1646435181492051970,
        fileName: 'IMG_7118.JPG',
        zhuanghao: '1号',
        sbjg: '正常',
        lfkd: 10,
        lfcd: 20,
        lfxz: '是裂缝',
        beizhu: '完蛋'
      },
      ],
        this.tableData2 = [{
          id: 1,
          rq: '2022-02-03 10:15:00',
          fileUrl: ['http://localhost:9000/file/1681374628735.JPG', 'http://localhost:9000/file/1681719161604.JPG'],
          fileId: [1646430641317355521, 1647875716870303745],
          fileName: ['IMG_7130.JPG', 'IMG_7150.JPG'],
          zhuanghao: '1号',
          sbjg: '正常',
          lfcctj: 10,
          lfbhzt: '严重',
          beizhu: '完蛋'
        },
        {
          id: 2,
          rq: '2022-02-03 10:15:00',
          fileUrl: ['http://localhost:9000/file/1681719165520.JPG', 'http://localhost:9000/file/1681719253337.JPG'],
          fileId: [1647875733353918466, 1647876101613809665],
          fileName: ['IMG_7150.JPG', 'IMG_7150.JPG'],
          zhuanghao: '1号',
          sbjg: '正常',
          lfcctj: 10,
          lfbhzt: '严重',
          beizhu: '完蛋'
        },
        {
          id: 3,
          rq: '2022-02-03 10:15:00',
          fileUrl: ['http://localhost:9000/file/1681719165520.JPG', 'http://localhost:9000/file/1681719253337.JPG'],
          fileId: [1647875733353918466, 1647876101613809665],
          fileName: ['IMG_7150.JPG', 'IMG_7150.JPG'],
          zhuanghao: '1号',
          sbjg: '正常',
          lfcctj: 10,
          lfbhzt: '严重',
          beizhu: '完蛋'
        },
        ]
    },
    view(id) {
      // var fileId = 123
      // let path = this.$router.resolve({
      //     path: '/view/file/?fileId=' + fileId
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
        let fileUrl = URL.createObjectURL(blob);
        window.open(fileUrl);


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
  mounted() {

  },
}
</script>

<style></style>