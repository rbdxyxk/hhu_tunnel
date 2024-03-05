<template>
    <div>
        <div id="yucePicture"
            style="width: 100%;height: 18rem;margin-top: 0.5rem;border-style: solid;border-width: 0.3rem;border-color:ghostwhite;">
        </div>
    </div>
</template>

<script>
export default {
    mounted() {
        var yucePicture = this.$echarts.init(document.getElementById('yucePicture'))
        var timeList = ["2022-05-15", "2022-05-16", "2022-05-17", "2022-05-18", "2022-05-19", "2022-05-20", "2022-05-21"]
        var data = [50, 40, 60, 60, 70, 90, 60]
        var jiaoJie = [40, 50, 70, 70, 65, 80, 60]
        var option = {
            title: {
                text: "xxx单位用水量图"
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
                y: "65",
                width: "60%", // 宽度
                height: "60%"// 高度
            },
            legend: {
                data: ['用水量', '计划用水量'],
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
                    name: '用水量',
                    data: data,
                    type: 'line',
                    itemStyle: {
                        normal: {
                            color: '#88c5da',
                            lineStyle: {
                                color: '#88c5da'
                            }
                        }
                    },
                },
                {
                    name: '计划用水量',
                    data: jiaoJie,
                    type: 'scatter',
                    //   symbol: "none",
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
        yucePicture.setOption(option, true)
    },
}
</script>

<style></style>