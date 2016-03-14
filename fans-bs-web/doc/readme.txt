项目名称：fans后台服务
项目基础架构：SpringMVC（4.2.0）+ Servlet(3.0)
持久层框架：Mybatis(3.2.0)
JDK版本：1.7以上
数据库：Mysql(5.5以上) + Mongo
连接池：DBCP
应用服务器：Tomcat7.0+
Web服务器：nginx
日志记录：logback

项目模块：
fans-bs-common(通用模块和工具类)
fans-bs-dao（持久层）
fans-bs-domain（模型）
fans-bs-rpc-service（远程服务，后期扩展用）
fans-bs-service（服务层）
fans-bs-delegate（事务代理层，需要加入事务的多个service方法调用）
fans-bs-worker
fans-bs-web

