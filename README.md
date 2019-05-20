

### 项目结构

![image](https://github.com/vihuela/XiaoeGeneral/blob/master/appDes.png ) 



组件化使用：

如果创建一个新的组件

1. as->file->new->new Project，创建phone module
2. 在对应的app.build文件中引入通用依赖。参考app2的build文件。注意resourcePrefix。这个配置仅会提醒资源的前缀。避免打包时资源冲突
3. 在模块对应的main目录创建debug文件。（参考app2模块）
   1. 拷入application时的AndroidManifest文件。有intent-filter入口。用于切换为application时
   2. 修改main所在目录的AndroidManifest文件，删除intent-filter入口。用于切换为library时
   3. 底层会根据其它模块依赖来自动选择对应的AndroidManifest文件
4. over！