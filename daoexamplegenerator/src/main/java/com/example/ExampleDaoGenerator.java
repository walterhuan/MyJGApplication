package com.example;


import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class ExampleDaoGenerator {
    public static  void main(String[] args) throws Exception{
        //创建了一个用于添加实体（Entity）的模式（Schema）对象。
        //两个参数分别代表：数据库版本号与自动生成代码的包路径。
        Schema schema = new Schema(2,"com.walter.greendao");
        addNote(schema);
        new DaoGenerator().generateAll(schema,"/Android/AndroidStudioProjects/MyJGApplication/app/src/main/java-gen");


    }

    private static void addNote(Schema schema){
        // 一个实体（类）就关联到数据库中的一张表，此处表名为「Note」（既类名）
        Entity note =schema.addEntity("Note");
        note.addIdProperty();
        note.addStringProperty("text").notNull();
        note.addStringProperty("comment");
        note.addDateProperty("date");



        //第二个版本 新增 一个字段  一个新表
        note.addStringProperty("whatcall");

        Entity  flightInfo= schema.addEntity("FlightInfo");
        flightInfo.addIdProperty();
        flightInfo.addStringProperty("flightNum");





    }

}
