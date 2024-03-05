
import request from '@/utils/request'
export function monitorDict() {
    return request({
      url: '/monitor/dict',
      method: 'get',
    })
  }
  export function monitorTable(data,current,limit) {
    return request({
      url: `/monitor/getTable/${current}/${limit}`,
      method: 'post',
      data
    })
  }
  export function addmonitorData(data,current,limit) {
    return request({
      url: `/monitor/add/${current}/${limit}`,
      method: 'post',
      data
    })
  }
  export function monitorPage(data,current,limit) {
    return request({
      url: `/monitor/page/${current}/${limit}`,
      method: 'post',
      data
    })
  }
  export function monitorEditData(data) {
    return request({
        url: '/monitor/edit',
        method: 'post',
        data
      })
  }
  export function monitorDeleteData(data) {
    return request({
        url: '/monitor/delete',
        method: 'post',
        data
      })
  }

  export function monitorGetYuzhi(data) {
    return request({
        url: '/monitor/getYuzhi',
        method: 'post',
        data
      })
  }