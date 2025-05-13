import request from "../utils/request";
// 查询全部部门数据
export const queryAllApi = () => {
    return request.get('/depts');
}

//新增
export const addApi = (dept) => {
    return request.post('/depts', dept);
}

//修改


//删除
