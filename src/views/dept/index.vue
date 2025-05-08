<script setup>
// import { el } from 'element-plus/es/locale';
import { ref, onMounted } from 'vue'
import { Axios } from 'axios';
import {queryAllApi} from '@/api/dept'

//钩子函数
onMounted(()=> {
  search();
})//页面加载完毕后执行
//查询
const search = async () => {
  // const result = await Axios.get('http://localhost:8080/department/list');
  // if (result.data.code == 1) { //js隐式类型转换 0 -> false 其余为true；
  //   deptList.value = result.data.data;
  // }
  const result = await queryAllApi();
  if (result.code) { //js隐式类型转换 0 -> false 其余为true；
    deptList.value = result.data;
  }
}
const deptList = ref([
  {
    "id": 1,
    "name": "学工部",
    "createTime": "2021-09-01 10:00:00",
    "updateTime": "2021-09-01 10:00:00"
  }
])
</script>

<template>
  <h1>部门管理</h1>
  <div class="container">
    <el-button type="primary">+ 新增部门</el-button>
  </div>
  <div class="container">
    <el-table :data="deptList" border style="width: 100%">
      <el-table-column type="index" label="序号" width="100" align="center" />
      <el-table-column prop="name" label="部门名称" width="260" align="center" />
      <el-table-column prop="updateTime" label="最后操作时间" width="300" align="center" />
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button type="primary" size="small"><el-icon>
              <EditPen />
            </el-icon>编辑</el-button>
          <el-button type="primary" size="small"><el-icon>
              <Delete />
            </el-icon>删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<style scoped>
.container {
  margin: 10px 0px;
}
</style>
