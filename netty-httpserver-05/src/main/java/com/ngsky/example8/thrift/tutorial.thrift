include "shared.thrift"

namespace java com.ngsky.example8.thrift

const i32 INT32CONSTANT = 9853
const map<string, string> MAPCONSTANT = {'hello':'world', 'goodngiht':'moon'}

enum Operation {
    ADD = 1,
    SUBTRACT = 2,
    MULTIPLY = 3,
    DIVIDE = 4
}

/**
* 传输数据结构体
**/
struct Work {
    1:i32 num1 = 0,
    2:i32 num2,
    3:Operation op,
    4:optional string comment,
}

/**
* 定义接口抛出异常类型
**/
exception InvalidOperation {
    1:i32 whatOp,
    2:string why
}

/**
* 定义接口集合
**/
service Calculator extends shared.SharedService {
    void ping(),
    i32 add(1:i32 num1, 2:i32 num2),
    i32 calculate(1:i32 logid, 2:Work w) throws (1:InvalidOperation ouch),
    // 客户端只发送请求，不监听响应
    oneway void zip()
}
