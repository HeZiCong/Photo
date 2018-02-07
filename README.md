# photo
婚纱影楼摄影网站（The wedding photography website）

*maven project*

[分支1.0.0：带有jar包的javaweb project](https://github.com/HeZiCong/Photo/tree/1.0.0)

**gzgchzc博客最新更新地址：[http://blog.gzvtc.cc/](http://blog.gzvtc.cc/)**

这个项目是参照金夫人旧版的网站开发，实际业务根据客户提供的需求开发，使用java前后端分离而开发婚纱影楼摄影项目。

项目是接的一个项目，由于客户最后尾款未清，导致未能验收。<br>
也不想浪费了自己所花的时间，距离完成项目已经有一年多了。<br>
所以选择开源出来，请大家多多指导。

# 部分说明

接下来会重构此项目，有兴趣的可以一起参加进来。

# 运行环境
- JDK 8
- MySQL
- tomcat7+

# 主要技术

- Spring && SpringMvc
- Mybatis
- bootstrap
- AceAdmin




# 安装步骤

0 - clone或者下载本项目

1 - 创建数据库photo并导入跟目录sql/photo.sql文件到数据库

2 - 更新 dataSource.properties中数据库连接配置

3 - 导入ide部署到tomcat上运行

4 - http://localhost:8080/Photo 访问主页



## 后台模块

登陆路径 localhost:8080/Photo/manage/

默认账号 admin<br>
默认密码 admin

帮助文档在后台home页面


