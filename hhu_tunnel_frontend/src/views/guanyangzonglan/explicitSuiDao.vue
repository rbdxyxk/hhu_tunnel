<template>
  <div>
    <el-row>
      <el-col :span="24" style="height: 50px;">
        <div class="grid-content bg-purple-dark" style="text-align: center;margin-top: 1rem;font-weight: bold;">
          <el-image style="width: 180px; height: 48px;float: left;" :src="require('../../images/xiaohui.png')" fit="fill">
          </el-image>
          <span style="line-height: 30px;">
            隧道全寿命管控平台
          </span>
        </div>
      </el-col>
    </el-row>
    <el-row style="margin-top: 1rem;">
      <!-- 选择框和监测项目健康值区域 -->
      <el-col :span="8" style="border-style: solid;border-width: 0.6rem;height: 22rem;border-color:ghostwhite">
        <!-- 下拉框选择隧道 -->
        <el-select v-model="selectedItemId" placeholder="请选择监测项目" @change="getItemHealth" size="small">
          <el-option v-for="item in itemList" :key="item.id" :label="item.name" :value="item.id">
          </el-option>
        </el-select>
        <!-- 根据选择展示该条隧道对应监测项目近7天的健康值 -->
        <div id="oneItemHealth"
          style="width: 100%;height: 19rem;margin-top: 0.5rem;border-style: solid;border-width: 0.3rem;border-color:ghostwhite;">
        </div>
      </el-col>
      <!-- 图片展示 -->
      <el-col :span="8" style="border-style: solid;border-width: 0.6rem;height: 22rem;border-color:ghostwhite">
        <img style="width: 100%;height: 100%;" src="../../images/Snipaste_2023-03-19_11-13-16.png">
      </el-col>
      <!-- 病害展示 -->
      <el-col :span="8" style="border-style: solid;border-width: 0.6rem;height: 22rem;border-color:ghostwhite">
        <!-- 病害动态统计下拉 -->
        <el-dropdown @command="handleCommand">
          <el-button type="primary" plain>
            病害动态统计<i class="el-icon-arrow-down el-icon--right"></i>
          </el-button>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="1">裂缝</el-dropdown-item>
            <el-dropdown-item command="2">渗水</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
        <!-- 动态健康状况 -->
        <el-button type="primary" plain style="margin-left: 1rem;" @click="dynamicHealth">
          动态健康状况
        </el-button>
        <div id="youShang"
          style="width: 100%;height: 18rem;margin-top: 0.5rem;border-style: solid;border-width: 0.3rem;border-color:ghostwhite;">
        </div>
      </el-col>

    </el-row>
    <el-row>
      <!-- 自然电位法自动监测系统实时分析 -->
      <el-col :span="8" style="border-style: solid;border-width: 0.6rem;height: 22rem;border-color:ghostwhite">
        <span style="font-weight: bold;margin-left: 0.4rem;margin-bottom: 1rem;">自然电位法自动监测系统实时分析</span>
        <el-select v-model="cedianId" filterable placeholder="请选择" @change="getCedianShenlou" size="small"
          style="margin-top: 0.5rem;">
          <el-option v-for="item in cedianList" :key="item.id" :label="item.name" :value="item.id">
          </el-option>
        </el-select>
        <div id="cedianShenlou"
          style="width: 100%;height: 18rem;margin-top: 0.5rem;border-style: solid;border-width: 0.3rem;border-color:ghostwhite;">
        </div>
      </el-col>

      <!-- 安全预测专项分析 -->
      <el-col :span="8" style="border-style: solid;border-width: 0.6rem;height: 22rem;border-color:ghostwhite">
        <el-row>
          <span style="font-weight: bold;margin-left: 0.4rem;">安全预测专项分析</span>
        </el-row>
        <el-row>
          <el-select v-model="yuceMethodId" placeholder="请选择" @change="getYucePircture" size="small"
            style="margin-top: 0.5rem;">
            <el-option v-for="item in yuceMethodList" :key="item.id" :label="item.name" :value="item.id">
            </el-option>
          </el-select>
        </el-row>
        <div id="yucePicture"
          style="width: 100%;height: 18rem;margin-top: 0.5rem;border-style: solid;border-width: 0.3rem;border-color:ghostwhite;">
        </div>
      </el-col>
      <!-- 展现任务 -->
      <el-col :span="8" style="border-style: solid;border-width: 0.6rem;height: 22rem;border-color:ghostwhite">
        <el-table :data="taskData" height="330" style="width: 100%;height: 21rem">
          <el-table-column prop="suidao" label="隧道名" width="150">
          </el-table-column>
          <el-table-column prop="todayTask" label="当日任务" width="150">
          </el-table-column>
          <el-table-column prop="tomorrowTask" label="明日计划" width="180">
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  data() {
    return {
      //当前隧道id
      id: '',
      //当前隧道name
      name: '',
      //存放选择出来的itemId
      selectedItemId: "",
      //存放周边位移的项目list
      itemList: [],
      //item图标对象
      itemPicture: {},
      //右上方图像对象
      youShangPicture: {},
      //测点id
      cedianId: '',
      //测点集合
      cedianList: [],
      //测点渗漏图
      cedianShenlou: {},
      //预测方法id
      yuceMethodId: '',
      //预测方法列表
      yuceMethodList: [],
      //预测图像
      yucePicture: {},
      //任务列表
      taskData: [],
    }
  },

  created() {
    this.id = this.$route.params.id;
    this.name = this.$route.params.name + "安全度"
    //根据隧道id将所有的它的检测项目施工期如周边位移之类的选择出来,label是中文标签,id是对应的id
    this.itemList = [
      {
        id: 1,
        name: '周边位移'
      },
      {
        id: 2,
        name: '拱顶下沉'
      },
      {
        id: 3,
        name: '拱脚下沉'
      },
      {
        id: 4,
        name: '地表下沉'
      },
    ]
    //获取所有测点的id和name
    this.cedianList = [
      {
        id: 1,
        name: '测点1',
      },
      {
        id: 2,
        name: '测点2',
      },
      {
        id: 3,
        name: '测点3',
      },
      {
        id: 4,
        name: '测点4',
      },
      {
        id: 5,
        name: '测点5',
      },
      {
        id: 6,
        name: '测点6',
      },

    ]
    //获取所有预测方法
    this.yuceMethodList = [
      {
        id: 1,
        name: '安全预测'
      },
      {
        id: 2,
        name: '动态反演'
      },
      {
        id: 3,
        name: '统计学法'
      },
      {
        id: 4,
        name: '曲线拟合'
      },
    ]
    //获取当前隧道所有需要执行的任务
    this.taskData = [{
      suidao: '隧道1',
      todayTask: '完成全程的检查',
      tomorrowTask: '不降低隧道的...'
    },
    {
      suidao: '隧道2',
      todayTask: '完成全程的检查',
      tomorrowTask: '不降低隧道的...'
    },
    {
      suidao: '隧道3',
      todayTask: '完成全程的检查',
      tomorrowTask: '不降低隧道的...'
    },
    {
      suidao: '隧道4',
      todayTask: '完成全程的检查',
      tomorrowTask: '不降低隧道的...'
    },
    {
      suidao: '隧道5',
      todayTask: '完成全程的检查',
      tomorrowTask: '不降低隧道的...'
    },
    {
      suidao: '隧道6',
      todayTask: '完成全程的检查',
      tomorrowTask: '不降低隧道的...'
    },
    {
      suidao: '隧道7',
      todayTask: '完成全程的检查',
      tomorrowTask: '不降低隧道的...'
    },
    {
      suidao: '隧道8',
      todayTask: '完成全程的检查',
      tomorrowTask: '不降低隧道的...'
    },
    ]
  },
  methods: {
    getItemHealth(id) {
      //根据项目id和隧道id去查找对应7天的健康值
      var timeList = ["2022-05-15", "2022-05-16", "2022-05-17", "2022-05-18", "2022-05-19", "2022-05-20", "2022-05-21"]
      var data = [8.8, 8.6, 9.5, 9.3, 9, 9.7, 8.9]
      var name = this.name
      var option = {
        legend: {
          data: name
        },
        xAxis: {
          data: timeList
        },
        series: {
          name: name,
          data: data,
          type: "line",
          itemStyle : {  
	                 normal : {  
	                     color:'#88d28e',  
	                     lineStyle:{  
	                         color:'#88d28e'  
	                     }  
	                 }  
	             },  
        }

      }
      this.itemPicture.setOption(option)
    },
    //裂缝或渗水选择
    handleCommand(id) {
      var option = {}
      //如果选择的是裂缝
      if (id === '1') {
        //调用接口获得timeList,chenqieData,jietouData,chenqieYJ,jietouYJ
        var timeList = ["2022-05-15", "2022-05-16", "2022-05-17", "2022-05-18", "2022-05-19", "2022-05-20", "2022-05-21"]
        var chenqieData = [0.8, 0.85, 0.73, 0.87, 0.42, 0.9, 0.7]
        var jietouData = [0.7, 0.75, 0.73, 0.42, 0.8, 0.65]
        var chenqieYJ = [0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5]
        var jietouYJ = [0.6, 0.6, 0.6, 0.6, 0.6, 0.6, 0.6]
        option = {
          tooltip: {
            trigger: "axis"
          },
          dataZoom: {
            type: "inside",
            xAxisIndex: 0
          },
          grid: {
            x: "52",
            y: "65",
            width: "80%", // 宽度
            height: "60%"// 高度
          },
          legend: {
            data: ['衬砌裂缝病害值', '接头裂缝病害值', '衬砌裂缝预警值', '接头裂缝预警值'],
          },
          xAxis: {
            type: 'category',
            data: timeList,
            axisLabel: {
              //x轴文字的配置
              interval: 1,//使x轴文字显示全
            }
          },
          yAxis: {
            type: 'value'
          },
          series: [
            {
              name: '衬砌裂缝病害值',
              data: chenqieData,
              type: 'line'
            },
            {
              name: '接头裂缝病害值',
              data: jietouData,
              type: 'line'
            },
            {
              name: '衬砌裂缝预警值',
              data: chenqieYJ,
              type: 'line',
              symbol: "none",
              itemStyle: {
                normal: {
                  lineStyle: {
                    width: 2,
                    type: 'dashed'  //'dotted'点型虚线 'solid'实线 'dashed'线性虚线
                  }
                }
              },

            },
            {
              name: '接头裂缝预警值',
              data: jietouYJ,
              type: 'line',
              symbol: "none",
              itemStyle: {
                normal: {
                  lineStyle: {
                    width: 2,
                    type: 'dashed'  //'dotted'点型虚线 'solid'实线 'dashed'线性虚线
                  }
                }
              }
            },
          ]
        }
      }
      else if (id === '2') {
        //通过api获得该隧道最近7天渗水病害值
        var timeList = ["2022-05-15", "2022-05-16", "2022-05-17", "2022-05-18", "2022-05-19", "2022-05-20", "2022-05-21"]
        var shenshuiData = [0.8, 0.85, 0.73, 0.87, 0.42, 0.9, 0.7]
        var shenshuiYJ = [0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5]
        option = {

          tooltip: {
            trigger: "axis"
          },
          dataZoom: {
            type: "inside",
            xAxisIndex: 0
          },
          grid: {
            x: "52",
            y: "65",
            width: "80%", // 宽度
            height: "60%"// 高度
          },
          legend: {
            data: ['渗水病害值', '渗水预警值'],
          },
          xAxis: {
            type: 'category',
            data: timeList,
            axisLabel: {
              //x轴文字的配置
              interval: 1,//使x轴文字显示全
            }
          },
          yAxis: {
            type: 'value'
          },
          series: [
            {
              name: '渗水病害值',
              data: shenshuiData,
              type: 'line'
            },
            {
              name: '渗水预警值',
              data: shenshuiYJ,
              type: 'line',
              symbol: "none",
              itemStyle: {
                normal: {
                  lineStyle: {
                    width: 2,
                    type: 'dashed'  //'dotted'点型虚线 'solid'实线 'dashed'线性虚线
                  }
                }
              },
            },
          ]
        }
      }
      else
        return
      //加上true覆盖前面的
      this.youShangPicture.setOption(option, true)
    },
    //点击动态健康状况生成的图表
    dynamicHealth() {
      //通过隧道id获得隧道安全度,以及最近7天的时间
      var timeList = ["2022-05-15", "2022-05-16", "2022-05-17", "2022-05-18", "2022-05-19", "2022-05-20", "2022-05-21"]
      var suidaohealthData = [7.2, 8.6, 7.5, 7.7, 5.3, 8.6, 8.2, 8.5, 9.1]
      var suidaohealthYJ = [6, 6, 6, 6, 6, 6, 6]
      var option = {
        tooltip: {
          trigger: "axis"
        },
        dataZoom: {
          type: "inside",
          xAxisIndex: 0
        },
        grid: {
          x: "52",
          y: "65",
          width: "80%", // 宽度
          height: "60%"// 高度
        },
        legend: {
          data: ['隧道安全度', '隧道安全度预警值'],
        },
        xAxis: {
          type: 'category',
          data: timeList,
          axisLabel: {
            //x轴文字的配置
            interval: 1,//使x轴文字显示全
          }
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '隧道安全度',
            data: suidaohealthData,
            type: 'line'
          },
          {
            name: '隧道安全度预警值',
            data: suidaohealthYJ,
            type: 'line',
            symbol: "none",
            itemStyle: {
              normal: {
                lineStyle: {
                  width: 2,
                  type: 'dashed'  //'dotted'点型虚线 'solid'实线 'dashed'线性虚线
                }
              }
            },
          },
        ]
      }
      this.youShangPicture.setOption(option, true)
    },
    //根据测点来获得近7天的渗漏情况
    getCedianShenlou(id) {
      console.log("测点id" + id)
      if (id === '' || id === null || id === undefined) {
        console.log("没有选择测点")
        return
      }
      var timeList = ["2022-05-15", "2022-05-16", "2022-05-17", "2022-05-18", "2022-05-19", "2022-05-20", "2022-05-21"]
      var data = [6.9, 7.9, 7.4, 8.3, 9.5, 8.4, 7.7]
      // var jiaoJie = [9, 9, 9, 9, 9, 9, 9]
      var option = {
        tooltip: {
          trigger: "axis"
        },
        dataZoom: {
          type: "inside",
          xAxisIndex: 0
        },
        grid: {
          x: "52",
          y: "65",
          width: "80%", // 宽度
          height: "60%"// 高度
        },
        legend: {
          data: ['安全度'],
        },
        xAxis: {
          type: 'category',
          data: timeList,
          axisLabel: {
            //x轴文字的配置
            interval: 1,//使x轴文字显示全
          }
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '安全度',
            data: data,
            type: 'line',
            itemStyle: {
              normal: {
                color: '#5090de',
                lineStyle: {
                  color: '#5090de'
                }
              }
            }

          },
          // {
          //   name: '警戒值',
          //   data: jiaoJie,
          //   type: 'line',
          //   symbol: "none",
          //   itemStyle: {
          //     normal: {
          //       lineStyle: {
          //         width: 2,
          //         type: 'dashed'  //'dotted'点型虚线 'solid'实线 'dashed'线性虚线
          //       }
          //     }
          //   },
          // },
        ]
      }
      this.cedianShenlou.setOption(option, true)
    },
    getYucePircture(id) {
      //如果不是曲线拟合就从数据库里取值
      var option = {}
      if (id != '4') {
        var timeList = ["2022-05-15", "2022-05-16", "2022-05-17", "2022-05-18", "2022-05-19", "2022-05-20", "2022-05-21"]
        var data = [6.5, 6.9, 7.3, 8.7, 8.9, 9, 8.5]
        var jiaoJie = [8.7, 8.7, 8.7, 8.7, 8.7, 8.7, 8.7]
        option = {
          tooltip: {
            trigger: "axis"
          },
          dataZoom: {
            type: "inside",
            xAxisIndex: 0
          },
          grid: {
            x: "52",
            y: "65",
            width: "80%", // 宽度
            height: "60%"// 高度
          },
          legend: {
            data: ['预测安全度', '警戒值'],
          },
          xAxis: {
            type: 'category',
            data: timeList,
            axisLabel: {
              //x轴文字的配置
              interval: 1,//使x轴文字显示全
            }
          },
          yAxis: {
            type: 'value'
          },
          series: [
            {
              name: '预测安全度',
              data: data,
              type: 'line',
              itemStyle : {  
	                 normal : {  
	                     color:'#88c5da',  
	                     lineStyle:{  
	                         color:'#88c5da'  
	                     }  
	                 }  
	             },  
            },
            {
              name: '警戒值',
              data: jiaoJie,
              type: 'line',
              symbol: "none",
              itemStyle: {
                normal: {
                  lineStyle: {
                    width: 2,
                    type: 'dashed'  //'dotted'点型虚线 'solid'实线 'dashed'线性虚线
                  }
                }
              },
            },
          ]
        }
      }
      //如果是曲线拟合
      else {

      }
      this.yucePicture.setOption(option, true)
    }
  },
  mounted() {
    this.itemPicture = this.$echarts.init(document.getElementById('oneItemHealth'))
    var option = {
      title: {
        text: this.name
      },
      tooltip: {
        trigger: "axis"
      },
      dataZoom: {
        type: "inside",
        xAxisIndex: 0
      },
      grid: {
        x: "52",
        y: "60",
        width: "80%", // 宽度
        height: "60%"// 高度
      },

      xAxis: {
        type: 'category',
        axisLabel: {
          //x轴文字的配置
          interval: 1,//使x轴文字显示全
        }
      },
      yAxis: {
        type: 'value'
      },
    }
    this.itemPicture.setOption(option)
    this.youShangPicture = this.$echarts.init(document.getElementById('youShang'))
    this.cedianShenlou = this.$echarts.init(document.getElementById('cedianShenlou'))
    this.yucePicture = this.$echarts.init(document.getElementById('yucePicture'))
  },
}

</script>

<style></style>