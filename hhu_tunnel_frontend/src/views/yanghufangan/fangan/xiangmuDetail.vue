<template>
  <div>
    <el-table
      :data="tableData"
      :span-method="objectSpanMethod"
      border
      style="width: 100%; margin-top: 20px" :header-cell-style="rowClass">
      <el-table-column type="selection" width="55" :fixed="true"></el-table-column>
      <el-table-column prop="id" label="隧道名称" width="180"></el-table-column>
      <el-table-column label="项目名称">
        <el-table-column prop="name" width="180"></el-table-column>
        <el-table-column prop="name_true" width="180"></el-table-column>
      </el-table-column>
      <el-table-column prop="sttime" label="起始日期"></el-table-column>
      <el-table-column prop="edtime" label="终止日期"></el-table-column>
      <el-table-column prop="stpos"
                       label="起始测点">
      </el-table-column>
      <el-table-column prop="edpos"
                       label="终止测点">
      </el-table-column>
      <el-table-column
        prop="yujing"
        label="预警数占比">
      </el-table-column>
      <el-table-column
        prop="quanzhong"
        label="权重">
      </el-table-column>
      <el-table-column
        prop="op"
        label="操作">
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      tableData: [{
        name: '施工期项目',
        name_true: '周边位移',
      },
        {
          name_true: '地表水平位移'
        },
        {
          name_true: '钢架内力及外力'
        },
        {
          name_true: '渗水压力、水流量'
        },
        {

          name: '运营期项目',
          name_true: '竖向位移',
        },
        {
          name_true: '水平位移',
        },
        {
          name_true: '净空收敛',
        },
        {
          name: '检测项目',
          name_true: '防水卷材厚度、搭接宽度'
        },
        {
          name_true: '预埋式注浆管施工质量'
        }
      ]
    };
  },
  methods: {
    // arraySpanMethod({ row, column, rowIndex, columnIndex }) {
    //   if (rowIndex % 2 === 0) {
    //     if (columnIndex === 0) {
    //       return [1, 2];
    //     } else if (columnIndex === 1) {
    //       return [0, 0];
    //     }
    //   }
    // },

    rowClass({row,column,rowIndex,columnIndex}){
      if(rowIndex===1){
        return {
          display: 'none'
        }
      }
    },
    objectSpanMethod({ row, column, rowIndex, columnIndex }) {
      // if (columnIndex === 1) {
      //   return{
      //     rowspan: 5,
      //     colspan: 1
      //   }
      // }
      // if (columnIndex === 2) {
      //   return {
      //     rowspan: 4,
      //     colspan: 1
      //   }
      // }
      //   if (columnIndex === 3) {
      //     return {
      //       rowspan: 1,
      //       colspan: 1
      //     }
      //   }
      //     if(columnIndex ===2) {
      //        return{
      //          rowspan: 4,
      //          colspan: 1
      //        }
      //     }
      }
    }
};
</script>
