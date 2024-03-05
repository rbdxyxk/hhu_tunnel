//时间线组件TimeLine
<template>
  <div class="time-line">
    <div class="section">
      <ul>
        <li v-for="(item,index) in list" :key="index">
          <div class="line"></div>
          <div class="item-wrapper">
            <div class="circle"></div>
            <div class="across-line"></div>
            <div class="item-content">
              <h6 class="font_18 gray_color">{{item.time}}</h6>
              <h6 class="font_18 m_b1" style="color: #ffa64c">{{item.title}}</h6>
              <p class="font_16 justify">{{item.desc}}</p>
            </div>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: "TimeLine",
  props: {
    list: Array, //页面展示数据
  },
  data() {
    return {

    }
  },
  mounted() {
    // console.log(this.list)
  }
}
</script>

<style scoped lang="stylus">
$purple = #850ec2
$gray = #999999
$yellow = #F39E48
.section {
  max-width 600px
  margin 40px auto
  padding-left 50%
  li {
    box-sizing border-box
    position relative
    padding-bottom 20px
    .line {
      position absolute
      left 0
      height 100%
      border-left 1px solid $yellow
    }
    .item-wrapper {
      position relative
      .circle {
        width 10px
        height 10px
        border 2px solid $yellow
        background pink
        border-radius 50%
        position absolute
        top 0
        bottom 0
        margin-top auto
        margin-bottom auto
      }
      .across-line {
        width 18.5%
        height 1px
        background $yellow
        position absolute
        top 0
        bottom 0
        margin-top auto
        margin-bottom auto
      }
      .item-content {
        padding 10px 20px
        box-sizing border-box
        border 1px solid $yellow
        border-radius 6px
        p {
          line-height 1.5
        }
      }

    }
    &:nth-child(odd) {
      .item-wrapper {
        left -100%
        padding-right 25%
        .circle {
          right -7px
        }
        .across-line {
          right 7px
        }
      }
    }
    &:nth-child(even) {
      .item-wrapper {
        padding-left 25%
        .circle {
          left -7px
        }
        .across-line {
          left 7px
        }
      }
    }
  }
}
</style>
