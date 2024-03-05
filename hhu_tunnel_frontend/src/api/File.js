import request from '@/utils/request'
export function viewFileOnline(id) {
    return request({
      url: `/file/get/${id}`,
      method: 'get',
      responseType: 'blob',
    })
  }
  
  export function download(id) {
    return request({
      url: `/file/download/${id}`,
      method: 'get',
      responseType: 'blob',
    })
  }
  export function removeFile(id) {
    return request({
      url: `/file/remove/${id}`,
      method: 'get',
    })
  }