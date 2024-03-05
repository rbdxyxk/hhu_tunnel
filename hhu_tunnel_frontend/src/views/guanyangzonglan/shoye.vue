<template>
    <div>
        <!-- 标题 -->
        <el-row >
            <el-col :span="24" style="height: 40px;">
               
                <div class="grid-content bg-purple-dark" style="text-align: center;font-weight: bold;">
                    <el-image style="width: 180px; height: 51px;margin-top: 0.5rem;float: left;" :src="require('../../images/xiaohui.png')"
                    :fit="fill">
                </el-image>
                <span style="line-height: 60px;">
                    基于新一代信息技术的隧道智慧养护应急管理一体化平台
                </span>
                </div>
                <!-- <span style="text-align: center;">基于新一代信息技术的隧道智慧养护应急管理一体化平台</span> -->
               
            </el-col>
        </el-row>
        <!-- 展示隧道健康值 -->
        <el-row style="margin-top: 1rem;">
            <!-- 总共24分，三等分一个8分 -->
            <el-col :span="8" style="border-style: solid;border-width: 0.6rem;height: 24rem;border-color:ghostwhite">
                <!-- 下拉框选择隧道 -->
                <el-select v-model="selectedSuidaoId" placeholder="请选择隧道" @change="getSuidaoHealthById" size="small">
                    <el-option v-for="item in suidaoSelectorList" :key="item.id" :label="item.label" :value="item.id">
                    </el-option>
                </el-select>
                <!-- 根据选择具体展示某条隧道健康值 -->
                <div id="oneSuidaoHealth"
                    style="width: 100%;height: 10rem;margin-top: 0.5rem;border-style: solid;border-width: 0.4rem;border-color:ghostwhite;">
                </div>
                <div id="allSuidaoHealth"
                    style="width: 100%;height: 10rem;margin-top: 0.5rem;border-style: solid;border-width: 0.4rem;border-color:ghostwhite">
                </div>
            </el-col>
            <!-- 地图信息 -->
            <el-col :span="8" style="border-style: solid;border-width: 0.4rem;height: 24rem;border-color:ghostwhite">
                <!-- <div class="grid-content bg-purple-dark">
                    <div id="container" style="height: 300px;"></div>
                </div> -->
                <baidu-map id="allmap" style="height: 100%;width: 100%;" :center="center" :zoom="zoom" @ready="mapReady"
                    :scroll-wheel-zoom="true">
                    <!-- <bm-polyline :path="" ></bm-polyline> -->
                    <bm-marker v-for="item in path" :key="item.index"
                        :position="{ lng: item.children.lng, lat: item.children.lat }">
                        <bm-label :content="item.label" :labelStyle="{ color: 'black', fontSize: '15px' }"
                            :offset="{ width: -25, height: 30 }" />
                    </bm-marker>

                    <!-- <MyOverlay v-for="item in path" :key="item.index" :class="{ sample: true, active }"
                        :position="{ lng: item.children.lng, lat: item.children.lat }" :text="item.label">
                    </MyOverlay> -->

                </baidu-map>
            </el-col>
            <!-- 管养系统动态信息 -->
            <el-col :span="8" style="border-style: solid;border-width: 0.6rem;height: 24rem;border-color:ghostwhite">
                <div class="grid-content bg-purple-dark">
                    <span style="font-weight: bold;margin-left: 0.4rem;margin-bottom: 1rem;">管养系统的动态信息</span>
                    <table style="margin-top: 0.9rem;border-collapse:separate; border-spacing:0.8rem 1.5rem;">
                        <!-- 第一行 -->
                        <tr>
                            <td>总的隧道数量/里程:</td>
                            <td style="color:  #CC0000;">
                                1360米
                            </td>
                        </tr>
                        <!-- 第二行 -->
                        <tr>
                            <td>总体健康的隧道</td>
                            <td style="">
                                扬子江隧道
                            </td>
                        </tr>
                        <!-- 第三行 -->
                        <tr>
                            <td>健康最差的隧道</td>
                            <td style="">
                                长江隧道
                            </td>
                        </tr>
                        <!-- 第四行 -->
                        <tr>
                            <td>急需养护的隧道</td>
                            <td style="">
                                长江隧道
                            </td>
                        </tr>
                        <!-- 第五行 -->
                        <tr>
                            <td>近期需养护的隧道</td>
                            <td style="">
                                长江隧道
                            </td>
                        </tr>
                        <!-- 第六行 -->
                        <tr>
                            <td>最近出事故的隧道</td>
                            <td style="">
                                长江隧道
                            </td>
                        </tr>
                        <!-- 第七行 -->
                        <tr>
                            <td>目前事故隧道及事故处理</td>
                            <td style="">
                                扬子江隧道,已完成故事
                            </td>
                        </tr>
                    </table>
                </div>
            </el-col>
        </el-row>
        <el-row>
            <!-- 隧道管养解决问题的展示 -->
            <el-col :span="8" style="border-style: solid;border-width: 0.6rem;height: 24rem;border-color:ghostwhite;">
                <span style="font-weight: bold;margin-left: 0.4rem;margin-bottom: 1rem;">隧道管养亟待解决的问题</span>
                <el-table :data="problemData" style="width: 100%" height="100%" :show-header="false">
                    <el-table-column prop="name" label="隧道名称" width="120">
                    </el-table-column>
                    <el-table-column prop="problem" label="代解决问题" width="400">
                    </el-table-column>
                </el-table>
            </el-col>
            <!-- 隧道病害动态信息展示 -->
            <el-col :span="8" style="border-style: solid;border-width: 0.6rem;height: 24rem;border-color:ghostwhite;">
                <span style="font-weight: bold;margin-left: 0.4rem;margin-bottom: 1rem;">隧道病害动态信息展示</span>
                <el-table :data="binghaiData" style="width: 100%" height="100%" :show-header="false">
                    <el-table-column prop="name" label="隧道名称" width="120">
                    </el-table-column>
                    <el-table-column prop="problem" label="代解决问题" width="400">
                    </el-table-column>
                </el-table>
            </el-col>
            <!-- 隧道管养计划 -->
            <el-col :span="8" style="border-style: solid;border-width: 0.6rem;height: 24rem;border-color:ghostwhite;">
                <span style="font-weight: bold;margin-left: 0.4rem;margin-bottom: 1rem;">隧道病害动态信息展示</span>
                <el-table :data="planData" style="width: 100%" height="100%" :show-header="false">
                    <el-table-column prop="name" label="隧道名称" width="100">
                    </el-table-column>
                    <el-table-column prop="status" label="完成状况" width="120">
                        <template slot-scope="scope">
                            <div v-if="scope.row.status === '管养进行中'">
                                <span style="color: orange;">{{ scope.row.status }}</span>
                            </div>
                            <div v-else>
                                <span>{{ scope.row.status }}</span>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="describe" label="描述" width="300">
                    </el-table-column>
                </el-table>
            </el-col>
        </el-row>
    </div>
