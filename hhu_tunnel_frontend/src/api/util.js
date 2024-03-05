export function transformArrayDate(vue, data, ...column) {
    if (column.length === 0)
        return
    for (var i = 0; i < data.length; i++) {
        for (var j = 0; j < column.length; j++) {
            data[i][column[j]] = vue.$moment(data[i][column[j]]).format('YYYY-MM-DD')
        }
    }
    return data
}

export function transformDate(vue, data, ...column) {
    if (column.length === 0)
        return
    for (var j = 0; j < column.length; j++) {
        data[column[j]] = vue.$moment(data[column[j]]).format('YYYY-MM-DD')
    }
    return data
}

