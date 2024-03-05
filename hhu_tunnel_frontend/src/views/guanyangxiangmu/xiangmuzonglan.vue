<template>
  <div>
    <el-row>
      <el-button
        class="addBtn"
        type="primary"
        size="small"
        @click="openPro"
      >项目大事记</el-button>
      <el-button
        class="addBtn"
        type="primary"
        size="small"
        @click="openList"
      >项目列表</el-button>
      <el-button
        class="addBtn"
        type="primary"
        size="small"
        @click="openSta"
      >资金统计</el-button>
    </el-row>
    <div>
      <div :style="visibility0" class="container">
        <div class="block">
          <div class="radio">
            排序：
            <el-radio-group v-model="reverse">
              <el-radio :label="true">倒序</el-radio>
              <el-radio :label="false">正序</el-radio>
            </el-radio-group>
          </div>

          <el-timeline :reverse="reverse">
            <el-timeline-item
              v-for="(activity, index) in activities"
              :key="index"
              :timestamp="activity.timestamp">
              {{activity.content}}
            </el-timeline-item>
          </el-timeline>
        </div>
      </div>
      <div :style="visibility1">
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
      </div>
      <div :style="visibility2">

      </div>
    </div>
  </div>
</template>

<script>

export default {
  data() {
    return {
      visibility0: "visibility:visible",
      visibility1: "visibility:hidden",
      visibility2: "visibility:hidden",
      reverse: true,
      tableData: {
        basic: [//table的表头和参数名
          { prop: "date", title: "立项日期", id:1 },
          { prop: "name", title: "项目名称", id:2 },
          { prop: "pre", title: "预计完成时间", id:3 },
          { prop: "money", title: "预算（万元）", id:4 },
          { prop: "used_money", title: "已使用资金情况", id:5 },
          { prop: "manager", title: "负责人", id:6 },
          { prop: "team", title: "执行单位", id:7 },
          { prop: "state", title: "执行状态", id:8 },
        ],
        list: [
          {"date":"2020-10-01","name":"定淮门隧道项目","pre":"2021-10-01","money":"300","used_money":"130.37","manager":"刘阳","team":"中铁","state":"提前","ischecked":false},
          {"date":"2020-10-01","name":"定淮门隧道项目","pre":"2021-10-01","money":"300","used_money":"130.37","manager":"刘阳","team":"中铁","state":"提前","ischecked":false},
          {"date":"2020-10-01","name":"定淮门隧道项目","pre":"2021-10-01","money":"300","used_money":"130.37","manager":"刘阳","team":"中铁","state":"提前","ischecked":false},
        ],
      },
      activities: [{
        content: '项目开始',
        timestamp: '2018-04-15'
      }, {
        content: '项目审核',
        timestamp: '2018-04-13'
      }, {
        content: '项目上线',
        timestamp: '2018-04-11'
      }]
    }
  },
  methods: {
    openPro(){
      this.visibility0="visibility:visible";
      this.visibility1="visibility:hidden";
      this.visibility2="visibility:hidden";
    },
    openList(){
      this.visibility0="visibility:hidden";
      this.visibility1="visibility:visible";
      this.visibility2="visibility:hidden";
    },
    openSta(){
      this.visibility0="visibility:hidden";
      this.visibility1="visibility:hidden";
      this.visibility2="visibility:visible";
    },
    addItem() {
      for (let i of this.tableData.list) {//是否已经保存其它操作
        if (i.ischecked) return this.$message.warning("请先保存当前编辑项");
      };
      //创建空白条目并选中
      this.tableData.list.push({"date": "", "name": "","pre": "","money": "","used_money": "","manager":"","team":"","state":"", "ischecked": true });
      this.tableData.checked = JSON.parse(JSON.stringify({"date": "", "name": "","pre": "","money": "","used_money": "","manager":"","team":"","state":"", "ischecked": true }));
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
<style>

</style>
