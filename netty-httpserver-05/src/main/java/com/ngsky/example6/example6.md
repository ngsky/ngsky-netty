# Protobuf 简单实用
实用 Protobuf 构建简单 DataInfo，将DataInfo 转为字节数组，然后再从字节数组转化为对象 

# gradle 仓库
gradle.maven.org

# protoc 编译工具
protoc -I=$SRC_DIR --java_out=$DST_DIR $SRC_DIR/addressbook.proto
-I : 指定.proto 文件源路径
--java_out : 指定输出路径

H:\devtool\protobuf\protoc.exe -I=F:\2020\coding\Java\ngsky-netty\netty-httpserver-05\src\main\java\com\ngsky\example6\protobuf --java_out=F:\2020\coding\Java\ngsky-netty\netty-httpserver-05\src\main\java F:\2020\coding\Java\ngsky-netty\netty-httpserver-05\src\main\java\com\ngsky\example6\protobuf\addressbook.proto