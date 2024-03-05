<template>
  <div>
    <el-button type="primary" plain @click="editTableData">编辑</el-button>
    <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%"
     @selection-change="handleSelectionChange" >
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column label="项目" width="500" prop="label">
      </el-table-column>
      <el-table-column label="预警值≥" width="500" prop="children[0]">
      </el-table-column>
      <el-table-column label="预警值≤" width="500" prop="children[1]">
      </el-table-column>
    </el-table>
    <div v-if="editDialogVisual">
    <el-dialog title="编辑" :visible.sync="editDialogVisual" width="35%" :show-close="false">
      <el-form ref="edditform" :model="editData" label-width="10rem">
        <el-form-item label="项目" style="margin-bottom: 0.6rem;">
          <el-input placeholder="项目名称" v-model="editData.label" :disabled="true">
          </el-input>
        </el-form-item>
        <el-form-item label="预警值1" style="margin-bottom: 0.6rem;">
          <el-input placeholder="请输入预警值1" v-model="editData['children'][0]" >
          </el-input>
        </el-form-item>
        <el-form-item label="预警值2" style="margin-bottom: 0.6rem;">
          <el-input placeholder="预警值2" v-model="editData['children'][1]" >
          </el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelEdit">取 消</el-button>
        <el-button type="primary" @click="submitEdit">确 定</el-button>
      </span>
    </el-dialog>
  </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      tableData: [],
      selectedData: [],
      editData: {},
      oldEditData: {},
      editDialogVisual: false,
    }
  },
  created() {
    this.tableData = [{
      id: 1,
      firstIndexId: 1,
      secondIndexId: -1,
      label: '周边位移',
      children: [10, 20]
    },
    {
      id: 2,
      firstIndexId: 2,
      secondIndexId: -1,
      label: '拱顶下沉',
      children: [30, 40]
    }]
  },
  methods: {
    handleSelectionChange(data) {
      this.selectedData = data
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
      this.editData = this.selectedData[0]
      this.oldEditData = JSON.parse(JSON.stringify(this.selectedData[0]))
      this.editDialogVisual = true
    },
    cancelEdit() {
      var temTableData = []
      for (var i = 0; i < this.tableData.length; i++) {
        //找到修改的数据的id
        if (this.tableData[i].id === this.oldEditData.id) {
          temTableData.push(this.oldEditData)
        }
        else {
          temTableData.push(JSON.parse(JSON.stringify(this.tableData[i])))
        }
      }
      this.tableData = temTableData
      this.editDialogVisual = false
    },
    submitEdit() {
        //
        console.log(this.editData)
        this.editDialogVisual = false
        this.$message({
          message: '修改成功',
          type: 'success'
        });
    }
  },
}
</script>

<style></style>