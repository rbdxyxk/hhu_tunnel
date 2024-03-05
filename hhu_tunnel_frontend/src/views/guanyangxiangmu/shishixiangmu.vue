<template>
  <div>
    <el-row>
      <el-button
        class="addBtn"
        type="primary"
        size="small"
        @click="openAddTop"
      >添加</el-button>
<!--      <el-button-->
<!--        class="addBtn"-->
<!--        type="primary"-->
<!--        size="small"-->
<!--        @click="openList"-->
<!--      >资产清单</el-button>-->
<!--      <el-button-->
<!--        class="addBtn"-->
<!--        type="primary"-->
<!--        size="small"-->
<!--        @click="openSit"-->
<!--      >资产详情</el-button>-->
    </el-row>

    <!--    <el-input placeholder="输入关键字进行过滤" v-model="filterText">-->
    <!--    </el-input>-->
    <div class="container">
      <div class="left">
        <el-tree
          ref="SlotTree"
          :data="setTree"
          :props="defaultProps"
          :expand-on-click-node="false"
          highlight-current
          :node-key="NODE_KEY"
        >
          <div class="comp-tr-node" slot-scope="{ node, data }">
            <!-- 编辑状态 -->
            <template v-if="node.isEdit">
              <el-input
                v-model="data.label"
                autofocus
                size="mini"
                :ref="'slotTreeInput' + data[NODE_KEY]"
                @blur.stop="handleInput(node, data)"
                @keyup.enter.native="handleInput(node, data)"
              ></el-input>
            </template>
            <!-- 非编辑状态 -->
            <template v-else>
              <!-- 名称： 新增节点增加class（is-new） -->
              <span
                :class="[
                    data[NODE_KEY] < NODE_ID_START ? 'is-new' : '',
                    'comp-tr-node--name',
                  ]"
              >
                  {{ node.label }}
                </span>

              <!-- 按钮 -->
              <span class="comp-tr-node--btns">
<!--                  &lt;!&ndash; 新增 &ndash;&gt;-->
<!--                  <el-button-->
<!--                    type="text"-->
<!--                    size="mini"-->
<!--                    @click="appendChild(node, data)"-->
<!--                  >新增</el-button>-->

                <!-- 编辑 -->
                  <el-button
                    size="mini"
                    type="text"
                    @click="handleEdit(node, data)"
                  >编辑</el-button>

<!--                &lt;!&ndash; 删除 &ndash;&gt;-->
<!--                  <el-button-->
<!--                    size="mini"-->
<!--                    type="text"-->
<!--                    @click="handleDelete(node, data)"-->
<!--                  >删除</el-button>-->
                </span>
            </template>
          </div>
        </el-tree>
      </div>
      <div>
        <el-divider class="el-divider--vertical"></el-divider>
      </div>
      <!--      //右边放表格-->
      <div class="right">
        <div :style="visibility1">
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
        <div :style="visibility0">

        </div>
      </div>
    </div>
    <el-dialog
      :close-on-click-modal="false"
      :visible.sync="addDialogVisible"
      width="30%"
      top="5vh"
      :show-close="true"
      :before-close="addHandleClose"
      :title="addDialogTitle == 'edit' ? '编辑名称' : '新增名称'"
    >
      <el-form
        :model="addForm"
        :rules="addRules"
        label-width="80px"
        ref="addRef">
        <el-form-item label="名称" prop="name">
          <el-input size="medium" v-model="addForm.name" placeholder="名称" />
        </el-form-item>
      </el-form>
      <span class="dialog-footer">
        		<el-button size="small" @click="addHandleClose">取 消</el-button>
        		<el-button size="small" type="primary" @click="addData">确 定</el-button>
      		</span>
    </el-dialog>
  </div>
</template>
<script>
export default {
  data() {
    return {
      addType: 1,
      addNode:'',
      startId: null,
      visibility1: "visibility:visible",
      visibility0: "visibility:hidden",
      tableData: {
        checked: null,//选中行
        basic: [//table的表头和参数名
          {prop: "st_time",title:"起始时间",id:1},
          { prop: "name", title: "隧道名称", id:2 },
          { prop: "number", title: "系统名称", id:3 },
          { prop: "type", title:"养护类型",id:4},
          { prop: "manage", title:"主管方",id:5},
          { prop: "team", title:"项目执行单位",id:6},
          { prop: "manager", title:"项目负责人", id:7},
          {prop: "ed_time",title:"结束时间",id:8},
          {prop: "state",title:"进展情况",id:9},
        ],
        list: [
          {"st_time":"2018-01-03","name":"扬子江隧道","number":"南京扬子江隧道","type":"养护工程项目","manage":"南京公路防护设施","team":"南京公路防护","manager":"董猛","ed_time":"2018-12-03","state":"进行中","id":"3","ischecked":false},
          {"st_time":"2018-01-03","name":"扬子江隧道","number":"南京扬子江隧道","type":"养护工程项目","manage":"南京公路防护设施","team":"南京公路防护","manager":"董猛","ed_time":"2018-12-03","state":"进行中","id":"3","ischecked":false},
          {"st_time":"2018-01-03","name":"扬子江隧道","number":"南京扬子江隧道","type":"养护工程项目","manage":"南京公路防护设施","team":"南京公路防护","manager":"董猛","ed_time":"2018-12-03","state":"进行中","id":"3","ischecked":false},
        ],
      },
      setTree: [{
        id:1,
        label: '2018年',
      },
        {
          id:2,
          label: '2019年',
        },
        {
          id:3,
          label: '2020年',
        },
        {
          id:4,
          label: '2021年',
        },
      ], // tree数据
      NODE_KEY: "id", // id对应字段
      MAX_LEVEL: 5 , // 设定最大层级
      NODE_ID_START: 0, // 新增节点id，逐次递减
      defaultProps: {
        // 默认设置
        children: "children",
        label: "label",
      },
      addDataList: "",
      addForm: {
        name: "",
      },
      addRules: {
        name: [{ required: true, message: "请输入名称", trigger: "blur" }],
      },
      addDialogTitle: "new",
      addDialogVisible: false,
      // 之前遗忘了这个参数 重新补发
      initParam: {
        // 新增参数
        name: "",
        id: 0,
        children: [],
      },
    }
  },
  created() {
    // 初始值
    this.startId = this.NODE_ID_START;
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
      this.tableData.list.push({"name": "", "number": "", "ischecked": true });
      this.tableData.checked = JSON.parse(JSON.stringify({"name": "", "number": "", "ischecked": true }));
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

<style>
.container {
  display: flex;
}
.left {
  width: 20%;

}
.right {
  width: 80%;
  height: 1000px;
}
.el-divider--vertical{
  display:inline-block;
  width:1px;
  height:1000px;
  margin:0 8px;
  vertical-align:middle;
  position:relative;
  color: #1f2d3d;
}
</style>

