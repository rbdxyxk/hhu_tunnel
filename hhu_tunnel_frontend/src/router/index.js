import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)

/* Layout */
import Layout from '@/layout'
import { getSuidao } from '@/api/guanyangzonglan/shouye.js'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
var suidaoList = []

// 使用geiSuidao方法来获得隧道
var responseData = [{
  id: 1,
  name: '扬子江隧道'
},
{
  id: 2,
  name: '定淮门隧道'
}]
for (let index = 0; index < responseData.length; index++) {
  var temp = {
    path: '',
    meta: { title: '' },
    redirect: ''
  }
  temp.path = 'expand' + responseData[index].id
  temp.meta.title = responseData[index].name
  temp.redirect = '/explicit/suidao/' + responseData[index].id + '/' + responseData[index].name
  suidaoList.push(temp)
}
console.log(suidaoList)
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    // redirect: '/dashboard',
    // name: 'guan',
    meta: { title: '管养总览', icon: 'el-icon-s-help' },
    children: [{
      path: 'shouye',
      name: 'shouye',
      component: () => import('@/views/guanyangzonglan/shoye'),
      meta: { title: '首页', icon: 'dashboard' }
    },
    {
      path: 'suidaoqunluo',
      // name: 'suidaoqunluo',
      meta: { title: '隧道群落', icon: 'dashboard' },
      children: suidaoList
    },
    {
      path: 'guanyangrili',
      name: 'guanyangrili',
      component: () => import('@/views/guanyangzonglan/guanyangrili'),
      meta: { title: '管养日历', icon: 'dashboard' }
    },
    {
      path: 'jianxiurili',
      name: 'jianxiurili',
      component: () => import('@/views/guanyangzonglan/jianxiurili'),
      meta: { title: '检修日历', icon: 'dashboard' }
    }]
  },
  {
    path: '/zichanguanli',
    component: Layout,
    // redirect: '/dashboard',
    // name: 'guan',
    meta: { title: '资产管理', icon: 'el-icon-s-help' },
    children: [{
      path: 'shouye',
      name: 'shouye',
      component: () => import('@/views/guanyangzonglan/shoye'),
      meta: { title: '首页', icon: 'dashboard' }
    },
    {
      path: 'zichanqingdan',
      // name: 'suidaoqunluo',
      component: () => import('@/views/zichanguanli/zichanqingdan'),
      meta: { title: '资产清单', icon: 'dashboard' }
    }]
  },
  {
    path: '/guanyangxiangmu',
    component: Layout,
    // redirect: '/dashboard',
    // name: 'guan',
    meta: { title: '管养项目', icon: 'el-icon-s-help' },
    children: [{
      path: 'xiangmuzonglan',
      // name: 'shouye',
      component: () => import('@/views/guanyangxiangmu/xiangmuzonglan'),
      meta: { title: '项目总览', icon: 'dashboard' }
    },
    {
      path: 'shishixiangmu',
      // name: 'suidaoqunluo',
      component: () => import('@/views/guanyangxiangmu/shishixiangmu'),
      meta: { title: '实施项目', icon: 'dashboard' }
    },
    {
      path: 'xiangmuleixing',
      // name: 'suidaoqunluo',
      component: () => import('@/views/guanyangxiangmu/xiangmuleixing'),
      meta: { title: '项目类型', icon: 'dashboard' }
    }]
  },
  {
    path: '/liuchengguanli',
    component: Layout,
    // redirect: '/dashboard',
    // name: 'guan',
    meta: { title: '流程管理', icon: 'el-icon-s-help' },
    children: [{
      path: 'faqirenwu',
      // name: 'shouye',
      component: () => import('@/views/liuchengguanli/faqirenwu'),
      meta: { title: '发起任务', icon: 'dashboard' }
    },
    {
      path: 'dailingrenwu',
      // name: 'suidaoqunluo',
      component: () => import('@/views/liuchengguanli/dailingrenwu'),
      meta: { title: '待领任务', icon: 'dashboard' }
    },
    {
      path: 'daibanrenwu',
      // name: 'suidaoqunluo',
      component: () => import('@/views/liuchengguanli/daibanrenwu'),
      meta: { title: '待办任务', icon: 'dashboard' }
    },
    {
      path: 'yibanrenwu',
      // name: 'suidaoqunluo',
      component: () => import('@/views/liuchengguanli/yibanrenwu'),
      meta: { title: '已办任务', icon: 'dashboard' }
    }]
  },
  {
    path: '/explicit',
    name: 'explicitSuiDao',
    hidden: true,
    component: Layout,
    children: [{
      path: 'suidao/:id/:name',
      component: () => import('@/views/guanyangzonglan/explicitSuiDao'),
      hidden: true
    }]
  },

  {
    path: '/xuncha',
    component: Layout,
    name: 'xuncha',
    meta: { title: '巡视检查', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'richangxuncha',
        name: 'richangxuncha',
        component: () => import('@/views/xunshijiancha/richangxuncha'),
        meta: { title: '日常巡查', icon: 'table' }
      },
      {
        path: 'binghaitongji',
        name: 'binghaitongji',
        component: () => import('@/views/xunshijiancha/binghaitongji'),
        meta: { title: '病害统计', icon: 'tree' }
      },
      {
        path: 'binghaichuzhi',
        name: 'binghaichuzhi',
        component: () => import('@/views/xunshijiancha/binghaichuzhi'),
        meta: { title: '病害处置', icon: 'tree' }
      },
      {
        path: 'xunnchaluxian',
        name: 'xunnchaluxian',
        component: () => import('@/views/xunshijiancha/xunchaluxian'),
        meta: { title: '巡查路线', icon: 'tree' }
      }
    ]
  },
  {
    path: '/jiancejiance',
    component: Layout,
    name: 'jiancejiance',
    meta: { title: '监测检测', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'monitorxiangmu',
        name: 'monitorxiangmu',
        component: () => import('@/views/jiancejiance/monitorxiangmu'),
        meta: { title: '监测项目', icon: 'table' }
      },
      {
        path: 'checkxiangmu',
        name: 'checkxiangmu',
        component: () => import('@/views/jiancejiance/checkxiangmu'),
        meta: { title: '检测项目', icon: 'table' }
      },
      {
        path: 'yujingzhi',
        name: 'yujingzhi',
        meta: { title: '预警值设置', icon: 'table' },
        children: [
          {
            path: 'monitoryujing',
            redirect: '/yujing/monitoryujing',
            meta: { title: '监测项目', icon: 'table' }
          }, {
            path: 'checkyujing',
            name: 'checkyujing',
            redirect: '/yujing/checkyujing',
            meta: { title: '检测项目', icon: 'table' }
          }
        ]
      }
    ]
  },
  {
    path: '/yujing',
    name: 'yujing',
    hidden: true,
    component: Layout,
    children: [{
      path: 'monitoryujing',
      component: () => import('@/views/jiancejiance/monitoryujing'),
      hidden: true
    },
    {
      path: 'checkyujing',
      component: () => import('@/views/jiancejiance/checkyujing'),
      hidden: true
    }
    ]
  },

  {
    path: '/tujianguanyang',
    component: Layout,
    meta: { title: '土建管养', icon: 'form' },
    children: [
      {
        path: 'richangyanghu',
        name: 'richangyanghu',
        component: () => import('@/views/tujianguanyang/richangyanghu/index'),
        meta: { title: '日常养护', icon: 'form' },
        alwaysShow: true,
        children: [{
          path: 'gongchengliangqingdan',
          name: 'gongchengliangqingdan',
          component: () => import('@/views/tujianguanyang/richangyanghu/gongchengliangqingdan/index'),
          meta: { title: '工程量清单', icon: 'form' }
        },
        {
          path: 'richangyanghu',
          name: 'richangyanghu',
          component: () => import('@/views/tujianguanyang/richangyanghu/manage/index'),
          meta: { title: '日常养护管理', icon: 'form' }
        }]
      },
      {
        path: 'teshujiancha',
        name: 'teshujiancha',
        component: () => import('@/views/tujianguanyang/teshujiancha/index'),
        meta: { title: '特殊检查', icon: 'form' },
        children: [
          {
            path: 'zhuanxiang',
            name: 'zhuanxiang',
            component: () => import('@/views/tujianguanyang/teshujiancha/zhuanxiang'),
            meta: { title: '专项检查', icon: 'form' },
          },
          {
            path: 'shenlou',
            name: 'shenlou',
            component: () => import('@/views/tujianguanyang/teshujiancha/shenlou'),
            meta: { title: '渗漏专项', icon: 'form' },
          },
        ]
      }
    ]
  },

  {
    path: '/baogaobaobiao',
    component: Layout,
    name: 'baogaobaobiao',
    meta: {
      title: '报告报表',
      icon: 'nested'
    },
    children: [
      {
        path: 'monitorbaogao',
        component: () => import('@/views/baogaobaobiao/monitorbaogao'), // Parent router-view
        name: 'monitorbaogao',
        meta: { title: '监测报告', icon: 'nested' }
      },
      {
        path: 'checkbaogao',
        component: () => import('@/views/baogaobaobiao/checkbaogao'), // Parent router-view
        name: 'checkbaogao',
        meta: { title: '检测报告', icon: 'nested' }
      },
      {
        path: 'yanghuguanli',
        component: () => import('@/views/baogaobaobiao/yanghuguanli/index'), // Parent router-view
        meta: { title: '养护管理', icon: 'nested' },
        alwaysShow: true,
        children: [
          {
            path: 'binghaitongji',
            component: () => import('@/views/baogaobaobiao/yanghuguanli/binghaitongji'), // Parent router-view
            meta: { title: '病害统计报告', icon: 'nested' }
          },
          {
            path: 'yanghuwork',
            component: () => import('@/views/baogaobaobiao/yanghuguanli/yanghuwork'), // Parent router-view
            meta: { title: '养护工作报告', icon: 'nested' }
          },
          {
            path: 'safepredict',
            component: () => import('@/views/baogaobaobiao/yanghuguanli/safepredict'), // Parent router-view
            meta: { title: '安全预测报告', icon: 'nested' }
          }
        ]
      }

    ]
  },
  {
    path: '/binghaicheck',
    component: Layout,
    name: 'binghaicheck',
    meta: {
      title: '病害检测',
      icon: 'user'
    },
    children: [
      {
        path: 'liewen',
        component: () => import('@/views/binghaicheck/liewen'),
        name: 'liewen',
        meta: {
          title: '裂纹病害检测',
          icon: 'user'
        }
      },
      {
        path: 'shenloushui',
        component: () => import('@/views/binghaicheck/shenloushui'),
        name: 'shenloushui',
        meta: {
          title: '渗漏水病害检测',
          icon: 'user'
        }
      }
    ]
  },
  {
    path: '/dynamicSafe',
    component: Layout,
    name: 'dynamicSafe',
    meta: {
      title: '动态安全预测',
      icon: 'eye-open'
    },
    children: [{
      path: 'monitor',
      component: () => import('@/views/dynamicSafe/monitor'),
      name: 'monitor',
      meta: {
        title: '监测项目',
        icon: 'eye-open'
      }
    },
    {
      path: 'check',
      component: () => import('@/views/dynamicSafe/check'),
      name: 'check',
      meta: {
        title: '检测项目',
        icon: 'eye-open'
      }
    }]
  },
  {
    path: '/yanghufangan',
    component: Layout,
    name: 'yanghufangan',
    meta: {
      title: '养护方案',
      icon: 'eye'
    },
    children: [
      {
      path: 'binghai',
      component: () => import('@/views/yanghufangan/binghai'),
      name: 'binghai',
      meta: {
        title: '病害治理库',
        icon: 'eye'
      }
    },
    {
      path: 'classtic',
      component: () => import('@/views/yanghufangan/classtic'),
      name: 'classtic',
      meta: {
        title: '典型案例',
        icon: 'eye'
      }
    },
    {
      path: 'fangan',
      component: () => import('@/views/yanghufangan/fangan/index'),
      meta: {
        title: '养护方案建议',
        icon: 'eye',
      },
      alwaysShow: true,
      children: [
        {
          path: 'yanghufangan',
          component: () => import('@/views/yanghufangan/fangan/xiangmu'),
          name: 'yanghufangan',
          meta: {
            title: '养护方案',
            icon: 'eye'
          }
        },
        {
          path: 'yanghujianyi',
          name: '养护建议',
          component: () => import('@/views/yanghufangan/fangan/xiangmuDetail'),
          meta: {
            title: '养护建议',
            icon: 'eye'
          }
        },
      ]
    }
    ]
  },
  {
    path: '/yingji',
    meta: {
      title: '应急管理办法',
      icon: 'password'
    },
    redirect: '/yingjiguanli/index'
  },
  {
    path: '/yingjiguanli',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'index',
        component: () => import('@/views/yingjiguanli/index')
      }
    ]
  },

  {
    path: '/view/yanghu/:id',
    component: () => import('@/views/tujianguanyang/richangyanghu/view')
  },


  {
    path: '/test',
    component: () => import('@/views/test')
  },


  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
