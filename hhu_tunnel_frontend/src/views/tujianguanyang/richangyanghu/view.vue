<template>
    <div>
        <h1 style="text-align: center; font-size: 30px;font-weight: bold;margin-bottom: 5rem;">施工维修申请单</h1>
        <el-row style="margin-bottom: 1rem;">
            <hr />
            <span class="title">项目信息</span>
            <el-form :inline="true" :model="xiangmuInformation" class="demo-form-inline"
                style="width: 100%;text-align: center;font-size: 20px;margin-top: 1rem;" label-width="100px" size="medium">
                <el-form-item label="业主单位:">
                    <!-- <el-input v-model="" placeholder="审批人"></el-input> -->
                    <div>{{ xiangmuInformation['yzdw'] }}</div>
                </el-form-item>
                <el-form-item label="合同号:">
                    <!-- <el-input v-model="" placeholder="审批人"></el-input> -->
                    <div>{{ xiangmuInformation['hth'] }}</div>
                </el-form-item>
            </el-form>
            <el-form :inline="true" :model="xiangmuInformation" class="demo-form-inline"
                style="width: 100%;text-align: center;font-size: 20px;margin-top: 1rem;" label-width="100px" size="medium">
                <el-form-item label="承包单位:">
                    <!-- <el-input v-model="" placeholder="审批人"></el-input> -->
                    <div>{{ xiangmuInformation['cbdw'] }}</div>
                </el-form-item>
                <el-form-item label="编号:">
                    <!-- <el-input v-model="" placeholder="审批人"></el-input> -->
                    <div>{{ xiangmuInformation['bh'] }}</div>
                </el-form-item>
            </el-form>
        </el-row>
        <el-row style="margin-bottom: 1rem;">
            <hr />
            <span class="title">申请单信息</span>
            <el-form :inline="true" :model="shenqingdanInforamtion" class="demo-form-inline"
                style="width: 100%;font-size: 20px;margin-top: 1rem;" label-width="100px" size="medium">
                <el-form-item label="致:">
                    <div>{{ shenqingdanInforamtion['dw'] }}</div>
                </el-form-item>
            </el-form>
            <el-form :inline="true" :model="shenqingdanInforamtion" class="demo-form-inline"
                style="width: 100%;font-size: 20px;margin-top: 1rem;margin-left: 6rem;" label-width="100px" size="medium">
                <el-form-item label="">
                    <!-- <el-input type=" textarea" :rows="10" placeholder="请输入内容" v-model="shenqingdanInforamtion['content']">
                    </el-input> -->
                    <div> {{ shenqingdanInforamtion['content'] }} </div>
                </el-form-item>
            </el-form>
            <el-form :inline="true" :model="shenqingdanInforamtion" class="demo-form-inline"
                style="width: 100%;font-size: 20px;margin-top: 1rem;" label-width="100px" size="medium">

                <el-form-item label="日期:" style="float: right;">
                    <!-- <el-input v-model="" placeholder="审批人"></el-input> -->
                    <div>{{ shenqingdanInforamtion['rq'] }}</div>
                </el-form-item>
                <el-form-item label="申请人:" style="float: right;">
                    <!-- <el-input v-model="" placeholder="审批人"></el-input> -->
                    <div>{{ shenqingdanInforamtion['sqr'] }}</div>
                </el-form-item>
            </el-form>
        </el-row>
        <el-row style="margin-bottom: 1rem;">
            <hr />
            <span class="title">技术要求说明</span>
            <el-form :inline="true" :model="jishuInforamtion" class="demo-form-inline"
                style="width: 100%;font-size: 20px;margin-top: 1rem;margin-left: 6rem;" label-width="100px" size="medium">
                <el-form-item label="">
                    <!-- <el-input type=" textarea" :rows="10" placeholder="请输入内容" v-model="shenqingdanInforamtion['content']">
                    </el-input> -->
                    <div> {{ jishuInforamtion['content'] }} </div>
                </el-form-item>
            </el-form>
        </el-row>

        <el-row style="margin-bottom: 1rem;">
            <hr />
            <span class="title">申请附件</span>
            <el-table :data="fujianData" style="width: 100%">
                <el-table-column prop="fileName" label="文件名" width="800">
                </el-table-column>
                <el-table-column prop="status" label="状态" width="800">
                </el-table-column>
            </el-table>
        </el-row>

        <el-row style="margin-bottom: 1rem;">
            <hr />
            <span class="title">负责人意见</span>
            <el-form :inline="true" :model="yijianData" class="demo-form-inline"
                style="width: 100%;font-size: 20px;margin-top: 1rem;" label-width="100px" size="medium">
                <el-form-item label="意见:">
                    <!-- <el-input type=" textarea" :rows="10" placeholder="请输入内容" v-model="shenqingdanInforamtion['content']">
                    </el-input> -->
                    <div> {{ yijianData['content'] }} </div>
                </el-form-item>
            </el-form>

            <el-form :inline="true" :model="yijianData" class="demo-form-inline"
                style="width: 100%;font-size: 20px;margin-top: 1rem;" label-width="100px" size="medium">

                <el-form-item label="日期:" style="float: right;">
                    <!-- <el-input v-model="" placeholder="审批人"></el-input> -->
                    <div>{{ yijianData['rq'] }}</div>
                </el-form-item>
                <el-form-item label="负责人:" style="float: right;">
                    <!-- <el-input v-model="" placeholder="审批人"></el-input> -->
                    <div>{{ yijianData['fzr'] }}</div>
                </el-form-item>
            </el-form>
        </el-row>

        <el-row style="margin-bottom: 1rem;">
            <hr />
            <span class="title">综合管养中心意见</span>
            <el-form :inline="true" :model="dwyijianData" class="demo-form-inline"
                style="width: 100%;font-size: 20px;margin-top: 1rem;" label-width="100px" size="medium">
                <el-form-item label="意见:">
                    <!-- <el-input type=" textarea" :rows="10" placeholder="请输入内容" v-model="shenqingdanInforamtion['content']">
                    </el-input> -->
                    <div> {{ dwyijianData['content'] }} </div>
                </el-form-item>
            </el-form>

            <el-form :inline="true" :model="dwyijianData" class="demo-form-inline"
                style="width: 100%;font-size: 20px;margin-top: 1rem;" label-width="100px" size="medium">

                <el-form-item label="日期:" style="float: right;">
                    <!-- <el-input v-model="" placeholder="审批人"></el-input> -->
                    <div>{{ dwyijianData['rq'] }}</div>
                </el-form-item>
                <el-form-item label="管养中心:" style="float: right;">
                    <!-- <el-input v-model="" placeholder="审批人"></el-input> -->
                    <div>{{ dwyijianData['fzr'] }}</div>
                </el-form-item>
            </el-form>
        </el-row>
    </div>
