import request from '@/utils/request'

export function jianceDict() {
  return request({
    url: '/jiance/dict',
    method: 'get',
  })
}

export function jianceTable(data) {
    return request({
      url: '/jiance/getTable',
      method: 'post',
      data
    })
  }
  
