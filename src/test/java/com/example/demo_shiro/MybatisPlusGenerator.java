package com.example.demo_shiro;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.Scanner;

public class MybatisPlusGenerator {
    public static void main(String[] args) {
        //1. 全局配置
        GlobalConfig config = new GlobalConfig();
        // 是否⽀持AR模式
        config.setActiveRecord(true)
                // 作者
                //TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO
                .setAuthor("pangzhihao")
                // ⽣成路径，最好使⽤绝对路径，window路径是不⼀样的

                //TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO
                .setOutputDir("C://Users/pang/Desktop/shiro")
                // ⽂件覆盖
                .setFileOverride(true)
                // 主键策略
                //TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO
                .setIdType(IdType.AUTO)
                .setDateType(DateType.ONLY_DATE)
                // 设置⽣成的service接⼝的名字的⾸字⺟是否为I，默认Service是以I开头的
                .setServiceName("%sService")
                //实体类结尾名称
                .setEntityName("%sDO")
                //⽣成基本的resultMap
                .setBaseResultMap(true)
                //不使⽤AR模式
                .setActiveRecord(false)
                //⽣成基本的SQL⽚段
                .setBaseColumnList(true);
        //2. 数据源配置
        DataSourceConfig dsConfig = new DataSourceConfig();
        // 设置数据库类型
        dsConfig.setDbType(DbType.MYSQL)
                .setDriverName("com.mysql.cj.jdbc.Driver")
                //TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO
                .setUrl("jdbc:mysql://127.0.0.1:3306/xdclass_shiro?serverTimezone=UTC&useUnicode=true&useUnicode=true&characterEncoding=utf-8&useSSL=false")
                .setUsername("root")
                .setPassword("root");
        //3. 策略配置globalConfiguration中
        StrategyConfig stConfig = new StrategyConfig();
        //全局⼤写命名
        stConfig.setCapitalMode(true)
                // 数据库表映射到实体的命名策略
                .setNaming(NamingStrategy.underline_to_camel)
                //使⽤lombok
                .setEntityLombokModel(true)
                //使⽤restcontroller注解
                .setRestControllerStyle(true)

                // ⽣成的表, ⽀持多表⼀起⽣成，以数组形式填写
                //TODO TODO TODO TODO 两个⽅式，直接写，或者使⽤命令⾏输⼊
                .setInclude("permission", "role", "role_permission", "user", "user_role");
        //.setInclude(scanner("表名，多个英⽂逗号分割").split(","));
        //4. 包名策略配置
        PackageConfig pkConfig = new PackageConfig();
        pkConfig.setParent("com.example")
                .setMapper("mapper")
                .setService("service")
                .setController("controller")
                .setEntity("model")
                .setXml("mapper");
        //5. 整合配置
        AutoGenerator ag = new AutoGenerator();
        ag.setGlobalConfig(config)
                .setDataSource(dsConfig)
                .setStrategy(stConfig)
                .setPackageInfo(pkConfig);
        //6. 执⾏操作
        ag.execute();
        System.out.println("======= 相关代码⽣成完毕 ========");
    }

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
//    public static String scanner(String tip) {
//        Scanner scanner = new Scanner(System.in);
//        StringBuilder help = new
//                StringBuilder();
//        help.append("请输⼊" + tip + "：");
//        System.out.println(help.toString());
//        if (scanner.hasNext()) {
//            String ipt = scanner.next();
////            第2集 ⾃动化⽣成代码-加⼊项⽬和⾃动化⼯具思 考 简介：⽣成的代码加⼊项⽬说明和⾃动化⼯具思考 对⽐⽣成的代码进⾏配置
////                    数据库连接和库名称
////            需要⽣成的表
////⽣成的路径
////            拷⻉⾃动⽣成的代码进⼊到项⽬
////            model 类拷⻉
////            mapper 类拷⻉
////            mapper xml脚本拷⻉
////            service和controller不拷⻉
//            if (StringUtils.isNotBlank(ipt)) {
//                return ipt;
//            }
//        }
//        throw new MybatisPlusException("请输⼊正确的" + tip + "！");
//    }
}
