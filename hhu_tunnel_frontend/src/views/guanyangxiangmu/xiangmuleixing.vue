<template>
  <section>
    <div class="title">
      <el-button type='success' class="el-icon-plus" circle @click="addItem()"></el-button>
      <el-button
        class="delBtn"
        type="primary"
        size="small"
        @click="deletecheck" left="80px" style="margin-right: 80px">删除选中</el-button>
    </div>
    <el-table :data="tableData.list"  @selection-change="handleSelectionChange" border highlight-current-row>
      <el-table-column
        type="selection"
        width="55">
      </el-table-column>
      <el-table-column label="序号" type="index" width="60"></el-table-column>
      <el-table-column v-for="i in tableData.basic" :key='i.id' :prop="i.prop" :label="i.title">
        <template slot-scope="scope">
          <el-input v-if="scope.row.ischecked" v-model="tableData.checked[i.prop]"> </el-input>
          <span v-else>{{scope.row[i.prop]}}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" >
        <template slot-scope="scope">
          <el-button :type='scope.row.ischecked ? "success" : "primary"' @click = "editItem(scope.row,scope.$index,true)">
            {{scope.row.ischecked?'保存':"修改"}}
          </el-button>
          <el-button type='danger' v-if="!scope.row.ischecked" @click  ="delItem(scope.$index)">删除</el-button>
          <el-button v-else @click = "editItem(scope.row,scope.$index,false)">取消</el-button>
        </template>
      </el-table-column>
    </el-table>
  </section>
</template>

<script>
export default {
  data() {
    return {
      tableData: {
        checked: null,
        basic: [//table的表头和参数名
          { prop: "name", title: "类型编号", id:1 },
          { prop: "number", title: "类型名称", id:2 },
          { prop: "code", title: "类型代码", id:3 },
          { prop: "decrip", title: "描述", id:4 },
        ],
        list: [
          {"name":"09","number":"定期检查项目","code":"DJ","decrip":"","ischecked":false},
          {"name":"09","number":"定期检查项目","code":"DJ","decrip":"","ischecked":false},
          {"name":"09","number":"定期检查项目","code":"DJ","decrip":"","ischecked":false},
        ],
      },
      multipleSelection: [],
    }
  },
  methods: {
    //创建uuid
    guid(){
      let S4 = ()=>{
        return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
      };
      return (S4()+S4()+"-"+S4()+"-"+S4()+"-"+S4()+"-"+S4()+S4()+S4());
    },

    //添加
    addItem() {
      for (let i of this.tableData.list) {//是否已经保存其它操作
        if (i.ischecked) return this.$message.warning("请先保存当前编辑项");
      };
      //创建空白条目并选中
      this.tableData.list.push({"name": "", "number": "","code":"","decrip":"", "ischecked": true });
      this.tableData.checked = JSON.parse(JSON.stringify({"name": "", "number": "","code":"","decrip":"", "ischecked": true }));
    },

    //操作
    editItem(row, index, save) {
      for (let i of this.tableData.list) {//是否已经保存其它操作
        if (i.ischecked && i.id != row.id) return this.$message.warning("请先保存当前编辑项");
      };
      if (!save) {//点击取消
        if (!this.tableData.checked.id){ //判断新增还是修改
          this.tableData.list.splice(index, 1);
        };
        return row.ischecked = !row.ischecked;
      };
      if (row.ischecked) {//点击保存
        for(var i in this.tableData.checked){//要求信息填写完整
          if(!this.tableData.checked[i]) return this.$message.warning('请先完善信息');
        };
        if(!this.tableData.checked.id){//如果是新增的条目，就赋给一个uuid
          this.tableData.checked.id = this.guid();
        };
        let newItem = JSON.parse(JSON.stringify(this.tableData.checked));
        for (let i in newItem) row[i] = newItem[i];
        row.ischecked = false;
      } else {//点击修改
        row.ischecked = true;
        this.tableData.checked = JSON.parse(JSON.stringify(row));
      }
    },

    //删除
    delItem(ind){
      this.tableData.list.splice(ind,1);
    },

    //删除选中
    deletecheck(){
      for (let i = 0; i < this.tableData.list.length; i++) {
        const element = this.tableData.list[i];
        element.id = i
      }
      if (this.multipleSelection.length == 0) this.$message.error('请先至少选择一项')
      this.multipleSelection.forEach(element => {
        this.tableData.list.forEach((e, i) => {
          if (element.id == e.id) {
            this.tableData.list.splice(i, 1)
          }
        });
      });
    },
    //选
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
  }
}
</script>

<style lang="scss" scoped>
section{
  width: 600px;
  margin: 50px auto;
  .title{
    height: 70px;
    position: relative;
    button{
      position: absolute;
      right: 10px;
      bottom: 10px;
    }
  }
}
</style>





