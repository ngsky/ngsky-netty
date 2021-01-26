package com.ngsky.example6;

import com.google.protobuf.InvalidProtocolBufferException;
import com.ngsky.example6.protobuf.AddressBookProtos;

public class ProtobufTest {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        AddressBookProtos.AddressBook addressBook = AddressBookProtos.AddressBook.newBuilder()
                .addPeople( AddressBookProtos.Person.newBuilder().setId(0)
                .setName("张三")
                .setEmail("zhangsan@163.com"))
                .build();

        // 将对象转化为字节数组
        byte[] addressBookBytes = addressBook.toByteArray();

        // 将字节数组转化为对象
        AddressBookProtos.AddressBook ab = AddressBookProtos.AddressBook.parseFrom(addressBookBytes);
        System.out.println(ab);
        System.out.println(ab.toString());
        System.out.println(ab.getPeopleList());
    }
}
