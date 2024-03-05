<template>
  <div >
    <el-row :gutter="10">
      <el-col :span="6" style="border-style: solid;">
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
            :key="Math.random()" style="margin-top: 1rem;" :header-cell-style="{ textAlign: 'center' }"
            :cell-style="{ textAlign: 'center' }" height="575">
            <el-table-column type="selection" width="55" :fixed="true">
            </el-table-column>
            <el-table-column label="日期" prop="rq" width="160">
            </el-table-column>
            <el-table-column label="隧道名称" prop="suidaoName">
            </el-table-column>
            <el-table-column label="测段数" prop="cds">
            </el-table-column>
            <el-table-column label="专项维护次数" prop="zxwhcs">
            </el-table-column>
            <el-table-column label="最近专项维护开始时间" prop="zjwhkssj" width="160">
            </el-table-column>
            <el-table-column label="最近专项维护结束时间" prop="zjwhjssj" width="160">
            </el-table-column>
            <el-table-column label="目前维护状态" prop="whzt">
              <template slot-scope="scope">
                <span v-if="scope.row.whzt === 0">未开始</span>
                <span v-if="scope.row.whzt === 1">进行中</span>
                <span v-if="scope.row.whzt === 2">已结束</span>
              </template>
            </el-table-column>
            <el-table-column fixed="right" label="操作" width="150">
              <template slot-scope="scope">
                <el-button @click="download(scope.row.fileId, scope.row.fileName)" type="text" size="small">下载</el-button>
                <el-button @click="view(scope.row.fileId)" type="text" size="small">查看</el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-pagination background layout="prev, pager, next" :current-page.sync="pages.currentPage"
            :page-size="pages.size" :total="pages.total" @current-change="handleCurrentChange" style="margin-top: 1rem;">
          </el-pagination>
          <hr style="margin-top: 1rem;" />
          <h2>专项维护次数图 </h2>
          <el-form :inline="true" :model="binghaiQuery">
            <el-form-item>
              <el-date-picker v-model="binghaiQuery.rq" type="datetime" placeholder="选择日期时间"
                value-format="yyyy-MM-dd HH:mm:ss">
              </el-date-picker>
            </el-form-item>
            <el-form-item>
              <el-select v-model="binghaiQuery.suidaoId" placeholder="请选择隧道">
                <el-option v-for="item in suiDaoList" :key="item.id" :label="item.name" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-select v-model="binghaiQuery.whzt" placeholder="请选择维护状态">
                <el-option v-for="item in whzt" :key="item.value" :label="item.label" :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-button type="primary" @click="queryBinghai">确定</el-button>
          </el-form>
          <el-row>
              <el-col :span="5" style="margin-bottom: 1rem;display: flex;">
                <div style="width: 2rem; height: 2rem ;border-style: solid;border-width: 0.1rem;background-color: #a4cb3f;"></div>
                <span style="font-size: 15px; margin-left: 1rem; line-height: 2rem;">专项维护次数≤1</span>
              </el-col>
              <el-col :span="5" style="margin-bottom: 1rem;display: flex;">
                <div style="width: 2rem; height: 2rem ;border-style: solid;border-width: 0.1rem;background-color: #68349a;"></div>
                <span style="font-size: 15px; margin-left: 1rem; line-height: 2rem;">专项维护次数>1</span>
              </el-col>
          </el-row>
          <div style="margin-top: 2rem;height: 30rem;">
            <el-row >
              <div v-for="(item, index) in bingHaiTable" :key="index">
                <el-col :span="1.5" >
                 <div v-if="item <= 1" style="width: 4rem; height: 4rem ;border-style: solid;border-width: 0.1rem;background-color: #a4cb3f;" ></div>
                 <div v-else style="width: 4rem; height: 4rem ;border-style: solid;border-width: 0.1rem;background-color: #68349a;"></div>
                  <br />
                  <div style="font-size: 13px;width: 4rem;text-align: center;margin-bottom: 1rem;">测段{{ index + 1 }}</div>
                </el-col>
              </div>
            </el-row>
          </div>
        </div>

      </el-col>
    </el-row>
    <el-dialog title="添加" :visible.sync="addTableDataVisual" width="35%" :show-close="false" @close="colseDialog">
      <el-form ref="addform" :model="tempData" label-width="10rem">
        <el-form-item label="日期" style="margin-bottom: 0.6rem;">
          <el-date-picker v-model="tempData['rq']" type="datetime" placeholder="选择日期时间"
            value-format="yyyy-MM-dd HH:mm:ss">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="隧道名称" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['suidaoName']" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="测段数" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['cds']"></el-input>
        </el-form-item>
        <el-form-item label="专项维护次数" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['zxwhcs']"></el-input>
        </el-form-item>
        <el-form-item label="最近专项维护开始时间" style="margin-bottom: 0.6rem;">
          <el-date-picker v-model="tempData['zjwhkssj']" type="datetime" placeholder="选择专项维护开始时间"
            value-format="yyyy-MM-dd HH:mm:ss">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="最近专项维护结束时间" style="margin-bottom: 0.6rem;">
          <el-date-picker v-model="tempData['zjwhjssj']" type="datetime" placeholder="选择专项维护结束时间"
            value-format="yyyy-MM-dd HH:mm:ss">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="目前维护状态" style="margin-bottom: 0.6rem;">
          <el-select v-model="tempData['whzt']" placeholder="选择维护状态" style="width: 20rem;" :disabled="true"> 
            <el-option v-for="item2 in whzt" :key="item2.value" :label="item2.label" :value="item2.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="上传方案" style="margin-bottom: 0.6rem;">
          <el-upload class="upload-demo" :action="uploadUrl" :on-success="addfileId" ref="upload" :on-remove="removeFile"
            :file-list="fileList" :limit="1" accept=".pdf">
            <el-button type="info" plain size="small">上传<i class="el-icon-upload el-icon--right"></i></el-button>
            <div slot="tip" class="el-upload__tip">只能上传pdf文件</div>
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
        <el-form-item label="日期" style="margin-bottom: 0.6rem;">
          <el-date-picker v-model="tempData['rq']" type="date" placeholder="选择日期时间"
            value-format="yyyy-MM-dd">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="隧道名称" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['suidaoName']" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="测段数" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['cds']"></el-input>
        </el-form-item>
        <el-form-item label="专项维护次数" style="margin-bottom: 0.6rem;">
          <el-input v-model="tempData['zxwhcs']"></el-input>
        </el-form-item>
        <el-form-item label="最近专项维护开始时间" style="margin-bottom: 0.6rem;">
          <el-date-picker v-model="tempData['zjwhkssj']" type="datetime" placeholder="选择专项维护开始时间"
            value-format="yyyy-MM-dd HH:mm:ss">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="最近专项维护结束时间" style="margin-bottom: 0.6rem;">
          <el-date-picker v-model="tempData['zjwhjssj']" type="datetime" placeholder="选择专项维护结束时间"
            value-format="yyyy-MM-dd HH:mm:ss">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="目前维护状态" style="margin-bottom: 0.6rem;">
          <el-select v-model="tempData['whzt']" placeholder="选择维护状态" style="width: 20rem;">
            <el-option v-for="item2 in whzt" :key="item2.value" :label="item2.label" :value="item2.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="上传方案" style="margin-bottom: 0.6rem;">
          <el-upload class="upload-demo" :action="uploadUrl" :on-success="addfileId" ref="upload" :on-remove="removeFile"
            :file-list="fileList" :limit="1" accept=".pdf">
            <el-button type="info" plain size="small">上传<i class="el-icon-upload el-icon--right"></i></el-button>
            <div slot="tip" class="el-upload__tip">只能上传pdf文件</div>
          </el-upload>
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
import { removeFile, download, viewFileOnline } from '@/api/File'
import { transformArrayDate,transformDate } from '@/api/util'
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
      whzt: [
        {
          value: 0,
          label: '未开始'
        },
        {
          value: 1,
          label: '进行中'
        },
        {
          value: 2,
          label: '已结束'
        },
      ],
      uploadUrl: process.env.VUE_APP_BASE_API + '/file/upload',
      fileList: [],
      suidaoName: '',
      whztId: 0,
      binghaiQuery: {
        rq: '',
        suidaoId: '',
        whzt: '',
      },
      suiDaoList: [],
      bingHaiTable: [],
    }
  },
  methods: {
    handleNodeClick(data) {
      this.selectedNode = data
      var s = this.$refs.treeData.getCheckedNodes(false, true)
      if (s.length != 5)
        return
      var queyEntity = {
        suidaoId: -1,
        firstIndexId: -1,
        secondIndexId: -1,
        itemId: -1,
      }
      this.suidaoName = s[3].label
      this.whztId = s[4].id
      queyEntity.suidaoId = s[3].id
      queyEntity.firstIndexId = s[1].id
      queyEntity.secondIndexId = s[2].id
      queyEntity.itemId = s[4].id
      this.queryEntity = queyEntity
      console.log(this.queryEntity)
      this.getTableData(queyEntity)
    },
    getTableData(queryEntity) {
      this.tableData = [
        {
          id: 1,
          rq: '2022-02-03 00:00:00',
          suidaoName: '定淮门隧道',
          cds: '测段1',
          zxwhcs: 3,
          zjwhkssj: '2022-02-01 00:00:00',
          zjwhjssj: '2022-02-22 12:12:12',
          whzt: 0,
          fileId: '1648881188808445953',
          fileName: '测试.pdf',
          // fileUrl: 'http://localhost:9000/file/1681898909542.pdf',
        },
        {
          id: 2,
          rq: '2022-02-03',
          suidaoName: '定淮门隧道',
          cds: '测段2',
          zxwhcs: 3,
          zjwhkssj: '2022-02-01',
          zjwhjssj: '2022-02-06',
          whzt: 1,
          fileId: '1648881188808445953',
          fileName: '测试.pdf',
          // fileUrl: 'http://localhost:9000/file/1681898909542.pdf',
        },
        {
          id: 3,
          rq: '2022-02-03',
          suidaoName: '定淮门隧道',
          cds: '测段3',
          zxwhcs: 3,
          zjwhkssj: '2022-02-01',
          zjwhjssj: '2022-02-06',
          whzt: 2,
          fileId: '1648881188808445953',
          fileName: '测试.pdf',
          // fileUrl: 'http://localhost:9000/file/1681898909542.pdf',
        },
      ]
      this.tableData = transformArrayDate(this,this.tableData,'rq','zjwhkssj','zjwhjssj')
      this.isTableVisual = true
    },
    handleSelectionChange(data) {
      this.selectedData = data
    },
    download(id, fileName) {
      //根据id到数据库中找到文件存储的位置
      download(id).then(res => {
        if (res.size === 0) {
          this.$message.warning("文件不存在")
          return
        }
        const blob = new Blob([res]);
        if ('download' in document.createElement("a")) {
          const link = document.createElement("a");
          link.download = fileName;
          link.style.display = 'none';
          link.href = URL.createObjectURL(blob);
          document.body.appendChild(link);
          link.click();
          URL.revokeObjectURL(link.href);
          document.body.removeChild(link);
        } else {
          navigator.msSaveBlob(blob, fileName);
        }
      }).catch(error => {
        this.$message.warning('文件已被删除')
      })
    },
    view(id) {
      viewFileOnline(id).then(res => {
        if (res.size === 0) {
          this.$message.warning("文件不存在")
          return
        }
        //文件以pdf形式进行预览
        let blob = new Blob([res], {
          type: "application/pdf;chartset=UTF-8",
        });
        let fileUrl = URL.createObjectURL(blob);
        window.open(fileUrl);
      }).catch(error => {
        this.$message.warning("查看文件出错")
      })
    },
    addTableData() {
      this.tempData = {
        fileId: [],
        suidaoName: this.suidaoName,
        whzt: this.whztId,
      }
      this.addTableDataVisual = true
    },
    cancelAddTableData() {
      this.addTableDataVisual = false
    },
    submitAddTableData() {
      if (this.fileList.length > 0) {
        this.tempData['fileId'] = this.fileList[0].fileId
      }
      else {
        this.tempData['fileId'] = null
      }
      //转换data的日期格式
      transformDate(this,this.tempData,'rq','zjwhkssj','zjwhjssj')
      this.tableData.push(this.tempData)
      this.addTableDataVisual = false
    },
    editTableData() {
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
      this.fileList = []
      var temp = new Object()
      if (this.selectedData[0].fileId != undefined && this.selectedData[0].fileId != null && this.selectedData[0].fileId != '') {
        temp['name'] = this.selectedData[0].fileName
        // temp['url'] = this.selectedData[0].fileUrl
        temp['fileId'] = this.selectedData[0].fileId
        this.fileList.push(temp)
      }
      console.log(this.tempData)
      this.oldTempData = JSON.parse(JSON.stringify(this.selectedData[0]))
      this.edditTableDataVisual = true
    },
    cancelEdditTableData() {
      if (this.fileList.length > 0) {
        this.oldTempData['fileId'] = this.fileList[0].fileId
        this.oldTempData['fileName'] = this.fileList[0].name
      }
      else {
        this.oldTempData['fileId'] = null
        this.oldTempData['fileName'] = null
      }
      var index = this.tableData.findIndex(item => item.id === this.oldTempData.id)
      this.tableData[index] = this.oldTempData
      this.edditTableDataVisual = false
    },
    submitEdditTableData() {
      if (this.fileList.length > 0) {
        this.tempData['fileId'] = this.fileList[0].fileId
        this.tempData['fileName'] = this.fileList[0].name
      }
      else {
        this.tempData['fileId'] = null
        this.tempData['fileName'] = null
      }
      this.edditTableDataVisual = false
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

    addfileId(response, file, fileList) {
      var temp = new Object()
      temp['name'] = response.data.orginalName
      temp['url'] = response.data.fileUrl
      temp['fileId'] = response.data.fileId
      this.fileList.push(temp)
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
    colseDialog() {
      this.fileList = []
    },
    handleExceed() {
      this.$message.warning('最多只能上传一个文件')
    },
    handleCurrentChange(current) { },
    queryBinghai() {
      this.bingHaiTable = []
      if (this.binghaiQuery.rq === '' || this.binghaiQuery.rq === null || this.binghaiQuery.rq === undefined) {
        this.$message.warning("日期不能为空")
        return
      }
      if (this.binghaiQuery.suidaoId === '' || this.binghaiQuery.suidaoId === null || this.binghaiQuery.suidaoId === undefined) {
        this.$message.warning("隧道不能为空")
        return
      }
      if (this.binghaiQuery.whzt === '' || this.binghaiQuery.whzt === null || this.binghaiQuery.whzt=== undefined) {
        this.$message.warning("状态不能为空")
        return
      }
      //获取该隧道当前时间的测段数组,值是对应的病害id
      this.bingHaiTable = [0,1,2,3,5,6,1,2,1,2,1,2,1,2,1,2,1,2]
      if (this.bingHaiTable.length === 0) {
        this.$message.warning("隧道当前时间当前状态没有数据")
        return
      }
    }
  },
  created() {
    this.dict[0].children = [
      {
        id: 1,
        label: '2023',
        children: [
          {
            id: 2,
            label: '2月',
            children: [
              {
                id: 1001,
                label: '定淮门隧道',
                children: [
                  {
                    id: 0,
                    label: '未开始',
                    children: []
                  },
                  {
                    id: 1,
                    label: '进心中',
                    children: []
                  },
                  {
                    id: 2,
                    label: '已结束',
                    children: []
                  },
                ]
              }
            ]
          }
        ]
      },
    ]
    this.suiDaoList = [
      {
        id: 1001,
        name: '定淮门隧道'
      },
      {
        id: 1002,
        name: '扬子江隧道'
      },
    ]
  },
}
</script>

<style></style>