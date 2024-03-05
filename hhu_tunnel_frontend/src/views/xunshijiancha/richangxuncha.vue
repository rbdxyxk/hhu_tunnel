<template>
    <div>
        <el-row :gutter="10">
            <el-col :span="6" style="border-style: solid;">
                <el-tree ref="tree" :data="xunchaData" :props="defaultProps" node-key="id"  show-checkbox
                    :check-on-click-node="true" :expand-on-click-node="false" @node-click="handleNodeClick" highlight-current="true">
                    <!-- <span class="custom-tree-node" slot-scope="{  data }">
                        <span style="margin-right: 2rem;">{{ data.label }}</span>
                        <span>
                            <el-button type="text" size="mini" @click="() => view(data)">
                                查看
                            </el-button> -->
                            <!-- <el-button type="text" size="mini" @click="() => append(data)">
                                添加
                            </el-button>
                            <el-button type="text" size="mini" @click="() => remove(node, data)">
                                删除
                            </el-button> -->
                        <!-- </span>
                    </span> -->
                </el-tree>
            </el-col>
            <el-col :span="18" v-if="tableVisual">
                <el-button type="primary" plain @click="addXuncha">添加</el-button>
                <el-button type="primary" plain @click="editXuncha">编辑</el-button>
                <el-button type="primary" plain @click="deleteXuncha">删除</el-button>
                <el-table :data="tableData" border style="width: 100%;margin-top: 1rem;"
                    @selection-change="handleSelectionChange" :key="Math.random()">
                    <el-table-column type="selection" width="55">
                    </el-table-column>
                    <el-table-column prop="beginDate" label="开始时间" width="160">
                    </el-table-column>
                    <el-table-column prop="endDate" label="结束时间" width="160">
                    </el-table-column>
                    <el-table-column prop="bianhao" label="编号" width="100">
                    </el-table-column>
                    <el-table-column prop="gongweimingcheng" label="工位名称" width="100">
                    </el-table-column>
                    <el-table-column prop="resultAndDescribe" label="巡查结果及描述" width="100">
                    </el-table-column>
                    <el-table-column prop="binghaileixing" label="病害类型" width="100">
                        <template slot-scope="scope">
                            <div v-if="scope.row.binghaileixing === 0 || scope.row.binghaileixing === '0'">
                                <span>隧道水害</span>
                            </div>
                            <div v-if="scope.row.binghaileixing === 1 || scope.row.binghaileixing === '1'">
                                <span>衬砌损裂</span>
                            </div>
                            <div v-if="scope.row.binghaileixing === 2 || scope.row.binghaileixing === '2'">
                                <span>隧道冻害</span>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="fileName" label="附件" width="100">
                       
                    </el-table-column>
                    <el-table-column prop="zhixingren" label="执行人" width="100">
                    </el-table-column>
                    <el-table-column prop="jiluren" label="记录人" width="100">
                    </el-table-column>
                    <el-table-column prop="sta" label="任务状态" width="100">
                        <template slot-scope="scope">
                            <div v-if="scope.row.sta === 0 || scope.row.sta === '0'">
                                <span>已完成</span>
                            </div>
                            <div v-if="scope.row.sta === 1 || scope.row.sta === '1'">
                                <span>未完成</span>
                            </div>
                            <div v-if="scope.row.sta === 2 || scope.row.sta === '2'">
                                <span>正在执行</span>
                            </div>
                        </template>
                    </el-table-column>
                </el-table>
            </el-col>
        </el-row>
        <!-- 添加日常巡查弹框 -->
        <el-dialog title="添加日常巡查" :visible.sync="addDialogVisible" width="30%" :show-close="false">
            <el-form ref="form" :model="addData" label-width="5rem">
                <el-form-item label="开始时间" style="margin-bottom: 0.6rem;">
                    <el-date-picker v-model="addData.beginDate" type="datetime" placeholder="选择开始时间" value-format="yyyy-MM-dd HH:mm:ss">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="结束时间" style="margin-bottom: 0.6rem;">
                    <el-date-picker v-model="addData.endDate" type="datetime" placeholder="选择结束时间" value-format="yyyy-MM-dd HH:mm:ss">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="编号" style="margin-bottom: 0.6rem;">
                    <el-input v-model="addData.bianhao"></el-input>
                </el-form-item>
                <el-form-item label="工位名称" style="margin-bottom: 0.6rem;">
                    <el-input v-model="addData.gongweimingcheng"></el-input>
                </el-form-item>
                <el-form-item label="巡查结果及描述" style="margin-bottom: 0.6rem;">
                    <el-input v-model="addData.resultAndDescribe"></el-input>
                </el-form-item>
                <el-form-item label="病害类型" style="margin-bottom: 0.6rem;">
                    <el-select v-model="addData.binghaileixing" placeholder="选择病害类型">
                        <el-option v-for="item in binghaiSelector" :key="item.value" :label="item.label"
                            :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="上传附件" style="margin-bottom: 0.6rem;">
                    <el-upload class="upload-demo" :action="uploadUrl" :on-success="addFileId" ref="uploadADD">
                        <el-button type="info" plain size="small">上传<i
                                class="el-icon-upload el-icon--right"></i></el-button>
                    </el-upload>
                </el-form-item>
                <el-form-item label="执行人" style="margin-bottom: 0.6rem;">
                    <el-input v-model="addData.zhixingren"></el-input>
                </el-form-item>
                <el-form-item label="记录人" style="margin-bottom: 0.6rem;">
                    <el-input v-model="addData.jiluren"></el-input>
                </el-form-item>
                <el-form-item label="任务状态" style="margin-bottom: 0.6rem;">
                    <el-select v-model="addData.sta" placeholder="选择任务状态">
                        <el-option v-for="item in taskStatus" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="addDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="addXunchaData">确 定</el-button>
            </span>
        </el-dialog>
        <el-dialog title="编辑日常巡查" :visible.sync="editorDialogVisible" width="30%" :show-close="false">
            <el-form ref="form" :model="edditData" label-width="5rem">
                <el-form-item label="开始时间" style="margin-bottom: 0.6rem;">
                    <el-date-picker v-model="edditData.beginDate" type="datetime" placeholder="选择开始时间"
                        value-format="yyyy-MM-dd HH:mm:ss">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="结束时间" style="margin-bottom: 0.6rem;">
                    <el-date-picker v-model="edditData.endDate" type="datetime" placeholder="选择结束时间" value-format="yyyy-MM-dd HH:mm:ss">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="编号" style="margin-bottom: 0.6rem;">
                    <el-input v-model="edditData.bianhao"></el-input>
                </el-form-item>
                <el-form-item label="工位名称" style="margin-bottom: 0.6rem;">
                    <el-input v-model="edditData.gongweimingcheng"></el-input>
                </el-form-item>
                <el-form-item label="巡查结果及描述" style="margin-bottom: 0.6rem;">
                    <el-input v-model="edditData.resultAndDescribe"></el-input>
                </el-form-item>
                <el-form-item label="病害类型" style="margin-bottom: 0.6rem;">
                    <el-select v-model="edditData.binghaileixing" placeholder="选择病害类型">
                        <el-option v-for="item in binghaiSelector" :key="item.value" :label="item.label"
                            :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="上传附件" style="margin-bottom: 0.6rem;">
                    <el-upload class="upload-demo" :action="uploadUrl" :on-success="addFileId" ref="uploadADD">
                        <el-button type="info" plain size="small">上传<i
                                class="el-icon-upload el-icon--right"></i></el-button>
                    </el-upload>
                </el-form-item>
                <el-form-item label="执行人" style="margin-bottom: 0.6rem;">
                    <el-input v-model="edditData.zhixingren"></el-input>
                </el-form-item>
                <el-form-item label="记录人" style="margin-bottom: 0.6rem;">
                    <el-input v-model="edditData.jiluren"></el-input>
                </el-form-item>
                <el-form-item label="任务状态" style="margin-bottom: 0.6rem;">
                    <el-select v-model="edditData.sta" placeholder="选择任务状态">
                        <el-option v-for="item in taskStatus" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="cancelEdit">取 消</el-button>
                <el-button type="primary" @click="submitEdit">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import preventBack from 'vue-prevent-browser-back';
