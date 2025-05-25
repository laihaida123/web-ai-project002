<script setup>
import { watch } from 'vue';
import { ref } from 'vue';
import { queryPageApi } from '@/api/emp'
import { queryAllApi as queryAllDeptApi } from '@/api/dept'
import { onMounted } from 'vue';
import { ElMessage } from 'element-plus';
//元数据
//职位列表数据
const jobs = ref([{ name: '班主任', value: 1 }, { name: '讲师', value: 2 }, { name: '学工主管', value: 3 }, { name: '教研主管', value: 4 }, { name: '咨询师', value: 5 }, { name: '其他', value: 6 }])
//性别列表数据
const genders = ref([{ name: '男', value: 1 }, { name: '女', value: 2 }])
//部门列表数据
const depts = ref([])



//搜索表单对象
const searchEmp = ref({
  name: '',
  gender: '',
  date: [],
  begin: '',
  end: ''
})


// 分页配置
const currentPage = ref(1)//页码
const pageSize = ref(10)//每页展示记录数
const background = ref(true)//背景色
const total = ref(0)//总记录数



// 分页处理 
//每页展示记录数
const handleSizeChange = (val) => {
  console.log(`${val} 条/页`);
  search();
}
//页码变化时处理
const handleCurrentChange = (val) => {
  console.log(`当前页码：${val}`);
  search();
}
//侦听searchemp的date属性
watch(() => searchEmp.value.date, (newVal, oldVal) => {
  if (newVal.length == 2) {
    searchEmp.value.begin = newVal[0];
    searchEmp.value.end = newVal[1];
  } else {
    searchEmp.value.begin = '';
    searchEmp.value.end = '';
  }
}
)
//钩子函数
onMounted(() => {
  search();//查询员工列表
  queryAllDepts();//查询所有部门数据
})
//查询所以部门数据
const queryAllDepts = async () => {
  const result = await queryAllDeptApi();
  if (result.code) {
    depts.value = result.data;
  }
}
//查询员工列表
const search = async () => {
  const result = await queryPageApi(searchEmp.value.name, searchEmp.value.gender,
    searchEmp.value.begin, searchEmp.value.end,
    currentPage.value,pageSize.value
  );
  if (result.code) {
    empList.value = result.data.rows;
    total.value = result.data.total;
  }
  console.log(searchEmp.value);
}

//清空
const clear = () => {
  searchEmp.value = {
    name: '',
    gender: '',
    date: []
  }
  search();
}

//员工列表数据
const empList = ref([])
// watch侦听
//1.监听一个响应式数据
// const a = ref('');
// watch(a, (newVal, oldVal) => {
//   console.log(`变化后的值：${newVal}, 变化前的值${oldVal}`);
// })
// //2.侦听一个对象（侦听对象全部属性）
// const user = ref({name: '', age: 10});
// watch(user, (newVal, oldVal) => {
//   console.log(`变化后的值：${newVal}, 变化前的值${oldVal}`);
// }),deep(true)
//3.侦听某个对象的属性
const user = ref({ name: '', age: 10 });
watch(
  () => user.value.name,
  (newVal, oldVal) => {
    console.log(`变化后的值：${newVal}, 变化前的值${oldVal}`);
  }
)





/* 新增员工，修改员工对话框script */

// 新增员工
const addEmp = () => {
  dialogVisible.value = true;
  dialogTitle.value = '新增员工';
}
//新增/修改表单
const employee = ref({
  username: '',
  name: '',
  gender: '',
  phone: '',
  job: '',
  salary: '',
  deptId: '',
  entryDate: '',
  image: '',
  exprList: []
})

// 控制弹窗
const dialogVisible = ref(false)
const dialogTitle = ref('新增员工')

