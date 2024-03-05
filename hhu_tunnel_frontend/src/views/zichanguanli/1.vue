<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Title</title>
</head>
<div id="app">
  <el-upload
    action="/upload"
    :on-preview="handlePreview"
    :limit="3"
    accept=".pdf">
    <el-button size="small" type="primary">点击上传</el-button>
    <div slot="tip" class="el-upload__tip">只能上传pdf文件，且不超过10MB</div>
  </el-upload>
</div>
<body>
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script src="https://unpkg.com/element-ui/lib/index.js"></script>
<link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
<script>
  new Vue({
    el:'#app',
    methods:{
      handlePreview(file){
        window.open(file.response.url);
      }
    }
  })
</script>
</body>
</html>