export default {
    data() {
        return {
            suidaoId: 0,
            structId: 0,
            xunchaData: [
                {
                    id: -1,
                    label: "全部",
                    children: [],
                }
            ],
            defaultProps: {
                children: 'children',
                label: 'label'
            },
            addDialogVisible: false,
            editorDialogVisible: false,
            selectedXuncha: [],
            tableData: [],
            addData: {
                suidaoId: 0,
                structId: 0,
                beginDate: '',
                endDate: '',
                bianhao: '',
                gongweimingcheng: '',
                resultAndDescribe: '',
                binghaileixing: '',
                fileName: '',
                zhixingren: '',
                jiluren: '',
                sta: '',
                fileId: '',
            },
            edditData: {
                id: 0,
                suidaoId: 0,
                structId: 0,
                beginDate: '',
                endDate: '',
                bianhao: '',
                gongweimingcheng: '',
                resultAndDescribe: '',
                binghaileixing: '',
                fileName: '',
                zhixingren: '',
                jiluren: '',
                sta: '',
                //文件加密id
                fileId: '',
            },
            uploadUrl: process.env.VUE_APP_BASE_API + '/file/upload',
            taskStatus: [{
                value: 0,
                label: '已完成'
            },
            {
                value: 1,
                label: '未完成'
            },
            {
                value: 2,
                label: '正在执行'
            },],
            binghaiSelector: [{
                value: 0,
                label: '隧道水害'
            },
            {
                value: 1,
                label: '衬砌损裂'
            },
            {
                value: 2,
                label: '隧道冻害'
            }],
            oldEditData: {},
            tableVisual: false
        }
    },
    mixins: [preventBack],//注入  阻止返回上一页
    mounted() {
        //通过api获得所有children
        this.xunchaData[0].children = [
            {
                id: 2,
                label: "隧道1",
                children: [
                    {
                        id: 4,
                        parentId: 2,
                        label: "主体结构",
                        children: [],
                    },
                    {
                        id: 5,
                        parentId: 2,
                        label: "附属结构",
                        children: [],
                    },
                    {
                        id: 6,
                        parentId: 2,
                        label: "路面",
                        children: [],
                    },
                ],
            },
            {
                id: 3,
                label: "隧道2",
                children: [
                    {
                        id: 4,
                        parentId: 3,
                        label: "主体结构",
                        children: [],
                    },
                    {
                        id: 5,
                        parentId: 3,
                        label: "附属结构",
                        children: [],
                    },
                    {
                        id: 6,
                        parentId: 3,
                        label: "路面",
                        children: [],
                    },
                ],
            },

        ]

    },
    methods: {
        handleNodeClick(data) {
            console.log(data)
            if (data.children.length != 0)
                return;
            this.suidaoId = data.parentId
            this.structId = data.id
            //根据选中的data的id和parentId来获取对应的信息
            //通过api获得隧道的具体结构的数据
            this.tableData = [
                {
                    id: 1,
                    suidaoId: 2,
                    structId: 5,
                    beginDate: '2022-05-13',
                    endDate: '2022-05-14',
                    bianhao: '123103124',
                    gongweimingcheng: 'demo',
                    resultAndDescribe: '这个是结果和描述',
                    binghaileixing: 0,
                    fileName: 'test.doc',
                    fileId: "99",
                    zhixingren: '张三',
                    jiluren: '李四',
                    sta: 0
                },
                {
                    id: 2,
                    suidaoId: 2,
                    structId: 5,
                    beginDate: '2022-05-13',
                    endDate: '2022-05-14',
                    bianhao: '123103124',
                    gongweimingcheng: 'demo',
                    resultAndDescribe: '这个是结果和描述',
                    binghaileixing: 0,
                    fileName: 'test.doc',
                    fileId: "99",
                    zhixingren: '张三',
                    jiluren: '李四',
                    sta: 0
                },
                {
                    id: 3,
                    suidaoId: 2,
                    structId: 5,
                    beginDate: '2022-05-13',
                    endDate: '2022-05-14',
                    bianhao: '123103124',
                    gongweimingcheng: 'demo',
                    resultAndDescribe: '这个是结果和描述',
                    binghaileixing: 0,
                    fileName: 'test.doc',
                    fileId: "99",
                    zhixingren: '张三',
                    jiluren: '李四',
                    sta: 0
                },
            ]
            this.tableVisual = true
        },
        // append(data) {

        //     console.log(data)
        // },
        // remove(node, data) {
        //     const parent = node.parent;
        //     const children = parent.data.children || parent.data;
        //     const index = children.findIndex(d => d.id === data.id);
        //     children.splice(index, 1);
        // },
        //将选中的数据放入
        handleSelectionChange(val) {
            //注意选中的是数组,如果只想取第一个那么就选择0
            this.selectedXuncha = val
        },
        //添加
        addXuncha() {
            this.editorDialogVisible = false
            this.addData = {
                suidaoId: this.suidaoId,
                structId: this.structId,
                beginDate: '',
                endDate: '',
                bianhao: '',
                gongweimingcheng: '',
                resultAndDescribe: '',
                binghaileixing: '',
                zhixingren: '',
                jiluren: '',
                sta: '',
                //文件加密id
                fileId: "",
            },
                this.addDialogVisible = true
        },
        //编辑
        editXuncha() {
            this.addDialogVisible = false
            if (this.selectedXuncha.length < 1) {
                this.$message({
                    message: '请选择要编辑的信息',
                    type: 'warning'
                });
                return;
            }
            if (this.selectedXuncha.length > 1) {
                this.$message({
                    message: '只能编辑一条',
                    type: 'warning'
                });
                return;
            }
            this.edditData = this.selectedXuncha[0]
            this.oldEditData = JSON.parse(JSON.stringify(this.selectedXuncha[0]))
            this.editorDialogVisible = true
        },
     
        //添加巡查到数据库
        addXunchaData() {
            //将addData提交给后端,需要加入隧道id和结构id
            //收到回复后,将id复制给addData
            this.addData.id = 99
            this.tableData.push(this.addData)
            this.addDialogVisible = false
        },
        //成功就把response中的信息返回
        addFileId(response, file, fileList) {
            //从response中获取文件id
            console.log(response)
            this.addData.fileId = response.data.fileId
            this.addData.fileName = response.data.orginalName
            this.edditData.fileId =response.data.fileId
            this.edditData.fileName = response.data.orginalName
        },
        //提交修改  
        submitEdit() {
            this.editorDialogVisible = false
            //发送api给后端
        },
        //取消修改
        cancelEdit() {
            for(var i = 0; i < this.tableData.length; i++){
            //找到修改的数据的id
            if (this.tableData[i].id === this.oldEditData.id){
              this.tableData[i] = this.oldEditData
              break;
            }
          }
            this.editorDialogVisible = false
        },
        //删除数据
        deleteXuncha() {
            if (this.selectedXuncha.length < 1) {
                this.$message({
                    message: '请选择要删除的信息',
                    type: 'warning'
                });
                return;
            }
            this.$confirm('此操作将永久删除, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                //用户点击了确定,要删除信息
                var ids = []
                for(var i = 0; i < this.selectedXuncha.length; i++){
                    ids.push(this.selectedXuncha[i].id)
                }
                //将ids发送给后端，进行删除操作，删除完将tableData重新赋值
                //这里手动实现删除
                for (var i = 0; i < ids.length; i++){
                    for (var j = 0 ; j < this.tableData.length; j++){
                        if (this.tableData[j].id === ids[i]){
                            this.tableData.splice(j,1)
                        }
                    }
                }
                this.$message({
                    type: 'success',
                    message: '删除成功!'
                });
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        }

        
},
}
</script>

<style scoped></style>