//文件上传
// 图片上传成功后触发
const handleAvatarSuccess = (response) => {
  employee.value.image = response.data;
  console.log(response);
}
// 文件上传之前触发
// !== 是 JavaScript 中的 严格不相等运算符（Strict Inequality Operator），
// 用于比较两个值是否 类型不同 或 值不同。
// 它与 !=（非严格不相等）的区别在于 不会进行类型转换。
const beforeAvatarUpload = (rawFile) => {
  if (rawFile.type !== 'image/jpeg' && rawFile.type !== 'image/png') {
    ElMessage.error('只支持上传图片');
    return false;
  } else if (rawFile.size / 1024 / 1024 > 10) {
    ElMessage.error('只能上传10M以内图片');
    return false;
  }
  return true;
}

// 添加工作经历
const addExprItem = () => {
  employee.value.exprList.push({
    company: '',
    job: '',
    begin: '',
    end: '',
    exprDate: []
  });
}


// 删除工作经历
const delExprItem = (index) => {
  employee.value.exprList.splice(index, 1);
}

</script>
<!-- TODO: 员工管理增加 -->
<!-- 搜索栏 -->
<template>
  <h1>员工管理</h1>
  {{ searchEmp }}
  <div class="container">
    <el-form :inline="true" :model="searchEmp" class="demo-form-inline">
      <el-form-item label="姓名">
        <el-input v-model="searchEmp.name" placeholder="请输入员工姓名" />
      </el-form-item>
      <el-form-item label="性别">
        <el-select v-model="searchEmp.gender" placeholder="请选择">
          <el-option label="男" value="1" />
          <el-option label="女" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="入职时间">
        <el-date-picker v-model="searchEmp.date" type="daterange" range-separator="到" start-placeholder="开始日期"
          end-placeholder="结束日期" value-format="YYYY-MM-DD" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="search">查询</el-button>
        <el-button type="primary" @click="clear">清空</el-button>
      </el-form-item>
    </el-form>
  </div>

  <!-- 功能按钮 -->
  <div class="container">
    <el-button class="button" type="primary" @click="addEmp"> + 新增员工</el-button>
    <el-button class="button" type="danger" @click="clear"> + 批量删除</el-button>
  </div>

  <!-- 数据展示表格 -->
  <div class="container">
    <el-table :data="empList" border style="width: 100%">
      <el-table-column type="selection" width="55" align="center"></el-table-column>
      <el-table-column prop="name" label="姓名" width="120" align="center"></el-table-column>
      <el-table-column label="性别" width="170" align="center">
        <template #default="scope">
          {{ scope.row.gender == 1 ? '男' : '女' }}
        </template>
      </el-table-column>
      <el-table-column label="头像" width="170" align="center">
        <template #default="scope">
          <img :src="scope.row.image" alt="Avatar" class="avatar" />
        </template>
      </el-table-column>
      <el-table-column prop="deptName" label="部门名称" width="170" align="center"></el-table-column>
      <el-table-column label="职位" width="120" align="center">
        <template #default="scope">
          <span v-if="scope.row.job == 1">班主任</span>
          <span v-else-if="scope.row.job == 2">讲师</span>
          <span v-else-if="scope.row.job == 3">学工主管</span>
          <span v-else-if="scope.row.job == 4">教研主管</span>
          <span v-else-if="scope.row.job == 5">咨询师</span>
          <span v-else>其他</span>
        </template>
      </el-table-column>
      <el-table-column prop="entryDate" label="入职日期" width="180" align="center"></el-table-column>
      <el-table-column prop="updateTime" label="最后操作时间" width="210" align="center"></el-table-column>
      <el-table-column label="操作" fixed="right" align="center">
        <template #default="scope">
          <el-button size="small" type="primary" @click="">编辑</el-button>
          <el-button size="small" type="danger" @click="">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>

  <!-- 分页条 -->
  <div class="container">
    {{ currentPage }} page has {{ pageSize }}
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
      v-model:current-page="currentPage" v-model:page-size="pageSize" :page-sizes="[5, 10, 20, 30, 50, 75, 100]"
      layout="total, sizes, prev, pager, next, jumper" :total="total">
    </el-pagination>
  </div>


  <!-- 新增/修改员工的对话框 -->
  <el-dialog v-model="dialogVisible" :title="dialogTitle">
    {{ employee }}
    <el-form :model="employee" label-width="80px">
      <!-- 基本信息 -->
      <!-- 第一行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="用户名">
            <el-input v-model="employee.username" placeholder="请输入员工用户名，2-20个字"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="姓名">
            <el-input v-model="employee.name" placeholder="请输入员工姓名，2-10个字"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第二行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="性别">
            <el-select v-model="employee.gender" placeholder="请选择性别" style="width: 100%;">
              <el-option label="男" value="1"></el-option>
              <el-option label="女" value="2"></el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="手机号">
            <el-input v-model="employee.phone" placeholder="请输入员工手机号"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第三行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="职位">
            <el-select v-model="employee.job" placeholder="请选择职位" style="width: 100%;">
              <el-option v-for="j in jobs" :key="j.value" :label=j.name :value=j.value></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="薪资">
            <el-input v-model="employee.salary" placeholder="请输入员工薪资"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第四行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="所属部门">
            <el-select v-model="employee.deptId" placeholder="请选择部门" style="width: 100%;">
              <el-option v-for="d in depts" :key=d.id :label=d.name :value=d.id></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="入职日期">
            <el-date-picker v-model="employee.entryDate" type="date" style="width: 100%;" placeholder="选择日期"
              format="YYYY-MM-DD" value-format="YYYY-MM-DD"></el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第五行 -->
      <!-- :on-success="handleAvatarSuccess" 文件上传成功后，将图片的URL地址，保存到员工对象中 -->
      <!-- :before-upload=“beforeAvatarUpload"文件上传之前，把图片上传到服务器，并把图片的URL地址，保存到员工对象中 -->
      <el-row :gutter="20">
        <el-col :span="24">
          <el-form-item label="头像">
            <el-upload class="avatar-uploader" action="/api/upload" :show-file-list="false"
              :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
              <img v-if="employee.image" :src="employee.image" class="avatar" />
              <el-icon v-else class="avatar-uploader-icon">
                <Plus />
              </el-icon>
            </el-upload>
          </el-form-item>
        </el-col>
      </el-row>


      <!-- 工作经历 -->
      <!-- 第六行 -->
      <el-row :gutter="10">
        <el-col :span="24">
          <el-form-item label="工作经历">
            <el-button type="success" size="small" @click="addExprItem">+ 添加工作经历</el-button>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第七行 ...  工作经历 -->
      <el-row :gutter="3" v-for="(expr,index) in employee.exprList">
        <el-col :span="10">
          <el-form-item size="small" label="时间" label-width="80px">
            <el-date-picker type="daterange" v-model="expr.exprDate" range-separator="至" start-placeholder="开始日期"
              end-placeholder="结束日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD"></el-date-picker>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item size="small" label="公司" label-width="60px">
            <el-input placeholder="请输入公司名称" v-model="expr.company"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item size="small" label="职位" label-width="60px">
            <el-input placeholder="请输入职位" v-model="expr.job"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="2">
          <el-form-item size="small" label-width="0px">
            <el-button type="danger" @click="delExprItem(index)">- 删除</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <!-- 底部按钮 -->
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="">保存</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style scoped>
.container {
  margin: 10px 0px;
}
/* 设置 .container 下的 el-table 行高 */
.container .el-table th,
.container .el-table td {
  /* !important 是 CSS 中的一个特殊规则，
  用于 强制提升某条样式声明的优先级，
  使其覆盖其他可能存在的冲突样式 */
  height: 60px !important;
  /* 固定高度 */
  padding: 0 !important;
  /* 移除默认内边距 */
  vertical-align: middle !important;
  /* 内容垂直居中 */
}
/* 图片自适应 */
.container .el-table .avatar {
  max-height: 50px;
  /* 限制头像高度 */
  width: auto;
  display: block;
  margin: 0 auto;
}




/* 新增员工，修改员工对话框css样式 */
.avatar {
  height: 40px;
}

.avatar-uploader .avatar {
  width: 78px;
  height: 78px;
  display: block;
}

.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 78px;
  height: 78px;
  text-align: center;
  border-radius: 10px;
  /* 添加灰色的虚线边框 */
  border: 1px dashed var(--el-border-color);
}
</style>