</template>

<script>
import { getSuidao, getSuidaoHealthById } from '@/api/guanyangzonglan/shouye'
import MyOverlay from '@/views/guanyangzonglan/myOverLay.vue'
export default {
    components: {
        MyOverlay
    },
    data() {
        return {
            center: { lng: 0, lat: 0 },
            zoom: 8,
            //存储后端传过来的隧道集合,这里只需要隧道id,name
            suidaoSelectorList: [],
            //下拉框选中的隧道id
            selectedSuidaoId: "",
            //存储单独隧道的画图对象
            oneSuiDao: {},
            allSUiDao: {},
            //初始存放所有隧道健康值的list
            allSuiDaoData: [],
            //存放所有隧道名称
            allSuiDaoNames: [],
            //带解决隧道问题
            problemData: [],
            //病害数据
            binghaiData: [],
            //计划数据
            planData: [],
            //隧道经纬度数组
            path: [
            ],

        }
    },
    methods: {
        mapReady({ BMap, map }) {
            this.center.lng = 118.990387
            this.center.lat = 31.961236
        },
        //下拉框选中值触发
        getSuidaoHealthById(id) {
            //根据隧道id获得 getSuidaoHealthById,返回的是一个集合
            this.timeList = ["2022-05-15", "2022-05-16", "2022-05-17", "2022-05-18", "2022-05-19", "2022-05-20", "2022-05-21", "2022-05-17"]
            var data = [7.2,7.2,7.9,7.2,8.1,8.7,9.2]
            var option = {
                title: {
                    text: '隧道健康值'
                },
                tooltip: {
                    trigger: "axis"
                },
                grid: {
                    x: "54",
                    y: "55",
                    width: "80%", // 宽度
                    height: "45%"// 高度
                },
                dataZoom: {
                    type: "inside",
                    xAxisIndex: 0
                },
                legend: {
                    data: ['健康值']
                },
                xAxis: {
                    data: this.timeList,
                    axisLabel: {
                        //x轴文字的配置
                        interval: 1,//使x轴文字显示全
                    }
                },
                yAxis: {},
                series: [
                    {
                        name: '健康值',
                        type: 'line',
                        data: data
                    }
                ],
            }
            this.oneSuiDao.setOption(option, true)
        },
        //页面初始化获得时间列表,
        getAllSuidaoHealth() {
            //调用api获得所有隧道的信息,这里默认返回最近的7天,用timeList存放所有的时间
            this.timeList = ["2022-05-15", "2022-05-16", "2022-05-17", "2022-05-18", "2022-05-19", "2022-05-20", this.$moment(new Date()).format('YYYY-MM-DD')]
            //获得返回信息中所有的信息值
            this.allSuiDaoData = [
                [7.2,7.2,7.9,7.1,8,9,9.5],
                [7.2,7.3,7.8,8,7.8,8.3,8.5],
                [8.1,8.5,8,8.6,8.6,8.8,9],
                [7.1,6.8,6.5,7,7.4,7.2,7.5]
            ]
            this.allSuiDaoNames = ['定淮门隧道','长江隧道', '江心洲隧道', '竺山湖隧道']
        },
        //获取隧道管养需要解决的问题
        getProblems() {
            this.problemData = [
                {
                    name: '扬子江隧道',
                    problem: '隧道渗漏水问题,防水材料不合格'
                },
                {
                    name: '扬子江隧道',
                    problem: '隧道渗漏水问题,衬砌开裂破损'
                },
                {
                    name: '扬子江隧道',
                    problem: '隧道结构被腐蚀'
                },
                {
                    name: '扬子江隧道',
                    problem: '隧道中有瓦斯或其他可燃性气体'
                },
                {
                    name: '扬子江隧道',
                    problem: '隧道渗漏水问题,衬砌开裂破损'
                },
                {
                    name: '扬子江隧道',
                    problem: '隧道渗漏水问题,排水处理不当'
                },
                {
                    name: '扬子江隧道',
                    problem: '隧道渗漏水问题,防水材料不合格'
                },
                {
                    name: '扬子江隧道',
                    problem: '隧道渗漏水问题,衬砌开裂破损'
                },
                {
                    name: '扬子江隧道',
                    problem: '隧道结构被腐蚀'
                },
                {
                    name: '扬子江隧道',
                    problem: '隧道中有瓦斯或其他可燃性气体'
                },
                {
                    name: '扬子江隧道',
                    problem: '隧道渗漏水问题,衬砌开裂破损'
                },
                {
                    name: '扬子江隧道',
                    problem: '隧道渗漏水问题,排水处理不当'
                },
            ]
        },
        //获取病害信息
        getBinghai() {
            this.binghaiData = [
                {
                    "name": "定淮门隧道",
                    "problem": "隧道渗漏水问题,衬砌开裂破损,排水处理不当"
                },
                {
                    "name": "定淮门隧道",
                    "problem": "隧道结构被腐蚀"
                },
                {
                    "name": "定淮门隧道",
                    "problem": "隧道中有瓦斯或其他可燃性气体"
                },
                {
                    "name": "定淮门隧道",
                    "problem": "隧道渗漏水问题,衬砌开裂破损,排水处理不及时"
                },
                {
                    "name": "定淮门隧道",
                    "problem": "隧道渗漏水问题,衬砌开裂破损,排水处理不当"
                },
                {
                    "name": "定淮门隧道",
                    "problem": "隧道结构被腐蚀"
                },
                {
                    "name": "定淮门隧道",
                    "problem": "隧道中有瓦斯或其他可燃性气体"
                },
                {
                    "name": "定淮门隧道",
                    "problem": "隧道渗漏水问题,衬砌开裂破损,排水处理不及时"
                },

            ]
        },
        //获取计划
        getPlan() {
            this.planData = [
                {
                    "name": "隧道A",
                    "status": "管养完成",
                    "describe": "计划管养计划推迟到本月20日"
                },
                {
                    "name": "隧道A",
                    "status": "管养完成",
                    "describe": "计划管养计划推迟到本月20日"
                },
                {
                    "name": "隧道A",
                    "status": "管养完成",
                    "describe": "计划管养计划推迟到本月20日"
                },
                {
                    "name": "隧道A",
                    "status": "管养完成",
                    "describe": "计划管养计划推迟到本月20日"
                },
                {
                    "name": "隧道A",
                    "status": "管养进行中",
                    "describe": "计划管养计划推迟到本月20日"
                },
                {
                    "name": "隧道A",
                    "status": "管养进行中",
                    "describe": "计划管养计划推迟到本月20日"
                },
                {
                    "name": "隧道A",
                    "status": "管养进行中",
                    "describe": "计划管养计划推迟到本月20日"
                },
            ]
        },
        draw({ el, BMap, map }) {
            const { lng, lat } = this.position
            const pixel = map.pointToOverlayPixel(new BMap.Point(lng, lat))
            el.style.left = pixel.x - 16 + 'px'
            el.style.top = pixel.y - 16 + 'px'
        }

    },
    mounted() {
        this.oneSuiDao = this.$echarts.init(document.getElementById('oneSuidaoHealth'))
        //
        this.getAllSuidaoHealth()
        this.allSUiDao = this.$echarts.init(document.getElementById("allSuidaoHealth"))
        var allName = this.allSuiDaoNames
        var allData = this.allSuiDaoData
        var allOption = {
            tooltip: {
                trigger: "axis"
            },
            dataZoom: {
                type: "inside",
                xAxisIndex: 0
            },
            grid: {
                x: "54",
                y: "55",
                width: "80%", // 宽度
                height: "45%"// 高度
            },
            legend: {
                data: this.allSuiDaoNames
            },
            xAxis: {
                type: 'category',
                data: this.timeList,
                axisLabel: {
                    //x轴文字的配置
                    interval: 1,//使x轴文字显示全
                }
            },
            yAxis: {
                type: 'value'
            },
            series: function () {
                let serie = []
                allName.forEach((element, index) => {
                    serie.push(
                        {
                            data: allData[index],
                            name: allName[index],
                            type: "line",
                            //    itemStyle: {
                            //        color: colorArr[index]
                            //    },
                            symbol: 'circle',
                            symbolSize: 6,
                            smooth: true
                        }
                    )
                });
                return serie;
            }()

        }
        this.allSUiDao.setOption(allOption, true)
    },
    created() {
        //调用getSuidao获取所有隧道信息
        this.suidaoSelectorList = [
            {
                id: 1,
                label: '定淮门隧道',
                children: {
                    lng: 118.744844,
                    lat: 32.070552
                },
            },
            {
                id: 2,
                label: '长江隧道',
                children:
                {
                    lng: 118.686886,
                    lat: 32.05648
                },

            },
            {
                id: 3,
                label: '江心洲夹江隧道',
                children: {
                    lng: 118.699106,
                    lat: 32.004736
                },

            },
            {
                id: 4,
                label: '竺山湖隧道',
                children: {
                    lng: 120.145852,
                    lat: 31.474059
                },

            },
        ]
        //获取隧道管养要解决的问题
        this.getProblems()
        //获取病害信息
        this.getBinghai()
        //获取管养计划
        this.getPlan()
        //api获取隧道信息,这里也可以使用getSuidao得到的结果
        this.path = [{
            id: 1,
            label: '定淮门隧道',
            children: {
                lng: 118.744844,
                lat: 32.070552
            },
        },
        {
            id: 2,
            label: '长江隧道',
            children:
            {
                lng: 118.686886,
                lat: 32.05648
            },
        },
        {
            id: 3,
            label: '江心洲夹江隧道',
            children: {
                lng: 118.699106,
                lat: 32.004736
            },

        },
        {
            id: 4,
            label: '竺山湖隧道',
            children: {
                lng: 120.145852,
                lat: 31.474059
            },

        },]
    },
}

</script>

<style scoped></style>
