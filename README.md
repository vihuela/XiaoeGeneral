已将所有基础库更新至AndroidX。官方库需要更多请查看：<https://developer.android.com/jetpack/androidx/migrate>

组件化使用：

创建一个新的组件（**用于单独运行和被其它组件依赖**）


1. as->file->new->new Project，创建phone module
2. 在对应的app.build文件中引入通用依赖：app_envir.gradl。注意**resourcePrefix**。这个配置仅会提醒资源的前缀。避免打包时资源冲突，增加resourcePrefix之后需要对应需改res目录下的文件或资源。参考appbox-ui
3. 在模块对应的main目录创建debug文件。（参考appbox-ui模块）
   1. 拷入application时的AndroidManifest文件。有intent-filter入口。用于切换为application时
   2. 修改main所在目录的AndroidManifest文件，删除intent-filter入口。用于切换为library时
   3. 底层会根据其它模块依赖来自动选择对应的AndroidManifest文件
5. 切换as右上角可以直接运行。over.



创建一个新的组件（**仅用于被其它组件依赖**）.（参考xe-tools）

1. as->file->new->new Project，创建library module
2. 在对应的app.build文件中引入通用依赖：app_lib_envir.gradl。注意resourcePrefix。这个配置仅会提醒资源的前缀。避免打包时资源冲突



- **使用注意：**
- as创建组件时，如果moduleName不能设置有“-”，可以先去掉。然后创建好之后在重命名，选择rename moduleName
- 依赖组件使用addComponent关键字。
- 主app模块打包排除部分组件，local.properties中设置：moduleName = true，如appbox-ui = true。那么运行app时将不包括