</template>

<script>
export default {
    data() {
        return {
            xiangmuInformation: {},
            id: 0,
            shenqingdanInforamtion: {},
            jishuInforamtion: {},
            fujianData: [],
            yijianData: {},
        }
    },
    created() {
        this.id = this.$route.params.id
        console.log("收到id", this.id)
        this.xiangmuInformation = {
            id: 1,
            yzdw: '南京长江隧道有限责任公司',
            hth: 'demo',
            cbdw: '中交基础设施养护集团有限公司',
            bh: 'GC-A786'
        }
        this.shenqingdanInforamtion = {
            id: 1,
            dw: '综合管养中心',
            content: '经日常检查发现下列位置存在病害或缺陷，需组织维修,特此申请。附件......',
            sqr: '王林',
            rq: '2023-03-19 14:01:07'
        }

        this.jishuInforamtion = {
            id: 1,
            content: '技术要求说明必须填写方案'
        }

        this.yijianData = {
            id: 1,
            content: '申请维修处置',
            fzr: '王林',
            rq: '2023-03-19 14:15:22'
        }

        this.dwyijianData = {
            id: 1,
            content: '同意维修处置',
            fzr: '王林',
            rq: '2023-03-19 14:15:22'
        }

        // this.shenqingdanInforamtion['content'] = '<p>' + this.shenqingdanInforamtion['content'].replace(/ /g, '&nbsp;').replace(/\n|\r\n/g, '</p><p>') + '<p>'
    },
}
</script>

<style>
.title {
    font-size: 25px;
    font-weight: 500;
    margin-left: 1rem;
}

.el-form-item__label {
    font-size: 16px;
}

.el-form-item__content {
    font-size: 16px;
    border-style: solid;
    border-width: thin;
    border-color: gainsboro;
    width: 30rem;
    text-align: center;
}
</style>