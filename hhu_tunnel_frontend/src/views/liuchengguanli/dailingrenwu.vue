<template>
  <div>
    <div class="title">
      <h2>待领任务</h2>
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
      <el-table-column label="编号" type="index" width="60"></el-table-column>
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
  </div>

</template>

<script>
export default {
  data() {
    return {
      tableData: {
            checked: null,//选中行
            basic: [//table的表头和参数名
             { prop: "date", title: "创建日期", id:1 },
             { prop: "name", title: "任务名称", id:2 },
             { prop: "def", title: "流程定义", id:3 },
             { prop: "fabu", title: "发布人", id:4 },
             { prop: "zhixing", title: "执行人", id:5 },
            ],
          list: [
            {"date":"2023-4-1","name":"定淮门隧道","def":"发起","fabu":"张三","zhixing":"李四","ischecked":false,"id":"3"},
            {"date":"2023-4-1","name":"定淮门隧道","def":"发起","fabu":"张三","zhixing":"李四","ischecked":false,"id":"4"},
            {"date":"2023-4-1","name":"定淮门隧道","def":"发起","fabu":"张三","zhixing":"李四","ischecked":false,"id":"5"},
          ],
        },
      multipleSelection: [],
    }
  },
  methods: {
    openList(){
      this.visibility0="visibility:hidden";
      this.visibility1="visibility:visible";
    },
    openSit(){
      this.visibility1="visibility:hidden";
      this.visibility0="visibility:visible";
    },
    handleDelete(_node, _data) {
      // 删除节点
      console.log(_node, _data);
      // 判断是否存在子节点
      if (_data.children && _data.children.length !== 0) {
        this.$message.error("此节点有子级，不可删除！");
        return false;
      } else {
        // 删除操作
        let DeletOprate = () => {
          this.$nextTick(() => {
            if (this.$refs.SlotTree) {
              this.$refs.SlotTree.remove(_data);
              this.$message.success("删除成功！");
            }
          });
        };
        // 二次确认
        let ConfirmFun = () => {
          this.$confirm("是否删除此节点？", "提示", {
            confirmButtonText: "确认",
            cancelButtonText: "取消",
            type: "warning",
          })
            .then(() => {
              DeletOprate();
            })
            .catch(() => {});
        };
        // 判断是否新增： 新增节点直接删除，已存在的节点要二次确认
        _data[this.NODE_KEY] < this.NODE_ID_START
          ? DeletOprate()
          : ConfirmFun();
      }
    },
    handleInput(_node, _data) {
      // 修改节点
      console.log(_node, _data);
      // 退出编辑状态
      if (_node.isEdit) {
        this.$set(_node, "isEdit", false);
      }
    },
    handleEdit(_node, _data) {
      // 编辑节点
      console.log(_node, _data);
      // 设置编辑状态
      if (!_node.isEdit) {
        this.$set(_node, "isEdit", true);
      }
      // 输入框聚焦
      this.$nextTick(() => {
        if (this.$refs["slotTreeInput" + _data[this.NODE_KEY]]) {
          this.$refs["slotTreeInput" + _data[this.NODE_KEY]].$refs.input.focus();
        }
      });
    },
    // 新增子级节点打开事件
    appendChild(node,data) {
      this.addForm.name = "";
      this.addDialogVisible = true;
      this.addDialogTitle = "new";
      this.addDataList = data;
      this.addNode=node
      this.addType = 2;
    },
    // 新增子级节点
    handleAdd(_node, _data,_name) {
      // 新增节点
      console.log(_node, _data);
      // 判断层级
      if (_node.level >= this.MAX_LEVEL) {
        this.$message.warning("当前已达到" + this.MAX_LEVEL + "级，无法新增！");
        return false;
      }
      // 参数修改
      let obj = JSON.parse(JSON.stringify(this.initParam)); // copy参数
      obj.id = _data[this.NODE_KEY]; // 父id
      obj[this.NODE_KEY] = ++this.startId; // 节点id：逐次递减id
      obj.label=_name
      // 判断字段是否存在
      if (!_data.children) {
        this.$set(_data, "children", []);
      }
      // 新增数据
      _data.children.push(obj);
      // 展开节点
      if (!_node.expanded) {
        _node.expanded = true;
      }
    },
    // 新增顶级结点打开事件
    handleAddTop() {
      // 添加顶部节点
      let obj = JSON.parse(JSON.stringify(this.setTree[0])); // copy参数
      console.log(obj);
      obj[this.NODE_KEY] = ++this.startId; // 节点id：逐次递减id
      obj.label = this.addForm.name;
      console.log(obj)
      this.setTree.push(obj);
    },
    // 新增顶级结点
    openAddTop() {
      this.addForm.name = "";
      this.addDialogVisible = true;
      this.addDialogTitle = "new";
      this.addType = 1;
    },
    // 点击确定 新增子级菜单
    addData() {
      var t = this;
      this.$refs.addRef.validate(async (valid) => {
        if (valid) {
          if (t.addType == 1) {
            t.handleAddTop();
            this.addDialogVisible = false;
          } else if (t.addType == 2) {
            const obj = JSON.parse(JSON.stringify(t.addForm));
            // 需要判断新增还是编辑
            t.handleAdd(t.addNode,t.addDataList,obj.name)
            this.addDialogVisible = false;
          }
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    addHandleClose() {
      this.addDialogVisible = false;
      this.$refs.addRef.resetFields();
    },
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
      this.tableData.list.push({"date": "", "name": "", "def": "", "fabu": "", "zhixing": " ", "ischecked": true });
      this.tableData.checked = JSON.parse(JSON.stringify({"date": "","name": "","def": "","fabu": "","zhixing": " ","ischecked": true }));
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





