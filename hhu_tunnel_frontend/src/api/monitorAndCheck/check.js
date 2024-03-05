
import request from '@/utils/request'
export function jianceDict() {
    return request({
      url: '/jiance/dict',
      method: 'get',
    })
  }
  export function jianceTable(data,current,limit) {
    return request({
      url: `/jiance/getTable/${current}/${limit}`,
      method: 'post',
      data
    })
  }
  export function addJianceData(data,current,limit) {
    return request({
      url: `/jiance/add/${current}/${limit}`,
      method: 'post',
      data
    })
  }
  export function jiancePage(data,current,limit) {
    return request({
      url: `/jiance/page/${current}/${limit}`,
      method: 'post',
      data
    })
  }
  export function jianceEditData(data) {
    return request({
        url: '/jiance/edit',
        method: 'post',
        data
      })
  }
  export function jianceDeleteData(data) {
    return request({
        url: '/jiance/delete',
        method: 'post',
        data
      })
  }

  export function jianceGetYuzhi(data) {
    return request({
        url: '/jiance/getYuzhi',
        method: 'post',
        data
      })
  }