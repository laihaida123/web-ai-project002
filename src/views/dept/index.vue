<script setup>
// import { el } from 'element-plus/es/locale';
import { ref, onMounted } from 'vue'
import { Axios } from 'axios';
import { queryAllApi, addApi } from '@/api/dept'
import { ElMessage } from 'element-plus'

//钩子函数
onMounted(() => {
  search();
})//页面加载完毕后执行
const deptList = ref([])
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
// < !--dialog对话框组件 -->
const dialogFormVisible = ref(false);
const formTitle = ref('');
const dept = ref({ name: '' });
// 新增部门
const addDept = () => {
  dialogFormVisible.value = true;
  formTitle.value = '新增部门';
  dept.value = { name: '' };
  //重置表单校验规则--提示信息
  if (!deptFormRef.value) {
    deptFormRef.value.resetFields();
  }
}
// 保存部门
const save = async () => {
  //表单校验
  if (!deptFormRef.value) {
    return;
  } else {
    deptFormRef.value.validate(async (valid) => {//valid表示是否校验通过，true表示通过
      if (valid) {//通过
        const result = await addApi(dept.value);
        if (result.code == 1) {//成功
          //提示操作成功
          ElMessage.success('操作成功');
          //关闭对话框
          dialogFormVisible.value = false;
          //查询
          search();
        } else {//失败
          ElMessage.error(result.msg);
        }
      } else {
        ElMessage.error('表单校验不通过');
      }
    })
  }

}
// 表单校验
const rules = ref({
  name: [//require表示必填blur表示离焦，鼠标一放上去就校验
    { required: true, message: '部门名称是必填项奥！！！', trigger: 'blur' },
    { min: 2, max: 10, message: '部门名称长度要在2到10之间奥！！！嘻嘻', trigger: 'blur' },
  ]
})
const deptFormRef = ref();
</script>

<template>
  <h1>部门管理</h1>
  <div class="container">
    <el-button type="primary" @click="addDept">+ 新增部门</el-button>
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
  <!-- dialog对话框组件 -->
  <!-- v-model true显示，false隐藏 -->
  <!-- rules定义检阅规则，并将检验规则和表单进行绑定 -->
  <el-dialog v-model="dialogFormVisible" :title="formTitle" width="500">
    <el-form :model="dept" :rules="rules" ref="deptFormRef">
      <el-form-item label="部门名称" label-width="80px" prop="name">
        <el-input v-model="dept.name" />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="save">
          确定
        </el-button>
      </div>
    </template>
  </el-dialog>
  <!-- //dialog对话框 -->

</template>

<style scoped>
.container {
  margin: 10px 0px;
}
</style>
