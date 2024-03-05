<template>
    <div>
   <bm-overlay
    ref="customOverlay"
    :class="{sample: true}"
    pane="labelPane"
    @draw="draw">
  </bm-overlay>
  <div v-text="text"></div>
</div>
</template>
<script>
export default {
props: ['position','text'],
watch: {
    position: {
      handler () {
        this.$refs.customOverlay.reload()  // 当位置发生变化时，重新渲染，内部会调用draw
      },
      deep: true
    }
  },
  methods: {
    // 这是百度地图的重绘函数,用于维持覆盖物的位置（这里的值貌似会影响拖拉时的偏移度）
    draw ({ el, BMap, map }) {
      const { lng, lat } = this.position
      const pixel = map.pointToOverlayPixel(new BMap.Point(lng, lat))
      el.style.left = pixel.x - 5 + 'px'
      el.style.top = pixel.y - 5+ 'px'
    }
  }

}
</script>

<style>

.sample {
  width: 10px;
  height: 10px;
  line-height: 10px;
  border-radius: 32px;
  background: red;
  overflow: hidden;
  box-shadow: 0 0 5px #000;
  color: #fff;
  text-align: center;
  position: absolute;
}
.sample.active {
  background: rgba(0,0,0,0.75);
  color: #fff;
}

</style>