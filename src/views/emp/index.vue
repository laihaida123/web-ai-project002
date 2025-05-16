<script setup>
import { watch } from 'vue';
import { ref } from 'vue';
import { queryPageApi } from '@/api/emp'
import { onMounted } from 'vue';
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
  search();
})
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
    <el-button class="button" type="primary" @click="search">新增员工</el-button>
    <el-button class="button" type="danger" @click="clear">批量删除</el-button>
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
</style>