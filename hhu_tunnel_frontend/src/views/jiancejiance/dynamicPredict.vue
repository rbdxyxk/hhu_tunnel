<template>
    <div style="height: 30rem;">
        <h2>动态安全预测</h2>
        <el-form :inline="true" :model="queryEntity" class="demo-form-inline">
            <el-form-item label="预测项目">
                <el-select v-model="queryEntity['prop']" placeholder="请选择预测项目">
                    <el-option v-for="item in predictEntityList" :key="item.prop" :label="item.label" :value="item.prop">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="测点位置">
                <el-input v-model="queryEntity['cdwz']" placeholder="请输入测点位置">
                </el-input>
            </el-form-item>
            <el-form-item label="起始日期">
                <el-date-picker v-model="queryEntity['beginTime']" type="datetime" placeholder="选择起始日期">
                </el-date-picker>
            </el-form-item>
            <el-form-item label="终点日期">
                <el-date-picker v-model="queryEntity['endTime']" type="datetime" placeholder="选择终点日期">
                </el-date-picker>
            </el-form-item>
            <el-form-item label="预测日期">
                <el-date-picker v-model="queryEntity['predictTime']" type="datetime" placeholder="选择预测日期">
                </el-date-picker>
            </el-form-item>
            <el-form-item label="预测方法">
                <el-select v-model="queryEntity['method']" placeholder="请选择预测方法">
                    <el-option v-for="item in methodList" :key="item.id" :label="item.label" :value="item.id">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="onSubmit">预测</el-button>
            </el-form-item>
        </el-form>
        <div id="picture" style="width: 100%;height: 19rem;">

        </div>
    </div>
</template>

<script>
import request from '@/utils/request'
export default {
    data() {
        return {
            queryEntity: {},
            methodList: [],
            predictEntityList: [],
        }
    },
    created() {
        this.$bus.$on('queryEntity', (data, predictEntityList) => {
            this.queryEntity = data
            this.predictEntityList = predictEntityList
        }
        )
        this.methodList = [
            {
                id: 1,
                label: '统计分析预测值'
            },
            {
                id: 2,
                label: '动态反演法预测值'
            },
            {
                id: 3,
                label: '大数据分析法预测值'
            },
            {
                id: 4,
                label: '拟合法预测值'
            },
        ]
    },
    methods: {
        onSubmit() {
            //获取预测query
            if (this.queryEntity['prop'] == undefined || this.queryEntity['prop'] == '' || this.queryEntity['prop'] == null) {
                this.$message.warning("预测项目没有选择")
                return
            }
            if (this.queryEntity['cdwz'] == undefined || this.queryEntity['cdwz'] == '' || this.queryEntity['cdwz'] == null) {
                this.$message.warning("测点位置没有输入")
                return
            }
            if (this.queryEntity['beginTime'] == undefined || this.queryEntity['beginTime'] == '' || this.queryEntity['beginTime'] == null) {
                this.$message.warning("起始时间没有选择")
                return
            }
            if (this.queryEntity['endTime'] == undefined || this.queryEntity['endTime'] == '' || this.queryEntity['endTime'] == null) {
                this.$message.warning("结束时间没有选择")
                return
            }
            if (this.queryEntity['predictTime'] == undefined || this.queryEntity['predictTime'] == '' || this.queryEntity['predictTime'] == null) {
                this.$message.warning("预测时间没有选择")
                return
            }
            if (this.queryEntity['method'] == undefined || this.queryEntity['method'] == '' || this.queryEntity['method'] == null) {
                this.$message.warning("预测方法没有选择")
                return
            }
            if (this.queryEntity['method'] != 4) {
                this.$message.warning("该预测方法暂未实现")
                return
            }
            //转换时间格式
            this.queryEntity['beginTime'] = this.$moment(this.queryEntity['beginTime']).format('YYYY-MM-DD HH:mm:ss')
            this.queryEntity['endTime'] = this.$moment(this.queryEntity['endTime']).format('YYYY-MM-DD HH:mm:ss')
            this.queryEntity['predictTime'] = this.$moment(this.queryEntity['predictTime']).format('YYYY-MM-DD HH:mm:ss')
            var picture = this.$echarts.init(document.getElementById("picture"))
            //调用后端借口获取数据,传递的是queryEntity,其中有type字段   
            request({
                url: "/dynamic/predict",
                method: "post",
                data: this.queryEntity
            }).then(response => {
                if (response.code === 201) {
                    this.$message.warning(response.data)
                    return
                }
                 var timeList = response.data.timeList
                 var data = response.data.re
            var option = {
                title: {
                    text: '预测曲线图'
                },
                tooltip: {
                    trigger: "axis"
                },
                grid: {
                    x: "54",
                    y: "50",
                    width: "90%", // 宽度
                    height: "75%"// 高度
                },
                dataZoom: {
                    type: "inside",
                    xAxisIndex: 0
                },
                legend: {
                    data: ['预测值']
                },
                xAxis: {
                    data: timeList,
                    axisLabel: {
                        //x轴文字的配置
                        // interval: 1,//使x轴文字显示全
                    }
                },
                yAxis: {},
                series: [
                    {
                        name: '预测值',
                        type: 'line',
                        data: data
                    }
                ],
            }
            picture.setOption(option, true)
            })
           
        }
    },
}
</script>

<style></style>