

### 项目结构

![image](https://github.com/vihuela/XiaoeGeneral/blob/master/appDes.png ) 

已将所有基础库更新至AndroidX。官方库需要更多请查看：<https://developer.android.com/jetpack/androidx/migrate>

项目基础使用：

1. 全局工具类：已包含大多数工具类。使用之前看wiki。https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/README-CN.md
2. 列表刷新、适配器：SmartRefreshLayout+multitype。已包含一个快速构建管理类。参考ImageFragment.kt文件
3. 事件总线均为所有UI类，重写isRegisterEventBus，然后重写onEvent接收，发送使用全局扩展sendEvent
4. rx..

组件化使用：

如果创建一个新的组件

1. as->file->new->new Project，创建phone module
2. 在对应的app.build文件中引入通用依赖。参考app2的build文件。注意resourcePrefix。这个配置仅会提醒资源的前缀。避免打包时资源冲突
3. 在模块对应的main目录创建debug文件。（参考app2模块）
   1. 拷入application时的AndroidManifest文件。有intent-filter入口。用于切换为application时
   2. 修改main所在目录的AndroidManifest文件，删除intent-filter入口。用于切换为library时
   3. 底层会根据其它模块依赖来自动选择对应的AndroidManifest文件
4. 模块的各种环境，比如网络数据库、工具类。参考app2的app.kt文件，直接拷贝即可
5. 切换as右上角可以直接运行。over.