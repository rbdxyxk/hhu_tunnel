<template>
  <div class="dashboard-container">
    <el-calendar v-model="day">
      <template slot="dateCell" slot-scope="{ data}">
        <p :class="data.isSelected ? 'is-selected' : ''">
          {{ data.day.split('-').slice(1).join('-') }} {{ data.isSelected ? '✔️' : '' }}
        </p>
        <div style="width: 100%" v-for="(item, index) in guanyangData" :key="index">
          <div v-if="item.rq.indexOf(data.day) != -1">
            <ul>
              <li style="color: darksalmon;">
                未执行:{{ item.notBegin }}
              </li>
              <li style="color: blue;">
                进行中:{{ item.begining }}
              </li>
              <li style="color: orangered;">
                已完成:{{ item.finished }}
              </li>
            </ul>
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
      guanyangData: [],
    }
  },
  created() {
    this.guanyangData = [{
      rq: '2023-3-19 00:00:00',
      notBegin: 90,
      begining: 12,
      finished: 66
    },
    {
      rq: '2023-3-18 00:00:00',
      notBegin: 13,
      begining: 8,
      finished: 6
    },
    {
      rq: '2023-2-18 ',
      notBegin: 13,
      begining: 8,
      finished: 6
    }]
    for (let index = 0; index < this.guanyangData.length; index++) {
      this.guanyangData[index].rq = this.$moment(this.guanyangData[index].rq).format('YYYY-MM-DD')
    }
    console.log(this.guanyangData)
  },
}
</script>

<style scoped>
  .is-selected {
    color: #1989fa;
  }

  p {
    margin: 10px auto;
  }

  ul {
    list-style: none;
    padding: 0;
  }

  ::v-deep 
    .el-calendar-day {
      height: auto;
    }


</style>