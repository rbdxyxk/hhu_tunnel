import request from '@/utils/request'
//获取所有隧道
export function  getSuidao(){
    return request({
        url: '/shouye/getAllSuidao',
        method: 'get',
      })
}

//根据隧道id获得所有日期的健康值
export function  getSuidaoHealthById(id){
    return request({
        url: `/shouye/getSuidaoHealthById/${id}`,
        method: 'get',
      })
}

