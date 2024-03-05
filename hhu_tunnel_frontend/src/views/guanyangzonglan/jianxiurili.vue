<template>
  <div>
    <el-calendar v-model="day">
      <template slot="dateCell" slot-scope="{ data}">
        <p :class="data.isSelected ? 'is-selected' : ''">
          {{ data.day.split('-').slice(1).join('-') }} {{ data.isSelected ? '✔️' : '' }}
        </p>
        <div style="width: 100%" v-for="(item, index) in jianxiuData" :key="index">
          <div v-if="item.rq.indexOf(data.day) != -1">
            <div style="width: 3rem;height: 3rem;background-color: grey;border-radius: 50%;text-align: center;line-height: 3rem;">
              <span style="color: white;">{{ item.data }}</span>
            </div>
          </div>
        </div>
      </template>
    </el-calendar>
  </div>
</template>

<script>
export default {
  data() {
    return {
      day: new Date(),
      jianxiuData: []
    }
  },
  created() {
    this.jianxiuData = [{
      rq: '2023-3-5',
      data: 446
    },
    {
      rq: '2023-3-8',
      data: 280
    }]
    for (let index = 0; index < this.jianxiuData.length; index++) {
      this.jianxiuData[index].rq = this.$moment(this.jianxiuData[index].rq).format('YYYY-MM-DD')
    }
    console.log(this.jianxiuData)
  },
}
</script>

<style scoped>
::v-deep .el-calendar-day {
  height: auto;
}
</style>